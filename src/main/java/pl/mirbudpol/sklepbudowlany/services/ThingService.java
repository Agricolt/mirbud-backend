package pl.mirbudpol.sklepbudowlany.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mirbudpol.sklepbudowlany.DTO.*;
import pl.mirbudpol.sklepbudowlany.entities.*;
import pl.mirbudpol.sklepbudowlany.exceptions.DuplicatedValueException;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.*;

import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ThingService {

    private final ThingRepository thingRepository;
    private final RatingRepository ratingRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryObjectRepository categoryObjectRepository;
    private final ImagesRepository imagesRepository;
    private final ElectronicalMaterialRepository electronicalMaterialRepository;
    private final CategoryObjectService categoryObjectService;


    public Thing findById(Long id) {
        return thingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Przedmiot o " + id + " nie istnieje"));
    }

    public List<Thing> findAllByNazwaContaining(String name) {
        return thingRepository.findAllByNazwaContaining(name).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono"));
    }

    public List<Thing> findAll() {
        return thingRepository.findAll();
    }

    public Float avgRating(Long id) {

        List<Rating> ratings = ratingRepository.findAllByThingId(id).orElseThrow(() -> new ResourceNotFoundException("Przedmiot nie ma ocen"));

        Float ocena = 0f;
        Integer i = 0;
        if (ratings.size() != 0) {
            for (Rating rating : ratings) {
                ocena += rating.getOcena();
                i++;
            }
            return ocena / i;
        } else
            return ocena;

    }

    public Images getImageByRefAndId(ImageDTO dto, Long id) {
        return imagesRepository.findByRefAndThingId(dto.getRef(), id).orElseThrow(() -> new ResourceNotFoundException("Nie istnieje takie zdjęcie"));
    }

    @Transactional
    public Thing createThing(ThingDTO dto) {
        final Thing thing = new Thing(dto);

        List<Category> kategorie = new ArrayList<>();

        for (String nazwa : dto.getKategoriaId()) {
            Category kategoria = categoryService.findByNazwaKategorii(nazwa);
            kategorie.add(kategoria);
        }

        List<CategoryObject> przedmiotyKategorie = new ArrayList<>();

        for (Category kategoria : kategorie) {
            CategoryObject objektKategorii = new CategoryObject();
            objektKategorii.setThing(thing);
            objektKategorii.setCategory(kategoria);
            przedmiotyKategorie.add(objektKategorii);
        }

        List<Images> zdjecia = new ArrayList<>();
        for (String ref : dto.getZdjecia()) {
            Images zdjecie = new Images();
            zdjecie.setRef(ref);
            zdjecie.setThing(thing);
            zdjecia.add(zdjecie);
        }

        List<ElectronicMaterial> materalyElektroniczne = new ArrayList<>();
        for (String ref : dto.getMaterialyElektroniczne()) {
            ElectronicMaterial electronicMaterial = new ElectronicMaterial();
            electronicMaterial.setRef(ref);
            electronicMaterial.setThing(thing);
            materalyElektroniczne.add(electronicMaterial);
        }


        thing.setCategoryObjects(przedmiotyKategorie);
        thing.setZdjecia(zdjecia);
        thing.setMaterialyElektoniczne(materalyElektroniczne);
        return thingRepository.save(thing);
    }

    @Transactional
    public void updateThing(Long id, ThingDTO dto) {

        Thing thing = this.findById(id);
        thing.update(dto);

    }


    @Transactional
    public void deleteThing(Long id) {
        //thingRepository.deleteById(id);
        Thing thing = this.findById(id);
        thing.setCzyArchiwalny(true);
    }

    public ThingDTOdetails getThing(Long id) {

        ThingDTOdetails dto = new ThingDTOdetails(this.findById(id));

        return dto;
    }

    public ThingDTOdetails2 getThing2(Long id) {

        ThingDTOdetails2 dto = new ThingDTOdetails2(this.findById(id));

        return dto;
    }

    public Integer getQuantity(Long id) {
        return this.findById(id).getIloscNaMagazynie();
    }

    public List<ThingDTOpage1> recommendedThings() {

        Map<Long, Float> srednia = new HashMap<>();

        List<Thing> rekomednowane = thingRepository.findAllByCzyArchiwalnyFalse();
        List<ThingDTOpage1> rekomendowane_zwrot = new ArrayList<>();

        for (Thing thing : rekomednowane) {
            Float avg = 0f;
            int i = 0;
            List<Rating> oceny = ratingRepository.findAllByThingId(thing.getId()).orElse(new ArrayList<>());
            for (Rating rating : oceny) {
                avg += rating.getOcena();
                i++;
            }
            srednia.put(thing.getId(), avg / i);
        }

        LinkedHashMap<Long, Float> sorted_srednia = new LinkedHashMap<>();

        srednia.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sorted_srednia.put(x.getKey(), x.getValue()));


        int liczba_zwracanych_przedmiotow = srednia.size() > 6 ? 6 : srednia.size();
        int j = 0;

        for (Map.Entry<Long, Float> entry : sorted_srednia.entrySet()) {
            if (j == liczba_zwracanych_przedmiotow)
                break;
            else {
                Thing przedmiot = this.findById(entry.getKey());
                if (przedmiot.getRatings().size() == 0)
                    j--;
                else {
                    Float sr = entry.getValue();
                    ThingDTOpage1 przedmiot_rekomendowany = new ThingDTOpage1(przedmiot, sr);
                    rekomendowane_zwrot.add(przedmiot_rekomendowany);
                }
            }
            j++;
        }


        return rekomendowane_zwrot;
    }

    @Transactional
    public void addCategory(ItemCategoryDTO dto, Long id) {

        Thing thing = this.findById(id);
        Category category = categoryService.findByNazwaKategorii(dto.getCategoryName());

        for (CategoryObject object : thing.getCategoryObjects()) {
            if (object.getCategory().getNazwaKategorii().equals(dto.getCategoryName()))
                throw new DuplicatedValueException("Przedmiot posiada już tę kategorię");
        }

        CategoryObject categoryObject = new CategoryObject(category, thing);
        thing.getCategoryObjects().add(categoryObject);

        thingRepository.save(thing);
    }

    @Transactional
    public void deleteCategory(String name, Long id) {

        CategoryObject categoryObject = categoryObjectRepository.findByThing_IdAndCategory_Id(id, categoryService.findByNazwaKategorii(
                name).getId()).orElseThrow(() -> new ResourceNotFoundException("Nieznaleziono takiego przedmiotu lub kategorii"));

        categoryObjectRepository.deleteById(categoryObject.getId());
    }

    @Transactional
    public void addImage(ImageDTO dto, Long id) {

        Thing thing = this.findById(id);

        for (Images images : thing.getZdjecia()) {
            if (images.getRef().equals(dto.getRef()))
                throw new DuplicatedValueException("Przedmiot posiada już to zdjęcie");
        }

        Images image = new Images(dto.getRef(), thing);
        thing.getZdjecia().add(image);

        thingRepository.save(thing);
    }

    @Transactional
    public void deleteImage(String ref, Long id) {

        Images image = imagesRepository.findByRefAndThingId(ref, id).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono takiego przedmiotu lub zdjęcia"));
        imagesRepository.deleteById(image.getId());
    }

    @Transactional
    public void addElectronicalMaterial(ImageDTO dto, Long id) {

        Thing thing = this.findById(id);

        for (ElectronicMaterial electronicMaterial : thing.getMaterialyElektoniczne()) {
            if (electronicMaterial.getRef().equals(dto.getRef()))
                throw new DuplicatedValueException("Przedmiot posiada już ten materiał elektroniczny");
        }

        ElectronicMaterial electronicMaterial = new ElectronicMaterial(dto.getRef(), thing);
        thing.getMaterialyElektoniczne().add(electronicMaterial);

        thingRepository.save(thing);
    }

    @Transactional
    public void deleteElectronicalMaterial(String ref, Long id) {

        ElectronicMaterial electronicMaterial = electronicalMaterialRepository.findByRefAndThingId(ref, id).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono takiego przedmiotu lub materiału elektronicznego"));
        electronicalMaterialRepository.deleteById(electronicMaterial.getId());
    }

    public List<ThingDTOpage1> getItemsByName(String name) {

        List<Thing> things;

        if (name.equals("empty") || name.trim().isEmpty() || name.equals(null)) {
            things = thingRepository.findAll();
        } else {
            things = this.findAllByNazwaContaining(name);
        }
        List<ThingDTOpage1> dtos = new ArrayList<>();

        for (Thing thing : things) {

            ThingDTOpage1 dto = new ThingDTOpage1(thing, this.avgRating(thing.getId()));
            dtos.add(dto);
        }
        return dtos;

    }

    public List<ThingDTOpage1> getItemsByCategories(List<String> categories) {

        Integer size = categories.size();
        List<ThingDTOpage1> items = new ArrayList<>();

        if (size == 0)
            return items;

        List<List<CategoryObject>> itemsInCategory = new ArrayList<>();

        for (String name : categories) {

            List<CategoryObject> list = categoryObjectService.findAllByCategory_Id(categoryService.findByNazwaKategorii(name).getId());
            itemsInCategory.add(list);
        }

        List<Long> itemsId = new ArrayList<>();

        for (List<CategoryObject> list : itemsInCategory) {

            for (CategoryObject object : list) {
                itemsId.add(object.getThing().getId());
            }
        }

        Integer index = 0;
        Long currentId;
        Collections.sort(itemsId);

        if (itemsId.size() == 0)
            return items;

        currentId = itemsId.get(0);


        for (Long id : itemsId) {

            if (id.equals(currentId))
                index++;
            else {
                currentId = id;
                index = 1;
            }

            if (index.equals(size)) {
                if(this.findById(currentId).getCzyArchiwalny().equals(false))
                items.add(new ThingDTOpage1(this.findById(currentId), this.avgRating(currentId)));
            }


        }

        return items;
    }

    public List<ThingDTOpage1> getActiveItemsByCategory(List<String> categories) {

        Integer size = categories.size();
        List<ThingDTOpage1> items = new ArrayList<>();

        if (size == 0)
            return items;

        List<List<CategoryObject>> itemsInCategory = new ArrayList<>();

        for (String name : categories) {

            List<CategoryObject> list = categoryObjectService.findAllByCategory_IdAndThing_CzyArchiwalny(categoryService.findByNazwaKategorii(name).getId(), false);
            itemsInCategory.add(list);
        }

        List<Long> itemsId = new ArrayList<>();

        for (List<CategoryObject> list : itemsInCategory) {

            for (CategoryObject object : list) {
                itemsId.add(object.getThing().getId());
            }
        }

        Integer index = 0;
        Long currentId;
        Collections.sort(itemsId);

        if (itemsId.size() == 0)
            return items;

        currentId = itemsId.get(0);


        for (Long id : itemsId) {

            if (id.equals(currentId))
                index++;
            else {
                currentId = id;
                index = 1;
            }

            if (index.equals(size))
                items.add(new ThingDTOpage1(this.findById(currentId), this.avgRating(currentId)));


        }

        return items;
    }

    public List<ThingDTOpage1> getItemsByAllCategories(List<String> categories) {
        Integer size = categories.size();
        List<ThingDTOpage1> items = new ArrayList<>();


        List<CategoryObject> list = new ArrayList<>();
        List<CategoryObject> list2 = new ArrayList<>();

        if (size == 0) {
            List<Category> categoriesNames = categoryRepository.findAll();
            for(Category category: categoriesNames){
                categories.add(category.getNazwaKategorii());
            }
        }

        for (String name : categories) {
            list2 = categoryObjectService.findAllByCategory_Id(categoryService.findByNazwaKategorii(name).getId());
            list.addAll(list2);
        }

        for (CategoryObject categoryObject : list) {
            ThingDTOpage1 thingDTOpage1 = new ThingDTOpage1();
            thingDTOpage1.setId(categoryObject.getThing().getId());
            items.add(thingDTOpage1);
        }

        Set<ThingDTOpage1> set = new HashSet<>(items);
        List<ThingDTOpage1> items2  = new ArrayList<>(set);

        return items2;
    }


    public Long getImageId(ImageDTO dto, Long id) {
        return this.getImageByRefAndId(dto, id).getId();
    }

    @Transactional
    public void deleteImageById(Long id) {
        imagesRepository.deleteById(id);
    }

    @Transactional
    public void deleteMaterialById(Long id) {
        electronicalMaterialRepository.deleteById(id);
    }

}


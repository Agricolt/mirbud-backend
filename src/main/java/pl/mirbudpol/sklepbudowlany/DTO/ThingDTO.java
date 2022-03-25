package pl.mirbudpol.sklepbudowlany.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.CategoryObject;
import pl.mirbudpol.sklepbudowlany.entities.ElectronicMaterial;
import pl.mirbudpol.sklepbudowlany.entities.Images;
import pl.mirbudpol.sklepbudowlany.entities.Thing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThingDTO {

    private Long id;

    @NotBlank(message = "Nieprawidłowa nazwa")
    private String nazwa;
    @NotBlank(message = "Nieprawidłowy opis")
    private String opis;
    @NotNull(message = "Nieprawidłowa cena zakupu")
    private Float cenaZakupu;
    @NotNull(message = "Nieprawidłowa ilość na magazynie")
    private Integer iloscNaMagazynie;
    @NotNull(message = "Nieprawidłowa cena sprzedaży")
    private Float cenaSprzedazy;
    @NotNull(message = "Nieprawidłowa czy archiwalny")
    private Boolean czyArchiwalny;

    private List<String> kategoriaId = new ArrayList<>();
    private List<String> zdjecia = new ArrayList<>();
    private List<String> materialyElektroniczne = new ArrayList<>();



    public ThingDTO(String nazwa, String opis, Float cenaZakupu, Integer iloscNaMagazynie,
                    Float cenaSprzedazy, Boolean czyArchiwalny, List<String> kategoriaId) {
        this
                (null, nazwa, opis, cenaZakupu, iloscNaMagazynie, cenaSprzedazy, czyArchiwalny, kategoriaId, null, null);
    }

    public ThingDTO(Thing thing) {
        this.id = thing.getId();
        this.nazwa = thing.getNazwa();
        this.opis = thing.getOpis();
        this.cenaZakupu = thing.getCenaZakupu();
        this.cenaSprzedazy = thing.getCenaSprzedazy();
        this.czyArchiwalny = thing.getCzyArchiwalny();
        this.iloscNaMagazynie = thing.getIloscNaMagazynie();

        for (CategoryObject categoryObject : thing.getCategoryObjects()) {
            this.kategoriaId.add(categoryObject.getCategory().getNazwaKategorii());
        }

        for (Images images : thing.getZdjecia()) {
            this.zdjecia.add(images.getRef());
        }

        for (ElectronicMaterial electronicMaterial : thing.getMaterialyElektoniczne()) {
            this.materialyElektroniczne.add(electronicMaterial.getRef());
        }
    }


}

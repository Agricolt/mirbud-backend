package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.ElectronicMaterial;
import pl.mirbudpol.sklepbudowlany.entities.Images;
import pl.mirbudpol.sklepbudowlany.entities.Rating;
import pl.mirbudpol.sklepbudowlany.entities.Thing;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThingDTOdetails2 extends ThingDTO {

    @NotNull(message = "Nieprawidłowa ocena")
    private Float ocena;

    @NotNull
    List<RatingDTO> ratings = new ArrayList<>();

    @NotNull
    List<ImageDTO> imagesDTO = new ArrayList<>();

    @NotNull
    List<ImageDTO> materialsDTO = new ArrayList<>();


    public ThingDTOdetails2(String nazwa, String opis, Float cenaZakupu, Integer iloscNaMagazynie,
                           Float cenaSprzedazy, Boolean czyArchiwalny, List<String> kategoriaId) {
        super(null, nazwa, opis, cenaZakupu, iloscNaMagazynie, cenaSprzedazy, czyArchiwalny, kategoriaId, null, null);

    }

    public ThingDTOdetails2(Thing thing) {
        super(thing);

        Float ocena = 0f;
        Integer i = 0;
        for (Rating rating : thing.getRatings()) {
            RatingDTOdetails ratingDTOdetails = new RatingDTOdetails(rating);
            this.ratings.add(ratingDTOdetails);
            ocena += rating.getOcena();
            i++;
        }
        this.ocena = ocena / i;


        for(Images image: thing.getZdjecia()){
            ImageDTO dto = new ImageDTO(image);
            this.imagesDTO.add(dto);
        }

        for(ElectronicMaterial material: thing.getMaterialyElektoniczne()){
            ImageDTO dto = new ImageDTO(material);
            this.materialsDTO.add(dto);
        }

    }



}

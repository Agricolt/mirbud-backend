package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.Rating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    private Long id;

    @NotNull(message = "Brak oceny")
    private Integer ocena;

    @NotBlank
    @Size(min = 2, max = 20,message = "Błędny komentarz")
    private String komentarz;

    @NotNull(message = "Brak przedmiotu")
    private Long thingId;
    @NotNull(message = "Brak klienta")
    private Long klientId;

    public RatingDTO(Integer ocena, String komentarz, Long thingId, Long klientId) {
        this(null, ocena, komentarz, thingId, klientId);
    }

    public RatingDTO(Rating rating) {
        this.id = rating.getId();
        this.ocena = rating.getOcena();
        this.komentarz = rating.getKomentarz();
        this.thingId = rating.getThing().getId();
        this.klientId = rating.getClient().getId();
    }

}

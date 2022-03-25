package pl.mirbudpol.sklepbudowlany.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.Rating;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTOdetails extends RatingDTO {

    @NotNull(message = "Brak loginu")
    String login;

    public RatingDTOdetails(Rating rating){
        super(rating);
        this.login = rating.getClient().getImie();
    }
}

package pl.mirbudpol.sklepbudowlany.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.enums.Country;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdressDTO {

    private Long id;

    @NotNull
    private Country kraj;

    @NotBlank
    @Size(min = 6,max = 6,message = "Nieprawidłowy kod pocztowy")
    private String kodPocztowy;

    @NotBlank(message = "Nieprawidłowa miejscowść")
    private String miejscowosc;

    @NotBlank(message = "Nieprawidłowa ulica oraz numer domu")
    private String ulicaNrDomu;


    public AdressDTO(Country kraj,String kodPocztowy,String miejscowosc,String ulicaNrDomu){
        this(null,kraj,kodPocztowy,miejscowosc,ulicaNrDomu);
    }

}

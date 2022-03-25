package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.ElectronicMaterial;
import pl.mirbudpol.sklepbudowlany.entities.Images;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    Long id;

    @NotBlank(message = "Nieprawidłowy link do zdjęcia")
    String ref;

    public ImageDTO(String ref) {
        this(null, ref);
    }

    public ImageDTO(Images image) {
        this.id = image.getId();
        this.ref = image.getRef();
    }

    public ImageDTO(ElectronicMaterial material) {
        this.id = material.getId();
        this.ref = material.getRef();
    }

}

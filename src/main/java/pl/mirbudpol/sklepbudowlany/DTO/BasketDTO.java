package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {

    @NotNull
    Long clientId;
    @NotNull
    List<Long> itemsId = new ArrayList();
    @NotNull
    List<Integer> itemsQuantity = new ArrayList<>();

}

package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.entities.ItemsOrders;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsOrdersDTO {

    @NotNull(message = "Nieprawidłowa ilość przedmiotu")
    private Integer iloscPrzedmiotu;
    @NotNull(message = "Nieprawidłowa cena sprzedaży")
    private Float cenaSprzedazy;
    @NotNull(message = "Brak zamówienia")
    private Long orderId;
    @NotNull(message = "Brak przedmiotu")
    ThingDTO item;


    public ItemsOrdersDTO(ItemsOrders itemsOrders) {

        this.iloscPrzedmiotu = itemsOrders.getIloscPrzedmiotu();
        this.cenaSprzedazy = itemsOrders.getCenaSprzedazy();
        this.orderId = itemsOrders.getZamowienie().getId();
        this.item = new ThingDTO(itemsOrders.getPrzedmiot());


    }

}

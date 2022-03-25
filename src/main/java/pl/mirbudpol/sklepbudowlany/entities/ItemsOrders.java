package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "przedmioty_zamowienia")
public class ItemsOrders extends ID {

    @Column(nullable = false,name = "ilosc_przedmiotu")
    private Integer iloscPrzedmiotu;

    @Column(nullable = false,name = "cena_sprzedazy")
    private Float cenaSprzedazy;


    @ManyToOne
    @JoinColumn(name = "zamowienie_id",nullable = false)
    private Order zamowienie;


    @ManyToOne
    @JoinColumn(name = "przedmiot_id",nullable = false)
    private Thing przedmiot;



}

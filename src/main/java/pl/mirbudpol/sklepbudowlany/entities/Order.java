package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "zamowienia")
public class Order extends ID {

    @Column(nullable = false,name = "data_zamowienia")
    private String dataZamowienia;

    @Column(nullable = false,name = "czy_zrealizowane")
    private Boolean czyZrealizowane;

    @Column(nullable = false,name = "wartosc_zamowienia")
    private Float wartoscZamowienia;

    @ManyToOne
    @JoinColumn(name = "klient_id",nullable = false)
    private Client klient;

    @OneToMany(mappedBy = "zamowienie",cascade = CascadeType.ALL)
    private List<ItemsOrders> przedmiotyZamowienia = new ArrayList<>();



}

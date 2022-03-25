package pl.mirbudpol.sklepbudowlany.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.DTO.ThingDTO;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "przedmiot")
public class Thing extends ID {

    @Column(nullable = false)
    private String nazwa;

    @Column(nullable = false)
    private String opis;

    @Column(nullable = false, name = "cena_zakupu")
    private Float cenaZakupu;

    @Column(nullable = false, name = "ilosc_na_magazynie")
    private Integer iloscNaMagazynie;

    @Column(nullable = false, name = "cena_sprzedazy")
    private Float cenaSprzedazy;


    @Column(nullable = false)
    private Boolean czyArchiwalny;


    @OneToMany(mappedBy = "przedmiot")
    private List<ItemsOrders> przedmiotyZamowienia = new ArrayList<>();

    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Images> zdjecia = new ArrayList<>();

    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<ElectronicMaterial> materialyElektoniczne = new ArrayList<>();


    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CategoryObject> categoryObjects = new ArrayList<>();

    public Thing(ThingDTO dto) {
        this.nazwa = dto.getNazwa();
        this.opis = dto.getOpis();
        this.cenaZakupu = dto.getCenaZakupu();
        this.iloscNaMagazynie = dto.getIloscNaMagazynie();
        this.cenaSprzedazy = dto.getCenaSprzedazy();
        this.czyArchiwalny = dto.getCzyArchiwalny();
    }

    public void update(ThingDTO dto) {
        this.nazwa = dto.getNazwa();
        this.opis = dto.getOpis();
        this.iloscNaMagazynie = dto.getIloscNaMagazynie();
        this.cenaSprzedazy = dto.getCenaSprzedazy();
        this.czyArchiwalny = dto.getCzyArchiwalny();

    }
}


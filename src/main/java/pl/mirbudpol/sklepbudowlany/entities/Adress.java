package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.DTO.RegisteredClientDTO;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;
import pl.mirbudpol.sklepbudowlany.enums.Country;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "adresy_zamieszkania")
public class Adress extends ID {


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country kraj = Country.POLSKA;
    @Column(nullable = false, name = "kod_pocztowy")
    private String kodPocztowy;
    @Column(nullable = false)
    private String miejscowosc;
    @Column(nullable = false, name = "ulica_nr_domu")
    private String ulicaNrDomu;

    @OneToOne
    @JoinColumn(name = "klient_id", referencedColumnName = "id")
    private Client klient;

    public Adress(RegisteredClientDTO dto) {
        this.kodPocztowy = dto.getKodPocztowy();
        this.miejscowosc = dto.getMiejscowosc();
        this.ulicaNrDomu = dto.getUlicaNrDomu();
    }

}

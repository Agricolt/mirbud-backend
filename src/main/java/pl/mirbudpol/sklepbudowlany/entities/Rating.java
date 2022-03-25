package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "oceny")
public class Rating extends ID{

    @Column(nullable = false)
    private Integer ocena;

    @Column(nullable = false)
    private String komentarz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "przedmiot_id", nullable = false)
    private Thing thing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "klient_id", nullable = false)
    private Client client;

}

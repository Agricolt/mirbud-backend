package pl.mirbudpol.sklepbudowlany.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "materialyElektroniczne")
public class ElectronicMaterial extends ID {

    @Column(nullable = false)
    private String ref;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "przedmioty_id",nullable = false)
    private Thing thing;
}

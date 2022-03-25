package pl.mirbudpol.sklepbudowlany.entities;

import lombok.*;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "kategoria")
public class Category extends ID{

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CategoryObject> categoryObjects  = new ArrayList<>();

    @Column(nullable = false,name = "nazwa_kategorii")
    private String nazwaKategorii;
}

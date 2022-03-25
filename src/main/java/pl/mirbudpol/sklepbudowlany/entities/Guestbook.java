package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.Setter;
import pl.mirbudpol.sklepbudowlany.additionalClasses.ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;

@Setter
@Getter
@Entity(name = "ksiegaGosci")
public class Guestbook extends ID {

@Column(nullable = false)
private String wpis;

@Column(nullable = false)
private SimpleDateFormat data = new SimpleDateFormat("MM-dd-yyyy");

}

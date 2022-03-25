package pl.mirbudpol.sklepbudowlany.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.security.Timestamp;

@Getter
@Setter
@Entity(name = "persistent_logins")
public class PersistentLogins {

    @Id
    private String series;

    private String login;

    private String token;

    private Timestamp lastUsed;

}

package pl.mirbudpol.sklepbudowlany.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ThingReportDTO {
    Long id;
    public String nazwa;
    public Integer ilosc;
    public Float zysk;

    public ThingReportDTO(Long id, String nazwa, Integer ilosc, Float zysk){
        this.id = id;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.zysk = zysk;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ThingReportDTO)) {
            return false;
        }

        ThingReportDTO e = (ThingReportDTO) obj;

        return id.equals(e.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

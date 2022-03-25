package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mirbudpol.sklepbudowlany.entities.ElectronicMaterial;

import java.util.Optional;

public interface ElectronicalMaterialRepository extends JpaRepository<ElectronicMaterial,Long> {

    Optional<ElectronicMaterial> findByRefAndThingId(String ref, Long id);

}

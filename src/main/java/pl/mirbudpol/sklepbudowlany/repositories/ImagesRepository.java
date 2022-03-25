package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mirbudpol.sklepbudowlany.entities.Images;

import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images,Long> {

    Optional<Images> findByRefAndThingId(String ref,Long id);

}

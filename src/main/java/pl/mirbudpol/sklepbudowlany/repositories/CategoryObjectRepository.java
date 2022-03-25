package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mirbudpol.sklepbudowlany.entities.CategoryObject;

import java.util.List;
import java.util.Optional;

public interface CategoryObjectRepository extends JpaRepository<CategoryObject, Long> {

    Optional<CategoryObject> findByThing_IdAndCategory_Id(Long ThingId, Long CategoryId);
    Optional<List<CategoryObject>> findAllByCategory_Id(Long id);
    Optional<List<CategoryObject>> findAllByCategory_IdAndThing_CzyArchiwalny(Long id, Boolean archive);
}

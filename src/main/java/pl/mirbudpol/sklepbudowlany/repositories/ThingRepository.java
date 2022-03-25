package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.mirbudpol.sklepbudowlany.entities.Thing;

import java.util.List;
import java.util.Optional;

public interface ThingRepository extends CrudRepository<Thing, Long>, PagingAndSortingRepository<Thing, Long> {

    Optional<Thing> findById(Long id);
    List <Thing> findAll();
    List <Thing> findAllByCzyArchiwalnyFalse();
    Optional<List<Thing>> findAllByNazwaContaining(String name);
    Optional<List<Thing>> findAllByNazwaContainingAndCzyArchiwalnyFalse(String name);
}

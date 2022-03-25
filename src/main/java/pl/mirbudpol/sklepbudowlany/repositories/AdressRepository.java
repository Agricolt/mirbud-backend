package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.mirbudpol.sklepbudowlany.entities.Adress;

import java.util.Optional;

public interface AdressRepository extends CrudRepository<Adress, Long>, PagingAndSortingRepository<Adress, Long> {

    Optional<Adress> findById(Long id);

}

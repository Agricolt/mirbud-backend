package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.mirbudpol.sklepbudowlany.entities.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long>, PagingAndSortingRepository<Rating, Long> {

    Optional<Rating> findById(Long id);

    Optional<List<Rating>> findAllByThingId(Long id);

}
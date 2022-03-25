package pl.mirbudpol.sklepbudowlany.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mirbudpol.sklepbudowlany.entities.CategoryObject;
import pl.mirbudpol.sklepbudowlany.entities.ItemsOrders;
import pl.mirbudpol.sklepbudowlany.entities.Thing;

import java.util.List;
import java.util.Optional;

public interface ItemsOrdersRepository extends CrudRepository<ItemsOrders, Long> {

    Optional<List<ItemsOrders>> findAllByZamowienie_Id(Long id);
    Optional<ItemsOrders> findById(Long id);
}

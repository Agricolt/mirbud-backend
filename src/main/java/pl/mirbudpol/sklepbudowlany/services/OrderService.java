package pl.mirbudpol.sklepbudowlany.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mirbudpol.sklepbudowlany.DTO.*;
import pl.mirbudpol.sklepbudowlany.entities.Client;
import pl.mirbudpol.sklepbudowlany.entities.ItemsOrders;
import pl.mirbudpol.sklepbudowlany.entities.Order;
import pl.mirbudpol.sklepbudowlany.entities.Thing;
import pl.mirbudpol.sklepbudowlany.exceptions.LackOfResources;
import pl.mirbudpol.sklepbudowlany.exceptions.NoPermissions;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.ItemsOrdersRepository;
import pl.mirbudpol.sklepbudowlany.repositories.OrderRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final ClientService clientService;
    private final ThingService thingService;
    private final OrderRepository orderRepository;

    public List<Order> findAllByKlient_Id(Long id) {
        return orderRepository.findAllByKlient_Id(id).orElseThrow(() -> new ResourceNotFoundException("Brak wcześniejszych zamówień"));
    }

    @Transactional
    public void addOrder(BasketDTO dto) {

        Client client = clientService.findById(dto.getClientId());

        if (client.getCzyAktywne() == false || client.getTypUzytkownika() == 1 || client.getTypUzytkownika() == 2)
            throw new NoPermissions("Brak uprawnień");

        Order order = new Order();
        order.setKlient(client);
        order.setCzyZrealizowane(false);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dataZamowienia = new SimpleDateFormat("MM-dd-yyyy");
        order.setDataZamowienia(dataZamowienia.format(date));

        client.getZamowienia().add(order);

        Integer i = 0;
        Float value = 0f;

        List<Thing> thingList = new ArrayList<>();

        for (Long id : dto.getItemsId()) {
            thingList.add(thingService.findById(id));
        }

        for (Thing thing : thingList) {

            ItemsOrders itemsOrders = new ItemsOrders();
            if (thing.getIloscNaMagazynie() < dto.getItemsQuantity().get(i))
                throw new LackOfResources("Brak przedmiotu " + thing.getNazwa() + " na magazynie");
            else {
                itemsOrders.setIloscPrzedmiotu(dto.getItemsQuantity().get(i));
                thing.setIloscNaMagazynie(thing.getIloscNaMagazynie() - dto.getItemsQuantity().get(i));
                if(thing.getIloscNaMagazynie() == 0)
                    thing.setCzyArchiwalny(true);
            }
            itemsOrders.setCenaSprzedazy(thing.getCenaSprzedazy());
            itemsOrders.setPrzedmiot(thing);
            itemsOrders.setZamowienie(order);

            order.getPrzedmiotyZamowienia().add(itemsOrders);

            value += itemsOrders.getCenaSprzedazy() * itemsOrders.getIloscPrzedmiotu();
        }

        order.setWartoscZamowienia(value);
        orderRepository.save(order);


    }


    public List<OrderDTO> getOrderHistory(Long id) {

        List<OrderDTO> orderDTOS = new ArrayList<>();


        for (Order order : this.findAllByKlient_Id(id)) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;

    }
}

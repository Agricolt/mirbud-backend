package pl.mirbudpol.sklepbudowlany.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mirbudpol.sklepbudowlany.DTO.ReportFirstDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RaportRequestDTO;
import pl.mirbudpol.sklepbudowlany.DTO.ThingDTOpage1;
import pl.mirbudpol.sklepbudowlany.DTO.ThingReportDTO;
import pl.mirbudpol.sklepbudowlany.entities.ItemsOrders;
import pl.mirbudpol.sklepbudowlany.entities.Order;
import pl.mirbudpol.sklepbudowlany.entities.Thing;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.ItemsOrdersRepository;
import pl.mirbudpol.sklepbudowlany.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReportsService {

    private final ThingService thingService;
    private final OrderRepository orderRepository;
    private final ItemsOrdersRepository itemsOrdersRepository;

    public List<Order> findAllByDataZamowieniaBetween(String startDate, String endDate) {
        return orderRepository.findAllByDataZamowieniaBetween(startDate, endDate).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono"));
    }

    public List<ItemsOrders> findAllByZamowienie_Id(Long id) {
        return itemsOrdersRepository.findAllByZamowienie_Id(id).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono"));
    }

    public ItemsOrders findById(Long id) {
        return itemsOrdersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Przedmiot o " + id + " nie istnieje"));
    }

    @Transactional
    public RaportRequestDTO getReportFirst(ReportFirstDTO dto) {
        RaportRequestDTO raportRequestDTO = new RaportRequestDTO();
        List<Order> orderList =  findAllByDataZamowieniaBetween(dto.getStartDate(), dto.getEndDate());
        List<ItemsOrders> itemsOrders = new ArrayList<>();
        List<Thing> things = new ArrayList<>();
        Float profit = 0f;

        for(Order order: orderList){
            List<ItemsOrders> temp = findAllByZamowienie_Id(order.getId());
            itemsOrders.addAll(temp);
        }

        List<ThingReportDTO> thingsRaport = new ArrayList<>();
        List<ThingReportDTO> thingsRaport2 = new ArrayList<>();
        List<ThingReportDTO> thingsRaport3 = new ArrayList<>();
        List<ThingReportDTO> thingsRaport4 = new ArrayList<>();
        List<ThingDTOpage1> thingDTOpage1List = thingService.getItemsByAllCategories(dto.getCategories());

        for(ItemsOrders itemOrder: itemsOrders){
            Thing thing = thingService.findById(itemOrder.getPrzedmiot().getId());
            things.add(thing);
            ThingReportDTO thingReportDTO = new ThingReportDTO(thing.getId(), thing.getNazwa(), itemOrder.getIloscPrzedmiotu(), itemOrder.getCenaSprzedazy() - thing.getCenaZakupu());
            ThingReportDTO thingReportDTO2 = new ThingReportDTO(thing.getId(), thing.getNazwa(), itemOrder.getIloscPrzedmiotu(), itemOrder.getCenaSprzedazy() - thing.getCenaZakupu());
            thingsRaport2.add(thingReportDTO);
            thingsRaport4.add(thingReportDTO2);
        }

        Set<ThingReportDTO> set = new HashSet<>(thingsRaport2);
        thingsRaport3 = new ArrayList<>(set);

        for(ThingReportDTO thingReportDTO: thingsRaport3){
            thingReportDTO.ilosc = 0;
            thingReportDTO.zysk = 0f;
        }

        for(ThingReportDTO thingReportDTO2: thingsRaport4){
            for(ThingReportDTO thingReportDTO3: thingsRaport3){
                if(thingReportDTO2.equals(thingReportDTO3)){
                    thingReportDTO3.ilosc += thingReportDTO2.ilosc;
                    thingReportDTO3.zysk += thingReportDTO2.zysk * thingReportDTO2.ilosc;
                }
            }
        }
        for(ThingDTOpage1 thingDTOpage1: thingDTOpage1List){
            System.out.println(thingDTOpage1.getId());
            for(ThingReportDTO thingReportDTO: thingsRaport3) {
                if(thingReportDTO.getId().equals(thingDTOpage1.getId())){
                    thingsRaport.add(thingReportDTO);
                    profit += thingReportDTO.getZysk();
                }
            }
        }

        raportRequestDTO.setProfit(profit);
        raportRequestDTO.setThings(thingsRaport);
        return raportRequestDTO;
    }
}

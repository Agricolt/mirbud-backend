package pl.mirbudpol.sklepbudowlany.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mirbudpol.sklepbudowlany.DTO.BasketDTO;
import pl.mirbudpol.sklepbudowlany.DTO.OrderDTO;
import pl.mirbudpol.sklepbudowlany.services.OrderService;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@RequestBody BasketDTO dto) {
        orderService.addOrder(dto);
    }

    @GetMapping("/{ClientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getOrderHistory(@PathVariable Long ClientId) {
        return orderService.getOrderHistory(ClientId);
    }
}

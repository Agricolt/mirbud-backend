package pl.mirbudpol.sklepbudowlany.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mirbudpol.sklepbudowlany.DTO.EmailDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RatingDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RegisteredClientDTO;
import pl.mirbudpol.sklepbudowlany.services.ClientService;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {

    private final ClientService clientService;

    @ApiOperation("Endpoint do zarejestrowania zwykłego użytkownika")
    @PostMapping(path = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerClient(@Validated @RequestBody RegisteredClientDTO dto) {
        clientService.createRegisteredClient(dto, 3);
    }

    @ApiOperation("Endpoint do zarejestrowania menadżera")
    @PostMapping(path = "/registration/manager")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerManager(@Validated @RequestBody RegisteredClientDTO dto) {
        clientService.createManager(dto);
    }

    @ApiOperation("Endpoint do zarejestrowania admina")
    @PostMapping(path = "/registration/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@Validated @RequestBody RegisteredClientDTO dto) {
        clientService.createAdmin(dto);
    }

    @ApiOperation("Zwraca wszystkie oceny wystawione przez danego użytkownika")
    @GetMapping(path = "/ratings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingDTO> getClientRatings(@PathVariable Long id) {
        return clientService.getClientRatings(id);
    }

    @ApiOperation("Zwraca liste menadżerów")
    @GetMapping(path = "/managers")
    @ResponseStatus(HttpStatus.OK)
    public List<RegisteredClientDTO> getManagers() {
        return clientService.getManagers();
    }

    @ApiOperation("Usuwa menadżera o podanym id")
    @DeleteMapping(path = "/managers/{menagoId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteManager(@PathVariable Long menagoId) {
        clientService.deleteManager(menagoId);
    }

    @ApiOperation("Endpoint pobrania roli użytkownika na podstawie jego emailu")
    @PostMapping(path = "/getRole")
    @ResponseStatus(HttpStatus.CREATED)
    public int getRole(@Validated @RequestBody EmailDTO dto) {
        return clientService.getRole(dto);
    }

    @ApiOperation("Endpoint pobrania id użytkownika na podstawie jego emailu")
    @PostMapping(path = "/getIdByEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public Long getIdByEmail(@Validated @RequestBody EmailDTO dto) {return clientService.getIdByEmail(dto);}

    @GetMapping(path = "/tele/{number}")
    public Boolean isValid(@PathVariable String number){
        return clientService.validPhoneNumber(number);
    }

}

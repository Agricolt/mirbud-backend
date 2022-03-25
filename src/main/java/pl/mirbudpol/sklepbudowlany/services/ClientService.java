package pl.mirbudpol.sklepbudowlany.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mirbudpol.sklepbudowlany.DTO.EmailDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RatingDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RegisteredClientDTO;
import pl.mirbudpol.sklepbudowlany.entities.Adress;
import pl.mirbudpol.sklepbudowlany.entities.Client;
import pl.mirbudpol.sklepbudowlany.entities.Rating;
import pl.mirbudpol.sklepbudowlany.exceptions.NoPermissions;
import pl.mirbudpol.sklepbudowlany.exceptions.NotValidPhoneNumber;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final static String regex = "(?:(?:(?:\\+|00)?48)|(?:\\(\\+?48\\)))?(?:1[2-8]|2[2-69]|3[2-49]|4[1-8]|5[0-9]|6[0-35-9]|[7-8][1-9]|9[145])\\d{7}";
    private final static Pattern pattern = Pattern.compile(regex);

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Klient o id " + id + " nie istnieje"));
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Klient o id " + email + " nie istnieje"));
    }

    public List<Client> findAllByTypUzytkownika(Integer role) {
        return clientRepository.findAllByTypUzytkownika(role).orElseThrow(() -> new ResourceNotFoundException("Brak menadżerów"));
    }

    public void permissions(Client client) {

        if (client.getTypUzytkownika() != 1 || client.getTypUzytkownika() != 2 && client.getCzyAktywne() == false)
            throw new NoPermissions("Brak uprawnień");

    }


    public Boolean validPhoneNumber(String phoneNumber) {

        final Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();

    }

    @Transactional
    public Client createRegisteredClient(RegisteredClientDTO dto, Integer role) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getHaslo());
        dto.setHaslo(encodedPassword);

        if (validPhoneNumber(dto.getNrTelefonu())) {
            Client klient = new Client(dto, role);

            klient.setHaslo(encodedPassword);

            Adress adres = new Adress(dto);
            adres.setKlient(klient);

            klient.setAdres(adres);

            return clientRepository.save(klient);
        } else
            throw new NotValidPhoneNumber("Nieprawidłowy numer telefonu");
    }

    @Transactional
    public Client createManager(RegisteredClientDTO dto) {

      //  Client client = this.findById(id);
       // this.permissions(client);

        return this.createRegisteredClient(dto, 2);

    }

    @Transactional
    public Client createAdmin(RegisteredClientDTO dto) {

       // Client client = this.findById(id);

       // if (client.getTypUzytkownika() == 1 && client.getCzyAktywne() == true)
            return this.createRegisteredClient(dto, 1);
       // else
          //  throw (new ResourceNotFoundException("Brak uprawnień"));
    }

    @Transactional
    public void deleteManager(Long menagoId) {

        //Client client = this.findById(userId);
        //permissions(client);

        clientRepository.deleteById(menagoId);

    }

    public List<RegisteredClientDTO> getManagers() {

        //Client client = this.findById(id);
        List<RegisteredClientDTO> registeredClientDTOS = new ArrayList<>();
       // this.permissions(client);

        List<Client> clients = this.findAllByTypUzytkownika(2);

        for (Client object : clients) {

            RegisteredClientDTO dto = new RegisteredClientDTO(object);
            registeredClientDTOS.add(dto);
        }
        return registeredClientDTOS;
    }



    public List<RatingDTO> getClientRatings(Long id) {

        Client client = this.findById(id);
        List<RatingDTO> ratingsDTO = new ArrayList<>();

        for (Rating rating : client.getOceny()) {
            RatingDTO ratingDTO = new RatingDTO(rating.getId(), rating.getOcena(), rating.getKomentarz(), rating.getThing().getId(), rating.getClient().getId());
            ratingsDTO.add(ratingDTO);
        }

        return ratingsDTO;
    }

    public int getRole(EmailDTO dto){
        return this.findByEmail(dto.getEmail()).getTypUzytkownika();
    }

    public Long getIdByEmail(EmailDTO dto){
        return this.findByEmail(dto.getEmail()).getId();
    }

}

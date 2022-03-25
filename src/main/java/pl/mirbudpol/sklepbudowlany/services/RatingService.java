package pl.mirbudpol.sklepbudowlany.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mirbudpol.sklepbudowlany.DTO.RatingDTO;
import pl.mirbudpol.sklepbudowlany.entities.Client;
import pl.mirbudpol.sklepbudowlany.entities.Rating;
import pl.mirbudpol.sklepbudowlany.entities.Thing;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.ClientRepository;
import pl.mirbudpol.sklepbudowlany.repositories.RatingRepository;
import pl.mirbudpol.sklepbudowlany.repositories.ThingRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ClientRepository clientRepository;
    private final ThingRepository thingRepository;
    private final ThingService thingService;
    private final ClientService clientService;

    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating o id " + id + " nie istnieje"));
    }

    public List<Rating> findAllByThingId(Long id) {
        return ratingRepository.findAllByThingId(id).orElseThrow(() -> new ResourceNotFoundException("Przedmiot nie posiada ocen"));
    }


    @Transactional
    public Rating createRating(RatingDTO dto) {
        final Rating rating = new Rating();
        rating.setOcena(dto.getOcena());
        rating.setKomentarz(dto.getKomentarz());

        Thing thing = thingService.findById(dto.getThingId());
        rating.setThing(thing);

        Client client = clientService.findById(dto.getKlientId());
        rating.setClient(client);

        return ratingRepository.save(rating);
    }

}
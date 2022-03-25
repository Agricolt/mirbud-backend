package pl.mirbudpol.sklepbudowlany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.mirbudpol.sklepbudowlany.DTO.CustomUserDetailsDTO;
import pl.mirbudpol.sklepbudowlany.entities.Client;
import pl.mirbudpol.sklepbudowlany.exceptions.ResourceNotFoundException;
import pl.mirbudpol.sklepbudowlany.repositories.ClientRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    public Client findByEmail(String login) {
        return clientRepository.findByEmail(login).orElseThrow(() -> new ResourceNotFoundException("Klient o emailu " + login + " nie istnieje"));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = findByEmail(login);
        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetailsDTO(client);
    }

}

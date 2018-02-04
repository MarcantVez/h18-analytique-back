package service;

import model.CompteUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailsUtilisateurServiceImpl  implements UserDetailsService {

    @Autowired
    private CompteUtilisateurService compteUtilisateurService;

    @Override
    public UserDetails loadUserByUsername(String courriel) throws UsernameNotFoundException {
        CompteUtilisateur appUser = compteUtilisateurService.trouverParCourriel(courriel);

        if (appUser == null) {
            System.out.println("Utilisateur inexistant pour " + courriel);
            throw new UsernameNotFoundException("L'tilisateur " + courriel + "n'a pas ete trouve dans la base de donnees");
        }

        //System.out.println("Found User: " + appUser.getCourriel());
        //System.out.println(appUser.getTypeAdmin());

        // Liste des autorites
        List<String> authorityNames = new ArrayList<>();
        authorityNames.add(appUser.getTypeAdmin());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (authorityNames != null) {
            for (String authorityName : authorityNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new User(appUser.getCourriel(), appUser.getMotDePasse(), grantList);

        return userDetails;
    }

}
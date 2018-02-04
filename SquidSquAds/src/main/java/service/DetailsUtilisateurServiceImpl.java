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
            System.out.println("User not found! " + courriel);
            throw new UsernameNotFoundException("User " + courriel + " was not found in the database");
        }

        System.out.println("Found User: " + appUser.getCourriel());
        System.out.println(appUser.getTypeAdmin());

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = new ArrayList<>();
        roleNames.add(appUser.getTypeAdmin());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getCourriel(), //
                appUser.getMotDePasse(), grantList);

        return userDetails;
    }

}
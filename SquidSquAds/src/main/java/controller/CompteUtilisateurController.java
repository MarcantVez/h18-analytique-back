package controller;

import javassist.NotFoundException;
import model.CompteUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CompteUtilisateurService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


// Autre moyen d'access: https://o7planning.org/en/11705/create-a-login-application-with-spring-boot-spring-security-jpa#a13944805
// https://jaxenter.com/rest-api-spring-java-8-112289.html

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-22
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController("CompteUtilisateurController")
public class CompteUtilisateurController {

    @Autowired
    CompteUtilisateurService compteUtilisateurService;


    // Recuperer tous les utilisateurs
    @GetMapping("/tousUtilisateurs")
    public List<CompteUtilisateur> trouverTousLesUtilisateurs() {
        List<CompteUtilisateur> list = new ArrayList<>();
        compteUtilisateurService.trouverTous().forEach(list::add);

        return list;
    }


    // Nouvelle utilisateur
    // Accessible par POST en JSON
    //    {
    //        "typeAdmin": "WEB",
    //            "courriel": "json@json.com",
    //            "motDePasse": "1234",
    //            "noCompteBanque": "0000"
    //    }
    @PostMapping("/nouvelleUtilisateur")
    public CompteUtilisateur ajouterUtilisateur(@Valid @RequestBody CompteUtilisateur note) {
        return compteUtilisateurService.ajouterCompteUtilisateur(note);
    }

    // Recuperer un utilisateur
    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<CompteUtilisateur> trouverUtilisateurParID(@PathVariable(value = "id") Long id) {
        CompteUtilisateur utilisateur = compteUtilisateurService.trouverUtilisateurParNumeroCompte(id);

        if(utilisateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(utilisateur);
    }

    // Mettre a jour un utilisateur
    @PostMapping("/utilisateur/{id}/update")
    public ResponseEntity<CompteUtilisateur> majUtilisateurParID(@PathVariable(value = "id") Long id) {
        ResponseEntity<CompteUtilisateur> re;

        try {
            CompteUtilisateur utilisateur = compteUtilisateurService.majUtilisateurParID(id);
            re = ResponseEntity.ok().body(utilisateur);
        } catch (NotFoundException e) {
            re = ResponseEntity.notFound().build();
        }

        return re;
    }


    // Supprimer un utilisateur
    @GetMapping("/utilisateur/{id}/delete")
    public void supprimerUtilisateurParID(@PathVariable(value = "id") Long id) {
        try {
            compteUtilisateurService.supprimerUtilisateurParID(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}

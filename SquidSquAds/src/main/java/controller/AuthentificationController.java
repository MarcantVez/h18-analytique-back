package controller;

import model.Authentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController("AuthController")
public class AuthentificationController {

    @Autowired
    CompteUtilisateurController compteUtilisateurController;

    // Nouvelle utilisateur
    // Accessible par POST en JSON
    //    {
    //        "courriel": "json@json.com",
    //        "motDePasse": "1234"
    //    }
    @PostMapping("/authentifier")
    public boolean authentifierUtilisateur(@Valid @RequestBody Authentification authentification) {
        return compteUtilisateurController.authentifierUtilisateur(authentification.getCourriel(), authentification.getMotDePasse());
    }
}

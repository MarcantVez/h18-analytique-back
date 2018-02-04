package service;

import model.CompteUtilisateur;


public interface ICompteUtilisateurService {
    Iterable<CompteUtilisateur> trouverTous();

    CompteUtilisateur trouverParCourriel(String courriel);

    CompteUtilisateur ajouterCompteUtilisateur(CompteUtilisateur compteUtilisateur);
}

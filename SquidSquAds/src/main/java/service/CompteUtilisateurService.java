package service;

import javassist.NotFoundException;
import model.CompteUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CompteUtilisateurRepository;

@Service
public class CompteUtilisateurService implements ICompteUtilisateurService {

    @Autowired
    CompteUtilisateurRepository compteUtilisateurRepository;

    @Override
    public Iterable<CompteUtilisateur> trouverTous() {
        return compteUtilisateurRepository.findAll();
    }

    @Override
    public CompteUtilisateur trouverParCourriel(String courriel) {
        CompteUtilisateur utilisateur = compteUtilisateurRepository.findByCourriel(courriel);
        return utilisateur;
    }

    @Override
    public CompteUtilisateur ajouterCompteUtilisateur(CompteUtilisateur compteUtilisateur) {
        return compteUtilisateurRepository.save(compteUtilisateur);
    }

    public CompteUtilisateur trouverUtilisateurParNumeroCompte(Long numeroCompte)
    {
        return compteUtilisateurRepository.findOne(numeroCompte);
    }

    public CompteUtilisateur majUtilisateur(CompteUtilisateur compteUtilisateur) throws NotFoundException {
        return majUtilisateurParID(compteUtilisateur.getNumeroCompte());
    }

    public CompteUtilisateur majUtilisateurParID(Long numeroCompte) throws NotFoundException {
        CompteUtilisateur utilisateur = trouverUtilisateurParNumeroCompte(numeroCompte);
        if( utilisateur == null )
        {
            throw new NotFoundException("Utilisateur " + numeroCompte + " inexistant");
        }

        return compteUtilisateurRepository.save(utilisateur);
    }

    public void supprimerUtilisateur(CompteUtilisateur utilisateur) throws NotFoundException {
        supprimerUtilisateurParID(utilisateur.getNumeroCompte());
    }

    public void supprimerUtilisateurParID(Long numeroCompte) throws NotFoundException {
        CompteUtilisateur utilisateur = trouverUtilisateurParNumeroCompte(numeroCompte);
        if( utilisateur == null )
        {
            throw new NotFoundException("Utilisateur " + numeroCompte + " inexistant");
        }

        compteUtilisateurRepository.delete(numeroCompte);
    }
}

package controller;

import model.Campagne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.CampagneRepository;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@RestController("CampagneController")
public class CampagneController {
    @Autowired
    CampagneRepository campagneRepository;

    @RequestMapping("/save")
    public String process(){
        // save a single Customer
        //campagneRepository.save(new Campagne());
        return "Done";
    }

    @RequestMapping("/findAllforAuthor")
    public String findAllforAuthor(int numeroCompte){
        String result = "";

        for(Campagne campagne : campagneRepository.findAllByNumeroCompte(numeroCompte)){
            result += campagne.toString() + "<br>";
        }

        return result;
    }
}

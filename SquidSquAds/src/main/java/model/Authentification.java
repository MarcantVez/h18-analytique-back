package model;

public class Authentification {
    String courriel;
    String motDePasse;

    public Authentification() {}

    public Authentification(String courriel, String motDePasse) {
        this.courriel = courriel;
        this.motDePasse = motDePasse;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

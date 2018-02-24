package com.squidsquads.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;


/**
 * Classe utilitaire servant à calculer le temps écoulé entre un ancien timestamp et le moment courrant.
 */
@Component
public class TimeSpentCalculator {

    /**
     *
     * @param previousTime un ancien Timestamp duquel calculer le temps écoulé
     * @return un entier en secondes montrant le temps écoulé
     */
    public int calculateTimeFromNow(Timestamp previousTime){
        Timestamp newTime = Timestamp.from(Instant.now());
        return (int) (newTime.getTime() - previousTime.getTime())/1000;
    }
}

package model;

import java.util.Random;

public class AllelePair {
    private final Allele maternalAllele;
    private final Allele paternalAllele;

    public AllelePair(Allele maternalAllele, Allele paternalAllele) {
        this.maternalAllele = maternalAllele;
        this.paternalAllele = paternalAllele;
    }

    // GETTERS
    public Allele getMaternalAllele() {
        return this.maternalAllele;
    }

    public Allele getPaternalAllele() {
        return this.paternalAllele;
    }

    // EFFECT: returns a random allele from allele pair
    // REQUIRES: random != null
    public Allele getRandomAllele(Random random) {
        int value = random.nextInt(2); // return int between 0 and 1
        if (value == 0) {
            return getMaternalAllele();
        } else {
            return getPaternalAllele();
        }
    }
}

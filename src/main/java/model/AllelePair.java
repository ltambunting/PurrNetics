package model;

import java.util.Random;

public class AllelePair {
    private final Gene gene;
    private final Gene.Allele maternalAllele;
    private final Gene.Allele paternalAllele;

    public AllelePair(Gene gene, Gene.Allele maternalAllele, Gene.Allele paternalAllele) {
        this.gene = gene;
        this.maternalAllele = maternalAllele;
        this.paternalAllele = paternalAllele;
    }

    // GETTERS
    public Gene getGene() {
        return this.gene;
    }

    public Gene.Allele getMaternalAllele() {
        return this.maternalAllele;
    }

    public Gene.Allele getPaternalAllele() {
        return this.paternalAllele;
    }

    // EFFECT: returns randomly a maternal or paternal allele 
    // from allele pair (utilizing deterministic dependency injection)
    // REQUIRES: random != null
    public Gene.Allele getRandomAllele(Random random) {
        int value = random.nextInt(2); // return int between 0 and 1
        if (value == 0) {
            return getMaternalAllele();
        } else {
            return getPaternalAllele();
        }
    }
}

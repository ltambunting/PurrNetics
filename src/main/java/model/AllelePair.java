package model;

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

}

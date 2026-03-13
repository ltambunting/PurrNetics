package model;

public class AllelePair {
    private final Allele maternalAllele;
    private final Allele paternalAllele;

    public AllelePair(Allele maternalAllele, Allele paternalAllele) {
        this.maternalAllele = maternalAllele;
        this.paternalAllele = paternalAllele;
    }
}

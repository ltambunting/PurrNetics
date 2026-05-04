package model;

public class CompleteDominance implements DominanceRule {
    // EFFECT: if two copies of recessive allele, return recessive trait
    //         otherwise, return dominant trait
    // REQUIRES: genotype != null
    @Override
    public String resolvePhenotype(AllelePair allelePair) {
        Gene.Allele a = allelePair.getMaternalAllele();
        Gene.Allele b = allelePair.getPaternalAllele();

        // if same allele, express it
        if (a.getAlleleSymbol().equals(b.getAlleleSymbol())) {
            return a.getVariant();
        }

        // compare dominance rank between alleles
        if (a.getRank() > b.getRank()) {
            return a.getVariant();
        } else if (b.getRank() > a.getRank()) {
            return b.getVariant();
        }

        // if equal rank, default detministic fallback
        return a.getVariant();
    }
}

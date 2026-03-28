package model;

public class CompleteDominance implements DominanceRule {
    // EFFECT: if two copies of recessive allele, return recessive trait
    //         otherwise, return dominant trait
    // REQUIRES: genotype != null
    @Override
    public String resolvePhenotype(AllelePair allelePair) {
        Gene.Allele maternalCopy = allelePair.getMaternalAllele();
        Gene.Allele paternalCopy = allelePair.getPaternalAllele();
        if (maternalCopy.getRank() > paternalCopy.getRank()) {
            return maternalCopy.getVariant(); // maternal copy is dominant and now we express maternal variant
        } else if (paternalCopy.getRank() > maternalCopy.getRank()) {
            return paternalCopy.getVariant(); // paternal copy dominant now we express paternal variant
        }
        return maternalCopy.getVariant(); // both parents provide the same allele variant,
                                          // so arbitrarily return maternal variant
    }
}

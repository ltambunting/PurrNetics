package model;
import java.util.Random;

public class AutosomalInheritance implements InheritanceRule {

    // EFFECT: randomly assigns allele pair from maternal and paternal copies
    //         based on autosomal mode of inheritance
    // REQUIRES: maternal != null and paternal != null
    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Random random) {
        Gene.Allele maternalCopy = maternal.getRandomAllele(random);
        Gene.Allele paternalCopy = paternal.getRandomAllele(random);
        AllelePair offspringAllelePair = new AllelePair(maternalCopy, paternalCopy);
        return offspringAllelePair;
    } 
    
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
        return maternalCopy.getVariant(); // final combination is both equal so arbitrarily return maternal
    }
}

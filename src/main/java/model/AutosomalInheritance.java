package model;
import java.util.Random;

public class AutosomalInheritance implements InheritanceRule {

    // EFFECT: randomly assigns allele pair from maternal and paternal copies
    //         based on autosomal mode of inheritance
    // REQUIRES: maternal != null and paternal != null
    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Random random) {
        Allele maternalCopy = maternal.getRandomAllele(random);
        Allele paternalCopy = paternal.getRandomAllele(random);
        AllelePair offspringAllelePair = new AllelePair(maternalCopy, paternalCopy);
        return offspringAllelePair;
    } 
    
    // EFFECT: if two copies of recessive allele, return recessive trait
    //         otherwise, return dominant trait
    // REQUIRES: genotype != null
    @Override
    public String resolvePhenotype(AllelePair genotype) {
        return null; //stub
    };
}

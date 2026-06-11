package model;
import java.util.Random;

public class AutosomalInheritance implements InheritanceRule {

    // EFFECT: randomly assigns allele pair from maternal and paternal copies
    //         based on autosomal mode of inheritance
    // REQUIRES: maternal != null and paternal != null
    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        Gene.Allele maternalCopy = maternal.getRandomAllele(random);
        Gene.Allele paternalCopy = paternal.getRandomAllele(random);
        AllelePair offspringAllelePair = new AllelePair(maternalCopy, paternalCopy);
        return offspringAllelePair;
    } 
}

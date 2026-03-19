package model;

public class AutosomalInheritance implements InheritanceRule {

    // EFFECT: randomly assigns allele pair from amternal and paternal copies
    //         based on autosomal mode of inheritance
    // REQUIRES: maternal != null and paternal != null
    public AllelePair inherit(AllelePair maternal, AllelePair paternal) {
        return null; //stub
    } 
    
    // EFFECT: if two copies of recessive allele, return recessive trait
    //         otherwise, return dominant trait
    // REQUIRES: genotype != null
    public Trait resolvePhenotype(AllelePair genotype) {
        return null; //stub
    };
}

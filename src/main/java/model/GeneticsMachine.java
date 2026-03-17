package model;

import java.util.HashMap;
import java.util.Map;

public class GeneticsMachine {
            // field to store information of how the genetics map to phenotype 
        //          (Gene -> Alelle variants -> Trait variants)
        // function to shuffle alleles of same gene
        // function to create new cat with assigned genotype profile
        // function to get from genotype profile, create phenotype profile
    private final Map<Gene, Map<Allele, String>> alleleEffectMapping;
    
    public GeneticsMachine() {
            alleleEffectMapping = new HashMap<>();
        }
    
    // EFFECTS: maps each Trait to resulting trait variant for Cat,
    //          computed from Cat's genotype
    public Map<Trait, String> resolvePhenotype(Cat cat) {
        return null; // stub
    }
}


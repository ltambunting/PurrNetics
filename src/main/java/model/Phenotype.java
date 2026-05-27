package model;

import java.util.Collections;
import java.util.Map;

public class Phenotype {
    private final Map<Trait, String> expressedVariants; //the expressed variant for each trait
    
    public Phenotype(Map<Trait, String> traitMap) {
        expressedVariants = traitMap;
    }

    public Map<Trait, String> getExpressedVariants() {
        return Collections.unmodifiableMap(expressedVariants);
    }

    // For particular phenotype, return expressed variant given trait
    public String getExpressedVariant(Trait trait) {
        return getExpressedVariants().get(trait);
    }
}

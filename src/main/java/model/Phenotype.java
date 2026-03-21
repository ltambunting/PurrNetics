package model;

import java.util.HashMap;
import java.util.Map;

public class Phenotype {
    private final Map<Trait, String> expressedVariants; //the expressed variant
    
    public Phenotype() {
        expressedVariants = new HashMap<>();
    }
}

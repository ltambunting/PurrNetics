package model;

import java.util.HashSet;
import java.util.Set;

public class Gene {
    private final String symbol;
    private final Trait trait;
    private final InheritanceRule inheritanceRule;
    private Set<Allele> variants;

    public Gene(String symbol, Trait trait, InheritanceRule inheritanceRule) {
        this.symbol = symbol;
        this.trait = trait; // represents trait/phenotype that locus determines
        this.inheritanceRule = inheritanceRule;
        this.variants = new HashSet<>();
    }

    // GETTERS
    public String getSymbol() {
        return this.symbol;
    }

    public Trait getTrait() {
        return this.trait;
    }

    public InheritanceRule getInheritanceRule() {
        return this.inheritanceRule;
    }

    public Set<Allele> getAlleles() {
        return this.variants;
    }

    // EFFECTS: adds an allele/gene variant to set of alleles at this gene/locus
    // REQUIRES: a cannot be NULL
    // MODIFIES: this.alleles
    public void addAllele(Allele a) {
        this.variants.add(a);
    }

}

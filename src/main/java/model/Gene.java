package model;

import java.util.ArrayList;
import java.util.List;

public class Gene {
    private final String symbol;
    private final Trait trait;
    private final InheritanceRule ir;
    private List<Allele> variants;

    public Gene(String symbol, Trait trait, InheritanceRule ir) {
        this.symbol = symbol;
        this.trait = trait; // represents trait/phenotype that locus determines
        this.ir = ir;
        this.variants = new ArrayList<>();
    }

    // GETTERS
    public String getSymbol() {
        return this.symbol;
    }

    public Trait getTrait() {
        return this.trait;
    }

    public InheritanceRule getInheritanceRule() {
        return this.ir;
    }

    public List<Allele> getAlleles() {
        return this.variants;
    }

    // EFFECTS: adds an allele/gene variant to list of alleles at this gene/locus
    // REQUIRES: a cannot be NULL
    // MODIFIES: this.alleles
    public void addAllele(Allele a) {
        this.variants.add(a);
    }

}

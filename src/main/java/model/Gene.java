package model;

import java.util.ArrayList;
import java.util.List;

public class Gene {
    private final String symbol;
    private final String traitName;
    private final InheritanceRule ir;
    private List<Allele> alleles;

    public Gene(String symbol, String traitName, InheritanceRule ir) {
        this.symbol = symbol;
        this.traitName = traitName; // represents trait/phenotype that locus determines
        this.ir = ir;
        this.alleles = new ArrayList<>();
    }

    // GETTERS
    public String getSymbol() {
        return this.symbol;
    }

    public String getTraitName() {
        return this.traitName;
    }

    public InheritanceRule getInheritanceRule() {
        return this.ir;
    }

    public List<Allele> getAlleles() {
        return this.alleles;
    }

    // EFFECTS: adds an allele/gene variant to list of alleles at this gene/locus
    // REQUIRES: a cannot be NULL
    // MODIFIES: this.alleles
    public void addAllele(Allele a) {
        // STUB
    }

}

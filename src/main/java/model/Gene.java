package model;

import java.util.HashSet;
import java.util.Set;

public class Gene {
    private final String symbol;
    private final Trait trait;
    private final InheritanceRule inheritanceRule;
    // because the interaction between alleles at a single locus/gene is
    // what determines if it is dominant, recessive, or other, the information
    // should be stored here in Gene class as nested class for better modularity
    private Set<Allele> alleles; // holds all known alleles of this locus and its information in context of gene

    public Gene(String symbol, Trait trait, InheritanceRule inheritanceRule) {
        this.symbol = symbol;
        this.trait = trait; // represents trait/phenotype that locus determines
        this.inheritanceRule = inheritanceRule;
        this.alleles = new HashSet<>();
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
        return this.alleles;
    }


    // EFFECTS: adds an allele/gene variant to set of alleles at this gene/locus
    // REQUIRES: a cannot be NULL
    // MODIFIES: this.alleles
    public Allele addAllele(String symbol, Integer rank, String variant) {
        Allele allele = new Allele(symbol, rank, variant);
        alleles.add(allele);
        return allele;
    }

    // Allele is an inner class representing information that cannot meaningfully live outside of Gene,
    // as Alleles are only meaningful in the context of the Gene/locus they are tied to
    public class Allele {
        private final String alleleSymbol;
        private final int rank;
        private final String variant;

        private Allele(String symbol, int rank, String variant) {
            this.alleleSymbol = symbol;
            this.rank = rank;
            this.variant = variant;
        }

        public String getAlleleSymbol() {
            return this.alleleSymbol;
        }

        public int getRank() {
            return this.rank;
        }

        public String getVariant() {
            return this.variant;
        }
    }
}

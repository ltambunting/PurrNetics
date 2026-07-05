package com.purrnetics.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Gene {
    private final String symbol;
    private final Trait trait; // represents trait/phenotype that locus determines
    private final InheritanceRule inheritanceRule;
    private final ExpressionRule expressionRule;
    private final Set<Allele> alleles; // holds all known alleles of this locus and its information in context of gene

    public Gene(String symbol, Trait trait, InheritanceRule inheritanceRule, ExpressionRule expressionRule) {
        this.symbol = symbol;
        this.trait = trait; 
        this.inheritanceRule = inheritanceRule;
        this.expressionRule = expressionRule;
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

    public ExpressionRule getExpressionRule() {
        return this.expressionRule;
    }

    public Set<Allele> getAlleles() {
        return Collections.unmodifiableSet(this.alleles);
    }

    // EFFECTS: returns the allele with corresponding symbol
    //          if allele doesn't exist, throws exception
    public Allele getAlleleBySymbol(String symbol) {
        for (Allele allele: alleles) {
            if (allele.getAlleleSymbol().equals(symbol)) {
                return allele;
            }
        }
        throw new IllegalArgumentException("Unknown allele symbol: " + symbol);
    }

    @Override
    public String toString() {
        return symbol;
    }


    // EFFECTS: adds an allele/gene variant to set of alleles at this gene/locus
    // REQUIRES: a cannot be NULL
    // MODIFIES: this.alleles
    public Allele addAllele(String symbol, int rank, String variant) {
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

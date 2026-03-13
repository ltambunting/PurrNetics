package model;

public class Allele {
    private final Gene gene;
    private final String symbol;
    private final DominanceRule dr;
    private final String traitVariant;

    public Allele(Gene gene, String symbol, DominanceRule dr, String traitVariant) {
        this.gene = gene;
        this.symbol = symbol;
        this.dr = dr;
        this.traitVariant = traitVariant;
    }

    // GETTERS
    public Gene getGene() {
        return this.gene;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public DominanceRule getDominanceRule() {
        return this.dr;
    }

    public String getTraitVariant() {
        return this.traitVariant;
    }
}

package model;

public class Allele {
    private final Gene gene;
    private final String symbol;
    private final DominanceRule dr;

    public Allele(Gene gene, String symbol, DominanceRule dr) {
        this.gene = gene;
        this.symbol = symbol;
        this.dr = dr;
    }
}

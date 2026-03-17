package model;

public class Allele {
    private final Gene gene;
    private final String symbol;
    private final int rank; // dominance ranking, higher = more dominant

    public Allele(Gene gene, String symbol, int rank) {
        this.gene = gene;
        this.symbol = symbol;
        this.rank = rank;
    }

    // GETTERS
    public Gene getGene() {
        return this.gene;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getRank() {
        return this.rank;
    }
}

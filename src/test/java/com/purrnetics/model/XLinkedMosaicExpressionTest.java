package com.purrnetics.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XLinkedMosaicExpressionTest {
    private static final String ORANGE_VARIANT = "Orange";
    private static final String NON_ORANGE_VARIANT = "Non-Orange";
    private static final String MOSAIC_VARIANT = "Mosaic";
    private static final Gene.Allele NO_X_ALLELE = null;
    private Gene gene;
    private Trait trait;
    private InheritanceRule xLinkedInheritance;
    private XLinkedMosaicExpression xLinkedMosaicExpression;
    private Gene.Allele orangeAllele;
    private Gene.Allele nonOrangeAllele;

    @BeforeEach
    public void setup() {
        trait = new Trait("Orange Fur Colour");
        xLinkedInheritance = new XLinkedInheritance();
        xLinkedMosaicExpression = new XLinkedMosaicExpression();
        gene = new Gene("O", trait, xLinkedInheritance, xLinkedMosaicExpression);
        orangeAllele = gene.addAllele("XO", 1, ORANGE_VARIANT);
        nonOrangeAllele = gene.addAllele("Xo", 1, NON_ORANGE_VARIANT);
    }

    @Test
    public void XOXOResultsInOrangeTest() {
        AllelePair XOXOAllelePair = new AllelePair(gene, orangeAllele, orangeAllele);
        assertEquals(ORANGE_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XOXOAllelePair));
    }

    @Test
    public void XoXoResultsInNonOrangeTest() {
        AllelePair XoXoAllelePair = new AllelePair(gene, nonOrangeAllele, nonOrangeAllele);
        assertEquals(NON_ORANGE_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XoXoAllelePair));
    }

    @Test
    public void XOXoResultsInTortoiseshellTest() {
        AllelePair XOXoAllelePair = new AllelePair(gene, orangeAllele, nonOrangeAllele);
        assertEquals(MOSAIC_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XOXoAllelePair));
    }

    @Test
    public void XoXOResultsInTortoiseshellTest() {
        AllelePair XoXOAllelePair = new AllelePair(gene, nonOrangeAllele, orangeAllele);
        assertEquals(MOSAIC_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XoXOAllelePair));
    }

    // paternal X-linked chromosome: second allele is absent (hemizygous Y-side)
    @Test
    public void XOYResultsInOrangeTest() {
        AllelePair XOYAllelePair = new AllelePair(gene, orangeAllele, NO_X_ALLELE);
        assertEquals(ORANGE_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XOYAllelePair));
    }

    @Test
    public void XoYResultsInNonOrangeTest() {
        AllelePair XoYAllelePair = new AllelePair(gene, nonOrangeAllele, NO_X_ALLELE);
        assertEquals(NON_ORANGE_VARIANT, xLinkedMosaicExpression.resolvePhenotype(XoYAllelePair));
    }
}

package model;

import java.util.Random;

/**
 * Handles X-linked inheritance.
 *
 * Males are hemizygous for X-linked loci and therefore inherit only one X allele.
 * To maintain a uniform AllelePair representation, the second allele position is
 * treated as structurally absent (Y-side) rather than modeled as a genetic allele.
 *
 * The Y chromosome does not encode an allele for X-linked loci and is not used in
 * phenotype determination. Note: null is used intentionally to represent absence rather than unknown data.
 */
public class XLinkedInheritance implements InheritanceRule {
    private static final Gene.Allele NO_X_ALLELE = null;
    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        Gene.Allele maternalInheritedAllele = null;
        Gene.Allele paternalInheritedAllele = null;
        if (offspringSex == Sex.FEMALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = paternal.getMaternalAllele();
        } else if (offspringSex == Sex.MALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = NO_X_ALLELE;

        }
        return new AllelePair(maternalInheritedAllele, paternalInheritedAllele);
    }

}

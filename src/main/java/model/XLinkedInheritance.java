package model;

import java.util.Random;

/**
 * Models inheritance of X-linked loci.
 *
 * Note: male offspring are represented using a special Y placeholder allele
 * to preserve compatibility with the AllelePair-based genotype model.
 * The placeholder does not represent a true allele at this locus; rather,
 * it indicates the absence of a corresponding X-linked allele on the Y chromosome.
 */
public class XLinkedInheritance implements InheritanceRule {

    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        Gene.Allele maternalInheritedAllele = null;
        Gene.Allele paternalInheritedAllele = null;
        if (offspringSex == Sex.FEMALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = paternal.getMaternalAllele();
        } else if (offspringSex == Sex.MALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = paternal.getPaternalAllele();

        }
        return new AllelePair(maternalInheritedAllele, paternalInheritedAllele);
    }

}

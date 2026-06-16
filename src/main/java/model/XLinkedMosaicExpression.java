package model;

public class XLinkedMosaicExpression implements ExpressionRule {

    @Override
    public String resolvePhenotype(AllelePair allelePair) {
        Gene.Allele maternalAllele = allelePair.getMaternalAllele();
        Gene.Allele paternalAllele = allelePair.getPaternalAllele();

        if (paternalAllele == null || maternalAllele.equals(paternalAllele)) {
            return maternalAllele.getVariant();
        } else return "Mosaic";
    }
    
}

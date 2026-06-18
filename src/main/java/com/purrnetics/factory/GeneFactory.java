package com.purrnetics.factory;

import com.purrnetics.model.AutosomalInheritance;
import com.purrnetics.model.CompleteDominance;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Trait;
import com.purrnetics.model.XLinkedInheritance;
import com.purrnetics.model.XLinkedMosaicExpression;

public class GeneFactory {

    public Gene createFurLengthGene() {
        Trait furLength = new Trait("Fur Length");
        furLength.addTraitVariant("Short hair");
        furLength.addTraitVariant("Long hair");

        Gene furLengthGene = new Gene("L", furLength, new AutosomalInheritance(), new CompleteDominance());
        furLengthGene.addAllele("L", 1, "Short hair");
        furLengthGene.addAllele("l", 0, "Long hair");

        return furLengthGene;
    }

    public Gene createAgoutiGene() {
        Trait agoutiFur = new Trait("Agouti Fur");
        agoutiFur.addTraitVariant("Agouti fur");
        agoutiFur.addTraitVariant("Non-agouti fur");

        Gene agoutiGene = new Gene("A", agoutiFur, new AutosomalInheritance(), new CompleteDominance());
        agoutiGene.addAllele("A", 1, "Agouti fur");
        agoutiGene.addAllele("a", 0, "Non-agouti fur");

        return agoutiGene;
    }

    public Gene createOrangeGene() {
        Trait orangeFur = new Trait("Orange Fur");
        orangeFur.addTraitVariant("Orange fur");
        orangeFur.addTraitVariant("Non-orange fur");
        orangeFur.addTraitVariant("Mosaic");

        Gene orangeGene = new Gene("O", orangeFur, new XLinkedInheritance(), new XLinkedMosaicExpression());
        orangeGene.addAllele("O", 1, "Orange fur");
        orangeGene.addAllele("o", 1, "Non-orange fur");
        
        return orangeGene;
    }
}

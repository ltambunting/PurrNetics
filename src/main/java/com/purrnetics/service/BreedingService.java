package com.purrnetics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.purrnetics.model.AllelePair;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Cat;
import com.purrnetics.model.ExpressionRule;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Genotype;
import com.purrnetics.model.InheritanceRule;
import com.purrnetics.model.ParentPair;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Sex;
import com.purrnetics.model.SexLinkedTraitDistribution;
import com.purrnetics.model.Trait;
import com.purrnetics.model.XLinkedInheritance;

/**
 * BreedingService orchestrates the biological simulation of cat genetics involved in one fertilization event.
 *
 * <p>Responsibilities:
 * - Coordinates the full breeding process between two parent Cats
 * - Applies gene-specific InheritanceRule to construct offspring genotype
 * - Applies gene-specific DominanceRule to resolve phenotype
 * - Ensures correct execution order: inheritance → genotype → phenotype
 *
 * <p>Scope:
 * - Acts as the single entry point for running genetic simulations
 * - Operates on domain objects (Cat, Gene, AllelePair, Genotype)
 * - Manages randomness for inheritance
 *
 * <p>Non-responsibilities:
 * - Does NOT define genetic rules (delegates to InheritanceRule and DominanceRule)
 * - Does NOT store long-term state of Cats or populations
 * - Does NOT model UI, persistence, or external systems
 *
 * <p>Design Notes:
 * - This class separates simulation logic from domain models (e.g., ParentPair, Cat)
 * - Domain objects represent biological entities; BreedingService executes biology
 * - Current implementation uses a simplified inheritance model (no explicit gametes)
 *
 * <p>Future Extensions:
 * - Gamete generation (meiosis modeling)
 * - X-linked and non-autosomal inheritance
 * - More complex dominance relationships (codominance, incomplete dominance)
 */

@Service
public class BreedingService {
    private final Random random;

    public BreedingService(Random random) {
        this.random = random; // stores randomly generated value for reproducibility and testing purposes
    }

    // EFFECTS: simulates single fertilization event between two parent cats 
    //          and generates one offspring with inherited genotype and resolved phenotype
    //          based on genetic rules defined at each gene locus
    // MODIFIES: ParentPair.offspring
    public Cat breed(ParentPair parentPair) {
        Genotype motherGenotype = parentPair.getMother().getGenotype();
        Genotype fatherGenotype = parentPair.getFather().getGenotype();

        Map<Gene, AllelePair> childGenotypeInheritedAlleles = new HashMap<>();
        Map<Trait, String> childPhenotypeMap = new HashMap<>();

        Sex offspringSex = assignSex();

        // assume number of genes equal (in typical mammals chromosomes are set amount therefore genes too)
        for (Gene gene : motherGenotype.getInheritedAlleles().keySet()) {
            AllelePair motherAllelePair = motherGenotype.getAllelePair(gene);
            AllelePair fatherAllelePair = fatherGenotype.getAllelePair(gene);
            
            AllelePair inheritedAllelePair = gene.getInheritanceRule().inherit(gene, motherAllelePair, fatherAllelePair, offspringSex, random);
            String expressedTraitVariant = gene.getExpressionRule().resolvePhenotype(inheritedAllelePair);

            childGenotypeInheritedAlleles.put(gene, inheritedAllelePair);
            childPhenotypeMap.put(gene.getTrait(), expressedTraitVariant);
        }
        Cat child = new Cat(null, null, offspringSex, parentPair, new Genotype(childGenotypeInheritedAlleles), new Phenotype(childPhenotypeMap));
        parentPair.getOffspring().add(child);
        return child;
    }

    public BreedingResult getBreedingResult(ParentPair parentPair) {
        Map<Gene, Map<AllelePair, Double>> genotypeDistributionMap = new HashMap<>();
        Map<Trait, Map<String, Double>> phenotypeDistributionMap = new HashMap<>();
        List<SexLinkedTraitDistribution> sexLinkedTraitDistributionList = new ArrayList<>();
        Genotype motherGenotype = parentPair.getMother().getGenotype();
        Genotype fatherGenotype = parentPair.getFather().getGenotype();

        for (Gene gene : motherGenotype.getInheritedAlleles().keySet()) {
            AllelePair motherAllelePair = motherGenotype.getAllelePair(gene);
            AllelePair fatherAllelePair = fatherGenotype.getAllelePair(gene);
            InheritanceRule inheritanceRule = gene.getInheritanceRule();
            Map<AllelePair, Double> allelePairDistributionMap = inheritanceRule.getInheritanceDistribution(gene, motherAllelePair, fatherAllelePair);
            genotypeDistributionMap.put(gene, allelePairDistributionMap);

            Trait trait = gene.getTrait();
            if (inheritanceRule instanceof XLinkedInheritance) {
                SexLinkedTraitDistribution sexLinkedTraitDistribution = getSexLinkedTraitDistribution(trait, allelePairDistributionMap);
                sexLinkedTraitDistributionList.add(sexLinkedTraitDistribution);
            } else {
                Map<String, Double> expressedVariantsMap = getPhenotypeDistribution(allelePairDistributionMap);
                phenotypeDistributionMap.put(trait, expressedVariantsMap);
            }
        }
        return new BreedingResult(genotypeDistributionMap, phenotypeDistributionMap, sexLinkedTraitDistributionList);
    }

    // Private helper for autosomal gene trait resolution
    private Map<String, Double> getPhenotypeDistribution(Map<AllelePair, Double> allelePairDistribution) {
        Map<String, Double> expressedVariantsMap = new HashMap<>();
        for (AllelePair allelePair : allelePairDistribution.keySet()) {
            Double probability = allelePairDistribution.get(allelePair);
            ExpressionRule expressionRule = allelePair.getGene().getExpressionRule();
            String expressedVariant = expressionRule.resolvePhenotype(allelePair);

            expressedVariantsMap.merge(expressedVariant, probability, (newProbability, oldProbability) -> newProbability + oldProbability);
        }
        return expressedVariantsMap;
    }

    private Sex assignSex() {
        int value = random.nextInt(2); // return int between 0 and 1
        if (value == 0) {
            return Sex.FEMALE;
        } else {
            return Sex.MALE;
        }
    }

    private Sex getXLinkedSex(AllelePair allelePair) {
        if (allelePair.getPaternalAllele() == null) {
            return Sex.MALE;
        }
        else return Sex.FEMALE;
    }

    // private helper for sex-linked inheritance resolution
    private SexLinkedTraitDistribution getSexLinkedTraitDistribution(Trait trait, Map<AllelePair, Double> allelePairDistribution) {
        Map<Sex, Map<String, Double>> sexLinkedTraitMap = new HashMap<>();
        for (AllelePair allelePair : allelePairDistribution.keySet()) {
            Sex sex = getXLinkedSex(allelePair); // check sex
            String expressedVariant = allelePair.getGene().getExpressionRule().resolvePhenotype(allelePair); // resolve phenotype from pair
            Double probability = allelePairDistribution.get(allelePair); // get probability from genotype distribution map
            if (!sexLinkedTraitMap.containsKey(sex)) { // put sex into sex linked trait map
                sexLinkedTraitMap.put(sex, new HashMap<>());
            }
            Map<String, Double> variants = sexLinkedTraitMap.get(sex); // get variant map from sex
            if (!variants.containsKey(expressedVariant)) { // if variant does not exist
                variants.put(expressedVariant, probability); // put and use existing genotype probability
            } else {
                variants.put(expressedVariant, variants.get(expressedVariant) + probability); // if exist add probability to existing value
            }
        }

        for (Map<String, Double> variants : sexLinkedTraitMap.values()) {
            double total = 0.0;
            for (Double probability : variants.values()) {
                total += probability;
            }

            for (String variant : variants.keySet()) {
                variants.put(variant, variants.get(variant)/ total);
            }
        } // calculate total probability for each sex then normalize each phenotype probability by sex
        return new SexLinkedTraitDistribution(trait, sexLinkedTraitMap);
    }
}

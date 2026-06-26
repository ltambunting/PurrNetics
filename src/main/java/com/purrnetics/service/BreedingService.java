package com.purrnetics.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.purrnetics.model.AllelePair;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Cat;
import com.purrnetics.model.ExpressionRule;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Genotype;
import com.purrnetics.model.ParentPair;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Sex;
import com.purrnetics.model.Trait;

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
        Cat child = new Cat(null, offspringSex, parentPair, new Genotype(childGenotypeInheritedAlleles), new Phenotype(childPhenotypeMap));
        parentPair.getOffspring().add(child);
        return child;
    }

    public BreedingResult breedingResult(ParentPair parentPair) {
        Map<Gene, Map<AllelePair, Double>> genotypeDistributionMap = new HashMap<>();
        Map<Trait, Map<String, Double>> phenotypeDistributionMap = new HashMap<>();

        Genotype motherGenotype = parentPair.getMother().getGenotype();
        Genotype fatherGenotype = parentPair.getFather().getGenotype();

        for (Gene gene : motherGenotype.getInheritedAlleles().keySet()) {
            AllelePair motherAllelePair = motherGenotype.getAllelePair(gene);
            AllelePair fatherAllelePair = fatherGenotype.getAllelePair(gene);
            Map<AllelePair, Double> allelePairDistributionMap = gene.getInheritanceRule().getInheritanceDistribution(gene, motherAllelePair, fatherAllelePair);
            Map<String, Double> expressedVariantsMap = getPhenotypeDistribution(allelePairDistributionMap);

            genotypeDistributionMap.put(gene, allelePairDistributionMap);
            phenotypeDistributionMap.put(gene.getTrait(), expressedVariantsMap);
        }
        return new BreedingResult(genotypeDistributionMap, phenotypeDistributionMap);
    }

    private Map<String, Double> getPhenotypeDistribution(Map<AllelePair, Double> genotypeDistribution) {
        Map<String, Double> expressedVariantsMap = new HashMap<>();
        for (AllelePair allelePair : genotypeDistribution.keySet()) {
            Double probability = genotypeDistribution.get(allelePair);
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
}

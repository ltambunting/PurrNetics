package com.purrnetics.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purrnetics.dto.AlleleOutcomeDto;
import com.purrnetics.dto.BreedingResponseDto;
import com.purrnetics.dto.GeneResultDto;
import com.purrnetics.dto.SexLinkedTraitResultDto;
import com.purrnetics.dto.SexLinkedVariantOutComeDto;
import com.purrnetics.dto.TraitResultDto;
import com.purrnetics.dto.VariantOutcomeDto;
import com.purrnetics.model.AllelePair;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Gene;
import com.purrnetics.model.ParentPair;
import com.purrnetics.model.Sex;
import com.purrnetics.model.SexLinkedTraitDistribution;
import com.purrnetics.model.Trait;

public class BreedingMapper {
    public BreedingResponseDto toDto(ParentPair parentPair, BreedingResult breedingResult) {
        List<GeneResultDto> geneDtos = new ArrayList<>();
        
        // Genes
        for (Map.Entry<Gene, Map<AllelePair, Double>> entry : breedingResult.genotypeDistribution().entrySet()) {
            Gene gene = entry.getKey();
            Map<AllelePair, Double> alleleMap = entry.getValue();

            List<AlleleOutcomeDto> outcomes = new ArrayList<>();

            for (Map.Entry<AllelePair, Double> alleleEntry : alleleMap.entrySet()) {
                AllelePair allelePair = alleleEntry.getKey();
                Double probability = alleleEntry.getValue();

                outcomes.add(new AlleleOutcomeDto(allelePair.toString(), probability));
            }

            geneDtos.add(new GeneResultDto(gene.toString(), outcomes));
        }

        // Non-Sex-Linked Traits
        List<TraitResultDto> traitDtos = new ArrayList<>();

        for (Map.Entry<Trait, Map<String, Double>> entry : breedingResult.phenotypeDistribution().entrySet()) {
            Trait trait = entry.getKey();
            Map<String, Double> variantMap = entry.getValue();

            List<VariantOutcomeDto> variants = new ArrayList<>();

            for (Map.Entry<String, Double> variantEntry : variantMap.entrySet()) {
                variants.add(new VariantOutcomeDto(variantEntry.getKey(), variantEntry.getValue()));
            }

            traitDtos.add(new TraitResultDto(trait.toString(), variants));
        }

        // Sex-Linked Traits
        List<SexLinkedTraitResultDto> sexLinkedTraitDtos = new ArrayList<>();
        for (SexLinkedTraitDistribution distribution : breedingResult.sexLinkedTraitDistribution()) {
            List<SexLinkedVariantOutComeDto> sexOutcomes = new ArrayList<>();
            for (Map.Entry<Sex, Map<String, Double>> entry : distribution.distributions().entrySet()) {
                Sex sex = entry.getKey();
                Map<String, Double> variantMap = entry.getValue();
                List<VariantOutcomeDto> variants = new ArrayList<>();
                for (Map.Entry<String, Double> variantEntry : variantMap.entrySet()) {
                    variants.add(new VariantOutcomeDto(variantEntry.getKey(), variantEntry.getValue()));
                }
                sexOutcomes.add(new SexLinkedVariantOutComeDto(sex.toString(), variants));
            }
            sexLinkedTraitDtos.add(new SexLinkedTraitResultDto(distribution.trait().toString(), sexOutcomes));
        }
        
        return new BreedingResponseDto(parentPair.getMother().getName(), parentPair.getFather().getName(), geneDtos, traitDtos, sexLinkedTraitDtos);
    }
}

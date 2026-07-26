package com.purrnetics.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purrnetics.dto.ExpressedTraitDto;
import com.purrnetics.dto.PossibleKittenDto;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Sex;
import com.purrnetics.model.SexLinkedTraitDistribution;
import com.purrnetics.model.Trait;

public class PossibleKittenMapper {
    public List<PossibleKittenDto> toPossibleKittenDtoList(BreedingResult breedingResult) {
        Map<Trait, Map<String, Double>> phenotypeDistribution = breedingResult.phenotypeDistribution();
        List<SexLinkedTraitDistribution> sexLinkedTraitDistribution = breedingResult.sexLinkedTraitDistribution();
        List<TraitCombination> autosomalTraitCombinations = getAutosomalTraitCombinations(phenotypeDistribution);
        List<SexTraitCombination> sexTraitCombinations = getSexTraitCombinations(sexLinkedTraitDistribution);

        List<PossibleKittenDto> possibleKittenDtos = new ArrayList<>();
        for (TraitCombination autosomalTraitCombination : autosomalTraitCombinations) {
            for (SexTraitCombination sexTraitCombination : sexTraitCombinations) {
                List<ExpressedTraitDto> combinedExpressedTraitDtos = new ArrayList<>();
                List<ExpressedTraitDto> autosomalExpressedTraitDtos = autosomalTraitCombination.expressedTraitDtos();
                List<ExpressedTraitDto> sexLinkedExpressedTraitDtos = sexTraitCombination.expressedTraitDtos();
                combinedExpressedTraitDtos.addAll(autosomalExpressedTraitDtos);
                combinedExpressedTraitDtos.addAll(sexLinkedExpressedTraitDtos); // combine all expressed trait dtos

                Double combinedProbability = autosomalTraitCombination.probability() * sexTraitCombination.probability() * 0.5; // multiply by 0.5 to account for male vs. female probability
                Sex sex = sexTraitCombination.sex();

                PossibleKittenDto possibleKittenDto = new PossibleKittenDto(sex.toString(), combinedExpressedTraitDtos, combinedProbability);
                possibleKittenDtos.add(possibleKittenDto);
            }
        }
        return possibleKittenDtos;
    }

    private List<TraitCombination> getAutosomalTraitCombinations(Map<Trait, Map<String, Double>> phenotypeDistribution) {
        // Extract all autosomal traits and possible variants, create list of ExpressedTraitDto and get combined probability and store as list of trait combos for future calcs
        List<TraitCombination> traitCombinations = new ArrayList<>(); // list to store all trait combinations
        traitCombinations.add(new TraitCombination(new ArrayList<>(), 1.0)); // initial list

        for (Map.Entry<Trait, Map<String, Double>> phenotypeDistributionEntry : phenotypeDistribution.entrySet()) {
            String traitName = phenotypeDistributionEntry.getKey().getKey(); // trait name
            Map<String, Double> variantMap = phenotypeDistributionEntry.getValue(); // variant map at current trait
            List<TraitCombination> nextTraitCombinations = new ArrayList<>(); // prepare new array to replace old combination list
            
            for(Map.Entry<String, Double> variantEntry : variantMap.entrySet()) { // iterate through variant + probability entries
                ExpressedTraitDto expressedTraitDto = new ExpressedTraitDto(traitName, variantEntry.getKey()); // new expressed trait dto with trait name and variant name
                Double variantProbability = variantEntry.getValue(); // value of current variant probability
                
                for (TraitCombination traitCombination : traitCombinations) { // for each existing trait combination
                    TraitCombination newTraitCombination = getTraitCombination(traitCombination, expressedTraitDto, variantProbability);
                    nextTraitCombinations.add(newTraitCombination);// add updated trait combination to nextTraitCombinations
                }
            }
            traitCombinations = nextTraitCombinations; // replace existing top level trait combos with the new trait combinations
        }
        return traitCombinations;
    }

    private List<SexTraitCombination> getSexTraitCombinations(List<SexLinkedTraitDistribution> sexLinkedTraitDistribution) {
        // Extract all sex linked traits and possible variants, create a list of ExpressedTraitDto and get combined probability and store as list of combos for future calcs
        List<SexTraitCombination> femaleTraitCombinations = new ArrayList<>(); 
        List<SexTraitCombination> maleTraitCombinations = new ArrayList<>();
        femaleTraitCombinations.add(new SexTraitCombination(Sex.FEMALE, new ArrayList<>(), 1.0)); // set up initial list for both sexes
        maleTraitCombinations.add(new SexTraitCombination(Sex.MALE, new ArrayList<>(), 1.0));

        for (SexLinkedTraitDistribution sexLinkedTraitDistributionEntry : sexLinkedTraitDistribution) { // iterate through each sex linked trait distribution entry
            List<SexTraitCombination> nextFemaleTraitCombinations = new ArrayList<>(); // initialize empty list for next female combinations
            List<SexTraitCombination> nextMaleTraitCombinations = new ArrayList<>(); // initialize empty list for next male combinations
            String traitName = sexLinkedTraitDistributionEntry.trait().getKey(); // get name of trait
            Map<Sex, Map<String, Double>> distributionMap = sexLinkedTraitDistributionEntry.distributions(); // for trait, get sex linked distribution of possible variants
            Map<String, Double> femaleDistributionMap = distributionMap.get(Sex.FEMALE); // female map of possible variants for this trait
            Map<String, Double> maleDistributionMap = distributionMap.get(Sex.MALE); // male map of possible variants for this trait

            // iterate through female variants
            for (Map.Entry<String, Double> femaleVariantEntry : femaleDistributionMap.entrySet()) { // iterate through variant + probability entries
                ExpressedTraitDto expressedTraitDto = new ExpressedTraitDto(traitName, femaleVariantEntry.getKey()); // new ExpressedTraitDto with trait and this variant
                Double variantProbability = femaleVariantEntry.getValue(); // probability of particular variant
                for (SexTraitCombination femaleTraitCombination : femaleTraitCombinations) { // iterate through existing top level female combinations
                    nextFemaleTraitCombinations.add(getSexTraitCombination(femaleTraitCombination, Sex.FEMALE, expressedTraitDto, variantProbability)); // add current combination to next
                }
            }
            
            // iterate through male variants
            for(Map.Entry<String, Double> maleVariantEntry : maleDistributionMap.entrySet()) {
                ExpressedTraitDto expressedTraitDto = new ExpressedTraitDto(traitName, maleVariantEntry.getKey());
                Double variantProbability = maleVariantEntry.getValue();
                for (SexTraitCombination maleTraitCombination : maleTraitCombinations) {
                    nextMaleTraitCombinations.add(getSexTraitCombination(maleTraitCombination, Sex.MALE, expressedTraitDto, variantProbability));
                }
            }
            femaleTraitCombinations = nextFemaleTraitCombinations; // replace top level female top combinations with updated
            maleTraitCombinations = nextMaleTraitCombinations;
        } // end loop once all possible combinations of sex linked variants for each trait are calculated
        List<SexTraitCombination> combinedSexTraitCombinations = new ArrayList<>(); // combine both female and male trait combinations and return combined
        combinedSexTraitCombinations.addAll(femaleTraitCombinations);
        combinedSexTraitCombinations.addAll(maleTraitCombinations);
        return combinedSexTraitCombinations;
    }

    private TraitCombination getTraitCombination(TraitCombination traitCombination, ExpressedTraitDto expressedTraitDto, Double variantProbability) {
        List<ExpressedTraitDto> expressedTraitDtos = traitCombination.expressedTraitDtos(); // existing list of expressed trait dto
        Double probability = traitCombination.probability(); // existing probability
        List<ExpressedTraitDto> updatedExpressedTraitDtos = new ArrayList<>(expressedTraitDtos); // create new array with existing trait dtos
        updatedExpressedTraitDtos.add(expressedTraitDto); // add new dto to new list
        Double updatedProbability = variantProbability * probability; // new probability
        return new TraitCombination(updatedExpressedTraitDtos, updatedProbability); // return a new trait combination with updated list and probability
    }

    private SexTraitCombination getSexTraitCombination(SexTraitCombination sexTraitCombination, Sex sex, ExpressedTraitDto expressedTraitDto, Double variantProbability) {
        List<ExpressedTraitDto> expressedTraitDtos = sexTraitCombination.expressedTraitDtos(); // current female ExpressedTraitDto combinations at top level
        Double probability = sexTraitCombination.probability(); // current probability of combined ExpressedTraitDto combos at top level
        List<ExpressedTraitDto> updatedExpressedTraitDtos = new ArrayList<>(expressedTraitDtos); // copy existing list
        updatedExpressedTraitDtos.add(expressedTraitDto); // add new dto to existing
        Double updatedProbability = probability * variantProbability; // update probability to reflect combination of existing and current
        return new SexTraitCombination(sex, updatedExpressedTraitDtos, updatedProbability);
    } 

    private record TraitCombination(List<ExpressedTraitDto> expressedTraitDtos, Double probability) {}
    private record SexTraitCombination(Sex sex, List<ExpressedTraitDto> expressedTraitDtos, Double probability) {}
}

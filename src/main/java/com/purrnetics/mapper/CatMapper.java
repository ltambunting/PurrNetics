package com.purrnetics.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purrnetics.dto.CatResponseDto;
import com.purrnetics.dto.ExpressedTraitDto;
import com.purrnetics.dto.InheritedAllelesDto;
import com.purrnetics.model.AllelePair;
import com.purrnetics.model.Cat;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Genotype;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Trait;

public class CatMapper {

    public CatResponseDto toDto(Cat cat) {
        List<InheritedAllelesDto> inheritedAllelesDtos = new ArrayList<>();
        List<ExpressedTraitDto> expressedTraitDtos = new ArrayList<>();

        String catName = cat.getName();
        String catSex = cat.getSex().toString();

        Genotype genotype = cat.getGenotype();
        Map<Gene, AllelePair> allelePairMap = genotype.getInheritedAlleles();
        for (Map.Entry<Gene, AllelePair> entry : allelePairMap.entrySet()) {
            inheritedAllelesDtos.add(new InheritedAllelesDto(entry.getKey().getTrait().getKey(), entry.getKey().getSymbol(), entry.getValue().toString()));
        }


        Phenotype phenotype = cat.getPhenotype();
        Map<Trait, String> phenotypeMap = phenotype.getExpressedVariants();
        for (Map.Entry<Trait, String> entry : phenotypeMap.entrySet()) {
            expressedTraitDtos.add(new ExpressedTraitDto(entry.getKey().toString(), entry.getValue()));
        }
        
        return new CatResponseDto(catName, catSex, inheritedAllelesDtos, expressedTraitDtos);
    }
}

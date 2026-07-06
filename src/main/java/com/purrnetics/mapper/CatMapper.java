package com.purrnetics.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purrnetics.dto.CatResponseDto;
import com.purrnetics.dto.ExpressedTraitDto;
import com.purrnetics.model.Cat;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Trait;

public class CatMapper {

    public CatResponseDto toDto(Cat cat) {
        List<ExpressedTraitDto> expressedTraitDtos = new ArrayList<>();

        String catName = cat.getName();
        String catSex = cat.getSex().toString();

        Phenotype phenotype = cat.getPhenotype();
        Map<Trait, String> phenotypeMap = phenotype.getExpressedVariants();
        for (Map.Entry<Trait, String> entry : phenotypeMap.entrySet()) {
            expressedTraitDtos.add(new ExpressedTraitDto(entry.getKey().toString(), entry.getValue()));
        }
        
        return new CatResponseDto(catName, catSex, expressedTraitDtos);
    }
}

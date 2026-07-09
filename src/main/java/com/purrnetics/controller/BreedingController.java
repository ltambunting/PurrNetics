package com.purrnetics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purrnetics.dto.BreedingRequestDto;
import com.purrnetics.dto.BreedingResponseDto;
import com.purrnetics.mapper.BreedingMapper;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Cat;
import com.purrnetics.model.ParentPair;
import com.purrnetics.service.BreedingService;
import com.purrnetics.service.PresetCatService;

@RestController
@RequestMapping("/breed")
public class BreedingController {
    private final BreedingService breedingService;
    private final PresetCatService presetCatService;
    private final BreedingMapper breedingMapper;

    public BreedingController(BreedingService breedingService, PresetCatService presetCatService, BreedingMapper breedingMapper) {
        this.breedingService = breedingService;
        this.presetCatService = presetCatService;
        this.breedingMapper = breedingMapper;
    }

    @PostMapping("/result")
    public BreedingResponseDto breed(@RequestBody BreedingRequestDto request) {
        Cat mother = presetCatService.getCat(request.motherId());
        Cat father = presetCatService.getCat(request.fatherId());
        ParentPair parentPair = new ParentPair(mother, father);
        BreedingResult breedingResult = breedingService.getBreedingResult(parentPair);
        return breedingMapper.toDto(parentPair, breedingResult);
    }
}




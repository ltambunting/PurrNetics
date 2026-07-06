package com.purrnetics.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purrnetics.dto.CatResponseDto;
import com.purrnetics.mapper.CatMapper;
import com.purrnetics.model.Cat;
import com.purrnetics.service.PresetCatService;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final PresetCatService presetCatService;
    private final CatMapper catMapper;

    public CatController(PresetCatService presetCatService, CatMapper catMapper) {
        this.presetCatService = presetCatService;
        this.catMapper = catMapper;
    }

    @GetMapping("/list")
    public List<CatResponseDto> getCatList() {
        List<CatResponseDto> catResponseDtos = new ArrayList<>();
        Collection<Cat> presetCats = presetCatService.getPresetCatMap().values();
        for (Cat cat : presetCats) {
            catResponseDtos.add(catMapper.toDto(cat));
        }
        return catResponseDtos;
    }

    @GetMapping("/{catId}")
    public CatResponseDto getCatById(@PathVariable String catId) {
        Cat cat = presetCatService.getCat(catId);
        return catMapper.toDto(cat);
    }
}
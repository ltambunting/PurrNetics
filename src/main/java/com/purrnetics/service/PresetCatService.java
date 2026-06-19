package com.purrnetics.service;

import java.util.ArrayList;
import java.util.List;

import com.purrnetics.factory.PresetCatFactory;
import com.purrnetics.model.Cat;

public class PresetCatService {
    private PresetCatFactory presetCatFactory;

    public PresetCatService(PresetCatFactory presetCatFactory) {
        this.presetCatFactory = presetCatFactory;
    }

    public List<Cat> getPresetCatList() {
        List<Cat> presetCatList = new ArrayList<>();
        presetCatList.add(presetCatFactory.createFemaleShortHairAgoutiTortieCat());
        presetCatList.add(presetCatFactory.createFemaleShortHairAgoutiOrangeCat());
        presetCatList.add(presetCatFactory.createFemaleShortHairAgoutiNonOrangeCat());
        presetCatList.add(presetCatFactory.createMaleShortHairAgoutiOrangeCat());
        presetCatList.add(presetCatFactory.createMaleShortHairAgoutiNonOrangeCat());
        return presetCatList;
    }
}

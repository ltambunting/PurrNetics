package com.purrnetics.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.purrnetics.factory.PresetCatFactory;
import com.purrnetics.model.Cat;

@Service
public class PresetCatService {
    private static final String CAT_ID_1 = "cid1";
    private static final String CAT_ID_2 = "cid2";
    private static final String CAT_ID_3 = "cid3";
    private static final String CAT_ID_4 = "cid4";
    private static final String CAT_ID_5 = "cid5";
    private final Map<String, Cat> presetCatMap;
    private PresetCatFactory presetCatFactory;

    public PresetCatService(PresetCatFactory presetCatFactory) {
        this.presetCatFactory = presetCatFactory;
            
        this.presetCatMap = new HashMap<>();
        presetCatMap.put(CAT_ID_1, presetCatFactory.createFemaleShortHairAgoutiTortieCat());
        presetCatMap.put(CAT_ID_2, presetCatFactory.createFemaleShortHairAgoutiOrangeCat());
        presetCatMap.put(CAT_ID_3, presetCatFactory.createFemaleShortHairAgoutiNonOrangeCat());
        presetCatMap.put(CAT_ID_4, presetCatFactory.createMaleShortHairAgoutiOrangeCat());
        presetCatMap.put(CAT_ID_5, presetCatFactory.createMaleShortHairAgoutiNonOrangeCat());
    }

    public Map<String, Cat> getPresetCatMap() {
        return this.presetCatMap;
    }

    public Cat getCat(String id) {
        return presetCatMap.get(id);
    }
}

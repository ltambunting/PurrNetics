package com.purrnetics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purrnetics.factory.GeneFactory;
import com.purrnetics.factory.PresetCatFactory;
import com.purrnetics.model.Cat;

public class PresetCatServiceTest {
    private static final String CAT_ID_1 = "cid1";
    private GeneFactory geneFactory;
    private PresetCatFactory presetCatFactory;
    private PresetCatService presetCatService;

    @BeforeEach
    public void setup() {
        geneFactory = new GeneFactory();
        presetCatFactory = new PresetCatFactory(geneFactory);
        presetCatService = new PresetCatService(presetCatFactory);
    }

    @Test
    public void getAllPresetCatsReturnsCorrectSizeTest() {
        assertEquals(5, presetCatService.getPresetCatMap().size());
    }

    @Test
    public void getCatByIdReturnsCorrectCatTest() {
        Cat cat = presetCatService.getPresetCatMap().get("cid1");
        assertEquals("cid1", cat.getID());
    }

    @Test
    public void getCatTest() {
        Cat cat = presetCatService.getCat(CAT_ID_1);
        assertEquals(CAT_ID_1, cat.getID());
    }
}

package com.purrnetics.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purrnetics.factory.GeneFactory;
import com.purrnetics.factory.PresetCatFactory;
import com.purrnetics.model.Cat;
import com.purrnetics.model.Sex;

public class PresetCatServiceTest {
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
    public void getPresetCatsReturnsAllExpectedCatsTest() {
        List<Cat> cats = presetCatService.getPresetCatList();
        assertEquals(5, cats.size());
        assertTrue(containsCatNamed(cats, "Tortie Queen"));
        assertTrue(containsCatNamed(cats, "Tiger Queen"));
        assertTrue(containsCatNamed(cats, "Tabby Queen"));
        assertTrue(containsCatNamed(cats, "Tiger Tom"));
        assertTrue(containsCatNamed(cats, "Tabby Tom"));
    }

    @Test
    public void getPresetCatsHasExpectedSexDistributionTest() {
        List<Cat> cats = presetCatService.getPresetCatList();
        assertEquals(3, countCatsBySex(cats, Sex.FEMALE));
        assertEquals(2, countCatsBySex(cats, Sex.MALE));
    }

    private boolean containsCatNamed(List<Cat> cats, String name) {
        for (Cat cat : cats) {
            if (cat.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private int countCatsBySex(List<Cat> cats, Sex sex) {
        int count = 0;
        for (Cat cat : cats) {
            if (cat.getSex() == sex) {
                count++;
            }
        }
        return count;
    }
}

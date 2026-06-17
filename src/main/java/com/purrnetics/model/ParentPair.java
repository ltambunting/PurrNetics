package com.purrnetics.model;

import java.util.ArrayList;
import java.util.List;

public class ParentPair {
    private final Cat mother;
    private final Cat father;
    private List<Cat> offspring;

    public ParentPair(Cat mother, Cat father) {
        this.mother = mother;
        this.father = father;
        this.offspring = new ArrayList<>();
    }

    // GETTERS
    public Cat getMother() {
        return this.mother;
    }

    public Cat getFather() {
        return this.father;
    }

    public List<Cat> getOffspring() {
        return this.offspring;
    }

}

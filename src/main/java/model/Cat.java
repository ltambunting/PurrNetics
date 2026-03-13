package model;

import java.util.HashMap;
import java.util.Map;

public class Cat {
    private String name;
    private final Sex sex;
    private final ParentPair parents;
    private final Map<Gene, AllelePair> genotype;

    public Cat(String n, Sex s, ParentPair parents) {
        this.name = n;
        this.sex = s;
        this.parents = parents;
        this.genotype = new HashMap<>();
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    public Sex getSex() {
        return this.sex;
    }

    public ParentPair getParents() {
        return this.parents;
    }

    public Map<Gene, AllelePair> getGenotype() {
        return this.genotype;
    }

    // SETTERS
    public void setName(String newName) {
        this.name = newName;
    }
    
}
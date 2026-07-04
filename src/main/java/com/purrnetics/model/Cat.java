package com.purrnetics.model;

public class Cat {
    private String id;
    private String name;
    private final Sex sex;
    private final ParentPair parents;
    private final Genotype genotype;
    private final Phenotype phenotype;

    public Cat(String id, String name, Sex sex, ParentPair parents, Genotype genotype, Phenotype phenotype) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.parents = parents;
        this.genotype = genotype;
        this.phenotype = phenotype;
    }

    // GETTERS
    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Sex getSex() {
        return this.sex;
    }

    public ParentPair getParents() {
        return this.parents;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public Phenotype getPhenotype() {
        return this.phenotype;
    }

    // SETTERS
    public void setName(String newName) {
        this.name = newName;
    }
    
}
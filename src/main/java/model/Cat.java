package model;

import java.util.Map;

public class Cat {
    private String name;
    private final Sex sex;
    private final ParentPair parents;
    private final Genotype genotype;
    private final Phenotype phenotype;

    public Cat(String name, Sex sex, ParentPair parents, Genotype genotype, Phenotype phenotype) {
        this.name = name;
        this.sex = sex;
        this.parents = parents;
        this.genotype = genotype;
        this.phenotype = phenotype;
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
        return this.genotype.getInheritedAlleles();
    }

    public Map<Trait, String> getPhenotype() {
        return this.phenotype.getExpressedVariants();
    }

    // SETTERS
    public void setName(String newName) {
        this.name = newName;
    }
    
}
package model;

import java.util.ArrayList;
import java.util.List;

public class Gene {
    private final String name;
    private final String traitName;
    private final InheritanceRule ir;
    private final List<Allele> alleles;

    public Gene(String name, String traitName, InheritanceRule ir) {
        this.name = name;
        this.traitName = traitName;
        this.ir = ir;
        this.alleles = new ArrayList<>();
    }
}

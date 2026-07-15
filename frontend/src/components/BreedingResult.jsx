function BreedingResult({ result }) {
    if (!result) {
        return null;
    }

    return (
        <div>

            <h2>
                New Kitten 
            </h2>

            <p>
                Parents: {result.motherName} + {result.fatherName}
            </p>

            <h3>
                Genotype Outcomes
            </h3>

            {result.genes.map(gene => (
                <div key = {gene.geneName}>

                    <h4>
                        {gene.geneName}
                    </h4>
                    
                    <ul>
                        {gene.alleleOutcomes.map(allele => (
                            <li key = {allele.alleleSymbol}>
                                {allele.alleleSymbol}: {allele.probability * 100}%
                            </li>
                        ))}
                    </ul>
                </div>
                ))
            }

            <h3>
                Phenotype Outcomes
            </h3>

            {result.traits.map(trait => (
                <div key = {trait.traitName}>

                    <h4>
                        {trait.traitName}
                    </h4>
                    
                    <ul>
                        {trait.variants.map(variant => (
                            <li key = {variant.variant}>
                                {variant.variant}: {variant.probability * 100}%
                            </li>
                        ))}
                    </ul>
                </div>
                ))
            }


        </div>
    );
}

export default BreedingResult;
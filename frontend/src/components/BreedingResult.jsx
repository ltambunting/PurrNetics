import ProbabilityBar from "./ProbabilityBar";

function BreedingResult({ result }) {
    if (!result) {
        return null;
    }

    return (
        <div className = "breeding-result">

            <h2>
               🐈 New Kitten 🐈
            </h2>

            <p>
                Parents: {result.motherName} + {result.fatherName}
            </p>

            <h3>
               🧬 Genotype Outcomes 🧬
            </h3>

            {result.genes.map(gene => (
                <div 
                    key = {gene.geneName}
                    className = "gene-card"
                >

                    <h4>
                        {gene.geneName}
                    </h4>
                    
                    <div> 
                        {gene.alleleOutcomes.map(allele => (
                            <ProbabilityBar
                                key = {allele.alleleSymbol}
                                label = {allele.alleleSymbol}
                                probability = {allele.probability}
                            />
                        ))}
                </div>
            </div>
        ))}
            

            <h3>
              ✨ Phenotype Outcomes ✨
            </h3>

            {result.traits.map(trait => (
                <div 
                    key = {trait.traitName}
                    className = "trait-card"
                >

                    <h4>
                        {trait.traitName}
                    </h4>

                    <div>
                        {trait.variants.map(variant => (
                            <ProbabilityBar
                                key = {variant.variant}
                                label = {variant.variant}
                                probability = {variant.probability}
                                />
                        ))}
                    </div>
                </div>
                ))
            }

            {result.sexLinkedTraits.map(trait => (
                <div
                    key = {trait.traitName}
                    className = "trait-card"
                >
                    <h4>{trait.traitName}</h4>
                    {trait.outcomes.map(sexOutcome => (
                        <div key = {sexOutcome.sex}>
                            <h5>{sexOutcome.sex}</h5>
                        {sexOutcome.outcomes.map(variant => (
                            <ProbabilityBar
                                key = {variant.variant}
                                label = {variant.variant}
                                probability = {variant.probability}
                            />
                        ))}
                </div>
            ))}
        </div>
        ))}
    </div>
    );
}

export default BreedingResult;
// Component displaying a cat's information
function CatCard({ cat }) {
    if (!cat) {
        return null;
    }

    return (
        <div>
            <h2> {cat.name} </h2>

            <p>Sex: {cat.sex}</p>

            <h3>
                Genetics
            </h3>

            <ul>
                {
                    cat.inheritedAlleles.map(allele => (
                        <li key = {allele.trait}>
                            {allele.trait}: {allele.inheritedAllelePair}
                        </li>
                    ))

                }
            </ul>

            <h3>
                Traits
            </h3>

            <ul>{
                    cat.expressedTraits.map(trait => (
                       <li key = {trait.traitName}> 
                        {trait.traitName}: {trait.expressedVariant}
                       </li>
                    ))
                }
            </ul>

        </div>
    );
}

export default CatCard;
import PossibleKittenCard from "./PossibleKittenCard";

function PossibleKittenOutcome({ kittens }) {
    if (kittens.length === 0) {
        return null;
    }
    return (
        <div className="possible-kitten-section">
            <div className = "possible-kitten-grid">
            {kittens.map((kitten, index) => (
                <PossibleKittenCard
                    key = {index}
                    kitten = {kitten}
                />
            ))}
        </div>
        <div className = "genetics-note">
            <strong> Genetics Note </strong>
            <p>
                You may notice that non-agouti/non-agouti results in some stripes in orange cats. The orange coat colour involves additional gene interactions called <strong>epistasis</strong>. 
                In real cats, the orange gene causes orange cats to display tabby markings regardless of their agouti genotype. PurrNetics V1 simplifies this interaction while still
                distinguishing the underlying genotypes visually. Full epistatic modeling is planned for V2.
            </p>
        </div>
    </div>
    );
}

export default PossibleKittenOutcome;
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
    </div>
    );
}

export default PossibleKittenOutcome;
import { getSprite } from "../utils/SpriteMapper";
import CatSprite from "./CatSprite";
import { AGE_STAGE } from "../constants/ageStages";

function PossibleKittenCard({ kitten }) {
    return(
        <div className = "possible-kitten-card">
            <h2>Possible Kitten</h2>
            <CatSprite
                cat = {kitten}
                ageStage = {AGE_STAGE.KITTEN}
            />
            <h3>{kitten.sex}</h3>
            <p>
                Probability: {" "}
                {(kitten.probability * 100).toFixed(2)}%
            </p>
            <h3> Traits ✨</h3>
            <ul>
                {kitten.expressedTraits.map((trait) => (
                    <li key = {trait.traitName}>
                        {trait.traitName}: {trait.expressedVariant}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PossibleKittenCard;
import { getSprite } from "../utils/spriteMapper";

function CatSprite({ cat, ageStage }) {
    const sprite = getSprite(
        cat,
        ageStage
    );

    console.log("sprite value:", sprite);
    return(
        <img
            src = {sprite}
            alt= {`${cat.name} sprite`}
            className = "cat-sprite"
        />
    );
}

export default CatSprite;
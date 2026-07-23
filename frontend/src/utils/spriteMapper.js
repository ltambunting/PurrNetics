import adultOrangeTabbyShort from "../assets/sprites/adult/orange-tabby-adult.png";
import adultTabbyShort from "../assets/sprites/adult/non-orange-tabby-adult.png";
import adultTortieShort from "../assets/sprites/adult/mosaic-adult.png";

function getExpressedTrait(cat, traitName) {
    const trait = cat.expressedTraits.find(
        t => t.traitName === traitName
    );

    if (trait) {
        return trait.expressedVariant;
    }

    return undefined;
}

function getAppearance(cat) {
    const orangeTrait = getExpressedTrait(
        cat,
        "Orange Fur"
    );

    const agoutiTrait = getExpressedTrait(
        cat,
        "Agouti Fur"
    );

    const furLengthTrait = getExpressedTrait(
        cat,
        "Fur Length"
    );

    let colour;
    if (orangeTrait === "Mosaic") {
        colour = "TORTIE";
    } else if (orangeTrait === "Orange fur") {
        colour = "ORANGE";
    } else {
        colour = "NON_ORANGE";
    }

    let pattern;
    if (agoutiTrait === "Agouti fur") {
        pattern = "TABBY";
    } else {
        pattern = "SOLID";
    }

    let furLength;
    if (furLengthTrait === "Short hair") {
        furLength = "SHORT";
    } else {
        furLength = "LONG";
    }

    return {
        colour,
        pattern,
        furLength
    };

}

function createSpriteKey(appearance, ageStage) {
    return [
        ageStage,
        appearance.colour,
        appearance.pattern,
        appearance.furLength
    ].join("_");
}

// PNG lookup table
const spriteLibrary = {
    "ADULT_ORANGE_TABBY_SHORT":
        adultOrangeTabbyShort,
    "ADULT_NON_ORANGE_TABBY_SHORT":
        adultTabbyShort,
    "ADULT_TORTIE_TABBY_SHORT":
        adultTortieShort,

    // "KITTEN_ORANGE_TABBY_SHORT":
    //     kittenOrangeTabbyShort,
    // "KITTEN_NON_ORANGE_TABBY_SHORT":
    //     kittenTabbyShort,
    // "KITTEN_TORTIE_TABBY_SHORT":
    //     kittenTortieShort
    
}

export function getSprite(cat, ageStage) {
    const appearance = getAppearance(cat);
    const key = createSpriteKey(
        appearance,
        ageStage
    );
    console.log("Generated key:", key);
    console.log("Available sprites:", Object.keys(spriteLibrary));
    return spriteLibrary[key];
}
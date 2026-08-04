import adultOrangeTabbyShort from "../assets/sprites/adult/orange-tabby-adult.png";
import adultTabbyShort from "../assets/sprites/adult/non-orange-tabby-adult.png";
import adultTortieShort from "../assets/sprites/adult/mosaic-adult.png";

import kittenOrangeTabbyShort from "../assets/sprites/kitten/orange-agouti-short-kitten.png";
import kittenOrangeTabbyLong from "../assets/sprites/kitten/orange-agouti-long-kitten.png";
import kittenMosaicTabbyShort from "../assets/sprites/kitten/mosaic-agouti-short-kitten.png";
import kittenMosaicTabbyLong from "../assets/sprites/kitten/mosaic-agouti-long-kitten.png";
import kittenMosaicSolidShort from "../assets/sprites/kitten/mosaic-nonagouti-short-kitten.png";
import kittenMosaicSolidLong from "../assets/sprites/kitten/mosaic-nonagouti-long-kitten.png";
import kittenNonOrangeTabbyShort from "../assets/sprites/kitten/nonorange-agouti-short-kitten.png";
import kittenNonOrangeTabbyLong from "../assets/sprites/kitten/nonorange-agouti-long-kitten.png";
import kittenNonOrangeSolidShort from "../assets/sprites/kitten/nonorange-nonagouti-short-kitten.png";
import kittenNonOrangeSolidLong from "../assets/sprites/kitten/nonorange-nonagouti-long-kitten.png";
import kittenOrangeSolidLong from "../assets/sprites/kitten/orange-nonagouti-long-kitten.png";
import kittenOrangeSolidShort from "../assets/sprites/kitten/orange-nonagouti-short-kitten.png";


function getExpressedTrait(traits, traitName) {
    const trait = traits.expressedTraits.find(
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
// note that "solid" tabby still has agouti ticks to show epistasis in future
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

    "KITTEN_ORANGE_TABBY_SHORT":
        kittenOrangeTabbyShort,

    "KITTEN_ORANGE_TABBY_LONG":
        kittenOrangeTabbyLong,


    "KITTEN_TORTIE_TABBY_SHORT":
        kittenMosaicTabbyShort,

    "KITTEN_TORTIE_TABBY_LONG":
        kittenMosaicTabbyLong,


    "KITTEN_TORTIE_SOLID_SHORT":
        kittenMosaicSolidShort,

    "KITTEN_TORTIE_SOLID_LONG":
        kittenMosaicSolidLong,


    "KITTEN_NON_ORANGE_TABBY_SHORT":
        kittenNonOrangeTabbyShort,

    "KITTEN_NON_ORANGE_TABBY_LONG":
        kittenNonOrangeTabbyLong,


    "KITTEN_NON_ORANGE_SOLID_SHORT":
        kittenNonOrangeSolidShort,

    "KITTEN_NON_ORANGE_SOLID_LONG":
        kittenNonOrangeSolidLong,

    "KITTEN_ORANGE_SOLID_SHORT": 
        kittenOrangeSolidShort,

    "KITTEN_ORANGE_SOLID_LONG":
        kittenOrangeSolidLong
    
}

export function getSprite(traits, ageStage) {
    const appearance = getAppearance(traits);
    const key = createSpriteKey(
        appearance,
        ageStage
    );
    console.log("Generated key:", key);
    console.log("Available sprites:", Object.keys(spriteLibrary));
    return spriteLibrary[key];
}
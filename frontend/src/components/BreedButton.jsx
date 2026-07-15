import {breedCats} from "../services/PurrneticsApi";

function BreedButton({mother, father, setBreedingResult}) {
    // inner event handling function
    if (!mother || !father) {
        console.log("Please select parent cats");
        return;
    }
    
    async function handleBreed() {
        try {
            const result = await breedCats(
                mother.catId,
                father.catId
            );

            setBreedingResult(result);
            console.log(result);
        } catch (error) {
            console.error(error);
        }
    }
        return (
            <button onClick={handleBreed}>
                Breed
            </button>
        );
}

export default BreedButton;
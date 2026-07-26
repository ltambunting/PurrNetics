import { useEffect, useState} from "react";
import CatCard from "./components/CatCard";
import CatSelector from "./components/CatSelector";
import BreedButton from "./components/BreedButton";
import BreedingResult from "./components/BreedingResult";
import PossibleKittenOutcome from "./components/PossibleKittenOutcome";
import { getCats, breedCats } from "./services/PurrneticsApi.js";
import "./App.css";

function App() {
    const [cats, setCats] = useState([]); // initial value = empty list
    const [femaleCat, setFemaleCat] = useState(null);
    const [maleCat, setMaleCat] = useState(null); // initial value = null as not cat selected
    const [breedingResult, setBreedingResult] = useState(null);
    const [possibleKittens, setPossibleKittens] = useState([]); //possible outcomes after breeding

    useEffect(() => {
        getCats().then(data => {
            setCats(data);
        });
    }, []);

    return (
        <div className = "app">

            <h1> 🐱 Welcome to PurrNetics! 🧬</h1>

            <div className = "parent-container">

                <div className = "parent-column">

                    <CatSelector
                        cats = {cats}
                        sex = "FEMALE"
                        selectedCat = {femaleCat}
                        setSelectedCat= {setFemaleCat}
                    />

                    <CatCard cat = {femaleCat}/>
                </div>

                <div className = "parent-column">
                    <CatSelector
                        cats = {cats}
                        sex = "MALE"
                        selectedCat = {maleCat}
                        setSelectedCat= {setMaleCat}
                    />

                    <CatCard cat = {maleCat}/>
                </div>

            </div>

            <div className = "breed-section">
                <BreedButton
                    mother = {femaleCat}
                    father = {maleCat}
                    setBreedingResult={setBreedingResult}
                    setPossibleKittens={setPossibleKittens}
                />
            </div>
            
            <div className = "result-section">
                <BreedingResult result = {breedingResult}/>
            </div>

            <PossibleKittenOutcome  
                kittens={possibleKittens}
            />

        </div>

    );

}

export default App;
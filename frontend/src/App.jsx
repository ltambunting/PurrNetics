import { useEffect, useState} from "react";
import CatCard from "./components/CatCard";
import CatSelector from "./components/CatSelector";
import BreedButton from "./components/BreedButton";
import BreedingResult from "./components/BreedingResult";
import { getCats, breedCats } from "./services/PurrneticsApi.js";
import "./App.css";

function App() {
    const [cats, setCats] = useState([]); // initial value = empty list
    const [femaleCat, setFemaleCat] = useState(null);
    const [maleCat, setMaleCat] = useState(null); // initial value = null as not cat selected
    const [breedingResult, setBreedingResult] = useState(null);

    useEffect(() => {
        getCats().then(data => {
            setCats(data);
        });
    }, []);

    return (
        <div>

            <h1> Welcome to PurrNetics! </h1>

            <div className = "selector-container">

                <CatSelector
                    cats = {cats}
                    sex = "FEMALE"
                    selectedCat = {femaleCat}
                    setSelectedCat= {setFemaleCat}
                />

                <CatCard cat = {femaleCat}/>

                <CatSelector
                    cats = {cats}
                    sex = "MALE"
                    selectedCat = {maleCat}
                    setSelectedCat= {setMaleCat}
                />

                <CatCard cat = {maleCat}/>

            </div>

            <div>
                <BreedButton
                    mother = {femaleCat}
                    father = {maleCat}
                    setBreedingResult={setBreedingResult}
                />
            </div>

            <div>
                <BreedingResult result = {breedingResult}/>
            </div>

        </div>

    );

}

export default App;
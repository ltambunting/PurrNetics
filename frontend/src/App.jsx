import { useEffect, useState} from "react";
import CatCard from "./components/CatCard";
import CatSelector from "./components/CatSelector";
import { getCats } from "./services/PurrneticsApi";

function App() {
    const [cats, setCats] = useState([]); // initial value = empty list
    const [femaleCat, setFemaleCat] = useState(null);
    const [maleCat, setMaleCat] = useState(null); // initial value = null as not cat selected

    useEffect(() => {
        getCats().then(data => {
            setCats(data);
        });
    }, []);

    return (

        <div>

            <h1>PurrNetics</h1>

            <CatCard />

        </div>

    );

}

export default App;
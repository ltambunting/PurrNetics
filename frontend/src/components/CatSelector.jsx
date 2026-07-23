// show dropdown of preset cats and tell App when user picks one
function CatSelector({
    cats,
    sex,
    selectedCat,
    setSelectedCat
}) {
    const displaySex = sex.charAt(0) + sex.slice(1).toLowerCase();
    const icon = sex === "FEMALE" ? "♀️" : "♂️";

    return (
        <div>
            <h3>
                {icon} Choose {displaySex} cat         
            </h3>
            <select
                onChange = {(event) => {
                    const chosenCat = cats.find(
                        cat => cat.name === event.target.value
                    );
                    setSelectedCat(chosenCat);
                    }}
                >
                    <option>
                        Select a cat
                    </option>
                    {
                        cats.filter(cat => cat.sex === sex).map(cat => (<option key = {cat.name}>{cat.name}
                        </option>))
                    }
                </select>
        </div>
    );
}

export default CatSelector;
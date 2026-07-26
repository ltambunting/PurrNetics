const URL_PATH = "http://localhost:8080";

export async function getCats() {
    const response = await fetch(`${URL_PATH}/cats/list`);
    return response.json();
}

export async function breedCats(motherId, fatherId) {
    const response = await fetch(`${URL_PATH}/breed/result`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                motherId: motherId,
                fatherId: fatherId
            })
        }
    )

    return response.json();
}

export async function getPossibleKittens(motherId, fatherId) {
    const response = await fetch (`${URL_PATH}/breed/possible-kittens`, {
        method: "POST",
        headers: {
                "Content-Type": "application/json"
        },
        body: JSON.stringify({
            motherId: motherId,
            fatherId: fatherId
        })
    });
    return response.json();
}
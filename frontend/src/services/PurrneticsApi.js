const URL_PATH = "http://localhost:8080";

export async function getCats() {
    const response = await fetch(`${URL_PATH}/cats/list`);
    return response.json();
}
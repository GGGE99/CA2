const URL = 'http://localhost:8080/jpareststarter/api/person/'
const SERVER_URL = "https://marcge.dk/tomcat/hobbies/api/person/"
function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }
    return res.json();
}

function makeOptions(method, body) {
    var opts = {
        method: method,
        headers: {
            "Content-type": "application/json",
            "Accept": "application/json"
        }
    }
    if (body) {
        opts.body = JSON.stringify(body);
    }
    return opts;
}

function mapPerson(persons){
   return persons.map(person => `
    <tr>
        <td>${person.id}</td>
        <td>${person.name}</td>
        <td>${person.email}</td>
        <td>${person.gender}</td>
        <td>${person.birthday}</td>
        <td>${person.street}</td>
        <td>${person.zipCode}</td>
        <td>${person.city}</td>
        <td>${person.hobbies.map(hobby => hobby.name).join(",<br>")}</td>
        <td>${person.phones.map(phone => phone.number).join(",<br>")}</td>
    </tr>
    `)
}

function getAllPersons() {
    return fetch(SERVER_URL + "all")
        .then(handleHttpErrors)
}

function getHobbyById(id) {
    // https://marcge.dk/tomcat/hobbies/api/
    
    return fetch("http://localhost:8080/jpareststarter/api/hobby/"+id)
        .then(handleHttpErrors)
}

function getById(id) {
    return fetch(SERVER_URL + id)
        .then(handleHttpErrors)
}

function getByhobbyId(id) {
    return fetch(SERVER_URL + "hobby/" + id)
        .then(handleHttpErrors)
}

function addPerson(person) {
    const options = makeOptions("POST", person)
    return fetch(SERVER_URL, options)
        .then(handleHttpErrors)
}

function editPerson(person, id) {
    const options = makeOptions("PUT", person)
    return fetch(SERVER_URL + id , options)
        .then(handleHttpErrors)
}

const personFacade = {
    getAllPersons,
    getById,
    mapPerson,
    addPerson,
    editPerson,
    getByhobbyId,
    getHobbyById
}

export default personFacade;
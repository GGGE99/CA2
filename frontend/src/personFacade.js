
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

const URL = 'http://localhost:8080/jpareststarter/api/person/'

function getAllPersons() {
    return fetch(URL + "all")
        .then(handleHttpErrors)
}

function getById(id) {
    return fetch(URL + id)
        .then(handleHttpErrors)
}

const personFacade = {
    getAllPersons,
    getById,
    mapPerson,
}

export default personFacade;
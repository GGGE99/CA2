import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import personFacade from "./personFacade"

/* 
  Add your JavaScript for all exercises Below or in separate js-files, which you must the import above
*/
document.getElementById("error").style.display = "none"
/* JS For Exercise-1 below */


/* JS For Exercise-2 below */



/* JS For Exercise-3 below */
/*Her starter den function der laver en liste af alle personer*/
document.getElementById("getAllBTN").addEventListener("click", makeListOfAllUsers);
function makeListOfAllUsers() {
  personFacade.getAllPersons()
    .then(data => {
      document.getElementById("error").style.display = "none"  /* søger for at fjerne error div'en når functionen bliver kaldt efter en fejl */
      const persons = data.all;
      const tableRows = personFacade.mapPerson(persons);
      const tableRowsAsString = tableRows.join("");
      document.getElementById("allUserRows").innerHTML = tableRowsAsString;
    })
    .catch(err => {
      if (err.status) {
        document.getElementById("error").style.display = "block"
        err.fullError.then(e => {
          document.getElementById("error").innerHTML = e.detail
          console.log(e.detail)
        })
      }
      else { console.log("Network error"); }
    });
}
/*Her slutter den function der laver en liste af alle personer*/

/*Her starter find person by id */
document.getElementById("findByBTN").addEventListener("click", findByID);
function findByID() {
  let id = document.getElementById("findByID").value;
  personFacade.getById(id)
    .then(data => {
      document.getElementById("error").style.display = "none" /* søger for at fjerne error div'en når functionen bliver kaldt efter en fejl */
      const persons = [];
      persons.push(data);
      const tableRow = personFacade.mapPerson(persons);
      const tableRowAsString = tableRow.join("");
      document.getElementById("allUserRows").innerHTML = tableRowAsString;
    })
    .catch(err => {
      if (err.status) {
        document.getElementById("error").style.display = "block"
        err.fullError.then(e => {
          document.getElementById("error").innerHTML = e.detail
          console.log(e.detail)
        })
      }
      else { console.log("Network error"); }
    });
}
/*Her slutter find person by id*/

/*Her starter post person */

function add() {
  let name = document.getElementById("userName").value;
  let gender = document.getElementById("gender").value;
  let email = document.getElementById("email").value;
  let birthday = document.getElementById("birthday").value;
  let number = document.getElementById("phoneNumber").value;
  let phoneDescription = document.getElementById("phoneDescription").value;
  let phones = [{ "number": number, "description": phoneDescription }];
  let zipCode = document.getElementById("zipcode").value;
  let street = document.getElementById("street").value;
  // let city = document.getElementById("city").value;
  let hobby = document.getElementById("hobby").value;
  let hobbiesID = hobby.trim().split(",");

  let newPerson = {
    name,
    gender,
    email,
    birthday,
    phones,
    zipCode,
    street,
    // city,
    hobbiesID,
  }

  personFacade.addPerson(newPerson)
    .then(makeListOfAllUsers, document.getElementById("error").style.display = "none")
    .catch(err => {
      if (err.status) {
        document.getElementById("error").style.display = "block"
        err.fullError.then(e => {
          document.getElementById("error").innerHTML = e.detail
          console.log(e.detail)
        })
      }
      else { console.log("Network error"); }
    });
}
document.getElementById("addUserBTN").addEventListener("click", add)


/*Her slutter post person */

/*Her starter edit person */
document.getElementById("findUserToEditBTN").addEventListener("click", (evt) => {
  let ID = document.getElementById("personID").value;
  personFacade.getById(ID).then(data => {
    document.getElementById("editUserName").value = data.name
    document.getElementById("editGender").value = data.gender
    document.getElementById("editEmail").value = data.email
    document.getElementById("editBirthday").value = data.birthday
    data.phones.forEach(element => {
      document.getElementById("editPhoneNumber").value = element.number
    });
    data.phones.forEach(element => {
      document.getElementById("editPhoneDescription").value = element.description
    });
    document.getElementById("editZipcode").value = data.zipCode
    document.getElementById("editStreet").value = data.street
    let hobbies = "";
    
    data.hobbies.forEach(element => {
      hobbies += element.id + ","
      
    });
    hobbies = hobbies.substring(0,hobbies.length - 1)
    
    document.getElementById("editHobby").value = hobbies

  })
});

document.getElementById("editUserBTN").addEventListener("click", (evt) => {
  let name = document.getElementById("editUserName").value 
  let gender = document.getElementById("editGender").value
  let email = document.getElementById("editEmail").value
  let birthday = document.getElementById("editBirthday").value 
  let phones = [{ "number": document.getElementById("editPhoneNumber").value , "description": document.getElementById("editPhoneDescription").value }];
  let zipCode = document.getElementById("editZipcode").value
  let street = document.getElementById("editStreet").value
  let hobby = document.getElementById("editHobby").value
  let hobbiesID = hobby.trim().split(",");


  let editedPerson = {
    name,
    gender,
    email,
    birthday,
    phones,
    zipCode,
    street,
    hobbiesID,
  }
  console.log(editedPerson)

  personFacade.editPerson(editedPerson, document.getElementById("personID").value)
    .then(makeListOfAllUsers, document.getElementById("error").style.display = "none")
    .catch(err => {
      if (err.status) {
        document.getElementById("error").style.display = "block"
        err.fullError.then(e => {
          document.getElementById("error").innerHTML = e.detail
          console.log(e.detail)
        })
      }
      else { console.log("Network error"); }
    });
})


/*Her slutter edit person */

/* 
Do NOT focus on the code below, UNLESS you want to use this code for something different than
the Period2-week2-day3 Exercises
*/

function hideAllShowOne(idToShow) {
  document.getElementById("about_html").style = "display:none"
  document.getElementById("ex1_html").style = "display:none"
  document.getElementById("ex2_html").style = "display:none"
  document.getElementById("ex3_html").style = "display:none"
  document.getElementById(idToShow).style = "display:block"
}

function menuItemClicked(evt) {
  const id = evt.target.id;
  switch (id) {
    case "ex1": hideAllShowOne("ex1_html"); break
    case "ex2": hideAllShowOne("ex2_html"); break
    case "ex3": hideAllShowOne("ex3_html"); break
    default: hideAllShowOne("about_html"); break
  }
  evt.preventDefault();
}
document.getElementById("menu").onclick = menuItemClicked;
hideAllShowOne("about_html");




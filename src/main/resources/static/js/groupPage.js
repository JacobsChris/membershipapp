document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});



//Remember to change this when machines update

function makeGroupTable() {
    document.getElementById("tableHeading").innerHTML = "Groups";

    var groupTable = new Tabulator("#groupTable", {
        persistence: {
            sort: true,
        },
        persistenceID: "groupPersistence",
        layout: "fitColumns",
        ajaxURL: address + "MembershipApp/gathering/getAll",
        rowClick: function (e, row) {
            makeMemberTable(row._row.data["id"], row._row.data["location"]);
            var currentGroupID = row._row.data.id;
        },
        columns: [
            {
                title: "Location",
                field: "location",
            },

        ],
    });
    removeElement("memberTable");
    createElementWithID("div", "memberTable");
}


function makeMemberTable(currentGroupID, currentGroupName) {
    document.getElementById("tableHeading").innerHTML = currentGroupName + " Members";


    let memberTable = new Tabulator("#memberTable", {
        dataEdited: function (data) {
            // data - the updated table data
            submitDataChanges(data);


            // console.log("call back triggered")
        },


        persistence: {sort: true,},
        persistenceID: "memberPersistence",
        layout: "fitColumns",
        ajaxURL: address + "/MembershipApp/gathering/getMembers/" + currentGroupID,

        columns: [
            {
                title: "First Name",
                field: "firstName",
                editor: "input",
                align: "center",
                validator: ["required", "minLength:2", "maxLength:25"]
            },
            {
                title: "Second Name",
                field: "lastName",
                editor: "input",
                align: "center",
                validator: ["required", "minLength:2", "maxLength:25"]
            },
            {
                title: "Gloves",
                field: "hasGloves",
                align: "center",
                formatter: "tickCross",
                editor: "tickCross"
            },
            {
                title: "Membership",
                field: "paidMembership",
                align: "center",
                formatter: "tickCross",
                editor: "tickCross"
            },
            {
                title: "Shoes",
                field: "hasShoes",
                align: "center",
                formatter: "tickCross",
                editor: "tickCross"
            },
            {
                title: "Clothes",
                field: "hasClothes",
                align: "center",
                formatter: "tickCross",
                editor: "tickCross"
            },
            {
                title: "Officer",
                field: "isGatheringOfficer",
                align: "center",
                formatter: "tickCross",
                editor: "tickCross"
            },

        ],
    });
    removeElement("groupTable");
    createElementWithID("div", "groupTable");


    let table = document.getElementById("groupTable");


    let goBackButton = document.createElement("button");
    goBackButton.id = "goBackButton";
    goBackButton.innerHTML = "Go Back To Groups";
    table.appendChild(goBackButton);
    goBackButton.addEventListener("click", function () {
        makeGroupTable();
    });

    let addMemberButton = document.createElement("button");
    addMemberButton.id = "addMemberButton";
    addMemberButton.innerHTML = "Add New Member";
    table.appendChild(addMemberButton);

    let tempMemberJSON = JSON.stringify({firstName: "First name here", lastName: "Second name here"});
    addMemberButton.addEventListener("click", function () {
        addMember(tempMemberJSON, currentGroupID)
            .then(function () {
                memberTable.addRow(tempMemberJSON, false)
            })
            .then(function () {
                goBackButton.disabled = true;
                addMemberButton.disabled = true;
            })
            .then(function () {
                memberTable.setData(address + "/MembershipApp/gathering/getMembers/" + currentGroupID);
            })
    });


    let deleteMemberButton = document.createElement("button");
    deleteMemberButton.id = "deleteMemberButton";
    deleteMemberButton.innerHTML = "Delete Member";
    table.appendChild(deleteMemberButton);
    deleteMemberButton.addEventListener("click", function () {
        deleteMember(memberTable, (memberTable.getData()));
    });

    let instructionsText = document.createElement("ul");
    instructionsText.id = "instructionsText";
    instructionsText.innerHTML = "How to use:";
    instructionsText.innerHTML += "<li>Click on any cell to edit the data.  It will automatically update the database.</li>";
    instructionsText.innerHTML += "<li>The full name must be unique and both names must be between 2 and 25 characters.</li>";
    table.appendChild(instructionsText)

}

function submitDataChanges(data) {
    let listOfFirstNames = [];
    let listOfSecondNames = [];


    for (let i = 0; i < data.length; i++) {
        let memberData = data[i];

        memberData["firstName"] = memberData["firstName"]
            .toLowerCase()
            .replace(/^\w/, c => c.toUpperCase());
        memberData["lastName"] = memberData["lastName"]
            .toLowerCase()
            .replace(/^\w/, c => c.toUpperCase());


        let firstName = memberData["firstName"];
        let secondName = memberData["lastName"];

        if (firstName === "First name here" || secondName === "Second name here") {
            // console.log("if name equals filler text");
            extraInstructionMaker();
            document.getElementById("goBackButton").disabled = true;
            document.getElementById("addMemberButton").disabled = true;

            break
        } else if (listOfFirstNames.includes(firstName) && listOfSecondNames.includes(secondName)) {
            // console.log("not unique name");
            nameInstructionsMaker();
            document.getElementById("goBackButton").disabled = true;
            document.getElementById("addMemberButton").disabled = true;

            break

        } else {

            // console.log("else");
            if (memberData.hasOwnProperty("id")) {
                let memberID = memberData["id"];
                let memberJSON = memberData;
                delete memberJSON.id;
                memberJSON = JSON.stringify(memberJSON);
                $.ajax({
                    url: address + "/MembershipApp/member/update/" + memberID,
                    type: "PUT",
                    data: memberJSON,
                    contentType: "application/json",
                    dataType: "jsonp"
                });
                cleanUpText();
                document.getElementById("goBackButton").disabled = false;
                document.getElementById("addMemberButton").disabled = false;

                listOfFirstNames.push(firstName);
                listOfSecondNames.push(secondName);

            }
        }
    }
}


function addMember(tempMemberJSON, currentGroupID) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: address + "/MembershipApp/member/create/" + currentGroupID,
            type: "POST",
            data: tempMemberJSON,
            contentType: "application/json",
            dataType: "jsonp"
        }).done((response) => {
            resolve(response);
        }).fail((error) => {
            if (error.statusCode()["status"] === 409) {
                nameInstructionsMaker()
            }
            reject(error);
        }).then(function () {
            extraInstructionMaker()
        });
    });
}


function extraInstructionMaker() {
    let instructionText = document.getElementById("instructionsText");
    createElementWithID("p", "extraInstructions");
    let extraInstructions = document.getElementById("extraInstructions");
    instructionText.appendChild(extraInstructions);
    extraInstructions.innerHTML = "Please fill in your new member's details.  You cannot return to groups until you have"
}

function nameInstructionsMaker() {
    let instructionText = document.getElementById("instructionsText");

    createElementWithID("p", "nameInstructions");
    let nameInstructions = document.getElementById("nameInstructions");
    instructionText.appendChild(nameInstructions);
    nameInstructions.innerHTML = "Someone in the database already has that name combination.  Please try another.  You cannot return to groups until you have a unique name combination.  Note, these are not case sensitive."

}


function deleteMember(memberTable, data) {

    // console.log(data);

    if (confirm("Do you really want to delete a member?  This action cannot be undone.")) {
        // console.log("deleting things!");

        // var memberTable = document.getElementById("memberTable");

        var firstName = prompt("Please enter the first name of the member to delete");
        var secondName = prompt("Please enter the second name of the member to delete");

        for (let i = 0; i < data.length; i++) {
            let memberData = data[i];
            if ((memberData["firstName"] === firstName) && (memberData["lastName"] === secondName)) {
                let memberID = memberData["id"];
                // console.log(memberID);


                $.ajax({
                    url: address + "/MembershipApp/member/delete/" + memberID,
                    type: "DELETE",
                }).then(memberTable.setData())
            }
        }


    } else {
        // console.log("Not deleting things")
    }


}


function removeElement(elementId) {
    let element = document.getElementById(elementId);
    element.parentNode.removeChild(element);
}

function createElementWithID(elementTag, elementId) {
    let groupTable = document.createElement(elementTag);
    groupTable.id = elementId;
    let body = document.getElementsByTagName("body")[0];
    body.appendChild(groupTable)

}

function cleanUpText() {
    try {
        removeElement("extraInstructions");
    } catch {
    }
    try {
        removeElement("nameInstructions");
    } catch {
    }
}



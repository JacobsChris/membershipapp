document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
    // let tableHeading = document.getElementById("tableHeading");
});


function makeGroupTable() {
    document.getElementById("tableHeading").innerHTML = "Groups";

    var groupTable = new Tabulator("#groupTable", {
        persistence: {
            sort: true,
        },
        persistenceID: "groupPersistence",
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getAll",
        rowClick: function (e, row) {
            makeMemberTable(row._row.data["id"], row._row.data["location"]);
            var currentGroupID = row._row.data.id;
        },
        columns: [
            {
                title: "Location",
                field: "location",
                // align: "center"
            },

        ],
    });
    removeElement("memberTable");
    createElementWithID("div", "memberTable");
}


function makeMemberTable(currentGroupID, currentGroupName) {
    document.getElementById("tableHeading").innerHTML = currentGroupName + " Members";


    var memberTable = new Tabulator("#memberTable", {
        dataEdited: function (data) {
            //data - the updated table data
            submitDataChanges(data);
            alert("Member updated!");
        },


        persistence: {
            sort: true,
            // columns: true,
        },
        persistenceID: "memberPersistence",
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/" + currentGroupID,

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
    goBackButton.innerHTML = "Go Back To Groups";
    table.appendChild(goBackButton);
    goBackButton.addEventListener("click", function () {
        makeGroupTable();
    });

    let addMemberButton = document.createElement("button");
    addMemberButton.innerHTML = "Add New Member";
    table.appendChild(addMemberButton);
    let tempMemberJSON = JSON.stringify({firstName: "First name here", lastName: "Second name here"});
    addMemberButton.addEventListener("click", function () {
        memberTable.addRow(tempMemberJSON, false).then(function () {
                addMember(tempMemberJSON, currentGroupID)
            }
        )
    });


    let deleteMemberButton = document.createElement("button");
    deleteMemberButton.innerHTML = "Delete Member";
    table.appendChild(deleteMemberButton);
    deleteMemberButton.addEventListener("click", function () {
        deleteMember(memberTable, (memberTable.getData()));
    });

    let instructionsText = document.createElement("p");
    instructionsText.innerHTML = "Click on any cell to edit the data.  It will automatically update the database.  The full name must be unique and both names must be between 2 and 25 characters."
    table.appendChild(instructionsText)

}

function submitDataChanges(data) {
    for (let i = 0; i < data.length; i++) {
        let memberData = data[i];

        if (memberData.hasOwnProperty("id")) {
            let memberID = memberData["id"];
            let memberJSON = memberData;
            delete memberJSON.id;
            memberJSON = JSON.stringify(memberJSON);

            $.ajax({
                url: "http://localhost:8080/member/update/" + memberID,
                type: "PUT",
                data: memberJSON,
                contentType: "application/json"
            })
        }
    }
}

function addMember(tempMemberJSON, currentGroupID) {
    $.ajax({
        url: "http://localhost:8080/member/create/" + currentGroupID,
        type: "POST",
        data: tempMemberJSON,
        contentType: "application/json"
    })
}

function deleteMember(memberTable, data) {

    console.log(data);

    if (confirm("Do you really want to delete a member?  This action cannot be undone.")) {
        console.log("deleting things!");

        // var memberTable = document.getElementById("memberTable");

        var firstName = prompt("Please enter the first name of the member to delete");
        var secondName = prompt("Please enter the second name of the member to delete");

        for (let i = 0; i < data.length; i++) {
            let memberData = data[i];
            if ((memberData["firstName"] === firstName) && (memberData["lastName"] === secondName)) {
                let memberID = memberData["id"];
                console.log(memberID);


                $.ajax({
                    url: "http://localhost:8080/member/delete/" + memberID,
                    type: "DELETE",
                }).then(memberTable.setData())
            }
        }


    } else {
        console.log("Not deleting things")
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


/*
TODO add member functionality: should just be add button and then form

TODO delete member functionality: might just be a button and then type form
 */


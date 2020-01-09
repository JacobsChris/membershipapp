document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});


function makeGroupTable() {
    var groupTable = new Tabulator("#groupTable", {
        persistence: {
            sort: true,
            columns: true,
        },
        persistenceID: "groupPersistence",
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getAll",
        rowClick: function (e, row) {
            makeMemberTable(row._row.data.id);
            var currentGroupID = row._row.data.id;
        },
        columns: [
            {title: "ID", field: "id"},
            {title: "Location", field: "location"},

        ],
    });
    removeElement("memberTable");
    createElementWithID("div", "memberTable");
}


function makeMemberTable(currentGroupID) {

    var memberTable = new Tabulator("#memberTable", {
        dataEdited: function (data) {
            //data - the updated table data
            submitDataChanges(data);
            alert("Member updated!");
        },


        persistence: {
            sort: true,
            columns: true,
        },
        persistenceID: "memberPersistence",
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/" + currentGroupID,

        columns: [
            {
                title: "First Name",
                field: "firstName",
                editor: "input",
                validator: ["required", "minLength:2", "maxLength:25"]
            },
            {
                title: "Second Name",
                field: "lastName",
                editor: "input",
                validator: ["required", "minLength:2", "maxLength:25"]
            },
            {title: "Gloves", field: "hasGloves", formatter: "tickCross", editor: "tickCross"},
            {title: "Membership", field: "paidMembership", formatter: "tickCross", editor: "tickCross"},
            {title: "Shoes", field: "hasShoes", formatter: "tickCross", editor: "tickCross"},
            {title: "Clothes", field: "hasClothes", formatter: "tickCross", editor: "tickCross"},
            {title: "Officer", field: "isGatheringOfficer", formatter: "tickCross", editor: "tickCross"},

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


document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});


function makeGroupTable() {
    var groupTable = new Tabulator("#groupTable", {
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
            submitDataChanges(data, currentGroupID)
        },
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/" + currentGroupID,

        columns: [
            {title: "First Name", field: "firstName", editor: "input"},
            {title: "Second Name", field: "lastName", editor: "input"},
            {title: "Gloves", field: "hasGloves", formatter: "tickCross", editor: "tickCross"},
            {title: "Membership", field: "paidMembership", formatter: "tickCross", editor: "tickCross"},
            {title: "Shoes", field: "hasShoes", formatter: "tickCross", editor: "tickCross"},
            {title: "Clothes", field: "hasClothes", formatter: "tickCross", editor: "tickCross"},
            {title: "Officer", field: "isGatheringOfficer", formatter: "tickCross", editor: "tickCross"},

        ],
    });
    removeElement("groupTable");
    createElementWithID("div", "groupTable");

    var initialData = memberTable.getData();

    let table = document.getElementById("groupTable");


    let goBackButton = document.createElement("button");
    goBackButton.innerHTML = "Go back to Groups";
    table.appendChild(goBackButton);
    goBackButton.addEventListener("click", function () {
        makeGroupTable();
    });

    let addMemberButton = document.createElement("button");
    addMemberButton.innerHTML = "add new member";
    table.appendChild(addMemberButton);
    addMemberButton.addEventListener("click", function () {
        memberTable.addRow({firstName: "First name here", lastName: "Second name here"}, false);
    })


}

function submitDataChanges(data, currentGroupID) {
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
            }).then(r => function () {
                alert("Member updated!")
            })
        } else {
            let memberJSON = JSON.stringify(memberData);
            console.log(memberData);
            console.log(currentGroupID);
            console.log(memberJSON);

            $.ajax({
                url: "http://localhost:8080/member/create/" + currentGroupID,
                type: "POST",
                data: memberJSON,
                contentType: "application/json"
            }).then(r => function () {
                alert("Member created!")
            })

        }
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


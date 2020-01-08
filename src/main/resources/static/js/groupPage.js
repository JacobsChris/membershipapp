document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});


function makeGroupTable() {
    var groupTable = new Tabulator("#groupTable", {
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getAll",
        rowClick: function (e, row) {
            makeMemberTable(row._row.data.id)
        },
        columns: [
            {title: "ID", field: "id"},
            {title: "Location", field: "location"},

        ],
    });
    removeElement("memberTable");
    createElementWithID("div", "memberTable");
}

function submitDataChanges(data) {
    console.log(data);
    console.log(data.length);
    for (let i = 0; i < data.length; i++) {

    }
    /*
    var array = table.getData();
    var json = JSON.stringify(array);

    retrieves all data int able and then makes a JSON of it.

    could use row.getData if I can find the row ID to use

    need to access member ID from this and then PUT request the rest

     */
    /*
    see below about dataEdited callback in table
     */
}

function makeMemberTable(id) {

    var memberTable = new Tabulator("#memberTable", {
        dataEdited: function (data) {
            console.log("changed data is ");
            console.log(data);
            //data - the updated table data
            submitDataChanges(data)
        },
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/" + id,

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


    let goBackButton = document.createElement("button");
    goBackButton.innerHTML = "Go back to Groups";
    let table = document.getElementById("groupTable");
    table.appendChild(goBackButton);
    goBackButton.addEventListener("click", function () {
        makeGroupTable();
    });

    // let submitButton = document.createElement("button");
    // submitButton.innerHTML = "Confirm changes";
    // table.appendChild(submitButton);
    // submitButton.addEventListener("click", function () {
    //     submitDataChanges(memberTable, initialData);
    // });

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

TODO edit member functionality:
   retrieve edited data with tabulator
   compare edit with initial data
   post update

TODO delete member functionality: might just be a button and then type form
 */


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

function makeMemberTable(id) {

    var memberTable = new Tabulator("#memberTable", {
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/" + id,

        columns: [
            {title: "First Name", field: "firstName"},
            {title: "Second Name", field: "lastName"},
            {title: "Membership", field: "paidMembership"},
            {title: "Gloves", field: "hasGloves"},
            {title: "Shoes", field: "hasShoes"},
            {title: "Clothes", field: "hasClothes"},
            {title: "Officer", field: "isGatheringOfficer"},

        ],
    });
    removeElement("groupTable");
    createElementWithID("div", "groupTable");


    let goBackButton = document.createElement("button");
    goBackButton.innerHTML = "Go back to Groups";
    let body = document.getElementById("groupTable");
    body.appendChild(goBackButton);
    goBackButton.addEventListener("click", function () {
        makeGroupTable();
    });

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
   click on member to edit data
   retrieve edited data with tabulator
   compare edit with initial data
   post update

TODO delete member functionality: might just be a button and then type form
 */
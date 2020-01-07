document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});

function makeGroupTable() {
    var table = new Tabulator("#groupTable", {
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

    var table = new Tabulator("#memberTable", {
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getMembers/1",

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


document.addEventListener("DOMContentLoaded", function () {
    makeGroupTable();
});

function makeGroupTable() {
    var table = new Tabulator("#groupTable", {
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/gathering/getAll",
        rowClick: function (row) {
            makeMemberTable()
        },
        columns: [
            {title: "ID", field: "id"},
            {title: "Location", field: "location"},

        ],
    });
    removeElement("memberTable");
    createElementWithID("div", "memberTable");
}

function makeMemberTable() {

    var table = new Tabulator("#memberTable", {
        layout: "fitColumns",
        ajaxURL: "http://localhost:8080/member/getAll",
        columns: [
            {title: "First Name", field: "first_name"},
            {title: "Second Name", field: "last_name"},
            {title: "Membership", field: "paid_membership"},
            {title: "Gloves", field: "has_gloves"},
            {title: "Shoes", field: "has_shoes"},
            {title: "Clothes", field: "has_clothes"},
            {title: "Officer", field: "is_gathering_officer"},

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


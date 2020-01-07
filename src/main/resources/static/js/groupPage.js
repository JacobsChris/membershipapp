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

function submitDataChanges(memberTable, initialData) {
    let finalData = memberTable.getData();
    console.log(initialData);
    console.log(finalData);
    for (let i = 0; i < finalData.length; i++) {
        if (finalData[i] !== initialData[i]) {
            let id = finalData[i][0];
            let data = finalData[i];
            console.log(data);
            $.ajax({
                url: "http://localhost:8080/member/update/" + id,
                data: JSON.stringify(data),
                dataType: "json",
                async: false,
                success: function (msg) {
                    alert(msg)
                }
            })
        }
    }

}

function makeMemberTable(id) {

    var memberTable = new Tabulator("#memberTable", {
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

    let submitButton = document.createElement("button");
    submitButton.innerHTML = "Confirm changes";
    table.appendChild(submitButton);
    submitButton.addEventListener("click", function () {
        submitDataChanges(memberTable, initialData);
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
   retrieve edited data with tabulator
   compare edit with initial data
   post update

TODO delete member functionality: might just be a button and then type form
 */


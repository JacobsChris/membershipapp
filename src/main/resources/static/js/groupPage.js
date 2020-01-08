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
        dataEdited: function (data) {
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


}

function submitDataChanges(data) {
    for (let i = 0; i < data.length; i++) {
        let memberData = data[i];

        if (MemberHasID)
        {
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
        }
        else{
            send post request of member
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


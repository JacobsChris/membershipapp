//create Tabulator on DOM element with id "example-table"
var table = new Tabulator("#groupTable", {
    height: 205, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
    layout: "fitColumns", //fit columns to width of table (optional)
    columns: [ //Define Table Columns

        {title: "Location", field: "location"},
    ],
    rowClick: function (e, row) { //trigger an alert message when the row is clicked
        alert("Row " + row.getData().id + " Clicked!!!!");
    },
});

table.setData("localhost:8080/gathering/getall");//,{},"getAll");

var token = localStorage.getItem("Token");
var username = localStorage.getItem("Userdetail");
var CurrentDate = new Date();
var yearnow = CurrentDate.getFullYear();
var currentmonth = CurrentDate.getMonth();
var tax_Amount, ctc;
var id;
//$("div.datefilter").html('   <div class="datefilter form-inline mt-5" id="datefilter"><div class="form-group"><input type="text" class="form-control" id="startdate"  placeholder="Start date"></div><div class="form-group mx-3"><input type="text" class="form-control" id="enddate"  placeholder="End date"></div><div class="form-group mx-3"><button class="btn" type="button" id="daterange" > Filter</button></div></div>');
var table;
$(document).ready(function () {
    $.fn.dataTable.ext.errMode = 'none';
    document.getElementById('username').innerHTML = 'Username: ' + username;

    employeDatatable();

    $('#date').datepicker({
        uiLibrary: 'bootstrap4',
        format: "dd-mm-yyyy"
    });
    $('#startdate').datepicker({
        uiLibrary: 'bootstrap4',
        format: "yyyy-mm-dd"
    });
    $('#enddate').datepicker({
        uiLibrary: 'bootstrap4',
        format: "yyyy-mm-dd"
    });
//$("div.toolbar").html('<b>Custom tool bar! Text/images etc.</b>');
});

//$("#daterange").click(employeDatatable);



function employeDatatable() {
    table = $('#db_table').DataTable({
//            dom:'<lf<"datefilter">>rtip',

        ajax: {
            type: 'POST',
            url: "http://localhost:8080/getemployee",
            dataSrc: "",
            data: function () {
                return JSON.stringify({"startdate": $('#startdate').val(), "enddate": $('#enddate').val()});
            },

            headers: {
                'Content-Type': 'application/json',
                'Authorization': token

            },

            statusCode: {
                401: function () {
                    $("#modal").modal('show', function () {
                        redirect();
                    });

                }
            }
        },
        language: {
            search: "_INPUT_", //To remove Search Label
            searchPlaceholder: "Search..."
        },
//        "columnDefs": [ {
//          "targets": [1,5,6,7,8,9],
//          "orderable": false
//    } ],

        /* Disable initial sort */
//            "aaSorting": [],
        "order": [],

        "columns": [

            {"data": "name",
                "class": "text-left text-capitalize"
            },
            {"data": "gender",
                "orderable": false,
                "searchable": false,
                "class": "text-left text-capitalize"
            },
            {"data": "dateofjoin",
                "type": "date",
                "searchable": false,
                "class": "text-center"
            },
            {"data": "designation",
                "class": "text-left text-capitalize"
            },
            {"data": "dateofjoin",
                "class": "text-left",
                "searchable": false,
                "render": function (data) {
                    var Date1 = new Date(data);
                    var year = Date1.getFullYear();
                    var month = Date1.getMonth();
                    var yeardiff = yearnow - year;
                    var month = month <= 9 ? '0' + month : month;
                    var yeardiff = yeardiff <= 9 ? '0' + yeardiff : yeardiff;
                    if (month === "00") {
                        month = '';
                    } else {
                        month = month + "months";
                    }
                    data = yeardiff + "yrs" + " " + month;
                    return  data;
                }
            },
            {"data": "ctc",
                "orderable": false,
                "searchable": false,
                "class": "text-right"
            },
            {"data": "pf",
                "orderable": false,
                "searchable": false,
                "class": "text-right"
            },
            {"data": "esi",
                "orderable": false,
                "searchable": false,
                "class": "text-right"
            },
            {"data": "tax",
                "orderable": false,
                "searchable": false,
                "class": "text-right"
            },
            {"data": "takehome",
                "orderable": false,
                "searchable": false,
                "class": "text-right",
                "render": function (data) {
                    var takehome = data.toFixed(2);
                    return takehome;
                }

            },
            {
                "data": null,
                "className": " edit text-center",
                "defaultContent": '<i class="fa fa-pencil"/>',
//                "render": function (data) {
//                    return `<i onclick="edit(${data})" class="fa fa-pencil"/>`;
//                },

                "orderable": false,
                "searchable": false
            }
        ]

    });
    table.ajax.reload();

};
//function edit(id){
//    var id=id;
//    console.log(id);
//    var data = table.row( $(this).parents('tr') ).data();
//    console.log(data);
//};

$('table').on('click', 'tbody td.edit', function () {
    var row = table.row($(this).parents('tr')).data();
    console.log(row);
    console.log("id" + row.id);

    id = row.id;
    document.getElementById("name").value = row.name;

    var db_gender = row.gender;
    if (db_gender === "male") {
        document.getElementById('male').checked = true;
    } else if (db_gender === "female") {
        document.getElementById('female').checked = true;
    }
    console.log(db_gender);

    var table_date = new Date(row.dateofjoin);
    var db_date = table_date.getDate();
    db_date = db_date <= 9 ? '0' + db_date : db_date;
    var db_month = table_date.getMonth() + 1;
    db_month = db_month <= 9 ? '0' + db_month : db_month;

    var formatDate = db_date + "-" + db_month + "-" + table_date.getFullYear();
    console.log(formatDate);
    document.getElementById("date").value = formatDate;
    document.getElementById("designation").value = row.designation;
    var db_ctc = row.ctc;
    document.getElementById("ctc").value = db_ctc;
    document.getElementById("pf").value = row.pf;
    document.getElementById("esi").value = row.esi;
    var db_tax = row.tax;
    console.log(db_tax);
//    tax_Amount= (tax / 100) * (ctc / 12);
    db_tax = db_tax * 100 * (12 / db_ctc);
    console.log("tax" + db_tax);
    console.log(ctc);
    document.getElementById("tax").value = db_tax.toFixed(2);


});

function daterange(){
    var startdate=document.getElementById("startdate").value;
    var enddate=document.getElementById("enddate").value;
    if(startdate ==''){
        document.getElementById("errorstartdate").innerHTML="start date should not be empty";
    }else {
        document.getElementById("").innerHTML = '';
    }else if(enddate==''){
        document.getElementById("errorenddate").innerHTML="End date should not be empty";
    }else if(enddate < startdate){
        document.getElementById("errorenddate").innerHTML="End date should be greater than startdate";
    }else if(new Date(startdate)>CurrentDate){
        document.getElementById("errorenddate").innerHTML="Start date should be lesser than ";
    }else if(new Date(enddate)>CurrentDate){
        document.getElementById("errorenddate").innerHTML="End date should be lesser than current date";
    }
    else if(startdate!='' && enddate!=''){
        document.getElementById("errorstartdate").innerHTML="";
        document.getElementById("errorenddate").innerHTML=""
    }
    else {
        employeDatatable();
    }
};

function validate(message, alert) {
    document.getElementById("confirm").innerHTML = message;
    document.getElementById("confirm").className = alert;
    document.getElementById("confirm").style.display = "block";
    setTimeout(function () {
        document.getElementById("confirm").style.display = "none";
    }, 3000);
}
;
//tableList();

function functionAlert() {
    var name = document.getElementById("name").value;
    var date = document.getElementById("date").value;
    var desgination = document.getElementById("designation").value;

    var Pf = document.getElementById("pf").value;
    var esi = document.getElementById("esi").value;
    var tax = document.getElementById("tax").value;
    var dateofjoin = date.split('-').reverse().join('-');
    var gender;
    if (document.getElementById('male').checked) {
        gender = document.getElementById('male').value;
    } else if (document.getElementById('female').checked) {
        gender = document.getElementById('female').value;
    }
    ctc = document.getElementById("ctc").value;
    tax_Amount = (tax / 100) * (ctc / 12);
    let a = tax_Amount * 100 * (12 / ctc);
    console.log(a, ctc, tax_Amount);
    var calculate = parseFloat(Pf) + parseFloat(esi) + parseFloat(tax_Amount);
    //append string
    // console.log(Pf+esi+tax_Amount);
    //console.log("tax_Amount>>" + tax_Amount);
    if (name === '') {
        validate('Enter Name', 'alert alert-dark');
    } else if (document.getElementById('male').checked === false && document.getElementById('female').checked === false) {
        validate('Choose Gender ', 'alert alert-dark');
    } else if (date === '') {
        validate('Enter Date', 'alert alert-dark');
    } else if (new Date(dateofjoin) > CurrentDate) {
        validate("Date must be in the past", 'alert alert-dark');
    } else if (desgination === '') {
        validate('Enter Designation', 'alert alert-dark');
    } else if (ctc === '') {
        validate('Enter CTC', 'alert alert-dark');
    } else if (ctc < 0) {
        validate('CTC must be in postive number', 'alert alert-dark');
    } else if (Pf === '') {
        validate('Enter PF', 'alert alert-dark');
    } else if (Pf < 0) {
        validate('PF must in be postive number', 'alert alert-dark');
    } else if (esi === '') {
        validate('Enter ESI', 'alert alert-dark');
    } else if (esi < 0) {
        validate('ESI must in be postive number', 'alert alert-dark');
    } else if (tax === '') {
        validate('Enter Tax', 'alert alert-dark');
    } else if (tax < 0) {
        validate('Tax must in be postive number', 'alert alert-dark');
    } else if (tax >= 100) {
        validate('Tax must in be percentage', 'alert alert-dark');
    } else if ((ctc / 12) < calculate) {
        validate('CTC must be greater than total of Pf,ESI,Tax', 'alert alert-dark');
    } else {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8080/saveAndUpdtaeemployee", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.setRequestHeader("Authorization", token);

        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                // Response
                var response = this.responseText;
//                    $('#db_table').dataTable().clear();
//                    $('#db_table tbody').empty();
//                    var table = $('#db_table').DataTable();
//                    table.destroy();
                alert(response);
                employeDatatable();

//                    tableList();
//table.draw();
                clearfeild();
                validate(response, 'alert alert-success');
                id = '';
                console.log("id" + id);
            } else if (this.readyState === 400) {
                validate(response, 'alert alert-danger');
            } else if (this.status === 401) {
                $("#modal").modal('show', function () {
                    redirect();
                });
            }
        };
        var data = {
            "id": id ? id : '',
            "name": name,
            "gender": gender,
            "designation": desgination,
            "ctc": ctc,
            "pf": Pf,
            "esi": esi,
            "tax": tax_Amount,
            "dateofjoin": dateofjoin
        };

        xhttp.send(JSON.stringify(data));
    }


}
//console.log(tax_Amount);




function tableList() {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function ()
    {

        if (this.readyState === 4 && this.status === 200) {

            var response = JSON.parse(this.responseText);

            //            createTable()
            var emp_table = response.length;
            //console.log("emplength: " + emp_table);
            var emp_Details = JSON.stringify(response);
//                console.log("emp_details: " + emp_Details);
//                var table = document.getElementById("db_table");
            var html = '';
            for (var i = 0; i < emp_table; i++) {
                var Date1 = new Date(response[i].dateofjoin);
                //console.log(Date1);
                var day = Date1.getDate();
                var month = Date1.toLocaleString("default", {month: "short"});
                var year = Date1.getFullYear();
                var new_date = (day <= 9 ? '0' + day : day) + '-' + month + '-' + year;
                var years_difference = CurrentDate.getFullYear() - Date1.getFullYear();
                var months = Date1.getMonth();
                if (months === 00) {
                    months = '';
                } else {
                    months = months + "months";
                }
//                    console.log(months);
                var take_home = (response[i].ctc / 12) - (response[i].pf + response[i].esi + response[i].tax);

                html += `<tr>
                        <td class="text-left text-capitalize">${response[i].name}</td>
                        <td class="text-left text-capitalize">${response[i].gender}</td>
                        <td class="text-center">${new_date}</td>
                        <td class="text-left text-capitalize">${response[i].designation}</td>
                        <td class="text-left">${years_difference} yrs ${months}</td>
                        <td class="text-right">${response[i].ctc}</td>
                        <td class="text-right">${response[i].pf}</td>
                        <td class="text-right">${response[i].esi}</td>
                        <td class="text-right">${response[i].tax}</td>
                        <td class="text-right">${take_home.toFixed(2)}</td>
                    </tr>`;
                document.getElementById('tableBody').innerHTML = html;
            }

        } else if (this.status === 401) {
            localStorage.removeItem("Token");
            window.location.href = "loginform.html";

            console.log("failed");
//            alert(response);
        }
    };

    xhttp.open("GET", "http://localhost:8080/getemployee", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization", token);
    xhttp.send();

}


function clearfeild( ) {
    document.getElementById("name").value = '';
    document.getElementById("date").value = '';
    document.getElementById('male').checked = false;
    document.getElementById('female').checked = false;
    document.getElementById("designation").value = '';
    document.getElementById("ctc").value = '';
    document.getElementById("pf").value = '';
    document.getElementById("esi").value = '';
    document.getElementById("tax").value = '';
}

function logout() {
    redirect();
}

function redirect() {
    localStorage.removeItem("Token");
    localStorage.removeItem("Userdetail");
    window.location.href = "loginform.html";
}


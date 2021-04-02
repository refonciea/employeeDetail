var token = localStorage.getItem("Token");
if (token !== null) {
    window.location.href = "form.html";
}
function loginprocess() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (username === '') {
        document.getElementById("errorusername").innerHTML = "Enter the  UserName";
    } else {
        document.getElementById("errorusername").innerHTML = '';
    }

    if (password === '') {
        document.getElementById("errorpassword").innerHTML = "Enter the  Password";
    } else {
        document.getElementById("errorpassword").innerHTML = '';
    }
    if (username !== '' && password !== '') {
        document.getElementById("errorusername").innerHTML = "";
        document.getElementById("errorpassword").innerHTML = "";

        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8080/employeelogin", true);
        xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
        xhttp.setRequestHeader("Access-Control-Allow-Headers", "Content-Type");
        xhttp.setRequestHeader("Content-Type", "application/json");

        xhttp.onreadystatechange = function () {
            console.log(this.readyState, this.status);
            if (this.readyState === 4 && this.status === 200) {
                // Response
                var response = this.responseText;
//                    console.log(JSON.parse(response).jwtToken); 
//            if(response==="Bad Credentials"){
//                alert(response);
//            }
                var message = JSON.parse(response).message;
                if (message === "Login Sucessfully") {
//                alert(message);
                    var name=JSON.parse(response).name;
                    var jwtToken = JSON.parse(response).token;
                    jwtToken = "Bearer " + jwtToken;
                    localStorage.setItem("Token", jwtToken);
                    localStorage.setItem("Userdetail",name);
                    window.location.href = "form.html";
                } else {
                    $("#modal").modal("show");
//                        document.getElementById('message').value=message;
                    $('#message').text(message);
                    
                }

            } else if (this.readyState === 400) {
//            alert(response);
            }

        };
        var data = {
            "userName": username,
            "userPassword": password
        };
        xhttp.send(JSON.stringify(data));

//            console.log(data);
    }
}


function showHide() {
    var password = document.getElementById("password");

    if (password.type === "password") {
        password.type = "text";
    } else {
        password.type = "password";
    }
}

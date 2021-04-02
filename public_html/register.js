
function registerProcess() {
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var confirmpassword = document.getElementById("confirmpassword").value;
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]$/;
    if (firstname === '') {
        document.getElementById("errorfirstname").innerHTML = "Enter the  First Name";
    } else {
        document.getElementById("errorfirstname").innerHTML = '';
    }

    if (lastname === '') {
        document.getElementById("errorlastname").innerHTML = "Enter the  Last Name";
    } else {
        document.getElementById("errorlastname").innerHTML = '';
    }

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

    if (confirmpassword === '') {
        document.getElementById("errorconfirmpassword").innerHTML = "Enter the  Confirm Password";
    } else {
        document.getElementById("errorconfirmpassword").innerHTML = '';
    }

    if (firstname !== '' && lastname !== '' && username !== '' && password !== '' && confirmpassword !== '') {
        document.getElementById("errorfirstname").innerHTML = "";
        document.getElementById("errorlastname").innerHTML = "";
        document.getElementById("errorusername").innerHTML = "";
        document.getElementById("errorpassword").innerHTML = "";
        document.getElementById("errorconfirmpassword").innerHTML = "";

        if (!emailPattern.test(username)) {
            document.getElementById('message').value='Please provide a valid email address';
            $("#message").text("Please provide a valid email address");
            $("#modal").modal('show');
            
            return false;
        }
        if (password === confirmpassword) {

            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://localhost:8080/register", true);
            xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
            xhttp.setRequestHeader("Access-Control-Allow-Headers", "Content-Type");
            xhttp.setRequestHeader("Content-Type", "application/json");

            xhttp.onreadystatechange = function () {
                console.log(this.readyState, this.status);
                if (this.readyState === 4 && this.status === 200) {
                    // Response
                    var response = this.responseText;
//                    alert(response);
                    console.log('1' + response);
                    if (response === "Registered sucessfully.") {
//                        alert(response);
                        redirectlogin();
//                     clearfield();
                    }
                } else if (this.readyState === 4) {
                    alert(response);
                }

            };
            var data = {
                "userName": username,
                "userPassword": password,
                "firstName": firstname,
                "lastName": lastname
            };
            xhttp.send(JSON.stringify(data));

//            console.log(data);
        } else {
//            alert("password doesn't match");
                $('#modal').modal('show');
                $('#message').text("Password doesn't match");
        }
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

function passwordshow() {
    var confirmpassword = document.getElementById("confirmpassword");

    if (confirmpassword.type === "password") {
        confirmpassword.type = "text";
    } else {
        confirmpassword.type = "password";
    }
}

function redirectlogin() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
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
//            alert(response);
//            console.log(response);
            if (response !== "") {
                    var name=JSON.parse(response).name;
                    var jwtToken = JSON.parse(response).token;
                    jwtToken = "Bearer " + jwtToken;
                    localStorage.setItem("Token", jwtToken);
                    localStorage.setItem("Userdetail",name);
                    window.location.href = "form.html";

            }

        } else if (this.readyState === 4) {
            alert(response);
        }

    };
    var data = {
        "userName": username,
        "userPassword": password
    };
    xhttp.send(JSON.stringify(data));
}


function checkemail() {
    var username = document.getElementById("username").value;
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z.]{2,10}$/;
    if (!emailPattern.test(username)) {
        alert("Please provide a valid email address");
        return false;
    }
}
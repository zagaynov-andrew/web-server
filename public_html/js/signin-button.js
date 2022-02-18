
// waiting for the page to load fully
window.onload = function () {

    // getting the button ID
    var btn = document.getElementById('signin-button');

    // assign an event to btn
    btn.onclick = function() {

        const login = document.querySelector('#signin-login').value;
        const password = document.querySelector('#signin-password').value;
        const params = '?login=' + login + '&password=' + password;
        const url = 'signin' + params;

        sendAjaxForm('POST', url, signinResponseListener)
        return false;
    }
}

function signinResponseListener () {

    console.log(this.responseText);
    if (this.readyState === 4 && this.status === 200) {
        document.querySelector('#signin-errors').value = "";
        document.location.href = document.location.protocol + '//' + document.location.host + '/chat';
//        alert("Successful authorization!\n" + this.responseText);
    }
    else if (this.readyState === 4) {
        document.querySelector('#signin-errors').value = "Invalid password or login.";
    }
}

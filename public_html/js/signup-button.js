
// waiting for the page to load fully
window.onload = function () {

    // getting the button ID
    var btn = document.getElementById('signup-button');

    // assign an event to btn
    btn.onclick = function() {

        const login = document.querySelector('#signup-login').value;
        const password = document.querySelector('#signup-password').value;
        const email = document.querySelector('#signup-email').value;
        const params = '?login=' + login + '&password=' + password + '&email=' + email;
        const url = 'signup' + params;

        sendAjaxForm('POST', url, signupResponseListener)
        return false;
    }
}

function signupResponseListener () {

    console.log(this.responseText);
    if (this.readyState === 4 && this.status === 200) {
        document.querySelector('#signup-errors').value = "";
        document.location.href = document.location.protocol + '//' + document.location.host + '/chat';
    }
    else if (this.readyState === 4 && this.status === 409) {
        document.querySelector('#signup-errors').value = "Such a user is already registered.";
    }
    else if (this.readyState === 4) {
        document.querySelector('#signup-errors').value = "Registration error.";
    }
}

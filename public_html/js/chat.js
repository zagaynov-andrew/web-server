
var ws;

function init() {

    document.getElementById("message")
        .addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            document.getElementById("submitmsg").click();
        }
    });

    document.getElementById('signout-link').onclick = function() {

        console.log('Sign out');
        sendAjaxForm('DELETE', 'signin', null);
    }



    ws = new WebSocket("ws://localhost:8080/chat");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("messages");
        $textarea.value = $textarea.value + event.data + "\n";
    }
    ws.onclose = function (event) {

    }
};

function sendMessage() {

    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("login");
    var message = userNameField.innerText + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}


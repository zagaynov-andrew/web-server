
function checkAuthorization() {
    if (this.readyState === 4 && this.status === 200) {
        document.location.href = document.location.protocol + '//' + document.location.host + '/chat';
    }
    else if (this.readyState === 4) {
        document.location.href = document.location.protocol + '//' + document.location.host + '/sign-in.html';
    }
}

function init() {
    sendAjaxForm('GET', 'signin', checkAuthorization)
    document.location.href = document.location.protocol + '//' + document.location.host + '/chat';
}
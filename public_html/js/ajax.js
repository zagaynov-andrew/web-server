
function sendAjaxForm(method, url, reqListener) {

    const request = new XMLHttpRequest();

    request.open(method, url);
    request.setRequestHeader('Content-Type', 'application/x-www-form-url');
    request.addEventListener("readystatechange", reqListener);
    request.send();
}





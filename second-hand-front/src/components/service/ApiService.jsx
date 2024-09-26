import {API_BASE_URL} from "./ApiConfig";

export function apiCall(api, method, request) {
    let headers = new Headers({
        "Content-Type": "application/json",
    });

    const token = sessionStorage.getItem('token');
    if (token && token !== null) {
        headers.append('Authorization', 'Bearer ' + token);
    }

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options)
        .then((response) => {

        })
};
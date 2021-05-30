import {useCookies} from 'react-cookie';

const AuthProvider = () => {
    const [cookies, setCookie, removeCookie] = useCookies(['authtoken']);

    const authToken = cookies.token

    const login = async (username, password) => {
        // TODO: Change to api.dev.wokis.es
        await fetch('http://localhost:8000/login', {
            method: 'POST',
            headers: {
                //'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
                //'Access-Control-Allow-Origin': '*',
            },
            mode: "cors",
            body: JSON.stringify({"username": username, "password": password}),
            // body: JSON.stringify([{"id":2,"title":"Abstract","description":"Arte abstracto, no tiene más!","urlImage":"https://images.unsplash.com/photo-1615012553971-f7251c225e01?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1868&q=80","isFavorite":false}])
        }).then(response => response.text())
            .then(data => setCookie("token", data))

        return authToken
    }

    const logout = () => {
        removeCookie("token")
    }

    return {authToken, login, logout}
}


export {AuthProvider}

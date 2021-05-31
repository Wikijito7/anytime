import {useCookies} from 'react-cookie';
import {fetchBase} from '../utils/Const'


const AuthProvider = () => {
    const [cookies, setCookie, removeCookie] = useCookies(['authtoken']);

    const authToken = cookies.token

    const login = async (username, password) => {
        await fetch(`${fetchBase}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: "cors",
            body: JSON.stringify({"username": username, "password": password}),
        }).then(response => response.text())
            .then(data => setCookie("token", data));

        return authToken;
    }

    // {"username": username, "password": password, "email": email, "empresa": empresa}
    const register = async (registerUser) => {
        await fetch(`${fetchBase}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: "cors",
            body: JSON.stringify(registerUser),
        }).then(response => response.text())
            .then(data => setCookie("token", data));

        return authToken
    }

    const logout = () => {
        removeCookie("token");
    }

    return {authToken, login, register, logout};
}


export {AuthProvider};

import {useCookies} from 'react-cookie';
import {fetchBase} from '../utils/Const'
import {cookieExpire} from '../utils/DateUtils'

const AuthProvider = () => {
    const [cookies, setCookie, removeCookie] = useCookies(['authtoken']);

    const authToken = cookies.token

    const login = async (username, password) => {
        let token;


        await fetch(`${fetchBase}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: "cors",
            body: JSON.stringify({"username": username, "password": password}),
        }).then(response => response.text())
            .then(data => token = data);
        if (!token.toLowerCase().startsWith('wrong')) {
            setCookie("token", token, { expires: cookieExpire });
        }

        return token;
    }

    // {"username": username, "password": password, "email": email, "empresa": empresa}
    const register = async (registerUser) => {
        let token;

        await fetch(`${fetchBase}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: "cors",
            body: JSON.stringify(registerUser),
        }).then(response => response.text())
            .then(data => token = data);

        if (!token.toLowerCase().startsWith('wrong')) {
            setCookie("token", token, { expires: cookieExpire });
        }

        return token;
    }

    const logout = () => {
        removeCookie("token");
    }

    return {authToken, login, register, logout};
}


export {AuthProvider};

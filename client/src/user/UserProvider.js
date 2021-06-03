import {useState} from 'react';
import {fetchBase} from '../utils/Const'

const UserProvider = () => {
    const [user, setUser] = useState(null)

    const getUser = async (token) => {
        return user !== null ? user : await fetchUser(token);
    }

    const refreshUser = async (token) => {
        return await fetchUser(token);
    }

    const getUserByUsername = async (username, token) => {
        let user;

        await fetch(`${fetchBase}/user/${username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        }).then(res => res.json()
            .then(data => user = data));
        
        return user;
    }

    const fetchUser = async (token) => {
        let user;
        
        await fetch(`${fetchBase}/user`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        }).then(res => res.json()
        .then(data => user = data));

        setUser(user);
        return user;
    }

    const fetchFichados = async (token, user) => {
        const fichajes = [];

        await fetch(`${fetchBase}/fichaje/${user.username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        }).then(res => res.json()
            .then(data => fichajes.push(...data)));

        return fichajes;
    }

    return {getUser, refreshUser, getUserByUsername, fetchFichados};
}

export {UserProvider};

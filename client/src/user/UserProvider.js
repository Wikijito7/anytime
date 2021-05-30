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

    const fetchUser = async (token) => {
        fetch(`${fetchBase}/user`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authoritation': `Bearer ${token}`
            },
            mode: "cors"
        }).then(res => res.json()
            .then(data => setUser(data)));
    }

    return {getUser, refreshUser};
}

export default UserProvider;

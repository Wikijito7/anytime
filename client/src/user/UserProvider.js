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

    const clearUser = () => setUser(null);

    const getUserByUsername = async (username, token) => {
        let user;

        await fetch(`${fetchBase}/user/${username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        })
        .then(res => res.json())
        .then(data => user = data);
        
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
        })
        .then(res => res.json())
        .then(data => user = data);

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
        })
        .then(res => res.json())
        .then(data => fichajes.push(...data));

        return fichajes;
    }

    const fichar = async (token) => {
        let fichar;

        await fetch(`${fetchBase}/fichar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        })
        .then(res => res.json()
        .then(data => fichar = data));

        return fichar;
    }

    const desfichar = async (token, ficharDTO) => {
        let fichar;

        await fetch(`${fetchBase}/desfichar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(ficharDTO),
            mode: "cors"
        })
        .then(res => res.json()
        .then(data => fichar = data));

        return fichar;
    }

    const changeAvatar = async (token, file) => {
        let response;
        const formData = new FormData();

        formData.append("image", file);

        await fetch(`${fetchBase}/user/avatar`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData,
            mode: "cors"
        })
        .then(res => res.blob()
        .then(data => response = URL.createObjectURL(data)));

        return response;
    }

    const deleteAvatar = async (token) => {
        let response;

        await fetch(`${fetchBase}/user/avatar`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        })
        .then(res => response = res.status);

        return response;
    }

    const invite = async (token, email, empresa) => {
        let response;

        await fetch(`${fetchBase}/email`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                "email": email,
                "empresa": empresa
            }),
            mode: "cors"
        })
        .then(res => res.json())
        .then(data => response = data);

        return response;
    }

    const editRole = async (token, username, role) => {
        let response;

        await fetch(`${fetchBase}/user/${username}/role`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: role,
            mode: "cors"
        })
        .then(res => response = res.status);

        return response;
    }

    const deleteUser = async (username, token) => {
        let response;

        await fetch(`${fetchBase}/user/${username}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            mode: "cors"
        })
        .then(res => response = res.status);

        return response;
    }

    const deleteFichaje = async (fichajeDTO, token) => {
        let response;

        await fetch(`${fetchBase}/fichaje`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(fichajeDTO),
            mode: "cors"
        })
        .then(res => response = res.status);

        return response;
    }

    const editFichaje = async (fichajeDTO, token) => {
        let response;

        await fetch(`${fetchBase}/fichaje`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(fichajeDTO),
            mode: "cors"
        })
        .then(res => response = res.status);

        return response;
    }

    const editUser = async (user, token) => {
        let response;

        await fetch(`${fetchBase}/user`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(user),
            mode: "cors"
        })
            .then(res => response = res.status);

        return response;
    }

    const changePass = async (pass, token) => {
        // TODO: Implementar
    }

    return { getUser, refreshUser, getUserByUsername, fetchFichados, 
        fichar, desfichar, clearUser, changeAvatar, deleteAvatar,
        invite, editRole, deleteUser, deleteFichaje, editFichaje,
        editUser };
}

export {UserProvider};

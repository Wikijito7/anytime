import React, {useEffect, useState} from 'react'
import AppNavbar from './navbars/AppNavbar';

import {AuthProvider} from '../auth/AuthProvider'
import {fetchBase} from '../utils/Const'


const Admin = (props) => {
    const auth = AuthProvider();
    const userInstance = props.user

    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUser(auth.authToken);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        if (auth.authToken === undefined) {
            props.history.push("/login")
            return;
        }

        fetchUser();
    }, [])

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance} />
            <h1>Asmin</h1>
            {
                user && 
                <main id="cont-admin" >
                    {
                        user.empresa.users.map((user, index) =>
                            <div key={index} className="user container-row">
                                <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} />
                                <div className="container-column">
                                    {  
                                        user.nombre !== undefined ?
                                            <p id="userNombre">{`${user.nombre} ${user.apellidos !== undefined ? user.apellidos : ""}`}</p>
                                            : <p id="userNombre">{user.username}</p>
                                        
                                    }
                                    <p id="username">
                                        {user.username}
                                    </p>
                                    <p id="email">
                                        {user.email}
                                    </p>
                                </div>
                            </div>
                        )
                    }
                </main>
            }
        </div>
    )
}

export default Admin

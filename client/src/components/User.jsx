import React, {useEffect, useState} from 'react'
import {useParams} from 'react-router-dom';
import AppNavbar from './navbars/AppNavbar';
import {fetchBase} from '../utils/Const'


const User = (props) => {
    const {username} = useParams();

    const [user, setUser] = useState(null)

    const userInstance = props.user;
    const auth = props.auth;

    useEffect(() => {
        const fetchUser = async (username, token) => {
            try {
                const user = await userInstance.getUserByUsername(username, token);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        await fetchUser(username, auth.authToken);
        
    }, [username, auth])

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance} />
            <main id="perfil-cont">
                <h1>Holis, estoy en user component, soy {username}</h1>
                {
                    user && <div>
                        <p id="username">
                            {user.username}
                        </p>

                        {
                            user.nombre !== undefined ? <p id="userNombre">{user.nombre}</p> : ""
                        }

                        {
                            user.apellidos !== undefined ? <p id="userApellidos">{user.apellidos}</p> : ""
                        }

                        {
                            user.direccion !== undefined ? <p id="userDireccion">{user.direccion}</p> : ""
                        }

                        <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} />
                        
                    </div>
                }
            </main>
        </div>
    )
}

export default User

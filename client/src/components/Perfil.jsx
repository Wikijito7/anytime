import React, {useEffect, useState} from 'react'
import AppNavbar from './navbars/AppNavbar'
import {fetchBase} from '../utils/Const'


const Perfil = (props) => {
    const userInstance = props.user
    const auth = props.auth

    const [user, setUser] = useState(null)

    useEffect(() => {
        const fetchUser = async (token) => {
            try {
                const user = await userInstance.getUser( token);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        await fetchUser(userInstance, auth.authToken);

    }, [userInstance, auth])

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance} />
            <main id="perfil-cont">
                <h1>Holis, estoy en el perfil</h1>
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

export default Perfil

import React, {useEffect, useState} from 'react'
import {useParams, withRouter} from 'react-router-dom';
import AppNavbar from './navbars/AppNavbar';

import {AuthProvider} from '../auth/AuthProvider'
import {fetchBase} from '../utils/Const'


const User = (props) => {
    const {username} = useParams();

    const [user, setUser] = useState(null)

    const userInstance = props.user;
    const auth = AuthProvider();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUserByUsername(username, auth.authToken);
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
            <main id="perfil-cont">
                <h1>Holis, estoy en user component, soy {username}</h1>
                {
                    user && <div className="container-row">
                        <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} />
                        <div className="container-column">
                            <p className="label">Nombre de usuario</p>
                            <p id="username">
                                {user.username}
                            </p>
                            <p className="label">Email</p>
                            <p id="email">
                                {user.email}
                            </p>
                            {
                                user.nombre !== undefined ?
                                    <div>
                                        <p className="label">Nombre</p>
                                        <p id="userNombre">{user.nombre}</p>
                                    </div> : ""
                            }

                            {
                                user.apellidos !== undefined ?
                                    <div>
                                        <p className="label">Apellidos</p>
                                        <p id="userApellidos">{user.apellidos}</p>
                                    </div> : ""
                            }

                            {
                                user.direccion !== undefined ? <div>
                                    <p className="label">Dirección</p>
                                    <p id="userDireccion">{user.direccion}</p>
                                </div> : ""
                            }
                        </div>
                    </div>
                }
            </main>
        </div>
    )
}

export default withRouter(User)

import React, {useEffect, useState} from 'react'
import AppNavbar from './navbars/AppNavbar'

import {AuthProvider} from '../auth/AuthProvider'
import {fetchBase} from '../utils/Const'


const Perfil = (props) => {
    const userInstance = props.user
    const auth = AuthProvider();

    const [user, setUser] = useState(null)

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUser(auth.authToken);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        if (auth.authToken === undefined){
            props.history.push("/login")
            return;
        }
        
        fetchUser();

    }, [])

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance} />
            <main id="perfil-cont">
                <h1>Perfil</h1>
                {
                    user && <div className="container-row">
                        <div className="container-column">
                            {/*TODO: Hacer contenedor para hacer la imagen siempre redonda*/ }
                            <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} />
                            <button className="btn">Editar</button>
                        </div>
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

export default Perfil

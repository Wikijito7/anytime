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
            <main>
                <section id="perfil-cont">
                    {
                        user && <div className="container-row">
                            <div id="img-cont" className="container-column">
                                <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} />
                            </div>
                            <div className="container-column">

                                <div id="nombre-cont" className="container-row">
                                    {
                                        user.nombre !== undefined ?
                                            <p id="userNombre">{`${user.nombre} ${user.apellidos !== undefined ? user.apellidos : ""}`}</p>
                                            : <p id="userNombre">{user.username}</p>
                                    }
                                </div>

                                <div id="other-data" className="container-row">
                                    <div className="container-column">
                                        <p className="label">Nombre de usuario</p>
                                        <p id="username">
                                            {user.username}
                                        </p>
                                    </div>
                                    <div className="container-column">
                                        <p className="label">Email</p>
                                        <p id="email">
                                            {user.email}
                                        </p>
                                    </div>
                                    {
                                        user.direccion !== undefined ? <div className="container-column">
                                            <p className="label">Dirección</p>
                                            <p id="userDireccion">{user.direccion}</p>
                                        </div> : ""
                                    }
                                </div>
                            </div>
                        </div>
                    }
                </section>
                <section id="table">
                    <div className="botones-tabla">
                        <a href="#">Hoy</a>
                        <a href="#" className="btn-selected">Semana</a>
                        <a href="#">Mes</a>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Hora de entrada</th>
                                <th>Hora de salida</th>
                                <th>Tiempo fichado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td data="Fecha">19/10/2020</td>
                                <td data="Hora de entrada">8:10</td>
                                <td data="Hora de salida">14:31</td>
                                <td data="Tiempo fichado">6h 21 min</td>
                            </tr>
                            <tr>
                                <td data="Fecha">20/10/2020</td>
                                <td data="Hora de entrada">8:10</td>
                                <td data="Hora de salida">14:31</td>
                                <td data="Tiempo fichado">6h 21 min</td>
                            </tr>
                            <tr>
                                <td data="Fecha">21/10/2020</td>
                                <td data="Hora de entrada">8:10</td>
                                <td data="Hora de salida">14:31</td>
                                <td data="Tiempo fichado">6h 21 min</td>
                            </tr>
                        </tbody>
                    </table>
                </section>
            </main>
        </div>
    )
}

export default withRouter(User)

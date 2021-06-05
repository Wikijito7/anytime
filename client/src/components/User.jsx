import React, {useEffect, useState} from 'react'
import {useParams, withRouter} from 'react-router-dom';
import AppNavbar from './navbars/AppNavbar';

import {AuthProvider} from '../auth/AuthProvider'
import {
    dateInMonth,
    dateInToday,
    dateInWeek,
    drawDate,
    getHoras,
    getHorasToString,
    timeToString
} from '../utils/DateUtils'
import {fetchBase} from '../utils/Const'


const User = (props) => {
    const {username} = useParams();

    const [user, setUser] = useState(null);
    const [fichajes, setFichajes] = useState(null);
    const [selected, setSelected] = useState("");

    const userInstance = props.user;
    const auth = AuthProvider();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUserByUsername(username, auth.authToken);
                setUser(user);

                await fetchFichajes(user);
            } catch (error) {
                console.log(error);
            }
        }

        if (auth.authToken === undefined) {
            props.history.push("/login")
            return;
        }

        fetchUser();
        semana();
    }, [])

    const fetchFichajes = async () => {
        const user = await userInstance.getUserByUsername(username, auth.authToken);
        const fichajes = await userInstance.fetchFichados(auth.authToken, user);
        return fichajes;
    }

    const hoy = async () => {
        const listaFichajes = await fetchFichajes();
        const lista = listaFichajes.filter((fichaje) => dateInToday(fichaje.entrada))
        setSelected("hoy");
        setFichajes([])
        setFichajes(lista);
    }

    const semana = async () => {
        const listaFichajes = await fetchFichajes();
        const lista = listaFichajes.filter((fichaje) => dateInWeek(fichaje.entrada))
        setSelected("semana");
        setFichajes(lista);
    }

    const mes = async () => {
        const listaFichajes = await fetchFichajes();
        const lista = listaFichajes.filter((fichaje) => dateInMonth(fichaje.entrada))
        setSelected("mes");
        setFichajes(lista);
    }

    const editar = () => {

    }

    const borrar = () => {

    }

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance} />
            <main>
                <section id="perfil-cont">
                    {
                        user && <div className="container-row">
                            <div id="img-cont" className="container-column">
                                <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar`} alt="Avatar del usuario"/>
                                <button className="btn danger">Eliminar usuario</button>
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
                                        <p className="label">Usuario</p>
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
                        <a onClick={hoy} className={selected === "hoy" ? "btn-selected" : ""}>Hoy</a>
                        <a onClick={semana} className={selected === "semana" ? "btn-selected" : ""}>Semana</a>
                        <a onClick={mes} className={selected === "mes" ? "btn-selected" : ""}>Mes</a>
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
                            {
                                fichajes && fichajes.map((fichaje, index) =>
                                    <tr key={index}>
                                        <td data="Fecha">{drawDate(fichaje.entrada)}</td>
                                        <td data="Hora de entrada">{timeToString(fichaje.entrada)}</td>
                                        <td data="Hora de salida">{fichaje.salida !== undefined ? timeToString(fichaje.salida) : ""}</td>
                                        <td data="Tiempo fichado">{getHorasToString(getHoras(fichaje))}</td>
                                        <td id="opc-cont">
                                            <div className="container-row">
                                                <a className="opc-fichar" onClick={() => editar(fichaje)}><i className="fas fa-edit"></i></a>
                                                <a className="opc-fichar" onClick={() => borrar(fichaje)}><i className="fas fa-trash-alt"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </section>
            </main>
        </div>
    )
}

export default withRouter(User)

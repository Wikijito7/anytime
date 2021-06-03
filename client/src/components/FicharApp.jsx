import React, {useEffect, useState} from 'react'
import AppNavbar from './navbars/AppNavbar';
import {withRouter} from 'react-router-dom'

import {formatTime, getHoras} from '../utils/DateUtils'
import {AuthProvider} from '../auth/AuthProvider'


const FicharApp = (props) => {
    const userInstance = props.user;
    const auth = AuthProvider();

    const [user, setUser] = useState(null);

    const [modoFichar, setModoFichar] = useState(false)

    const [ficharDTO, setFicharDTO] = useState({})

    const [timer, setTimer] = useState(null)

    let interval;

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUser(auth.authToken);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }
        
        const updateView = async (fichajes) => {
            const listFichaje = await fichajes
            
            const ultimoFichaje = listFichaje.sort((a, b) => b["id"] - a["id"])[0]

            if (ultimoFichaje.salida === undefined) {
                setFicharDTO(ultimoFichaje)
                setTimer(getHoras(ultimoFichaje))
                interval = setInterval(() => setTimer(getHoras(ultimoFichaje)), 1000);
                setModoFichar(true);
            }
        }

        if (auth.authToken === undefined) {
            props.history.push("/login");
            return;
        }

        fetchUser();
        
        updateView(fetchFichados());
    }, [])

    const fetchFichados = async () => {
        const user = await userInstance.getUser(auth.authToken);
        const fichados = await userInstance.fetchFichados(auth.authToken, user);

        return fichados;
    }

    const fichar = async () => {
        setModoFichar(true);
        const ficharDTO = await userInstance.fichar(auth.authToken);

        interval = setInterval(() => setTimer(getHoras(ficharDTO)), 1000);
    }

    const desfichar = async () => {
        const res = await userInstance.desfichar(auth.authToken, ficharDTO);
        
        if (res != undefined) {
            clearInterval(interval);
            setModoFichar(false);
            setFicharDTO({})
        }

    }

    const pauseFichar = () => {

    }

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance}/>
            {user &&
                <main id="app">
                    {
                        !modoFichar ?
                        <section id="sinfichar">
                            <h2>Bienvenido de nuevo, {user.nombre !== undefined ? user.nombre : user.username}</h2>
                            <a onClick={fichar} className="boton">Fichar</a>
                        </section>
                        :
                        <section id="fichado"> 
                            <h2>Tiempo trabajado</h2>
                            <span id="time">{timer && `${formatTime(timer.horas)}:${formatTime(timer.minutos)}:${formatTime(timer.segundos)}`}</span>

                            <div className="container-row">
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-mug-hot"></i></a>
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-pause"></i></a>
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-utensils"></i></a>
                            </div>
                            <a className="boton" onClick={desfichar}>Finalizar</a>
                        </section>
                    }
                    
                       
                </main>
            }
        </div>
    )
}

export default withRouter(FicharApp)

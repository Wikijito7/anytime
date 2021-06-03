import React, {useEffect, useState} from 'react'
import AppNavbar from './navbars/AppNavbar';
import {withRouter} from 'react-router-dom'

import {AuthProvider} from '../auth/AuthProvider'


const FicharApp = (props) => {
    const userInstance = props.user;
    const auth = AuthProvider();

    const [user, setUser] = useState(null);

    const [modoFichar, setModoFichar] = useState(false)

    const [fichajes, setFichajes] = useState([]);

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
        
        if (auth.authToken === undefined) {
            props.history.push("/login")
        }

        const fetchFichados = async () => {
            const user = await userInstance.getUser(auth.authToken);
            const fichados = await userInstance.fetchFichados(auth.authToken, user)

            console.log(fichados);
        }

        fetchUser();
        fetchFichados();
    }, [])


    const fichar = () => {
        setModoFichar(true);
    }

    const desfichar = () => {
        setModoFichar(false);
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
                            <a href="#" onClick={fichar} className="boton">Fichar</a>
                        </section>
                        :
                        <section id="fichado"> 
                            <h2>Tiempo trabajado</h2>
                            <span id="time">0:00:00</span>

                            <div className="container-row">
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-mug-hot"></i></a>
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-pause"></i></a>
                                <a className="botoncir not-selected" onClick={pauseFichar(this)}><i className="fas fa-utensils"></i></a>
                            </div>
                            <a href="#" className="boton" onClick={desfichar}>Finalizar</a>
                        </section>
                    }
                    
                       
                </main>
            }
        </div>
    )
}

export default withRouter(FicharApp)

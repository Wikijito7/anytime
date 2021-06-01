import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom';
import {fetchBase} from '../../utils/Const'

const AppNavbar = (props) => {    
    const [user, setUser] = useState({})

    const token = props.token;

    useEffect(() => {
        const retrieveUser = async () => {
            let user = await props.user.getUser(token);
            setUser(user);
        }

        retrieveUser();
    }, [])

    return (
        <div>
            {user && 
                <header id="header-app">
                    <Link to="/admin" id="admin"><i className="fas fa-cogs"></i></Link>
                    <div id="imagen-app">
                        <Link to="/app"><img src="/img/logo.png" alt="Logo de Anytime" /></Link>
                    </div>
                    <Link to="/app/perfil" className="perfil"><img id="profile" src={`${fetchBase}/user/${user.username}/avatar`}
                        alt="Imagen del usuario" /></Link>
                </header>
            }
        </div>
    )
}

export default AppNavbar

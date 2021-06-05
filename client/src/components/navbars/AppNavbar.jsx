import React, {useEffect, useRef, useState} from 'react'
import {Link} from 'react-router-dom';
import {fetchBase} from '../../utils/Const'

const AppNavbar = (props) => {    
    const [user, setUser] = useState({})
    const [open, setOpen] = useState(false);


    const token = props.token;

    const menu = useRef();

    useEffect(() => {
        const retrieveUser = async () => {
            let user = await props.user.getUser(token);
            setUser(user);
        }

        retrieveUser();
    }, [])

    const menuDesplegable = () => {
        if (open) {
            menu.current.style.display = "none";
        } else {
            menu.current.style.display = "flex";
        }

        setOpen(!open)
    }

    return (
        <div>
            {user && 
                <header id="header-app">
                    <Link to="/app/admin" id="admin"><i className="fas fa-cogs"></i></Link>
                    <div id="imagen-app">
                        <Link to="/app"><img src="/img/logo.png" alt="Logo de Anytime" /></Link>
                    </div>
                    <a onClick={menuDesplegable} className="perfil">
                        <img id="profile" src={`${fetchBase}/user/${user.username}/avatar?${performance.now()}`}
                        alt="Imagen del usuario" />
                        <id id="menu" ref={menu} className="container-column">
                            <Link to="/app/perfil">Perfil</Link>
                            <Link to="/app/editar">Editar perfil</Link>
                            <Link to="/app/logout">Cerrar sesión</Link>
                        </id>
                    </a>
                </header>
            }
        </div>
    )
}

export default AppNavbar

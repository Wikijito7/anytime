import React, {useState} from 'react'

import {Link} from 'react-router-dom'

const NavbarFull = (props) => {
    const [lista, setLista] = useState(false)
    
    const nav = React.createRef();
    const moon = React.createRef();
    const sun = React.createRef();

    console.log();

    const toggleList = () => {
        if (lista) {
            nav.current.classList.replace("visible", "invisible");
        } else {
            nav.current.classList.replace("invisible", "visible");
        }

        setLista(!lista);
    }

    const changeDark = () => {
        document.body.classList.replace("light", "dark");
        sun.current.style.display = "block";
        moon.current.style.display = "none";
    }

    const changeLight = () => {
        document.body.classList.replace("dark", "light");
        moon.current.style.display = "block";
        sun.current.style.display = "none";
    }

    return (
        <header>
            <div className="responsive">
                <Link to="/"><img src="./img/logo.png" alt="Logo de Anytime"/></Link>
                <a id="lista" onClick={() => toggleList()}><i className="fas fa-list-ul" id="icon"></i></a>
            </div>
            <nav ref={nav} id="nav" className="invisible">
                <Link to="/">Inicio</Link>
                {props.token.authToken === undefined ? <Link to="/login">Login</Link> : <Link to="/app">Acceder</Link>}
                <Link to="contacto">Contacto</Link>
                <a ref={sun} id="sun" style={{ display: "none" }} onClick={() => changeLight(this)}><i className="far fa-sun"></i></a>
                <a ref={moon} id="moon" onClick={() => changeDark(this)}><i className="far fa-moon"></i></a>
            </nav>
        </header>
    )
}

export default NavbarFull

import React from 'react'
import {Link} from 'react-router-dom'


const LogoNavbar = () => {
    return (
        <header>
            <Link to="/" className="middle"><img src="/img/logo.png" alt="Logo de Anytime"/></Link>
        </header>
    )
}

export default LogoNavbar

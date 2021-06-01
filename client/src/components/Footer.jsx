import React from 'react'
import {Link} from 'react-router-dom';

const Footer = () => {
    return (
        <footer>
            <p><Link to="contacto">Anytime</Link><i className="fas fa-code"></i> con <i
                className="fas fa-heart"></i> por Cristan.</p>
            <p>Copyright © 2020 Cristian Andrades.</p>
            <p><Link to="term-cond">Términos y condiciones</Link></p>
            <a href="https://wokis.es">Web personal</a>
        </footer>
    )
}

export default Footer

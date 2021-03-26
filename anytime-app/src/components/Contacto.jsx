import React from 'react'

const Contacto = () => {
    return (
        <main>
            <section id="informacion">
                <h1>Sobre nosotros</h1>
                <img src="./img/logo2.png" alt="Logo de Anytime"/>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        
                <div id="contactanos">
                    <h2>Contáctanos</h2>
                    <div className="iconos">
                        <a href="#"><i className="fab fa-twitter"></i></a>
                        <a href="#"><i className="fas fa-phone"></i></a>
                        <a href="#"><i className="fas fa-envelope"></i></a>
                    </div>
                </div>
            </section>
    
            <section id="contacto">
                <h2>¿Tienes una consulta?</h2>
                <form className="" action="index.html" method="post">
                    <label for="correo">Correo electrónico</label><br/>
                    <input type="text" id="correo" name="correo" value=""/><br/>
        
                    <label for="nombre">Nombre y apellidos</label><br/>
                    <input type="text" id="nombre" name="nombre" value=""/><br/>
        
                    <label for="asunto">Asunto</label><br/>
                    <input type="text" id="asunto" name="asunto" value=""/><br/>
        
                    <label for="consulta">Consulta</label><br/>
                    <textarea id="consulta" name="consulta" rows="8" cols="80"></textarea><br/>
                    <input type="checkbox" id="term" name="term" value=""/>
                    <label for="term">
                        He leido y acepto los <a href="terminos-condiciones.html">términos y
                        condiciones de uso</a>.
                    </label><br/>
                    <input type="submit" className="button" name="" value="Enviar"/>
        
                </form>
            </section>
        </main>
    )
}

export default Contacto

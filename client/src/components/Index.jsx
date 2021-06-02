import React from 'react'
import {Link} from 'react-router-dom';

const Index = () => {
    return (
        <main>
            <section id="portada">
                <div className="container-row">
                    <div className="container-column">
                        <h1>Ficha donde y cuando quieras</h1>
                        <p>
                            Anytime no te limita a usarlo en un solo dispositivo. Ya sea al entrar
                            en la oficina desde el móvil o desde el ordenador, estamos disponibles en
                            cualquier dispositivo inteligente actual.
                        </p>
                    
                        <Link className="boton" to="login">Acceder</Link>
                        
                    </div>
                    <img src="./img/test-img.png"
                         alt="Imagen de la portada con la web abierta en un portatil y en un teléfono móvil."/>
                </div>
            </section>
            <section id="contenido">
                <article className="container-row">
                    <div className="container-column">
                        <h2>Simple de utilizar</h2>
                        <p>
                            Con un click ya has fichado. ¿Para qué complicarse? En Anytime
                            valoramos tu tiempo, por eso estamos disponibles en el momento en
                            el que te haga falta y donde te haga falta.
                        </p>
                    </div>
                    <img src="./img/imagen1.png" alt="Imagen trabajador con la aplicación en el teléfono movil"/>
                </article>
                <article className="container-row">
                    <div className="container-column">
                        <h2>Multiplataforma</h2>
                        <p>
                            ¿No tienes un ordenador a mano? ¡No hay problema! Puedes acceder a
                            Anytime en cualquier dispositivo, sea Android o iOS, Windows, Mac
                            o Linux.
                        </p>
                    </div>
                    <img src="./img/multiplataforma.png"
                         alt="Imagen de distintos dispositivos inteligentes que representa multiplataforma"/>
                </article>
                <article className="container-row">
                    <div className="container-column">
                        <h2>Configurable</h2>
                        <p>
                            ¡Te dejamos libertad de configuración! Usa la imagen, el nombre e
                            incluso el tema, además del tiempo de las pausas según creas
                            conveniente.
                        </p>
                    </div>
                    <img src="./img/config.png" alt="Imagen con tuercas y unas herramientas con fondo azul"/>
                </article>
            </section>
        </main>
    )
}

export default Index

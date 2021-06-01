import React from 'react'
import {Link} from 'react-router-dom';

// TODO: Hacer el código para conectar a la API y hacer Register real.

const Register = () => {
    const register = async (e) => {
        e.preventDefault()
    }
    return (
        <main>
            <div id="background">
                <img src="./img/background.png" alt="imagen fondo vectorial"/>
            </div>
            <section id="register">
                <h1>Crear una cuenta</h1>
                <form className="" onSubmit={register}>
                    <label htmlFor="email">Correo electrónico</label> <br/>
                    <input type="text" id="email" name="email" value=""/> <br/>

                    <label htmlFor="user">Nombre de usuario</label> <br/>
                    <input type="text" id="user" name="username" value=""/> <br/>

                    <label htmlFor="pass">Contraseña</label> <br/>
                    <input type="password" id="pass" name="pass" value=""/> <br/>

                    <input type="checkbox" id="term" name="term" value=""/>
                    <label id="term-cond" htmlFor="term">
                        He leido y acepto los <Link to="/term-cond">términos y
                        condiciones de uso</Link>.
                    </label>
                    <br/>

                    <div className="submit">
                        <button>Continuar</button>
                        <br/>
                        <Link to="/login">¿Tienes una cuenta?</Link>
                    </div>
                </form>
            </section>
        </main>
    )
}

export default Register

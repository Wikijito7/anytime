import React from 'react'

// TODO: Hacer el código para conectar a la API y hacer Login real.
// TODO: Añadir cabecera al componente.

const Login = () => {
    return (
        <main>
            <div id="background">
                <img src="./img/background.png" alt="imagen fondo vectorial"/>
            </div>
            <section id="login">
                <h1>Iniciar sesión</h1>
                <form className="" action="app.html" method="post">
                    <label for="usuario">Usuario o correo</label><br/>
                    <input type="text" id="usuario" name="" value=""/><br/>

                    <label for="pass">Contraseña</label><br/>
                    <input type="password" id="pass" name="" value=""/><br/>
                    <span>
                        <a href="recover-pass">¿Has olvidado tu contraseña?</a>
                    </span>
                    <br/>
                    <div className="submit">
                        <input type="submit" name="" value="Acceder"/><br/>
                        <span id="reg">
                            ¿No tienes cuenta? <a href="register.html">Regístrate</a>
                        </span>
                    </div>
                </form>
            </section>
        </main>
    )
}

export default Login

import React, {useEffect, useState} from 'react'
import {Link, withRouter} from 'react-router-dom';
import {AuthProvider} from '../auth/AuthProvider'

// TODO: Hacer el código para conectar a la API y hacer Login real.

const Login = (props) => {
    const auth = AuthProvider();

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const authInstance = props.authInstance

    const login = async (e) => {
        e.preventDefault();
        
        console.log(`${username}, ${password}`);

        if (!username.trim()) {
            console.log("username empty");
            return
        }

        if (!password.trim()) {
            console.log("password empty");
            return
        }

        try {
            let token = await auth.login(username, password)

            console.log(token);

            props.history.push("/app");
        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
        if (auth.authToken != undefined) {
            props.history.push("/app");
        }
    }, [])

    return (
        <main>
            <div id="background">
                <img src="./img/background.png" alt="imagen fondo vectorial"/>
            </div>
            <section id="login">
                <h1>Iniciar sesión</h1>
                <form className="" onSubmit={login}>
                    <label htmlFor="usuario">Usuario o correo</label><br/>
                    <input type="text" id="usuario" name="" onChange={e => setUsername(e.target.value)}
                           value={username}/><br/>

                    <label htmlFor="pass">Contraseña</label><br/>
                    <input type="password" id="pass" name="" onChange={e => setPassword(e.target.value)}
                           value={password}/><br/>
                    <span>
                        <a href="recover-pass">¿Has olvidado tu contraseña?</a>
                    </span>
                    <br/>
                    <div className="submit">
                        <button>Acceder</button><br/>
                        <span id="reg">
                            ¿No tienes cuenta? <Link to="register">Regístrate</Link>
                        </span>
                    </div>
                </form>
            </section>
        </main>
    )
}

export default withRouter(Login)

import React, {useState} from 'react'
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

        try {
            let token = await auth.login(username, password)
            console.log(token);
            
            props.history.push("/app")
            

        } catch(error) {
            console.log(error);
        }
    }

    return (
        <main>
            <div id="background">
                <img src="./img/background.png" alt="imagen fondo vectorial"/>
            </div>
            <section id="login">
                <h1>Iniciar sesión</h1>
                <form className="" onSubmit={login}>
                    <label htmlFor="usuario">Usuario o correo</label><br/>
                    <input type="text" id="usuario" onChange={(event) => setUsername(event.target.value)} name=""
                           value={username}/><br/>

                    <label htmlFor="pass">Contraseña</label><br/>
                    <input type="password" id="pass" name="" onChange={(event) => setPassword(event.target.value)}
                           value={password}/><br/>
                    <span>
                        <a href="recover-pass">¿Has olvidado tu contraseña?</a>
                    </span>
                    <br/>
                    <div className="submit">
                        <input type="submit" name="" value="Acceder"/><br/>
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

import React, {useEffect, useRef, useState} from 'react'
import {Link, useParams, withRouter} from 'react-router-dom';
import {AuthProvider} from '../auth/AuthProvider';

// TODO: Hacer el código para conectar a la API y hacer Register real.

const Register = (props) => {
    
    const { hash } = useParams();

    const { user } = props

    const auth = AuthProvider();

    const [error, setError] = useState("");

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [invitacion, setInvitacion] = useState(null)

    const checkbox = useRef()

    useEffect(() => {
        const getHashInfo = async () => {
            console.log(hash);
            if (hash !== undefined) {
                const invitation = await auth.getInvitation(hash);
                setInvitacion(invitation);
                setEmail(invitation.email)
            }
        }

        getHashInfo();
    }, [])

    const register = async (e) => {
        e.preventDefault();

        setError("");

        if (!username.trim()) {
            setError("El nombre de usuario es obligatorio.")
            return;
        }

        if (username.length < 6) {
            setError("El nombre de usuario debe tener al menos 6 caracteres")
            return;
        }

        if (!password.trim()) {
            setError("La contraseña es obligatoria.")
            return;
        }

        if (username.length < 6) {
            setError("El contraseña debe tener al menos 6 caracteres")
            return;
        }

        if (!checkbox.current.checked) {
            setError("Tienes que aceptar los términos y condiciones.")
            return;
        }

        const token = await auth.register({"username": username, "password": password, "email": email, "empresaId": invitacion.empresa.id})

        if (token != null) {
            await auth.removeInvitation(invitacion);
            props.history.push("/app")
        }

        props.user.clearUser();
    }


    return (
        <main>
            <div id="background">
                <img src="/img/background.png" alt="imagen fondo vectorial"/>
            </div>
            {
                invitacion &&
                <section id="register">
                    <h1>Crear una cuenta</h1>
                    <form className="" onSubmit={register}>
                        {error.trim() && <p className="error">{error}</p>}
                        <label htmlFor="email">Correo electrónico</label> <br/>
                        <input type="text" id="email" name="email" readOnly onChange={(e) => setEmail(e.target.value)} value={email}/> <br/>

                        <label htmlFor="user">Nombre de usuario</label> <br/>
                        <input type="text" id="user" name="username" min="6" max="20" onChange={(e) => setUsername(e.target.value)} value={username}/> <br/>

                        <label htmlFor="pass">Contraseña</label> <br/>
                        <input type="password" id="pass" name="pass" min="6" max="20" onChange={(e) => setPassword(e.target.value)} value={password}/> <br/>

                        <input type="checkbox" id="term" name="term" value="" ref={checkbox}/>
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
            }

        </main>
    )
}

export default withRouter(Register);

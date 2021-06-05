import React, {useEffect, useRef, useState} from 'react'
import {AuthProvider} from '../auth/AuthProvider';
import {ThemeProvider} from '../theme/ThemeProvider';
import {fetchBase} from '../utils/Const';
import AppNavbar from './navbars/AppNavbar';


const Edit = (props) => {
    const auth = AuthProvider();
    const themeInstance = ThemeProvider();
    const userInstance = props.user;

    const [user, setUser] = useState(null)
    const [error, setError] = useState("");

    const light = useRef();
    const dark = useRef();


    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUser(auth.authToken);
                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        fetchUser();
    }, [])

    const changeImage = async (e) => {
        setUser(null);

        const response = await userInstance.changeAvatar(auth.authToken, e.target.files[0])
        const user = await userInstance.refreshUser(auth.authToken);
        
        setUser(user);
    }

    const removeImage = () => {

    }

    const editarPerfil = () => {

    }

    const changeTheme = (theme) => {
        document.body.classList.replace(themeInstance.theme, theme);
        themeInstance.setTheme(theme);
    }
    // ?${performance.now()} es bien. Recargamos la imagen saltándonos ese caché. Gracias, stack.
    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance}/>
            <main>
                {
                    user &&
                    <section id="editar" className="container-column">
                        <article id="editar-avatar"  className="container-row">
                            <img id="userImage" src={`${fetchBase}/user/${user.username}/avatar?${performance.now()}`} alt="Avatar del usuario" />
                            <div className="container-column">
                                <h2>
                                    {
                                        user.nombre !== undefined ?
                                            <p id="userNombre">{`${user.nombre} ${user.apellidos !== undefined ? user.apellidos : ""}`}</p>
                                            : <p id="userNombre">{user.username}</p>
                                    }
                                </h2>
                                <form onSubmit={changeImage}>
                                    <input type="file" hidden id="img" onChange={changeImage} name="img" value="" accept="image/png, image/jpeg"/>
                                    <label htmlFor="img">Cambiar el avatar</label>
                                </form>
                                <span onClick={removeImage}>Eliminar avatar actual</span>
                            </div>
                        </article>
                        <article>
                            <h3>Tema de la aplicación</h3>
                            <div id="theme" className="container-row">
                                <form>
                                    <div className="container-row">
                                        <label>Claro</label>
                                        <input ref={light} type="radio" name="theme" value="claro" onClick={() => changeTheme("light")} checked={themeInstance.theme === "light"}/>
                                    </div>
                                    <div className="container-row">
                                        <label>Oscuro</label>
                                        <input ref={dark} type="radio" name="theme" value="oscuro" onClick={() => changeTheme("dark")} checked={themeInstance.theme === "dark"}/>
                                    </div>
                                </form>
                            </div>
                        </article>
                        <article id="editar-perfil">
                            <h3>Editar perfil</h3>
                            <form onSubmit={editarPerfil}>
                                <label>Email</label>
                                <input type="text" value={user.email} />

                                <label>Nombre</label>
                                <input type="text" value={user.nombre} />

                                <label>Apellidos</label>
                                <input type="text" value={user.apellidos} />

                                <label>Dirección</label>
                                <input type="text" value={user.direccion} />

                                <input type="submit" value="Cambiar" />
                            </form>
                        </article>
                        <article id="editar-pass">
                            <h3>Cambiar contraseña</h3>
                            {error.trim() && <p className="error">{error}</p>}
                            <form onSubmit={editarPerfil}>
                                <label>Antigua contraseña</label>
                                <input type="password" value="" />

                                <label>Nueva contrasñea</label>
                                <input type="password" value="" />

                                <input type="submit" value="Cambiar" />
                            </form>
                        </article>
                    </section>
                }
            </main>
        </div>
    )
}

export default Edit

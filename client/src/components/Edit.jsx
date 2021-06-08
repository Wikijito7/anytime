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

    const [email, setEmail] = useState("");
    const [nombre, setNombre] = useState("");
    const [apellidos, setApellidos] = useState("");
    const [direccion, setDireccion] = useState("");

    const [oldPass, setOldPass] = useState("");
    const [newPass, setNewPass] = useState("");

    const light = useRef();
    const dark = useRef();


    useEffect(() => {
        const fetchUser = async () => {
            try {
                const user = await userInstance.getUser(auth.authToken);

                setEmail(user.email)
                setNombre(user.nombre)
                setApellidos(user.apellidos)
                setDireccion(user.direccion)


                setUser(user);
            } catch (error) {
                console.log(error);
            }
        }

        fetchUser();
    }, [])

    const changeImage = async (e) => {
        const response = await userInstance.changeAvatar(auth.authToken, e.target.files[0])
        if (response !== undefined) {
            const user = await userInstance.refreshUser(auth.authToken);
            setUser(user);
        }
    }

    const removeImage = async () => {
        const response = await userInstance.deleteAvatar(auth.authToken)
        if (response != undefined && response === 200) {
            const user = await userInstance.refreshUser(auth.authToken);
            setUser(user);
        }
    }

    const editarPerfil = async (e) => {
        e.preventDefault();
        setError("");

        if (!email.trim()) {
            setError("El email no puede estar vacío.")
            return
        }
        
        const userEdited = user;
        userEdited.email = email

        if (userEdited.nombre === undefined) {
            Object.defineProperty(userEdited, "nombre", {
                value: nombre === "" ? null : nombre,
                writable: true,
                configurable: true,
                enumerable: true
            });

        } else {
            userEdited.nombre = nombre === "" ? null : nombre
        }

        if (userEdited.apellidos === undefined) {
            Object.defineProperty(userEdited, "apellidos", {
                value: apellidos === "" ? null : apellidos,
                writable: true,
                configurable: true,
                enumerable: true
            });

        } else {
            userEdited.apellidos = apellidos === "" ? null : apellidos
        }

        if (userEdited.direccion === undefined) {
            Object.defineProperty(userEdited, "direccion", {
                value: direccion === "" ? null : direccion,
                writable: true,
                configurable: true,
                enumerable: true
            });

        } else {
            userEdited.direccion = direccion === "" ? null : direccion
        }

        const response = await userInstance.editUser(userEdited, auth.authToken)

        if (response != undefined && response === 200) {
            const user = await userInstance.refreshUser(auth.authToken);
            setUser(user);
        }
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
                                {error.trim() && <p className="error">{error}</p>}
                                <label>Email</label>
                                <input type="text" onChange={(e) => setEmail(e.target.value)} value={email} />

                                <label>Nombre</label>
                                <input type="text" onChange={(e) => setNombre(e.target.value)} value={nombre} />

                                <label>Apellidos</label>
                                <input type="text" onChange={(e) => setApellidos(e.target.value)} value={apellidos} />

                                <label>Dirección</label>
                                <input type="text" onChange={(e) => setDireccion(e.target.value)} value={direccion} />

                                <input type="submit" value="Cambiar" />
                            </form>
                        </article>
                        <article id="editar-pass">
                            <h3>Cambiar contraseña</h3>
                            {error.trim() && <p className="error">{error}</p>}
                            <form onSubmit={editarPerfil}>
                                <label>Antigua contraseña</label>
                                <input type="password" onChange={(e) => setOldPass(e.target.value)} value={oldPass} />

                                <label>Nueva contraseña</label>
                                <input type="password" onChange={(e) => setNewPass(e.target.value)} value={newPass} />

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

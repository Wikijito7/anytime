import React from 'react'

// TODO: Hacer el código para conectar a la API y hacer Register real.
// TODO: Añadir cabecera al componente.

const Register = () => {
    return (
        <main>
            <div id="background">
            <img src="./img/background.png" alt="imagen fondo vectorial"/>
            </div>
            <section id="register">
            <h1>Crear una cuenta</h1>
            <form className="" action="" method="post">
                <label for="email">Correo electrónico</label> <br/>
                <input type="text" id="email" name="email" value=""/> <br/>
    
                <label for="user">Nombre de usuario</label> <br/>
                <input type="text" id="user" name="username" value=""/> <br/>
    
                <label for="pass">Contraseña</label> <br/>
                <input type="password" id="pass" name="pass" value=""/> <br/>
    
                <input type="checkbox" id="term" name="term" value=""/>
                <label for="term">
                    He leido y acepto los <a href="term-cond.html">términos y
                    condiciones de uso</a>.
                </label>
                <br/>
                
                <div className="submit">
                    <input type="submit" value="Continuar"/> <br/>
                    <a href="login.html">¿Tienes una cuenta?</a>
                </div>
            </form>
            </section>
        </main>
    )
}

export default Register

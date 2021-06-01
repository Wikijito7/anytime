import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import Footer from './components/Footer';
import Index from './components/Index';
import NavbarFull from './components/navbars/NavbarFull'
import LogoNavbar from './components/navbars/LogoNavbar';
import Login from './components/Login';
import Register from './components/Register';
import Contacto from './components/Contacto';
import FicharApp from './components/FicharApp';

import {AuthProvider} from './auth/AuthProvider'
import {UserProvider} from './user/UserProvider'
import Perfil from './components/Perfil';
import User from './components/User';


// TODO: Comprobar las cookies para saber si está logueado y pasarselo a la página que se encuentra para temas como
// el dark mode y que acceda directamente a la app en vez de pasar por el Login.

function App() {
    const auth = AuthProvider()
    const userInstance = UserProvider()

    return (
        <div>
            <Router>
                <Switch>
                    <Route path="/" exact>
                        <NavbarFull token={auth}/>
                        <Index/>
                        <Footer/>
                    </Route>
                    <Route path="/login">
                        <LogoNavbar/>
                        <Login auth={auth}/>
                    </Route>
                    <Route path="/register">
                        <LogoNavbar/>
                        <Register auth={auth}/>
                    </Route>
                    <Route path="/contacto">
                        <NavbarFull token={auth} />
                        <Contacto/>
                        <Footer/>
                    </Route>
                    <Route path="/app" exact>
                        <FicharApp auth={auth} user={userInstance}/>
                    </Route>
                    <Route path="/app/perfil">
                        <Perfil auth={auth} user={userInstance} />
                    </Route>
                    <Route path="/app/u/:username">
                        <User auth={auth} user={userInstance}/>
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;

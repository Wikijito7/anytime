import React, {useEffect, useState} from 'react';
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
import Perfil from './components/Perfil';


// TODO: Comprobar las cookies para saber si está logueado y pasarselo a la página que se encuentra para temas como
// el dark mode y que acceda directamente a la app en vez de pasar por el Login.

function App() {

    const [token, setToken] = useState(undefined)
    let auth = AuthProvider()

    useEffect(() => {
        const getToken = async () => {
            const token = auth.authToken
            setToken(token);
        }
        getToken();
    }, [])

    return (
        <div>
            <Router>
                <Switch>
                    <Route path="/" exact>
                        <NavbarFull token={token}/>
                        <Index/>
                        <Footer/>
                    </Route>
                    <Route path="/login">
                        <LogoNavbar/>
                        <Login/>
                    </Route>
                    <Route path="/register">
                        <LogoNavbar/>
                        <Register/>
                    </Route>
                    <Route path="/contacto">
                        <NavbarFull/>
                        <Contacto/>
                        <Footer/>
                    </Route>
                    <Route path="/app">
                        <FicharApp token={token}/>
                    </Route>
                    <Route path="/perfil">
                        <Perfil token={token}/>
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;

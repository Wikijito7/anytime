import React, {useEffect} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import Footer from './components/Footer';
import Index from './components/Index';
import NavbarFull from './components/navbars/NavbarFull'
import LogoNavbar from './components/navbars/LogoNavbar';
import Login from './components/Login';
import Register from './components/Register';
import Contacto from './components/Contacto';
import FicharApp from './components/FicharApp';
import Perfil from './components/Perfil';
import User from './components/User';
import Admin from './components/Admin';

import {AuthProvider} from './auth/AuthProvider'
import {UserProvider} from './user/UserProvider'
import {ThemeProvider} from './theme/ThemeProvider';
import Logout from './components/Logout';
import Edit from './components/Edit';
import TermCond from './components/TermCond';


// TODO: Comprobar las cookies para saber si está logueado y pasarselo a la página que se encuentra para temas como
// el dark mode y que acceda directamente a la app en vez de pasar por el Login.

function App() {
    const auth = AuthProvider();
    const userInstance = UserProvider();
    const themeProvider = ThemeProvider();

    useEffect(() => {
        const theme = () => {
            if (themeProvider.theme === undefined) {
                themeProvider.setTheme('light');
                return;
            }

            document.body.classList.replace('light', themeProvider.theme);
        }

        theme();
    }, [])

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
                        <Login auth={auth} user={userInstance}/>
                    </Route>
                    <Route path="/register">
                        <LogoNavbar/>
                        <Register auth={auth} user={userInstance}/>
                    </Route>
                    <Route path="/contacto">
                        <NavbarFull token={auth} />
                        <Contacto/>
                        <Footer/>
                    </Route>
                    <Route path="/app" exact>
                        <FicharApp user={userInstance}/>
                    </Route>
                    <Route path="/app/perfil">
                        <Perfil user={userInstance} />
                    </Route>
                    <Route path="/app/u/:username">
                        <User user={userInstance}/>
                    </Route>
                    <Route path="/app/admin">
                        <Admin user={userInstance} />
                    </Route>
                    <Route path="/app/logout">
                        <Logout user={userInstance}/>
                    </Route>
                    <Route path="/app/editar">
                        <Edit user={userInstance} />
                    </Route>
                    <Route path="/term-cond">
                        <NavbarFull token={auth} />
                        <TermCond />
                        <Footer />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;

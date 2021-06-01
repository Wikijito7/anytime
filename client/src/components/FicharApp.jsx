import React from 'react'
import AppNavbar from './navbars/AppNavbar';

import {AuthProvider} from '../auth/AuthProvider'


const FicharApp = (props) => {
    const userInstance = props.user;
    const auth = AuthProvider();

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance}/>
        </div>
    )
}

export default FicharApp

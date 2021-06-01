import React from 'react'
import AppNavbar from './navbars/AppNavbar';

const FicharApp = (props) => {
    const userInstance = props.user;
    const auth = props.auth;

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance}/>
            
        </div>
    )
}

export default FicharApp

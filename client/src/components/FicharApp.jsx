import React, {useEffect} from 'react'
import AppNavbar from './navbars/AppNavbar';
import {withRouter} from 'react-router-dom'

import {AuthProvider} from '../auth/AuthProvider'


const FicharApp = (props) => {
    const userInstance = props.user;
    const auth = AuthProvider();

    useEffect(() => {
        if (auth.authToken === undefined) {
            props.history.push("/login")
        }
    }, [])

    return (
        <div>
            <AppNavbar token={auth.authToken} user={userInstance}/>
        </div>
    )
}

export default withRouter(FicharApp)

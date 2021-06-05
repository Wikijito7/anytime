import React, {useEffect} from 'react'
import {withRouter} from 'react-router-dom';
import {AuthProvider} from '../auth/AuthProvider';

const Logout = (props) => {

    const auth = AuthProvider();
    const userIntance = props.user;
    
    useEffect(() => {
        const logout = () => {
            auth.logout();
            userIntance.clearUser();

            props.history.push("/")
        }

        logout();
    }, [])

    return (
        <div>
            <p>Hasta pronto.. <i className="fas fa-heart"></i></p>
        </div>
    )
}

export default withRouter(Logout)

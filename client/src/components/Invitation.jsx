import React, {useRef, useState} from 'react'

const Invitation = (props) => {
    
    const { token, userInstance, close } = props;

    const [loading, setLoading] = useState(false)
    const [sent, setSent] = useState(false)
    const [noSent, setNoSent] = useState(false)

    const [email, setEmail] = useState("");
    const [error, setError] = useState("");

    const padre = useRef()

    const sleep = (ms) => {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    const invitar = async (e) => {
        e.preventDefault();
        setError("")

        if (!email.trim()) {
            setError("El email es obligatorio")
            return
        }

        setLoading(true);
        const user = await userInstance.getUser(token)
        const response = await userInstance.invite(token, email, user.empresa);
        setLoading(false);

        if (response !== undefined) {
            setSent(true);
            await sleep(2000)
            setSent(false);
            setEmail("");
        } else {
            setNoSent(true);
            await sleep(2000)
            setNoSent(false);
            setEmail("");
        }
    }

    return (
        <div ref={padre} id="modal-container">
            <div id="modal-invite" className="container-column">
                {loading && 
                    <div id="loading" className="container-column">
                        <div>
                            <i className="far fa-clock fa-spin"></i>
                        </div>
                        <h2>Enviando la invitación</h2>
                    </div>
                }

                {
                    sent && 
                    <div id="sent" className="container-column">
                        <i className="far fa-check-circle"></i>
                        <h2>Enviada la invitación</h2>
                    </div>
                }

                {
                    noSent &&
                    <div id="nosent" className="container-column">
                        <i className="far fa-times-circle"></i>
                        <h2>Ha ocurrido un error</h2>
                    </div>
                }


                <div className="container-column">
                    <h2>Invitar a un usuario</h2>
                    {error.trim() && <p className="error">{error}</p>}
                    <form onSubmit={invitar}>
                        <label>Email</label>
                        <input type="text" onChange={(e) => setEmail(e.target.value)} value={email} />
                        <input type="submit" value="Invitar" />
                    </form>
                    <span onClick={() => close()} id="cerrar" className="far fa-times-circle"></span>
                </div>
            </div>
        </div>
    )
}

export default Invitation

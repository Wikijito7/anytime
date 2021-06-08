import React from 'react'

const ConfirmModal = (props) => {

    let { close, remove } = props

    return (
        <div id="modal-container">
            <div id="modal-invite" className="container-column">
                <div className="container-column">
                    <h2>¿Estás seguro?</h2>
                    <p>Una vez eliminado, no hay vuelta atrás.</p>
                    <button onClick={() => remove()} className="danger">Eliminar</button>
                    <button onClick={() => close()}>Volver</button>
                </div>
            </div>
        </div>
    )
}

export default ConfirmModal

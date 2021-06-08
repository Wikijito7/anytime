import React, {useEffect, useState} from 'react'
import {timeToString} from '../utils/DateUtils';

const EditFichajeModal = (props) => {
    
    const { token, fichajeDTO, userInstance, close, update } = props;

    const [entrada, setEntrada] = useState("");
    const [salida, setSalida] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const setupModal = () => {
            setEntrada(timeToString(fichajeDTO.entrada));

            if (fichajeDTO.salida != undefined) {
                setSalida(timeToString(fichajeDTO.salida))
            }
        }

        setupModal();
    }, [])

    const editarFichaje = async (e) => {
        e.preventDefault();
        setError("")

        if (!entrada.trim()) {
            setError("El campo de entrada es obligatorio.");
            return
        }
        
        if (!salida.trim()) {
            fichajeDTO.salida = null;
        } else {
            const salidaArray = salida.split(":");

            if (fichajeDTO.salida === undefined) {
                Object.defineProperty(fichajeDTO, 'salida', { 
                    value: {
                        "date": fichajeDTO.entrada.date,
                         "time": {
                                "hour": parseInt(salidaArray[0]),
                                "minute": parseInt(salidaArray[1]),
                                "second": 0
                            }
                    }, 

                    writable: true,
                    configurable: true,
                    enumerable: true
                });
            } else {
                fichajeDTO.salida.time.hour = parseInt(salidaArray[0]);
                fichajeDTO.salida.time.minute = parseInt(salidaArray[1]);
                fichajeDTO.salida.time.second = 0;
            }

        }
        
        const entradaArray = entrada.split(":");

        fichajeDTO.entrada.time.hour = parseInt(entradaArray[0]);
        fichajeDTO.entrada.time.minute = parseInt(entradaArray[1]);
        fichajeDTO.entrada.time.second = 0;


        const response = await userInstance.editFichaje(fichajeDTO, token);

        if (response === 200) {
            update();
            close();
        }
    }

    return (
        <div id="modal-container">
            <div id="modal-invite" className="container-column">
                <div className="container-column">
                    <h2>Editar fichaje</h2>
                    {error.trim() && <p className="error">{error}</p>}
                    <form onSubmit={editarFichaje}>
                        <label>Hora de entrada</label>
                        <input type="time" onChange={(e) => setEntrada(e.target.value)} value={entrada} />
                        <label>Hora de salida</label>
                        <input type="time" onChange={(e) => setSalida(e.target.value)} value={salida} />
                        <input type="submit" value="Editar" />
                    </form>
                    <span onClick={() => close()} id="cerrar" className="far fa-times-circle"></span>
                </div>
            </div>
        </div>
    )
}

export default EditFichajeModal

import moment from 'moment'

const cookieExpire = moment().add(30, 'days').toDate();
const themeExpire = moment().add(100, 'years').toDate();

const today = () => {
    const now = moment();

    return {
        "day": now.date(),
        "month": now.month() + 1,
        "year": now.year()
    };
}

const getHoras = (ficharDTO) => {
    const entrada = ficharDTO.entrada;
    const salida = ficharDTO.salida;
    const entradaFecha = moment(datetimeToString(entrada), "YYYY/MM/DD hh:mm:ss");
    let salidaFecha;

    if (salida !== undefined && salida !== null) {
        salidaFecha = moment(datetimeToString(salida), "YYYY/MM/DD hh:mm:ss");
    } else {
        salidaFecha = moment();
    }

    const horas = salidaFecha.diff(entradaFecha, 'hours');
    const minutos = salidaFecha.subtract(horas, 'hours').diff(entradaFecha, 'minutes');
    const segundos = salidaFecha.subtract(minutos, 'minutes').diff(entradaFecha, 'seconds');

    const horasObj = {
        "horas": horas,
        "minutos": minutos,
        "segundos": segundos
    };

    return horasObj;
}

const datetimeToString = (date) => {
    return `${date.date.year}/${date.date.month}/${date.date.day} ${date.time.hour}:${date.time.minute}:${date.time.second}`;
}

const dateToString = (date) => {
    return `${date.date.year}/${date.date.month}/${date.date.day}`;
}

const getHorasToString = (horas) => `${formatTime(horas.horas)}:${formatTime(horas.minutos)}:${formatTime(horas.segundos)}`;


const timeToString = (date) => {
    return `${formatTime(date.time.hour)}:${formatTime(date.time.minute)}:${formatTime(date.time.second)}`;
}

const formatTime = (time) => {
    return time >= 10 ? `${time}` : `0${time}`;
}

const drawDate = (date) => {
    return `${date.date.day}/${date.date.month}/${date.date.year}`;
}

const dateInWeek = (date) => {
    const firstDay = moment().startOf('isoWeek');
    const lastDay = moment().endOf('isoWeek');
    const day = moment(datetimeToString(date), "YYYY/MM/DD hh:mm:ss");

    return (day >= firstDay && day <= lastDay);
}

const dateInMonth = (date) => {
    const firstDay = moment().startOf('month');
    const lastDay = moment().endOf('month');
    const day = moment(datetimeToString(date), "YYYY/MM/DD hh:mm:ss");

    return (day >= firstDay && day <= lastDay);
}

const dateInToday = (date) => {
    const day = moment(datetimeToString(date), "YYYY/MM/DD hh:mm:ss");
    const today = moment()

    return day.date() === today.date() && day.month() === today.month() && day.year() === today.year()
}

export { cookieExpire, getHoras, datetimeToString, dateToString, 
    timeToString, formatTime, dateInWeek, dateInMonth, today,
    drawDate, getHorasToString, dateInToday, themeExpire
}
import moment from 'moment'


// const fetchBase = "dev.wokis.es"
const fetchBase = "http://localhost:8000"
const cookieExpire = moment().add(30, 'days').toDate();
export { fetchBase, cookieExpire}
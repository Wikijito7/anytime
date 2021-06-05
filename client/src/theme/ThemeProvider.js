import {useCookies} from 'react-cookie';
import {themeExpire} from '../utils/DateUtils'

const ThemeProvider = () => {
    const [cookies, setCookie, removeCookie] = useCookies(['theme']);

    const theme = cookies.theme

    const setTheme = (theme) => {
        setCookie('theme', theme, { expires: themeExpire });
    }

    return { theme, setTheme }
}

export { ThemeProvider }
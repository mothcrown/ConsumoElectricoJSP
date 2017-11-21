
package consumoelectricojsp.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  Clase ServletUtils. Aquí nos encargamos de lo de las cookies!
 * 
 * @author mothcrown
 */
public class ServletUtils {
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie cookie = null;
        
        if (request.getCookies() != null) {
            for(Cookie tmpCookie: request.getCookies()) {
                if (tmpCookie.getName().equalsIgnoreCase(cookieName))
                    cookie = tmpCookie;
            }
        }
        
        return cookie;
    }
}

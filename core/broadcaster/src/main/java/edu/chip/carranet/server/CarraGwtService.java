package edu.chip.carranet.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.carranet.client.auth.Token;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
public abstract class CarraGwtService extends RemoteServiceServlet {
    public static final String AUTH_STRING_TOKEN = "authorizationStringToken";

    public void setAuthToken(Token t) {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        session.setAttribute(AUTH_STRING_TOKEN, t);
    }

    public Token getAuthToken() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        Object result = session.getAttribute(AUTH_STRING_TOKEN);
        if (result != null) {
            return (Token) result;
        } else {
            return null;
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());
        AutowireCapableBeanFactory beanFactory = ctx
                .getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }



}

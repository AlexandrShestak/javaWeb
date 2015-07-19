package com.shestakam.authorization;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alexandr on 18.7.15.
 */
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session =  req.getSession();
        if(session==null){
            res.sendRedirect("index.jsp");
        }
        String login = (String)session.getAttribute("login");
        if (login!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            res.sendRedirect("index.jsp");
        }


    }

    @Override
    public void destroy() {

    }
}

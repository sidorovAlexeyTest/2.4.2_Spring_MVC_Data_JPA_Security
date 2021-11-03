package springMVCHibernate.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import springMVCHibernate.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
       if(roles.contains("ROLE_ADMIN") && roles.contains("ROLE_USER")) {
           response.sendRedirect("/admin");
       } else if (roles.contains("ROLE_USER")) {
           User user =  (User)authentication.getPrincipal();
           response.sendRedirect("/user/" + user.getUsername());
       }
    }
}
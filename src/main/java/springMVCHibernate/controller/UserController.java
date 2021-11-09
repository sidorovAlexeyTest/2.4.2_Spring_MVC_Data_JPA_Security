package springMVCHibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springMVCHibernate.model.User;
import springMVCHibernate.service.user.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String getUserHome(@ModelAttribute("user") User user, @PathVariable(name = "username") String username){
        user = userServiceImpl.findUserByName(username);
        return "userPage";
    }
}

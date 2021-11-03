package springMVCHibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springMVCHibernate.model.User;
import springMVCHibernate.service.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/{username}")
    public String getUserHome(@PathVariable(name = "username") String username, Model model){
        User user = userServiceImpl.findUserByName(username);
        model.addAttribute("user", user);
        return "userPage";
    }
}

package springMVCHibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springMVCHibernate.model.User;
import springMVCHibernate.service.user.UserService;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @PreAuthorize("#username == authentication.principal.username")
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ModelAndView getUserHome(@PathVariable("username") String username, ModelAndView modelAndView){
        User user = userServiceImpl.findUserByName(username);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    @Transactional
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("username") String username) {
        String userName = new String(user.getUsername().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String userSurname = new String(user.getSurname().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        User oldUser = userServiceImpl.findUserByName(username);
        userServiceImpl.update(oldUser.getId(), userName, userSurname, user.getBirthdate(), user.getPassword(), oldUser.getRoles());
        return "redirect:/user/"+userName;
    }
}

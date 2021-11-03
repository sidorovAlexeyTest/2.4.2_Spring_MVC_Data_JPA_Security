package springMVCHibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springMVCHibernate.model.Role;
import springMVCHibernate.model.User;
import springMVCHibernate.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private UserService userServiceImpl;

    @Autowired
    public AdminController(UserService userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping()
    public ModelAndView getHomePage(ModelAndView modelAndView, ModelMap modelMap) {
        List<User> userList = userServiceImpl.readAll();
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleAdmin.setId(1L);
        Role roleUser = new Role("ROLE_USER");
        roleUser.setId(2L);
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("roleAdmin", roleAdmin);
        modelMap.addAttribute("roleUser", roleUser);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("newUser") User newUser) {
        String userName = new String(newUser.getUsername().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String userSurname = new String(newUser.getSurname().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        userServiceImpl.addByAdmin(userName, userSurname, newUser.getBirthdate(), newUser.getPassword(), newUser.getRoles());
        return "redirect:/admin";
    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        String userName = new String(user.getUsername().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String userSurname = new String(user.getSurname().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        userServiceImpl.update(id, userName, userSurname, user.getBirthdate(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }

}

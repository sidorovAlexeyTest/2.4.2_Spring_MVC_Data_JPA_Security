package springMVCHibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springMVCHibernate.model.Role;
import springMVCHibernate.model.User;
import springMVCHibernate.service.role.RoleService;
import springMVCHibernate.service.user.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private UserService userServiceImpl;
    private static Role ROLE_ADMIN;
    private static Role ROLE_USER;

    @Autowired
    public AdminController(UserService userServiceImpl, RoleService roleServiceImpl){
        this.userServiceImpl = userServiceImpl;
        ROLE_ADMIN = roleServiceImpl.readAll().get(0);
        ROLE_USER = roleServiceImpl.readAll().get(1);
    }

    @RequestMapping()
    @Transactional
    public ModelAndView getHomePage(ModelAndView modelAndView, ModelMap modelMap) {
        List<User> userList = userServiceImpl.readAll();
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("admin_role", ROLE_ADMIN);
        modelMap.addAttribute("user_role", ROLE_USER);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("adminPage");
        return modelAndView;
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("newUser") User newUser,
                          @RequestParam(value = "action", required = true) String action) {
        String userName = new String(newUser.getUsername().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String userSurname = new String(newUser.getSurname().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Set<Role> roles = new HashSet<>();
        roles.add(ROLE_USER);
        if(action.equals("addAdmin")){
            roles.add(ROLE_ADMIN);
        }
        userServiceImpl.addByAdmin(userName, userSurname, newUser.getBirthdate(), newUser.getPassword(), roles);
        return "redirect:/admin";
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") long id) {
        String userName = new String(user.getUsername().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String userSurname = new String(user.getSurname().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        User updateUser = userServiceImpl.read(id);
        userServiceImpl.update(id, userName, userSurname, user.getBirthdate(), user.getPassword(), updateUser.getRoles());
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}

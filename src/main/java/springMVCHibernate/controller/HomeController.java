package springMVCHibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }
}

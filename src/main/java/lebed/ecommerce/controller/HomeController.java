package lebed.ecommerce.controller;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.HOME)
public class HomeController {

    @RequestMapping("/")
    String home() {
        return "index";
    }

}

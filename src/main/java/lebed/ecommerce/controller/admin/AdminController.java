package lebed.ecommerce.controller.admin;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.ADMIN)
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("")
    public String admin(Model model) {
        model.addAttribute("viewHelper", "success");
        return "admin/index";
    }
}

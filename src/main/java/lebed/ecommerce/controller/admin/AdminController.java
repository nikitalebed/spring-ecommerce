package lebed.ecommerce.controller.admin;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import lebed.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.ADMIN)
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("")
    public String admin(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/index";
    }
}

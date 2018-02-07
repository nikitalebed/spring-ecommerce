package lebed.ecommerce.controller.section;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.PRODUCT)
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("")
    public String product(Model model) {
        return "product/index";
    }

}

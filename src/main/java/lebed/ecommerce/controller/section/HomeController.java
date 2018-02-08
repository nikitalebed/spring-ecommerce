package lebed.ecommerce.controller.section;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import lebed.ecommerce.model.News;
import lebed.ecommerce.repository.NewsRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Navigation(Section.HOME)
public class HomeController {

    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping("/")
    String home(Model model) {
        List<News> latestNews =
                ((List<News>) newsRepository.findAll())
                        .stream()
                        .filter(n -> DateUtils.isSameDay(n.getCreateDate(), new Date()))
                        .collect(Collectors.toList());
        model.addAttribute("latestNews", latestNews);
        return "index";
    }

}

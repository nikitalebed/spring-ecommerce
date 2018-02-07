package lebed.ecommerce.controller.section;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import lebed.ecommerce.model.News;
import lebed.ecommerce.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@Navigation(Section.NEWS)
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping("")
    public String admin(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "news/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createNew(@RequestParam String github, @RequestParam String message, Model model) {
        newsRepository.save(new News(github, message, new Date()));
        model.addAttribute("news", newsRepository.findAll());
        return "news/index";
    }
}

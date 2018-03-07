package lebed.ecommerce.controller.section;

import lebed.ecommerce.config.web.Navigation;
import lebed.ecommerce.config.web.Section;
import lebed.ecommerce.config.web.domain.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Navigation(Section.CONTACT_US)
public class ContactUsController {

    private static final String FEEDBACK_MODEL_KEY = "feedback";

    private static final String CONTACT_US_VIEW_NAME = "contact/index";

    @RequestMapping(value = "/contactUs")
    public String contactGet(ModelMap model) {
        Feedback feedback = new Feedback();
        model.addAttribute(FEEDBACK_MODEL_KEY, feedback);
        return CONTACT_US_VIEW_NAME;
    }

//    @RequestMapping(value = "/contact", method = RequestMethod.POST)
//    public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) Feedback feedback) {
//        LOG.debug("Feedback content: {}", feedback);
//        emailService.sendFeedbackEmail(feedback);
//        return ContactController.CONTACT_US_VIEW_NAME;
//    }

}

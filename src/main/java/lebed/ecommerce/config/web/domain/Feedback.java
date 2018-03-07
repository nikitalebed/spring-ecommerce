package lebed.ecommerce.config.web.domain;

import lombok.Data;

@Data
public class Feedback {

    private String email;
    private String firstName;
    private String lastName;
    private String feedback;

}

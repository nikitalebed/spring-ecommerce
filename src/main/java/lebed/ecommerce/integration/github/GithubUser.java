package lebed.ecommerce.integration.github;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUser implements Serializable {

    private String email;

    private String name;

    private String company;

    private String blog;

    @JsonProperty("avatar_url")
    private String avatar;

}


package lebed.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String twitter;

    private String github;

    private String password;

    private String avatarUrl;

    @Column
    @Lob
    private String bio;

    private String created;

}

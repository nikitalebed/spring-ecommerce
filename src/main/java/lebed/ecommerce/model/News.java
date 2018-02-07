package lebed.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue
    private Integer id;

    private String user;

    private String message;

    private Date createDate;

    public News(String user, String message, Date createDate) {
        this.user = user;
        this.message = message;
        this.createDate = createDate;
    }
}

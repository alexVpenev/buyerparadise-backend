package individualProjectAleksandarPenev.BuyersParadise.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
public abstract class User {

    @Getter @Setter
    protected int id;

    @Getter @Setter
    protected String username;

    @Getter @Setter
    protected String email;

    @Getter @Setter
    protected String password;

    @Getter
    protected String role;


    public  User() {

    }

    public User(int id, String username,String email, String password, String role)
    {
        this.id=id;
        this.username = username;
        this.email=email;
        this.password=password;
        this.role = role;
    }
}

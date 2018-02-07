import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-16
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@SpringBootApplication
public class Application {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}

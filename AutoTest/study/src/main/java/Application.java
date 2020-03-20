import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apple on 2020/3/6.
 */
@SpringBootApplication
@ComponentScan("com.course")
public class Application {
    public static void main(String[] args){
        SpringApplication.run (Application.class,args);

    }
}

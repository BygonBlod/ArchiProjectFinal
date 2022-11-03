package InternCRM;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducingWebServiceApplication {

    public static void main(String[] args) {
        System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
        SpringApplication.run(ProducingWebServiceApplication.class, args);
    }
}

package pl.wsb;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessTracker {

    public static void main(String[] args) {
        SpringApplication.run(FitnessTracker.class, args);
        System.out.println("hello michał  from tomek");
    }

}

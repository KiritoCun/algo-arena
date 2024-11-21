package vn.udn.dut.algoarena.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Starting program
 *
 * @author HoaLD
 */
@EnableFeignClients
@SpringBootApplication
@ComponentScan("vn.udn.dut.algoarena")
public class AlgoArenaCustomerApplication {

    public static void main(String[] args) {
//    	System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(AlgoArenaCustomerApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("AlgoArena Customer started successfully");
    }
}

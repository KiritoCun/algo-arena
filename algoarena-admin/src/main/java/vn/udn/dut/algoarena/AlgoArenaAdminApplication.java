package vn.udn.dut.algoarena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Starting program
 *
 * @author HoaLD
 */
@EnableFeignClients
@SpringBootApplication
public class AlgoArenaAdminApplication {

    public static void main(String[] args) {
//    	System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(AlgoArenaAdminApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("AlgoArena Admin started successfully");
    }
}

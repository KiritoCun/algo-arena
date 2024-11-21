package vn.udn.dut.algoarena;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Deploy in web container
 *
 * @author HoaLD
 */
public class AlgoArenaAdminServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlgoArenaAdminApplication.class);
    }

}

package vn.udn.dut.algoarena.customer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Deploy in web container
 *
 * @author HoaLD
 */
public class AlgoArenaCustomerServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlgoArenaCustomerApplication.class);
    }

}

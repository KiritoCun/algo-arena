package vn.udn.dut.algoarena.common.core.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Read project-related configuration
 *
 * @author HoaLD
 */

@Data
@Component
@ConfigurationProperties(prefix = "algoarena")
public class AlgoArenaConfig {

    /**
     * project name
     */
    private String name;

    /**
     * Version
     */
    private String version;

    /**
     * copyright year
     */
    private String copyrightYear;

    /**
     * Example demo switch
     */
    private boolean demoEnabled;

    /**
     * get address switch
     */
    @Getter
    private static boolean addressEnabled;

    public void setAddressEnabled(boolean addressEnabled) {
        AlgoArenaConfig.addressEnabled = addressEnabled;
    }

}

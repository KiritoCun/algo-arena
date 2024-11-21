package vn.udn.dut.algoarena.common.oss.factory;

import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.algoarena.common.core.constant.CacheNames;
import vn.udn.dut.algoarena.common.core.utils.StringUtils;
import vn.udn.dut.algoarena.common.json.utils.JsonUtils;
import vn.udn.dut.algoarena.common.redis.utils.CacheUtils;
import vn.udn.dut.algoarena.common.redis.utils.RedisUtils;
import vn.udn.dut.algoarena.common.oss.constant.OssConstant;
import vn.udn.dut.algoarena.common.oss.core.OssClient;
import vn.udn.dut.algoarena.common.oss.exception.OssException;
import vn.udn.dut.algoarena.common.oss.properties.OssProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File Upload Factory
 *
 * @author HoaLD
 */
@Slf4j
public class OssFactory {

    private static final Map<String, OssClient> CLIENT_CACHE = new ConcurrentHashMap<>();

    /**
     * Get the default instance
     */
    public static OssClient instance() {
        // Get redis default type
        String configKey = RedisUtils.getCacheObject(OssConstant.DEFAULT_CONFIG_KEY);
        if (StringUtils.isEmpty(configKey)) {
            throw new OssException("The file storage service type cannot be found!");
        }
        return instance(configKey);
    }

    /**
     * Get an instance by type
     */
    public static OssClient instance(String configKey) {
        String json = CacheUtils.get(CacheNames.SYS_OSS_CONFIG, configKey);
        if (json == null) {
            throw new OssException("System exception, '" + configKey + "' configuration information does not exist!");
        }
        OssProperties properties = JsonUtils.parseObject(json, OssProperties.class);
        // Use the tenant ID to avoid overwriting multiple tenants with the same key instance
        String key = properties.getTenantId() + ":" + configKey;
        OssClient client = CLIENT_CACHE.get(key);
        if (client == null) {
            CLIENT_CACHE.put(key, new OssClient(configKey, properties));
            log.info("Create OSS instance key => {}", configKey);
            return CLIENT_CACHE.get(key);
        }
        // If the configuration is not the same, rebuild
        if (!client.checkPropertiesSame(properties)) {
            CLIENT_CACHE.put(key, new OssClient(configKey, properties));
            log.info("Reload OSS instance key => {}", configKey);
            return CLIENT_CACHE.get(key);
        }
        return client;
    }

}

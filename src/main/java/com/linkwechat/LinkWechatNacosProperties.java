package com.linkwechat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author leejoker <1056650571@qq.com>
 * @version 0.1-SNAPSHOT
 * @date 2021/4/19 10:18
 */
@Data
@ConfigurationProperties(prefix = "linkwechat.nacos")
public class LinkWechatNacosProperties {
    private String host = "127.0.0.1";
    private Integer port = 8848;
    private String namespace;
    private final String extension = "yaml";
    private final String applicationName = "link-wechat";

    @Autowired
    @JsonIgnore
    private Environment environment;

    @PostConstruct
    public void init() {
        this.overrideFromEnv();
    }

    private void overrideFromEnv() {
        if (StringUtils.isEmpty(this.getHost())) {
            String host = environment.resolvePlaceholders("${linkwechat.nacos.host:}");
            if (StringUtils.isEmpty(host)) {
                host = environment.resolvePlaceholders("${linkwechat.nacos.host:localhost}");
            }
            this.setHost(host);
        }
        if (this.getPort() == null) {
            this.setPort(Integer.parseInt(environment.resolvePlaceholders("${linkwechat.nacos.port:8848}")));
        }
        if (StringUtils.isEmpty(this.getNamespace())) {
            this.setNamespace(environment.resolvePlaceholders("${linkwechat.nacos.namespace:}"));
        }
    }
}

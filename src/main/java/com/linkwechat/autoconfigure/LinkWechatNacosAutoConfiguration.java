package com.linkwechat.autoconfigure;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkwechat.LinkWechatNacosProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * @author leejoker <1056650571@qq.com>
 * @version 0.1-SNAPSHOT
 * @date 2021/4/19 10:18
 */
@Configuration
@EnableDiscoveryClient
@ConditionalOnProperty(
        name = {"linkwechat.nacos.enabled"},
        matchIfMissing = true
)
@EnableConfigurationProperties({LinkWechatNacosProperties.class})
@AutoConfigureBefore(value = {NacosConfigAutoConfiguration.class})
public class LinkWechatNacosAutoConfiguration {
    @Autowired
    @JsonIgnore
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public LinkWechatNacosProperties linkWechatNacosProperties() {
        LinkWechatNacosProperties properties = new LinkWechatNacosProperties();
        if (StringUtils.isEmpty(properties.getHost())) {
            String host = environment.resolvePlaceholders("${linkwechat.nacos.host:}");
            if (StringUtils.isEmpty(host)) {
                host = environment.resolvePlaceholders("${linkwechat.nacos.host:localhost}");
            }
            properties.setHost(host);
        }
        if (properties.getPort() == null) {
            properties.setPort(Integer.parseInt(environment.resolvePlaceholders("${linkwechat.nacos.port:8848}")));
        }
        if (StringUtils.isEmpty(properties.getNamespace())) {
            properties.setNamespace(environment.resolvePlaceholders("${linkwechat.nacos.namespace:}"));
        }
        return properties;
    }


    @Bean
    @ConditionalOnMissingBean
    public NacosConfigProperties nacosConfigProperties(LinkWechatNacosProperties linkWechatNacosProperties) {
        NacosConfigProperties nacosConfig = new NacosConfigProperties();
        nacosConfig.setServerAddr(String.format("%s:%d", linkWechatNacosProperties.getHost(), linkWechatNacosProperties.getPort()));
        nacosConfig.setNamespace(linkWechatNacosProperties.getNamespace());
        return nacosConfig;
    }
}

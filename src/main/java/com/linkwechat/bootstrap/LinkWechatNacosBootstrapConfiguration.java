package com.linkwechat.bootstrap;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.linkwechat.LinkWechatNacosProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author leejoker
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableDiscoveryClient
@ConditionalOnProperty(
        name = {"linkwechat.nacos.enabled"},
        matchIfMissing = true
)
@EnableConfigurationProperties({LinkWechatNacosProperties.class})
public class LinkWechatNacosBootstrapConfiguration {
    @Bean
    @Primary
    public NacosConfigProperties linkWechatNacosConfig(LinkWechatNacosProperties linkWechatNacosProperties) {
        NacosConfigProperties nacosConfigProperties = new NacosConfigProperties();
        nacosConfigProperties.setServerAddr(String.format("%s:%d", linkWechatNacosProperties.getHost(), linkWechatNacosProperties.getPort()));
        nacosConfigProperties.setNamespace(linkWechatNacosProperties.getNamespace());
        nacosConfigProperties.setRefreshEnabled(true);
        nacosConfigProperties.setFileExtension(linkWechatNacosProperties.getExtension());
        nacosConfigProperties.setPrefix(linkWechatNacosProperties.getApplicationName());
        if (StringUtils.isNotBlank(linkWechatNacosProperties.getUsername())) {
            nacosConfigProperties.setUsername(linkWechatNacosProperties.getUsername());
            nacosConfigProperties.setPassword(linkWechatNacosProperties.getPassword());
        }
        return nacosConfigProperties;
    }
}

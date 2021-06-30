package com.linkwechat.autoconfigure;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.linkwechat.LinkWechatNacosProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author leejoker
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(value = NacosDiscoveryAutoConfiguration.class)
public class LinkWechatNacosAutoConfiguration {
    @Bean
    @Primary
    public NacosDiscoveryProperties linkWechatNacosDiscoveryConfig(LinkWechatNacosProperties linkWechatNacosProperties) {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        nacosDiscoveryProperties.setServerAddr(String.format("%s:%d", linkWechatNacosProperties.getHost(), linkWechatNacosProperties.getPort()));
        nacosDiscoveryProperties.setNamespace(linkWechatNacosProperties.getNamespace());
        nacosDiscoveryProperties.setService(linkWechatNacosProperties.getApplicationName());
        return nacosDiscoveryProperties;
    }
}

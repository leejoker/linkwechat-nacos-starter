package com.linkwechat.autoconfigure;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.linkwechat.LinkWechatNacosProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leejoker
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(value = {NacosConfigAutoConfiguration.class, NacosDiscoveryAutoConfiguration.class})
public class LinkWechatNacosAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public NacosDiscoveryProperties nacosProperties(LinkWechatNacosProperties linkWechatNacosProperties) {
        overrideBean(applicationContext);
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        nacosDiscoveryProperties.setServerAddr(String.format("%s:%d", linkWechatNacosProperties.getHost(), linkWechatNacosProperties.getPort()));
        nacosDiscoveryProperties.setNamespace(linkWechatNacosProperties.getNamespace());
        nacosDiscoveryProperties.setService(linkWechatNacosProperties.getApplicationName());
        if (StringUtils.isNotBlank(linkWechatNacosProperties.getUsername())) {
            nacosDiscoveryProperties.setUsername(linkWechatNacosProperties.getUsername());
            nacosDiscoveryProperties.setPassword(linkWechatNacosProperties.getPassword());
        }
        return nacosDiscoveryProperties;
    }

    @Bean
    public NacosServiceDiscovery nacosServiceDiscovery(
            NacosDiscoveryProperties nacosProperties,
            NacosServiceManager nacosServiceManager,
            LinkWechatNacosProperties linkWechatNacosProperties) {
        nacosProperties.setService(linkWechatNacosProperties.getApplicationName());
        return new NacosServiceDiscovery(nacosProperties, nacosServiceManager);
    }


    private void overrideBean(ApplicationContext applicationContext) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        Object configBean = beanFactory.getBean("linkWechatNacosConfig");
        beanFactory.removeBeanDefinition("linkWechatNacosConfig");
        beanFactory.registerSingleton("nacosConfigProperties", configBean);
    }
}

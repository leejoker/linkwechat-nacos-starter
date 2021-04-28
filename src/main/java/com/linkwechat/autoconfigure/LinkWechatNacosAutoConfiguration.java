package com.linkwechat.autoconfigure;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.linkwechat.LinkWechatNacosProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    @ConditionalOnMissingBean
    public LinkWechatNacosProperties linkWechatNacosProperties() {
        return new LinkWechatNacosProperties();
    }


    @Bean
    @ConditionalOnMissingBean
    public NacosConfigProperties nacosConfigProperties() {
        return new NacosConfigProperties();
    }
}

package com.linkwechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author leejoker <1056650571@qq.com>
 * @version 0.1-SNAPSHOT
 * @date 2021/4/19 10:18
 */
@Data
@ConfigurationProperties(prefix = "linkwechat.nacos")
public class LinkWechatNacosProperties {
    private boolean cloud = false;
    private String host = "127.0.0.1";
    private Integer port = 8848;
    private String namespace;
    private String fileExtension = "yml";
}

package com.lingxi.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QiniuConfig {

    @Value("${qiniu.accessKey:YhKDmPq1wIhOTaDkvtAw28NiyUIlsODPoK-kKdeP}")
    private String accessKey;

    @Value("${qiniu.secretKey:Rmf6yuBjbVoanh5nv68RmNTL5Iz9-tkw_iHSxBHQ}")
    private String secretKey;

    @Value("${qiniu.bucket:sportmember}")
    private String bucket;

    @Value("${qiniu.hostName:http://rzhkikf9h.hn-bkt.clouddn.com/}")
    private String hostName;

}

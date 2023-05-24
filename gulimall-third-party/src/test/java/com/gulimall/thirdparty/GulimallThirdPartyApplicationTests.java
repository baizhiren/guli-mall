package com.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Resource
    OSSClient ossClient;

    @Test
    void testUpload2() throws FileNotFoundException {
        String objectName = "noBug.jpeg";
        String bucketName = "gulimall-stock";

        ossClient.putObject(bucketName, objectName, new FileInputStream("D:\\pic\\noBug.jpeg"));
    }

}

package com.fast.spider;

import feign.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-07 13:45
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class SpiderApplication {
    private static final Logger log = LoggerFactory.getLogger(SpiderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
    }


    @Bean
    @ConditionalOnMissingBean
    public Client feignClient() throws Exception {
        SSLContext ctx = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        return new Client.Default(ctx.getSocketFactory(),
                (s, sslSession) -> true);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fast.spider"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("爬虫接口")
                //描述
                .description("爬取目标官网：https://www.icama.cn/PublicQuery/index.do")
                //创建人
                .contact(new Contact("dev", "", "wanghao@bkrwin.com"))
                //版本
                .version("1.0")
                .build();
    }
}

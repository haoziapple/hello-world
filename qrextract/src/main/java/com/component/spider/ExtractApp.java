package com.component.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ASUS on 2018/5/10.
 */
@SpringBootApplication
@EnableSwagger2
public class ExtractApp {
    private static final Logger log = LoggerFactory.getLogger(ExtractApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ExtractApp.class, args);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.component.spider"))
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
                .contact(new Contact("Wang Hao", "", "wanghao@bkrwin.com"))
                //版本
                .version("1.0")
                .build();
    }
}

package com.fh.shop.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {



    @Bean
    public Docket createApi(){

        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fh.shop.api"))
                //只显示admin路径下的页面
                .paths(PathSelectors.any())
                .build();

    }



    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("飞狐商场-接口")
                .description("飞狐电商接口管理")
                .termsOfServiceUrl("www.qweqwe.com")
                .contact(new Contact("张鹏展","","1991737677@qq.com"))
                .version("1.0")
                .build();
    }

}

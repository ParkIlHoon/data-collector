package io.hoon.datacollector.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocketConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("데이터 수집 서비스")
                .description("여러가지 찍먹용 데이터 수집 서비스")
                .version("0.1")
                .contact(new Contact().email("chiwoo2074@gmail.com").name("박일훈")));
    }
}

package com.geosensorx.microservice.vinapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebFluxConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                //.genericModelSubstitutes(Mono.class, Flux.class, Publisher.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("GSX Vin Config")
                                    .description("GSX Vin Config")
                                    .version("1.0").build();
    }
}
//public class SwaggerDocumentation implements WebFluxConfigurer {
//	public static final Contact CONTACT = new Contact("Murali", "http://muralitechblog.com/",
//			"muralitechblog@gmail.com");
//	public static final ApiInfo DEFAULT_API = new ApiInfo("swagger", "Swagger Documentation", "1.0", "urn:tos", CONTACT,
//			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
//	public static final Set<String> consumes = new HashSet<String>(Arrays.asList("application/json"));
//	public static final Set<String> produces = new HashSet<String>(Arrays.asList("application/json"));
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API).consumes(consumes).produces(produces);
//	}
//
//}

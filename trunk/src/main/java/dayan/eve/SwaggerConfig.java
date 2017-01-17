package dayan.eve;

import dayan.eve.config.EveProperties;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by xsg on 12/30/2016.
 */
@Configuration
@EnableSwagger2
//@Profile({"!" + Constants.SPRING_PROFILE_NO_SWAGGER, "!" + Constants.SPRING_PROFILE_PRODUCTION})
//@ConditionalOnProperty(prefix = "hal.swagger", name = "enabled")
public class SwaggerConfig {
    // TODO: 1/17/2017 learn swagger config
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger();

    private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    private static final String GROUP_WEB = "web";

    /**
     * Swagger Springfox configuration.
     *
     * @param eveProperties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringfoxDocket(EveProperties eveProperties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                eveProperties.getSwagger().getContactName(),
                eveProperties.getSwagger().getContactUrl(),
                eveProperties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                eveProperties.getSwagger().getTitle(),
                eveProperties.getSwagger().getDescription(),
                eveProperties.getSwagger().getVersion(),
                eveProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                eveProperties.getSwagger().getLicense(),
                eveProperties.getSwagger().getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_WEB)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}

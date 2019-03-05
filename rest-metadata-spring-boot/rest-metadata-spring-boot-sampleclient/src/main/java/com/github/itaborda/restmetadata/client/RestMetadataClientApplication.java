package com.github.itaborda.restmetadata.client;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class RestMetadataClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestMetadataClientApplication.class, args);
    }

    @Bean
    public Docket swaggerPersonApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.itaborda.restmetadata.client.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("RestMetadata Clietn App API").description("Documentation API v1.0").build());
    }


    MongoClient mongoClient;

    MongoClientFactory factory;

    MongoClientOptions options;

    public RestMetadataClientApplication(MongoProperties properties,
                                         ObjectProvider<MongoClientOptions> options, Environment environment) {
        this.options = options.getIfAvailable();
        this.factory = new MongoClientFactory(properties, environment);
    }


    @Bean
    public MongoClient mongo() {

        this.mongoClient = this.factory.createMongoClient(this.options);
        return this.mongoClient;
    }

    @Bean
    public Mongobee mongobee(MongoDbFactory f) {

        Mongobee runner = new Mongobee(mongo());
        runner.setDbName(f.getDb().getName());
        runner.setChangeLogsScanPackage(
                "com.github.itaborda.restmetadata.client.changelogs"); // the package to be scanned for changesets

        return runner;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}

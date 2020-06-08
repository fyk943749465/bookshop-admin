package com.store.bookshopadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJdbcHttpSession
public class BookshopAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopAdminApplication.class, args);
    }

}

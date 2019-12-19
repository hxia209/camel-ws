/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.hui.training;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.hui.training.impl.CustomerImpl;
import org.hui.training.model.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
@Configuration
@ComponentScan()
public class Application extends RouteBuilder {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/*");
        registration.setName("CamelServlet");
        return registration;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration().apiComponent("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .get("/customers")
                .produces("application/json")
                .route()
                .bean(CustomerImpl.class, "getAllCustomer")
                .endRest();

        rest()
                .post("/customer")
                .type(Customer.class)
                .consumes("application/json")
                .route()
                .bean(CustomerImpl.class, "createCustomer(${body.name},${body.age})")
                .endRest();
        rest()
                .delete("/customer/{name}")
                .route()
                .bean(CustomerImpl.class, "deleteCustomer('${header.name}')")
                .endRest();
        rest()
                .get("/hello")
                .route()
                .transform(constant("Hello World"));
    }
}

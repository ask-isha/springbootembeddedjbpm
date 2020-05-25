package com.cts.spring;

//import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
//import org.jbpm.services.api.DeploymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
@ComponentScan({"com.cts.spring"})
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
@ImportResource(value= {"classpath:config/jee-tx-context.xml","classpath:config/jpa-context.xml", "classpath:config/jbpm-context.xml"})
@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
    	System.out.println("in main" + args);
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        
    }
}
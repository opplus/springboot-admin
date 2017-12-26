package com.vigekoo;

import com.vigekoo.common.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    protected final static Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) throws Exception {
        log.debug("start application...");
        SpringApplication app = new SpringApplication(Application.class);
        Set<Object> set = new HashSet<>();
        if (set.size() > 0)
            app.setSources(set);
        app.run(args);
        try {
            String serverIp = InetAddress.getLocalHost().getHostAddress();
            log.info("app started. http://" + serverIp + ":" + SpringContextUtils.getPort() + SpringContextUtils.getContentPath());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}

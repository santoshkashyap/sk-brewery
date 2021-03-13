

package com.test.brewery.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import org.hibernate.Interceptor;

@Configuration
public class HibernateConfig implements HibernatePropertiesCustomizer {

    @Autowired
    Interceptor orderHeaderInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.ejb.interceptor", orderHeaderInterceptor);
    }

    @Bean
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}

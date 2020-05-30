package io.penguinstats.penguinbotx.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 15:58
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Data
@Slf4j
@Configuration
public class ApplicationContextAwareConfig implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("init context");
        context=applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}

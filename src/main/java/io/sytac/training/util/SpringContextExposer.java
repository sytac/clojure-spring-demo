package io.sytac.training.util;

import org.springframework.context.ApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

public class SpringContextExposer {

    private static final AtomicReference<ApplicationContext> ctx = new AtomicReference<ApplicationContext>();

    public static void setContext(ApplicationContext ctx) {
        SpringContextExposer.ctx.set(ctx);
    }

    public static ApplicationContext getContext(){
        return SpringContextExposer.ctx.get();
    }
}

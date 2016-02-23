package com.wutianyi.hystrix;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class CommandFacadeWithPrimarySecondaryTest {

    @Test
    public void testPrimary() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", true);
            System.out.println(new CommandFacadeWithPrimarySecondary(20).execute());
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }
    }

    @Test
    public void testSecondary() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", false);
            System.out.println(new CommandFacadeWithPrimarySecondary(20).execute());
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }
    }

}
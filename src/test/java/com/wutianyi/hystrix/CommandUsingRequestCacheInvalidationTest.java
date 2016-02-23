package com.wutianyi.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class CommandUsingRequestCacheInvalidationTest {

    @Test
    public void getGetSetGet() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            System.out.println(new CommandUsingRequestCacheInvalidation.GetterCommand(1).execute());
            CommandUsingRequestCacheInvalidation.GetterCommand commandAgainstCache = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
            System.out.println(commandAgainstCache.execute());
            System.out.println(commandAgainstCache.isResponseFromCache());

            new CommandUsingRequestCacheInvalidation.SetterCommand(1, "ValueAfterSet_").execute();
            CommandUsingRequestCacheInvalidation.GetterCommand commandAfterSet = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
            System.out.println(commandAfterSet.isResponseFromCache());
            System.out.println(commandAfterSet.execute());
        } finally {
            context.shutdown();
        }
    }
}
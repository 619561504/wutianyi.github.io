package com.wutianyi.hystrix.demo;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixMetrics;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.concurrent.*;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class HystrixCommandDemo {

	public static void main(String args[]) {
		new HystrixCommandDemo().startDemo();
	}

	public HystrixCommandDemo() {
		ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", 8);
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.CreditCardCommand.execution.isolation.thread.timeoutInMilliseconds", 3000);
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.GetUserAccountCommand.execution.isolation.thread.timeoutInMilliseconds", 50);
		// set the rolling percentile more granular so we see data change every second rather than every 10 seconds as is the default
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.metrics.rollingPercentile.numBuckets", 60);
	}

	private final ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.DAYS, new
			SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

	public void startDemo() {
		startMetricsMonitor();
		while (true) {
			runSimulatedRequestOnThread();
		}
	}

	public void runSimulatedRequestOnThread() {
		pool.execute(new Runnable() {

			@Override
			public void run() {
				HystrixRequestContext context = HystrixRequestContext.initializeContext();
				try {
					executeSimulatedUserRequestForOrderConfirmationAndCreditCardPayment();

					System.out.println("Request => " + HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					context.shutdown();
				}
			}

		});
	}

	public void executeSimulatedUserRequestForOrderConfirmationAndCreditCardPayment() throws InterruptedException, ExecutionException {
		/* fetch user object with http cookies */
		UserAccount user = new GetUserAccountCommand(new HttpCookie("mockKey", "mockValueFromHttpRequest")).execute();

        /* fetch the payment information (asynchronously) for the user so the credit card payment can proceed */
		Future<PaymentInformation> paymentInformation = new GetPaymentInformationCommand(user).queue();

        /* fetch the order we're processing for the user */
		int orderIdFromRequestArgument = 13579;
		Order previouslySavedOrder = new GetOrderCommand(orderIdFromRequestArgument).execute();

		CreditCardCommand credit = new CreditCardCommand(previouslySavedOrder, paymentInformation.get(), new BigDecimal(123.45));
		credit.execute();
	}

	public void startMetricsMonitor() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
					HystrixCommandMetrics creditCardMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey
							.Factory.asKey(CreditCardCommand.class.getSimpleName()));
					HystrixCommandMetrics orderMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory
							.asKey(GetOrderCommand.class.getSimpleName()));
					HystrixCommandMetrics userAccountMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey
							.Factory.asKey(GetUserAccountCommand.class.getSimpleName()));
					HystrixCommandMetrics paymentInformationMetrics = HystrixCommandMetrics.getInstance
							(HystrixCommandKey.Factory.asKey(GetPaymentInformationCommand.class.getSimpleName()));
					StringBuilder out = new StringBuilder();
					out.append("\n");
					out.append("#####################################################################################").append("\n");
					out.append("# CreditCardCommand: " + getStatsStringFromMetrics(creditCardMetrics)).append("\n");
					out.append("# GetOrderCommand: " + getStatsStringFromMetrics(orderMetrics)).append("\n");
					out.append("# GetUserAccountCommand: " + getStatsStringFromMetrics(userAccountMetrics)).append("\n");
					out.append("# GetPaymentInformationCommand: " + getStatsStringFromMetrics(paymentInformationMetrics)).append("\n");
					out.append("#####################################################################################").append("\n");
					System.out.println(out.toString());
				}
			}

			private String getStatsStringFromMetrics(HystrixCommandMetrics metrics) {
				StringBuilder m = new StringBuilder();
				if (metrics != null) {
					HystrixCommandMetrics.HealthCounts health = metrics.getHealthCounts();
					m.append("Requests: ").append(health.getTotalRequests()).append(" ");
					m.append("Errors: ").append(health.getErrorCount()).append(" (").append(health.getErrorPercentage
							()).append("%)	");
					m.append("Mean: ").append(metrics.getExecutionTimePercentile(50)).append(" ");
					m.append("75th: ").append(metrics.getExecutionTimePercentile(75)).append(" ");
					m.append("90th: ").append(metrics.getExecutionTimePercentile(90)).append(" ");
					m.append("99th: ").append(metrics.getExecutionTimePercentile(99)).append(" ");
				}
				return m.toString();
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
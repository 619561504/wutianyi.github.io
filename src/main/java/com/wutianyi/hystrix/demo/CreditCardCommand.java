package com.wutianyi.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.math.BigDecimal;
import java.net.HttpCookie;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class CreditCardCommand extends HystrixCommand<CreditCardAuthorizationResult> {

	private final static AuthorizeNetGateway DEFAULT_GATEWAY = new AuthorizeNetGateway();
	private final AuthorizeNetGateway gateway;
	private final Order order;
	private final PaymentInformation payment;
	private final BigDecimal amount;

	public CreditCardCommand(Order order, PaymentInformation payment, BigDecimal amount) {
		this(DEFAULT_GATEWAY, order, payment, amount);
	}

	public CreditCardCommand(AuthorizeNetGateway gateway, Order order, PaymentInformation payment, BigDecimal amount) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CreditCard")).andCommandPropertiesDefaults
				(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000)));
		this.gateway = gateway;
		this.order = order;
		this.payment = payment;
		this.amount = amount;
	}

	@Override
	protected CreditCardAuthorizationResult run() throws Exception {
		UserAccount user = new GetUserAccountCommand(new HttpCookie("mockKey", "mockValueFromHttpRequest")).execute();
		if (user.getAccountType() == 1) {

		} else {

		}

		Result<Transaction> result = gateway.submit(payment.getCreditCardNumber(), String.valueOf(payment
						.getExpirationMonth()), String.valueOf(payment.getExpirationYear()), TransactionType.AUTH_CAPTURE,
				amount, order);
		if (result.isApproved()) {
			return CreditCardAuthorizationResult.createSuccessResponse(result.getTarget().getTransactionId(), result
					.getTarget().getAuthorizationCode());
		} else if (result.isDeclined()) {
			return CreditCardAuthorizationResult.createFailedResponse(result.getReasonResponseCode() + " : " + result
					.getResponseText());
		} else {
			if (result.getReasonResponseCode().getResponseReasonCode() == 11) {
				if (result.getTarget().getAuthorizationCode() != null) {
					return CreditCardAuthorizationResult.createDuplicateSuccessResponse(result.getTarget()
							.getTransactionId(), result.getTarget().getAuthorizationCode());
				}
			}
			return CreditCardAuthorizationResult.createFailedResponse(result.getReasonResponseCode() + " : " + result
					.getResponseText());
		}
	}

	public static class AuthorizeNetGateway {
		public AuthorizeNetGateway() {
		}

		public Result<Transaction> submit(String creditCardNumber, String expirationMonth, String expirationYear,
										  TransactionType authCapture, BigDecimal amount, Order order) {
			try {
				Thread.sleep((int) (Math.random() * 700) + 800);
			} catch (InterruptedException e) {

			}
			if (Math.random() > 0.99) {
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {

				}
			}
			if (Math.random() < 0.8) {
				return new Result<Transaction>(true);
			} else {
				return new Result<Transaction>(false);
			}
		}
	}

	public static class Result<T> {
		private final boolean approved;

		public Result(boolean approved) {
			this.approved = approved;
		}

		public boolean isApproved() {
			return approved;
		}

		public ResponseCode getResponseText() {
			return null;
		}

		public Target getTarget() {
			return new Target();
		}

		public ResponseCode getReasonResponseCode() {
			return new ResponseCode();
		}

		public boolean isDeclined() {
			return !approved;
		}
	}


	public static class ResponseCode {
		public int getResponseReasonCode() {
			return 0;
		}
	}

	public static class Target {
		public String getTransactionId() {
			return "transactionId";
		}

		public String getAuthorizationCode() {
			return "authorizedCode";
		}
	}

	public static class Transaction {

	}

	public static enum TransactionType {
		AUTH_CAPTURE
	}
}

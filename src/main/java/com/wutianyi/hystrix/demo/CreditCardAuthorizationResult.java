package com.wutianyi.hystrix.demo;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class CreditCardAuthorizationResult {

	public static CreditCardAuthorizationResult createSuccessResponse(String transactionID, String authorizationCode) {
		return new CreditCardAuthorizationResult(true, transactionID, authorizationCode, false);
	}

	public static CreditCardAuthorizationResult createDuplicateSuccessResponse(String transactionID, String authorizationCode) {
		return new CreditCardAuthorizationResult(true, transactionID, authorizationCode, true);
	}

	public static CreditCardAuthorizationResult createFailedResponse(String errorMessage) {
		return new CreditCardAuthorizationResult(false, errorMessage, null, false);
	}

	private final boolean success;
	private final boolean isDuplicate;
	private final String authorizationCode;
	private final String transactionID;
	private final String errorMessage;

	public CreditCardAuthorizationResult(boolean success, String value, String value2, boolean isResponseDuplicate) {
		this.success = success;
		this.isDuplicate = isResponseDuplicate;

		if (success) {
			this.transactionID = value;
			this.authorizationCode = value2;
			this.errorMessage = null;
		} else {
			this.transactionID = null;
			this.errorMessage = value;
			this.authorizationCode = null;
		}

	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}

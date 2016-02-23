package com.wutianyi.hystrix.demo;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class UserAccount {
	private final int userId;
	private final String name;
	private final int accountType;
	private final boolean isFeatureXenabled;
	private final boolean isFeatureYenabled;
	private final boolean isFeatureZenabled;

	public UserAccount(int userId, String name, int accountType, boolean isFeatureXenabled, boolean isFeatureYenabled, boolean isFeatureZenabled) {
		this.userId = userId;
		this.name = name;
		this.accountType = accountType;
		this.isFeatureXenabled = isFeatureXenabled;
		this.isFeatureYenabled = isFeatureYenabled;
		this.isFeatureZenabled = isFeatureZenabled;
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public int getAccountType() {
		return accountType;
	}

	public boolean isFeatureXenabled() {
		return isFeatureXenabled;
	}

	public boolean isFeatureYenabled() {
		return isFeatureYenabled;
	}

	public boolean isFeatureZenabled() {
		return isFeatureZenabled;
	}
}

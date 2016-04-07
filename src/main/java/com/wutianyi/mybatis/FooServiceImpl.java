package com.wutianyi.mybatis;

/**
 * Created by hanjiewu on 2016/3/25.
 */
public class FooServiceImpl implements FooService {
	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User doSomeBusinessStuff(String userId) {
		return userMapper.getUser(userId);
	}
}

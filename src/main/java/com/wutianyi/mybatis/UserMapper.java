package com.wutianyi.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by hanjiewu on 2016/3/25.
 */
public interface UserMapper {
	@Select("select * from users where id=#{userId}")
	User getUser(@Param("userId") String userId);

}

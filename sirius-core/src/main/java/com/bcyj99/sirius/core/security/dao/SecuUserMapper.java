package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecuUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecuUser record);

    int insertSelective(SecuUser record);

    List<SecuUser> selectPagedUsers(@Param("user")SecuUser user,@Param("pageStart")Integer pageStart,@Param("pageSize")Integer pageSize);
    
    Integer selectPagedUsersCount(@Param("user")SecuUser user);

    SecuUser selectByPrimaryKey(Long id);
    
    SecuUser selectByUsername(String username);

    int updateByPrimaryKeySelective(SecuUser record);

    int updateByPrimaryKey(SecuUser record);
}
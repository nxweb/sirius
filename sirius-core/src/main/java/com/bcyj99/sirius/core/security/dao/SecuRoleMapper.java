package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecuRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecuRole record);

    int insertSelective(SecuRole record);

    SecuRole selectByPrimaryKey(Long id);
    
    SecuRole selectByRoleCode(String roleCode);
    
    List<SecuRole> selectPagedRoles(@Param("role") SecuRole role,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
    
    Integer selectPagedRolesCount(@Param("role") SecuRole role);

    int updateByPrimaryKeySelective(SecuRole record);

    int updateByPrimaryKey(SecuRole record);
}
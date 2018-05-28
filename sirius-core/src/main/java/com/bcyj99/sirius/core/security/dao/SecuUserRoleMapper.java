package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuUserRole;
import java.util.List;

public interface SecuUserRoleMapper {
    int deleteByPrimaryKey(Long id);
    
    int deleteUserRoles(List<SecuUserRole> userRoles);

    int insert(SecuUserRole record);
    
    int insertUserRoles(List<SecuUserRole> userRoles);

    SecuUserRole selectByPrimaryKey(Long id);

    List<SecuUserRole> selectAll();

    int updateByPrimaryKey(SecuUserRole record);
}
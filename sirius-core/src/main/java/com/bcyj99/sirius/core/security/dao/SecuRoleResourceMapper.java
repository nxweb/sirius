package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuRoleResource;
import java.util.List;

public interface SecuRoleResourceMapper {
    int deleteByPrimaryKey(Long id);
    
    int deleteByRoleIdResourceId(List<SecuRoleResource> roleResources);
    
    int deleteByResourceId(Long resourceId);

    int insert(SecuRoleResource record);
    
    int insertRoleResources(List<SecuRoleResource> roleResources);

    SecuRoleResource selectByPrimaryKey(Long id);

    List<SecuRoleResource> selectAll();

    int updateByPrimaryKey(SecuRoleResource record);
}
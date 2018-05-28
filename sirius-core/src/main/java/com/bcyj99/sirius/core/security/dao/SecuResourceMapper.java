package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuResource;
import com.bcyj99.sirius.core.security.vo.SecuResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecuResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecuResource record);

    int insertSelective(SecuResource record);

    SecuResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecuResource record);

    int updateByPrimaryKey(SecuResource record);
    
    List<SecuResource> selectPagedResources(@Param("secuResource") SecuResource secuResource,
    	@Param("pageStart")Integer pageStart,@Param("pageSize")Integer pageSize);
    
    Integer selectPagedResourcesCount(@Param("secuResource") SecuResource secuResource);
    
    List<SecuResource> selectAllResourceAndRole();
    
    SecuResource selectRolesByResourceId(Long id);
    
    List<SecuResource> selectAll();
}
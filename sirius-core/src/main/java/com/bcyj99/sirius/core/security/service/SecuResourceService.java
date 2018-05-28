package com.bcyj99.sirius.core.security.service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.security.vo.SecuResource;

public interface SecuResourceService {
    void addResource(SecuResource secuResource);
    
    void addOrModifyResource(SecuResource secuResource);
    
    PagedResultVo<SecuResource> queryPagedSecuResources(
    	SecuResource secuResource,Integer pageNo,Integer pageSize);
    
    SecuResource querySecuResourceById(Long id);
    
    void removeResourceById(Long resourceId);
}

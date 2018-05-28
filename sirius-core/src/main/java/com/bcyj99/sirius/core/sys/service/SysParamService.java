package com.bcyj99.sirius.core.sys.service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.sys.vo.SysParam;

public interface SysParamService {
    void addOrModifySysParam(SysParam sysParam);
    
    void removeSysParam(Long id,String paramKey);
    
    PagedResultVo<SysParam> queryPagedSysParams(SysParam sysParam,Integer pageNo,Integer pageSize);
    
    String getCacheSysParam(String paramKey);
}

package com.bcyj99.sirius.core.sys.service;

import java.util.List;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionary;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionaryVo;

public interface SysDataDictionaryService {
    void addOrModifySysDataDictionary(SysDataDictionary sysDataDictionary);
    
    void removeSysDataDictionary(Long id,String dictionaryKey);
    
    PagedResultVo<SysDataDictionary> queryPagedSysDataDictionarys(SysDataDictionary sysDataDictionary,Integer pageNo,Integer pageSize);
    
    List<SysDataDictionaryVo> getCacheSysDataDictionary(String dictionaryKey);
}

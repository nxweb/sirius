package com.bcyj99.sirius.core.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bcyj99.sirius.core.sys.vo.SysDataDictionary;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionaryVo;

public interface SysDataDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDataDictionary record);

    int insertSelective(SysDataDictionary record);

    SysDataDictionary selectByPrimaryKey(Long id);
    
    List<SysDataDictionary> selectDictionarys(SysDataDictionary record);
    
    List<SysDataDictionaryVo> selectDictionaryVos(SysDataDictionary record);

    int updateByPrimaryKeySelective(SysDataDictionary record);

    int updateByPrimaryKey(SysDataDictionary record);
    
    List<SysDataDictionary> selectPagedDictionarys(@Param("record")SysDataDictionary record,@Param("pageStart")Integer pageStart,@Param("pageSize")Integer pageSize);
    
    Integer selectPagedDictionarysCount(@Param("record")SysDataDictionary record);
}
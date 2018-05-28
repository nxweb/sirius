package com.bcyj99.sirius.core.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bcyj99.sirius.core.sys.vo.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
    
    SysParam selectByPrimaryKey(Long id);
    
    List<SysParam> selectAll();
    
    List<SysParam> selectPagedSysParams(@Param("record")SysParam record,@Param("pageStart")Integer pageStart,@Param("pageSize")Integer pageSize);
    
    Integer selectPagedSysParamsCount(@Param("record")SysParam record);
}
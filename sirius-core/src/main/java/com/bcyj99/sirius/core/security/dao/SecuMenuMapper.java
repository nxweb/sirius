package com.bcyj99.sirius.core.security.dao;

import com.bcyj99.sirius.core.security.vo.SecuMenu;
import java.util.List;

public interface SecuMenuMapper {
    int deleteByPrimaryKey(Long id);
    
    int deleteMenusByIds(List<SecuMenu> ids);

    int insert(SecuMenu record);

    int insertSelective(SecuMenu record);

    SecuMenu selectByPrimaryKey(Long id);
    
    List<SecuMenu> selectByCondition(SecuMenu record);

    int updateByPrimaryKeySelective(SecuMenu record);

    int updateByPrimaryKey(SecuMenu record);
}
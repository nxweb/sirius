package com.bcyj99.sirius.core.sys.service;

import java.util.List;

public interface CacheService<E> {
    E get(String key);
    
    void set(String key,E value);
    
    void delete(String key);
    
    List<E> getList(String key);
    
    void setList(String key,List<E> listValues);
}

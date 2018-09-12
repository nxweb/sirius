package com.bcyj99.sirius.core.sys.service;

import java.util.List;
import java.util.Set;

public interface CacheService<E> {
	Set<String> getAllKeys();
	
    E get(String key);
    
    boolean lock(String businessId, E uuid, int expireTimeInSecond);
    
    boolean releaseLock(String businessId, E uuid);
    
    void set(String key,E value);
    
    void delete(String key);
    
    List<E> getList(String key);
    
    void setList(String key,List<E> listValues);
}

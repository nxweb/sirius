package com.bcyj99.sirius.core.authentication.access;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.bcyj99.sirius.core.security.dao.SecuResourceMapper;
import com.bcyj99.sirius.core.security.vo.SecuResource;
import com.bcyj99.sirius.core.security.vo.SecuRole;

public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    
	private static final Logger logger = LoggerFactory.getLogger(MyFilterInvocationSecurityMetadataSource.class);
	
	private SecuResourceMapper secuResourceMapper;
    
	// resourceMap 为 资源权限的集合 key-url，value-Collection<ConfigAttribute>
//    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    
	private RedisTemplate<String,Serializable> redisTemplate;

    public MyFilterInvocationSecurityMetadataSource(SecuResourceMapper secuResourceMapper,RedisTemplate<String,Serializable> redisTemplate) {
    	this.secuResourceMapper = secuResourceMapper;
    	this.redisTemplate=redisTemplate;
        loadResourceDefine();
    }
    // 初始化 所有资源与权限的关系
    private void loadResourceDefine() {
//        if (resourceMap == null) {
//            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//            //1.查找所有资源
//            List<SecuResource> resources = secuResourceMapper.selectAll();
//            
//            //2.查找资源需要角色
//            for (SecuResource resource : resources) {
//            	Collection<ConfigAttribute> roles = null;
//                SecuResource secuResource = secuResourceMapper.selectRolesByResourceId(resource.getId());
//                List<SecuRole> secuRoles = secuResource.getSecuRoles();
//                if(secuRoles != null && !secuRoles.isEmpty()){
//                	roles = new ArrayList<ConfigAttribute>();
//                	for(SecuRole sr : secuRoles){
//                		roles.add(new SecurityConfig(sr.getRoleCode()));
//                	}
//                }
//                //缓存数据
//                resourceMap.put(resource.getResourceUrl(), roles);
//            }
//        }
        
        //1.查找所有资源
        List<SecuResource> resources = secuResourceMapper.selectAll();
        
        ValueOperations<String, Serializable> opsv = redisTemplate.opsForValue();
        //2.查找资源需要角色
        for (SecuResource resource : resources) {
        	logger.info(resource.getResourceUrl());
        	
        	HashSet<ConfigAttribute> roles = null;
            SecuResource secuResource = secuResourceMapper.selectRolesByResourceId(resource.getId());
            List<SecuRole> secuRoles = secuResource.getSecuRoles();
            if(secuRoles != null && !secuRoles.isEmpty()){
            	roles = new HashSet<ConfigAttribute>();
            	for(SecuRole sr : secuRoles){
            		roles.add(new SecurityConfig(sr.getRoleCode()));
            	}
            }
            //缓存数据
            opsv.set(resource.getResourceUrl(), roles);
        }
    }
    
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		ValueOperations<String, Serializable> opsv = redisTemplate.opsForValue();
        // 将参数转为url
        String url = ((FilterInvocation) object).getRequestUrl();
        
        int lastIndex = url.lastIndexOf("?");
        if(lastIndex==-1){
        	lastIndex = url.length();
        }
        HashSet<ConfigAttribute> roles = (HashSet<ConfigAttribute>) opsv.get(url.substring(0, lastIndex));
        return roles;
        
//		FilterInvocation filterInvocation =  ((FilterInvocation) object);
        //循环已有的角色配置对象 进行url匹配
//        Iterator<String> ite = resourceMap.keySet().iterator();
//        while (ite.hasNext()) {
//            String resURL = ite.next();
//            RequestMatcher urlMatcher = new AntPathRequestMatcher(resURL);
//            if (urlMatcher.matches(filterInvocation.getHttpRequest())) {
//                return resourceMap.get(resURL); 
//            }
//        }
//        return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}

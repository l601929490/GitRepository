package com.siwo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siwo.commons.ShiroRealm;

@Configuration
public class ShiroConfiguration {
	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("myShiroRealm") ShiroRealm shiroRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(shiroRealm);
		return manager;
	}

	// 配置自定义的权限登录器
	@Bean(name = "myShiroRealm")
	public ShiroRealm authRealm() {
		ShiroRealm myShiroRealm = new ShiroRealm();
		return myShiroRealm;
	}

	@Bean
	public ShiroFilterFactoryBean webFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
		// Map<K,V> K指的是拦截的url V值的是该url是否拦截
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器logout，由shiro实现
		filterChainMap.put("/logout", "logout");
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
		filterChainMap.put("/adminLogin", "anon");
		filterChainMap.put("/css/**", "anon");
		filterChainMap.put("/fonts/**", "anon");
		filterChainMap.put("/index.html", "anon");
		filterChainMap.put("/js/**", "anon");
		filterChainMap.put("/#/login", "anon");
		filterChainMap.put("/applet/**", "anon");
		filterChainMap.put("/manifest.json", "anon");
		filterChainMap.put("/favicon.ico", "anon");
		filterChainMap.put("/robots.txt", "anon");
		filterChainMap.put("/service-worker.js", "anon");
		filterChainMap.put("/img/**", "anon");
//		filterChainMap.put("/**", "authc");
		

		// 设置默认登录的URL.
		shiroFilterFactoryBean.setLoginUrl("/");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 	开启aop注解支持 即在controller中使用 @RequiresPermissions("user/userList")
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		// 设置安全管理器
		attributeSourceAdvisor.setSecurityManager(securityManager);
		return attributeSourceAdvisor;
	}

	

	/**
	 * 处理未授权的异常，返回自定义的错误页面（403）
	 * 
	 * @return
	 */
//	@Bean
//	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
//		Properties properties = new Properties();
//		/* 未授权处理页 */
//		properties.setProperty("UnauthorizedException", "403.html");
//		properties.setProperty("UnavailableSecurityManagerException", "403.html");
//		
//		resolver.setExceptionMappings(properties);
//		return resolver;
//	}
}

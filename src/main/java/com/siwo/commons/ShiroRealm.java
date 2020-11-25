package com.siwo.commons;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.siwo.dao.AdminDao;
import com.siwo.model.Admin;


public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private AdminDao dao;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		System.out.println("授权------------------------------------------------");
		System.out.println(principals);
		//获取当前登录的用户
		Admin admin = (Admin)principals.getPrimaryPrincipal();
		//通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色
        simpleAuthorizationInfo.addRole(admin.getAdminRole());
        
        List<String>permissions = dao.queryAdminPermissions(admin.getAdminId());
        admin.setPermissions(permissions);
        
        //添加权限
        simpleAuthorizationInfo.addStringPermissions(admin.getPermissions());
        return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		System.out.println("身份验证-------------------------------------------------");
		
		//1.获取用户输入的账号
        String adminAccount = (String)token.getPrincipal();
        //2.通过username从数据库中查找到user实体
        Admin admin = dao.queryAdminByAdminAccount(adminAccount);
     
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (admin != null) {
        	 simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin,admin.getAdminPassword(),getName());
        	 //4.用户账号状态验证等其他业务操作
             if("否".equals(admin.getAvailable())){
                 throw new AuthenticationException("该账号已经被禁用");
             }
		}
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
	}
}

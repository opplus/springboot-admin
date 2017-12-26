package com.vigekoo.common.shiro;

import com.vigekoo.modules.sys.service.SysUserTokenService;
import com.vigekoo.common.Constant;
import com.vigekoo.modules.sys.entity.SysUser;
import com.vigekoo.modules.sys.entity.SysUserToken;
import com.vigekoo.modules.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author oplus
 * @Description: TODO(认证)
 * @date 2017-6-23 15:07
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser)principals.getPrimaryPrincipal();
        Long userId = user.getId();

        //用户权限列表
        Set<String> permsSet = sysUserService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserToken tokenEntity = sysUserTokenService.queryByToken(accessToken);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        SysUser user = sysUserService.queryObject(tokenEntity.getUserId());
        //账号锁定
        if(Constant.UserStatus.DISABLE.getValue()==user.getStatus()){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}

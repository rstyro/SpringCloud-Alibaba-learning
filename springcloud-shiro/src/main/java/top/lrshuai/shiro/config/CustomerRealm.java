package top.lrshuai.shiro.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 realm
 */
public class CustomerRealm extends AuthorizingRealm {

    /**
     * 支持的 AuthenticationToken 类型
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomerToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) principals.getPrimaryPrincipal();
        System.out.println("授权token：" + token);
        if("rstyro".equals(token)){
            SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
            // 给用户添加角色
            simpleAuthenticationInfo.addRole("user");
            simpleAuthenticationInfo.addRole("admin");
            // 添加菜单权限
            List<String> permissions = new ArrayList<>();
            permissions.add("user:list");
            permissions.add("user:add");
            permissions.add("user:query");
            permissions.add("user:del");
            simpleAuthenticationInfo.addStringPermissions(permissions);
            return simpleAuthenticationInfo;

        }
        return null;
    }

    /**
     * 认证
     *
     * @param token 令牌
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginToken = (String) token.getPrincipal();
        if("rstyro".equals(loginToken)){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), this.getName());
            return simpleAuthenticationInfo;
        }
        // 返回null 则会报 UnknownAccountException 异常
        return null;
    }

    /**
     * 如果是admin 用户直接通过，不走权限限制
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return super.isPermitted(principals, permission);
    }

    /**
     * 如果是admin 用户直接通过，不走权限限制
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        return super.hasRole(principals, roleIdentifier);
    }

}

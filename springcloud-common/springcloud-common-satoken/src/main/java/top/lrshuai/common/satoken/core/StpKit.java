package top.lrshuai.common.satoken.core;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 多账户认证
 */
public class StpKit {
    /**
     * 默认原生会话对象
     */
    public static final StpLogic DEFAULT = StpUtil.stpLogic;

    /**
     * Admin 会话对象，管理 Admin 表所有账号的登录、权限认证
     */
    public static final StpLogic ADMIN = StpUtil.stpLogic;

    public static final StpLogic USER = new StpLogic("user");
}

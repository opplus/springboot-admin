package com.vigekoo.modules.sys.controller;

import com.vigekoo.modules.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author oplus
 * @Description: TODO(Controller公共组件)
 * @date 2017-6-23 15:07
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }


}

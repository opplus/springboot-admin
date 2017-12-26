package com.vigekoo.modules.api.controller;

import com.vigekoo.common.utils.Result;
import com.vigekoo.modules.api.annotation.Login;
import com.vigekoo.modules.sys.entity.SysUser;
import com.vigekoo.modules.api.utils.JwtUtils;
import com.vigekoo.modules.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO()
 * @date 2017-9-27 14:49
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/login")
    public Result login(String username, String password){
        //用户信息
        SysUser user = sysUserService.queryByUserName(username);

        //账号不存在
        if(user == null) {
            return Result.error("账号不存在");
        }

        //密码错误
        if(!user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return Result.error("密码不正确");
        }

        //生成token
        String token = jwtUtils.generateToken(user.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        Result r=Result.ok().put(map);
        return r;
    }

    @Login
    @GetMapping("/test")
    public Result test(){
        return Result.ok();
    }

}

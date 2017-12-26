package com.vigekoo.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.vigekoo.common.exception.AppException;
import com.vigekoo.common.utils.Result;
import com.vigekoo.common.utils.ShiroUtils;
import com.vigekoo.modules.sys.entity.SysUser;
import com.vigekoo.modules.sys.service.SysUserTokenService;
import com.vigekoo.common.Constant;
import com.vigekoo.common.utils.SpringContextUtils;
import com.vigekoo.config.KaptchaConfig;
import com.vigekoo.modules.sys.service.SysUserService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(登录相关)
 * @date 2017-6-23 15:07
 */
@RestController
public class SysLoginController extends AbstractController {

	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;

	@RequestMapping("/captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 验证码开关
	 */
	@RequestMapping("/sys/doGetKaptchaOnOff")
	public Result doGetKaptchaOnOff(){
		boolean kaptchaOnOff=SpringContextUtils.getBean(KaptchaConfig.class).getKaptchaOpen();
		return Result.ok().put("kaptchaOnOff", kaptchaOnOff);
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Result login(String username, String password, String captcha)throws IOException {
		//验证码
		if(SpringContextUtils.getBean(KaptchaConfig.class).getKaptchaOpen()){
			String kaptcha = getKaptcha(Constants.KAPTCHA_SESSION_KEY);
			if(!captcha.equalsIgnoreCase(kaptcha)){
				return Result.error("验证码不正确");
			}
		}

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

		//账号锁定
		if(Constant.UserStatus.DISABLE.getValue() == user.getStatus()){
			return Result.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		Map<String, Object> result=sysUserTokenService.createToken(user.getId());
		Result r =Result.ok().put(result);
		return r;
	}

    /**
	 * 退出
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
	public Result logout() {
		sysUserTokenService.logout(getUserId());
		return Result.ok();
	}

	/**
	 *从session中获取记录的验证码
	 */
	private String getKaptcha(String key) {
		Object kaptcha = ShiroUtils.getSessionAttribute(key);
		if(kaptcha == null){
			throw new AppException("验证码已失效");
		}
		ShiroUtils.getSession().removeAttribute(key);
		return kaptcha.toString();
	}
	
}

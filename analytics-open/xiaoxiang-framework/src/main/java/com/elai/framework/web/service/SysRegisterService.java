package com.elai.framework.web.service;

import com.elai.common.constant.Constants;
import com.elai.common.constant.UserConstants;
import com.elai.common.core.domain.entity.SysUser;
import com.elai.common.core.domain.model.RegisterBody;
import com.elai.common.core.redis.RedisCache;
import com.elai.common.exception.user.CaptchaException;
import com.elai.common.exception.user.CaptchaExpireException;
import com.elai.common.utils.MessageUtils;
import com.elai.common.utils.SecurityUtils;
import com.elai.framework.manager.AsyncManager;
import com.elai.framework.manager.factory.AsyncFactory;
import com.elai.system.service.ISysConfigService;
import com.elai.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 注册校验方法
 *
 * @author elai
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(),phonenumber=registerBody.getPhonenumber(), password = registerBody.getPassword();

        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(phonenumber))
        {
            msg = "手机号不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username)))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPhonenumber(phonenumber);
            sysUser.setRoleId(2L);
            String lesseecode=configService.selectConfigByKey("sys.account.default.lesseecode");
            sysUser.setLesseeId(lesseecode);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            String roleId=configService.selectConfigByKey("sys.account.default.role");
            if(com.elai.common.utils.StringUtils.isNotEmpty(roleId)){
                String []roleIds=roleId.split(",");
                Long []ids=new Long[roleIds.length];
                for(int i=0;i<roleIds.length;i++){
                    ids[i]=Long.valueOf(roleIds[i]);
                }
                userService.insertUserRole(sysUser.getUserId(),ids);
            }

            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
                        MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}

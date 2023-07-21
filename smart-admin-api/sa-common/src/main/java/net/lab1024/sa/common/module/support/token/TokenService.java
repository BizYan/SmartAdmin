package net.lab1024.sa.common.module.support.token;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import net.lab1024.sa.common.common.constant.StringConst;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户token 相关服务
 *
 * @author listen
 * @date 2023-07-12 22:48:35
 */
@Component
public class TokenService {

    public static final String EXTRA_KEY_USER_NAME = "userName";

    public static final String EXTRA_KEY_USER_TYPE = "userType";

    /**
     * 生成Token
     *
     * @param userId
     * @param userName
     * @param userTypeEnum
     * @param loginDeviceEnum
     * @return
     */
    public String generateToken(Long userId,
                                String userName,
                                UserTypeEnum userTypeEnum,
                                LoginDeviceEnum loginDeviceEnum) {

        /**
         * 设置登录模式参数
         * 具体参数 {@link SaLoginModel }  属性
         * 已经写的挺清楚的了
         */
        SaLoginModel loginModel = new SaLoginModel();
        // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
        loginModel.setDevice(String.valueOf(loginDeviceEnum.getDesc()));

        // 登录
        String loginId = generateLoginId(userId, userTypeEnum);
        StpUtil.login(loginId, loginModel);

        // 扩展参数 放入会话中 redis session
        SaSession session = StpUtil.getSession();
        session.set(EXTRA_KEY_USER_NAME, userName);
        session.set(EXTRA_KEY_USER_TYPE, userTypeEnum);
        return StpUtil.getTokenValue();
    }

    public static String generateLoginId(Long userId, UserTypeEnum userType) {
        return userType.getValue() + StringConst.COLON + userId;
    }

    public static Long getUserId(String loginId) {
        return Long.valueOf(loginId.substring(loginId.indexOf(StringConst.COLON) + 1));
    }

    public static Integer getUserType(String loginId) {
        return Integer.valueOf(loginId.substring(0, loginId.indexOf(StringConst.COLON)));
    }

    /**
     * 退出登录 注销
     */
    public void removeToken() {
        StpUtil.logout();
    }

    public void removeToken(Long userId, UserTypeEnum userType) {
        StpUtil.logout(generateLoginId(userId, userType));
    }

    public void removeToken(List<Long> userIdList, UserTypeEnum userType) {
        userIdList.forEach(id -> StpUtil.logout(generateLoginId(id, userType)));
    }
}
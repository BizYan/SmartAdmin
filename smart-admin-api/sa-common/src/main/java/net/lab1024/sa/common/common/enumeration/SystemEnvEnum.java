package net.lab1024.sa.common.common.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统环境枚举类
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2020-10-15 22:45:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@AllArgsConstructor
@Getter
public enum SystemEnvEnum implements BaseEnum {
    /**
     * dev
     */
    DEV(EnvConst.DEV, "开发环境"),

    /**
     * test
     */
    TEST(EnvConst.TEST, "测试环境"),

    /**
     * pre
     */
    PRE(EnvConst.PRE, "预发布环境"),

    /**
     * prod
     */
    PROD(EnvConst.PROD, "生产环境");

    private final String value;

    private final String desc;

    public static final class EnvConst {
        public static final String DEV = "dev";
        public static final String TEST = "test";
        public static final String PRE = "pre";
        public static final String PROD = "prod";
    }

}

package icu.xiaohu.backen_base.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author xiaohu
 * @date 2022/11/09/ 18:36
 * @description 生成验证码
 */
public class CodeGenerator {

    private static final Random random = new Random();

    /**
     * 四位验证码
     */
    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    /**
     * 六位验证码
     */
    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(random.nextInt(1000000));
    }

}

package com.young.common.util.test;

import org.junit.jupiter.api.Test;
import static com.young.common.util.TrimEnterUtil.trimEnter;

/**
 * 测试工具类TrimEnterUtil
 * @author Beijing R&D Center -hon,yang
 * @version 5.0.0
 * created at 2019/7/18 14:09
 * copyright @2019 Beijing
 */
public class TrimEnterUtilTest {

    /**
     * 去掉空格
     * @throws Exception
     */
    @Test
    private void trimEnterTest() throws Exception {
        //filePath-文件或者文件夹全路径
        trimEnter("C:\\ProjectResource\\ICB-POC\\loan-acm\\loan-acm-web\\src\\main\\webapp\\META-INF\\autoconf");
    }




}

package com.young.common.util;

import com.young.common.util.base.EncodingDetect;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * 编码转换
 * @author Beijing R&D Center -hon,yang
 * @version 5.0.0
 * created at 2019/7/18 15:20
 * copyright @2019 Beijing
 */
public class EncodeFileUtil {

    private static  String ORIGINAL_ENCODE="GBK";
    private static  String TARGET_ENCODE="UTF-8";

    /**
     * 本方法保证原来的编码不能乱码
     * @param DirPath  原文件路径
     * @param targetDirPath 目标文件路径，缺省值DirPath
     * @param originalEncode 原编码 缺省GBK
     * @param targetEncode 目标编码 缺省UTF-8
     */
    public static void encodeFile(String DirPath, String targetDirPath, String originalEncode, String targetEncode) {
        String srcDirPath = DirPath;
        if (StringUtils.isBlank(targetDirPath)) {
            targetDirPath = srcDirPath;
        }
        String utf8DirPath = targetDirPath;
        if(StringUtils.isBlank(originalEncode)){
            originalEncode=ORIGINAL_ENCODE;
        }
        if(StringUtils.isBlank(originalEncode)){
            targetEncode=TARGET_ENCODE;
        }

        if (new File(DirPath).isDirectory()) {
            Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[]{"java"}, true);
            for (File javaGbkFile : javaGbkFileCol) {
                String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
                try {

                    FileUtils.writeLines(new File(utf8FilePath), targetEncode, FileUtils.readLines(javaGbkFile, originalEncode));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                String fileEncode = EncodingDetect.detect(DirPath);

                if (!fileEncode.equals("UTF-8")) {
                    FileUtils.writeLines(new File(targetDirPath), targetEncode, FileUtils.readLines(new File(DirPath), originalEncode));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


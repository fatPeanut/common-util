package com.young.common.util;

import org.apache.commons.io.FileUtils;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * fiel  util
 * @author Beijing R&D Center -yang
 * @version 5.0.0
 * created at 2019/12/3 20:48
 * copyright @2019 Beijing
 */
public class FileUtil {
    /**
     * 该文件夹下面的所有文件拷贝到目标文件夹下面去
     * @param
     */
    private static void func(File fileDir, File destDir) throws IOException {
        File[] fs = fileDir.listFiles();
        for (File f : fs) {
            if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
                func(f, destDir);
            if (f.isFile())        //若是文件，直接打印
                FileUtils.copyFileToDirectory(f, destDir);
        }
    }
}

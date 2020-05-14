package com.young.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 末尾去掉换行符或者末尾转义符号
 * @author Beijing R&D Center -yang_han
 * @version 5.0.0
 * created at 2019/7/17 16:50
 * copyright @2019 Beijing
 */
public class TrimEnterUtil {

    public static void main(String[] args) throws Exception {
        trimEnter("C:\\ProjectResource\\netpay5.0\\bak\\settlement\\psmcore-web");
    }
    //换行符号
    private static String BREAK_LINE = "\n";

    /**
     * 末尾去掉换行符或者末尾转义符号
     * @param filePath
     * @throws Exception
     */
    public static void trimEnter(String filePath) throws Exception {
        traversalFile(new File(filePath));
    }

    /**
     * 遍历文件并调用格式化接口
     * file-文件或者文件夹路径
     * @param file
     * @throws Exception
     */
    private static void traversalFile(File file) throws Exception {

        File files[] = null;

        if (file.isDirectory()) {
            files = file.listFiles();
        } else {
            files = new File[]{file};
        }

        //遍历出指定文件路径下的所有符合筛选条件的文件
        for (File f : files) {
            if (f.isDirectory()) {
                /** 递归调用**/
                traversalFile(f);
            } else {
                //格式化iif文
                String filePath = f.getAbsolutePath();
                System.out.println("遍历的文件名称为" + filePath);
                formaterFile(filePath);
            }
        }
    }

    /**
     * 重写换行符
     * @param filePath
     * @throws Exception
     */
    public static void formaterFile(String filePath) throws Exception {

        File file = new File(filePath);

        //获取需要格式化的文件
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> fmtXml = new ArrayList();

        //获取需要格式化的字符串所处位置的最大值

        String str = null;
        //读取一行文件内容
        while ((str = bufferedReader.readLine()) != null) {
            //读取一行文件内容
            String linexml = str;
            //把每一行内容放入数组中
            fmtXml.add(linexml);
        }
        //设置成false清空原内容
        FileWriter fwold = new FileWriter(file, false);

        FileWriter fw = new FileWriter(file, true); //设置成true就是追加

        for (int i = 0; i < fmtXml.size(); i++) {

            String tmp = fmtXml.get(i);

            fw.write(tmp);

            fw.write(BREAK_LINE);
        }

        fw.close();
        fwold.close();

        inputStream.close();

        bufferedReader.close();

    }

}

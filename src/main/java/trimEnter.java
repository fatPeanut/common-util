import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangshenghe .
 * @Description:去掉windows系统下的换行符号
 * @Date: Created  in 下午6:13  2018/6/20
 * @Modified By:
 */
public class trimEnter {

    public static void main(String[] args) throws Exception {

        //String[] fmtStr = {"Start","name", "length","type",  "necessary","data_name","dict_name","expression","necessary","value","dict","desc", "default_value", "<!--"};
        String[] fmtStr = {"Start"};
        String filePath = "D:\\code\\CBA-POC\\customer-person-service\\customer-person-service-web\\src\\main\\java\\com\\murong\\ecp\\netpay\\cps\\person\\web\\dal";

        //格式化接口文件
        new trimEnter().listPath(fmtStr, new File(filePath));

    }


    /**
     * @Comments:
     * @param:
     * @return:
     * @exception:
     * @throws:
     * @author:wangshenghe
     * @Time:下午2:35 2018/6/21
     */
    public void listPath(String fmtStr[], File file) throws Exception {
        /**
         *  接收筛选过后的文件对象数组T4803170
         *  用文件对象调用listFiles(FilenameFilter filter);方法,
         *  返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录
         */
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
                listPath(fmtStr, f);
            } else {

                //格式化iif文
                String fileNm = f.getAbsolutePath();
                System.out.println("遍历的文件名称为" + fileNm);
                fmtXMLMultiple(fmtStr, fileNm);
            }
        }
    }

    /**
     * @Comments:格式化接口文件
     * @param:
     * @return:
     * @exception:
     * @throws:
     * @author:
     * @Time:下午2:54 2018/6/21
     */
    public void fmtXMLMultiple(String fmtStr[], String filePath) throws Exception {

        if (fmtStr == null || fmtStr.length == 0) {

            System.out.println("上送格式化字符串为空");

            return;
        }

        //System.out.println("格式化的文件名称为" + filePath);
            //处理文件去空格，第一次做

            fmtXML(fmtStr[0], filePath, 0);


    }


    /**
     * @Comments:读取xml并进行格式化
     * @param:
     * @return:
     * @exception:
     * @throws:
     * @author:wangshenghe
     * @Time:下午2:34 2018/6/21
     */

    public void fmtXML(String fmtStr, String filePath, int flg) throws Exception {

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

            fw.write("\n");
        }

        fw.close();
        fwold.close();

        inputStream.close();

        bufferedReader.close();


    }

}

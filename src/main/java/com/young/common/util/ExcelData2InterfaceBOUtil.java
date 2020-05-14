package com.young.common.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 根据excl自动生成类-暂时没有重构
 * @author Beijing R&D Center -hon,yang
 * @version 5.0.0
 * created at 2019/7/18 15:54
 * copyright @2019 Beijing
 */
public class ExcelData2InterfaceBOUtil {
    int rows;
    int columns;
    public Workbook workbook;
    //excl 路径
    public String filePath;
    //BO 路径
    public String boFilePath;
    public ArrayList<String> arrkey = new ArrayList<String>();

    String sourceFile;


    public ExcelData2InterfaceBOUtil(String filePath, String boFilePath) {
        super();
        this.filePath = filePath;
        this.boFilePath = boFilePath;
    }


    public void getExcelData(String prefix, String fileName, String sheetName, String boName, String suffix) throws IOException {
        System.out.println("???????" + filePath + fileName + "-" + sheetName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ HH:mm");
        String date = sdf.format(new Date());
        File file = new File(getPath(fileName));
        FileInputStream input = new FileInputStream(file);
        workbook = null;
        //
        String excelFileName = getPath(fileName);
        String fileExtensionName = excelFileName.substring(excelFileName
                .indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(input);

        } else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(input);

        }

        // ???sheet
        Sheet sheet = workbook.getSheet(sheetName);
        // ?????????
        rows = sheet.getLastRowNum();
        columns = sheet.getRow(0).getLastCellNum();
        System.out.println(rows + "-----" + columns);

        // // ?????????Object[][],??????????е??е???????
        HashMap<String, String>[][] arrmap = new HashMap[rows][1];
        // ???????????????hashmap???г????
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                arrmap[i][0] = new HashMap<>();
            }
        } else {
            System.out.println("excel?????????:" + rows);
        }

        // ??????е??????????hashmap??key?
        for (int c = 0; c < columns; c++) {
            Row firstRow = sheet.getRow(0);
            String cellvalue = firstRow.getCell(c).getStringCellValue();
            arrkey.add(cellvalue);
//            System.out.println(cellvalue);
        }
        // ???????е???????????hashmap??

        sheetName = prefix + sheetName;

        String BO1 = "/**\n" +
                " * " + boName + "\n" +
                " * @author ?????з?????-yang_han\n" +
                " * @version 5.0.0\n" +
                " * created at " + date + "\n" +
                " * copyright @2018???????????????????????\n" +
                " */\n";

//        String BO1 = "/**\n" +
//                " * @Project?????????-baseapp\n" +
//                " * com.murong.ecp.netpay\n" +
//                " * @Package: com.murong.ecp.netpay.mer.api.entity\n" +
//                " * @Description??" + boName + " \n" +
//                " * @Author: yang_han\n" +
//                " * @Date: Created in " + date + "\n" +
//                " * @Company: ???????????????????????\n" +
//                " * @Copyright: Copyright (c) 2018\n" +
//                " * @Version: 5.0.0\n" +
//                " * @Modified By:\n" +
//                " */\n";
        String baseBO = "";
        String importPack = "";
        if (sheetName.contains("ReqBO")) {
            baseBO = " extends MerAbstractBaseReqBO implements Serializable";
            importPack = "import com.murong.ecp.netpay.mer.api.entity.common.MerAbstractBaseReqBO;\n";
        } else {
            baseBO = " extends MerAbstractBaseRspBO implements Serializable";
            importPack = "import com.murong.ecp.netpay.mer.api.entity.common.MerAbstractBaseRspBO;\n";
        }
        String BO = "package com.murong.ecp.netpay.mer.api.entity.shopoper;\n" +
                "\n" +
                "import lombok.Data;\n" +
                "import net.sf.oval.constraint.NotNull;\n" +
                importPack +
                "\n" +
                "import java.io.Serializable;\n" +
                "\n" +
                BO1 +
                "@Data\n" +
                "public class " + sheetName + suffix + baseBO + "{\n";

        for (int r = 1; r < rows + 1; r++) {

            String strCn = "";//0==????????
            String notnull = "";//1==????
            String strEn = "";//2==???????
            String strTyp = "";//
            String strLen = "";
            String strRark = "";

            for (int c = 0; c < columns; c++) {
                Row row = sheet.getRow(r);
                Cell cell = row.getCell(c);
                String cellvalue = getCellValueByCell(cell);

                if (c == 0) {
                    strCn = cellvalue;
                }
                if (c == 1) {
                    if (cellvalue.equals("????")) {
                        notnull = "    @NotNull\n";
                    }
                }
                if (c == 2) {
                    strEn = cellvalue + ";\n";
                }
                if (c == 3) {
                    strTyp = "    private " + cellvalue + " ";
                }
                if (c == 4) {
                    strLen = cellvalue;
                }
                if (c == 5) {
                    strRark = "-" + cellvalue + "\n";
                }

            }

            BO = BO + "    /**\n     * " +
                    strCn + strRark + "     */\n"
                    + notnull +
                    strTyp + strEn;

        }
        BO = BO + "\n" +
                "}";
//        System.out.println(BO);
        workbook.close();
        input.close();

        writeBO(BO, sheetName, suffix);
    }

    private void writeBO(String BO, String sheetName, String suffix) throws IOException {
        File file = new File(boFilePath + sheetName + suffix + ".java");
        System.out.println("?????" + file);
        Writer out = new FileWriter(file);
        out.write(BO);
        out.close();

    }

    private static String getCellValueByCell(Cell cell) {
        //?ж?????null????
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        CellType cellType = cell.getCellType();

        if (cellType.equals(CellType.FORMULA)) {
            //???
            cellValue = cell.getStringCellValue().trim();
            cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
        } else if (cellType.equals(CellType.NUMERIC)) {
            //????
            cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
        } else if (cellType.equals(CellType.STRING)) {
            //text
            cellValue = cell.getStringCellValue();
        } else {
            System.out.println("????????????" + cell.getCellType());
        }

        return cellValue;

    }

    /**
     * ???excel????????
     * @return
     * @throws IOException
     */
    public String getPath(String fileName) throws IOException {
        File directory = new File(".");

        sourceFile = filePath + fileName;

        return sourceFile;
    }



    public static void main(String[] args) throws IOException {
        ExcelData2InterfaceBOUtil excelData = new ExcelData2InterfaceBOUtil("D:\\code\\myproject\\testDemo\\utils\\src\\main\\resources\\"
                , "D:\\code\\murongGit\\third_payment\\netpay\\netpay5.0\\baseapp\\baseapp-mer\\baseapp-urmcore-merchant-api\\src\\main\\java\\com\\murong\\ecp\\netpay\\baseapp\\merchant\\api\\entity\\common\\");

//   ??? start
//        excelData.getExcelData("Mer", "shop.xlsx", "AddMercShopReqBO", "??????????BO", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "AddMercShopRspBO", "??????????BO", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "MercShopInfoBO", "??????????BO", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "GetMercShopInfoReqBO", "????????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "GetMercShopInfoRspBO", "????????????", "");
//
//
//        excelData.getExcelData("Mer", "shop.xlsx", "ModMercShopReqBO", "????????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "ModMercShopRspBO", "????????????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "CreateMercShopOprReqBO", "???????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "CreateMercShopOprRspBO", "???????????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "ActMercShopOprReqBO", "???????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "ActMercShopOprRspBO", "???????????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "AddMercShopOprReqBO", "??????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "AddMercShopOprRspBO", "??????????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "DelMercShopOprReqBO", "??????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "DelMercShopOprRspBO", "??????????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "ChangeMercShopMgrReqBO", "???????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "ChangeMercShopMgrRspBO", "???????", "");
//
//        excelData.getExcelData("Mer", "shop.xlsx", "GetMercShopMgrReqBO", "?????????????", "");
//        excelData.getExcelData("Mer", "shop.xlsx", "GetMercShopMgrRspBO", "?????????????", "");
//


//   ??? end










//??????  start
//        excelData.getExcelData("b2cConsume.xlsx", "GetMercInfoReqBO", "?????????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "GetMercInfoRspBO", "?????????????BO", "");

//        excelData.getExcelData("b2cConsume.xlsx", "CheckMercInfoReqBO", "?????????BO", "");

//        excelData.getExcelData("b2cConsume.xlsx", "MercInitDatBO", "????????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "MercInitBaseInfoBO", "????????????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "MercInitAdmOprBO", "?????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "CreateMercReqBO", "??????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "CreateMercRspBO", "??????????BO", "");

//        excelData.getExcelData("b2cConsume.xlsx", "ModMercInfoReqBO", "??????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "ModMercInfoRspBO", "??????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "ModMercStsReqBO", "????????BO", "");
//        excelData.getExcelData("b2cConsume.xlsx", "ModMercStsRspBO", "????????BO", "");

//        excelData.getExcelData("Mer","b2cConsume.xlsx", "ModMrecTxPrivReqBO", "?????????????BO", "");
//        excelData.getExcelData("Mer","b2cConsume.xlsx", "ModMrecTxPrivRspBO", "?????????????BO", "");
//        excelData.getExcelData("","b2cConsume.xlsx", "MercTxBO", "??????????BO", "");
//        excelData.getExcelData("","b2cConsume.xlsx", "MercTxPrivDtlBO", "?????????????BO", "");

//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "GetMrecTxPrivReqBO", "?????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "GetMrecTxPrivRspBO", "?????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "CheckMercTxReqBO", "?????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "CheckMercTxRspBo", "?????????BO", "");

//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "VerifyMercPPwdReqBO", "?????????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "VerifyMercPPwdRspBO", "?????????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "ModMercPPwdRepBO", "?????????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "ModMercPPwdRspBO", "?????????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "ResetMercPPwdReqBO", "??????????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "ResetMercPPwdRspBO", "??????????????BO", "");
//
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "IssueMercCertReqBO", "?????????BO", "");
//        excelData.getExcelData("Mer", "b2cConsume.xlsx", "IssueMercCertRspBO", "?????????BO", "");

//???????  end
//
// ???Э??   start
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "SignMercAgrReqBO", "??????Э??BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "SignMercAgrRspBO", "??????Э??BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "UsrAgrInfoBO", "??????Э??BO", "");
//
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "ModMercAgrReqBO", "??????Э??BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "ModMercAgrRspBO", "??????Э??BO", "");
//
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "ModMercStatusReqBO", "??????Э????BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "ModMercStatusRspBO", "??????Э????BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "CanAgrInfoBO", "??????Э????BO", "");
//
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "GetMercAgrReqBO", "???Э?????BO", "");
//        excelData.getExcelData("Mer", "meragrbpc2.xlsx", "GetMercAgrRspBO", "???Э?????BO", "");

        //???Э??   end


//        ????????

//
//        excelData.getExcelData("Mer", "oper.xlsx", "AddMercOprReqBO", "???????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "AddMercOprRspBO", "???????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "UsrOprInfoBO", "???????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "DelMercOprReqBO", "???????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "DelMercOprRspBO", "???????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "GetMercOprInfoReqBO", "???????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "GetMercOprInfoRspBO", "???????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "VerifyMercOprLpwdReqBO", "????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "VerifyMercOprLpwdRspBO", "???????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "ModMercPorLPwdReqBO", "???????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "ModMercPorLPwdRspBO", "???????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "ResetMercOprLPwdReqBO", "????????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "ResetMercOprLPwdRspBO", "????????????BO", "");
//
//        excelData.getExcelData("Mer", "oper.xlsx", "ModMercOprReqBO", "????????????BO", "");
//        excelData.getExcelData("Mer", "oper.xlsx", "ModMercOprRspBO", "????????????BO", "");
//
//




//cusentbpc1 ?????????? start
//        excelData.getExcelData("cusentbpc1.xlsx", "GetEntCusInfoReqBO", "?????????BO", "");
//        excelData.getExcelData("cusentbpc1.xlsx", "GetEntCusInfoRspBO", "?????????BO", "");

//        excelData.getExcelData("cusentbpc1.xlsx", "AddEntCusReqBO", "?????????BO", "");
//        excelData.getExcelData("cusentbpc1.xlsx", "AddEntCusRspBO", "?????????BO", "");
//
//        excelData.getExcelData("cusentbpc1.xlsx", "CheckEntInfoReqBO", "?????????????BO", "");
//        excelData.getExcelData("cusentbpc1.xlsx", "CheckEntInfoRspBO", "?????????????BO", "");

//        excelData.getExcelData("cusentbpc1.xlsx", "ModEntCusInfoReqBO", " ?????????????BO", "");
//        excelData.getExcelData("cusentbpc1.xlsx", "ModEntCusInfoRspBO", " ?????????????BO", "");
//cusentbpc1 ?????????? end

//?????  start

//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercTxDtlReqBO", "??????????BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercTxDtlRspBO", "??????????BO", "");
//        excelData.getExcelData("", "query.xlsx", "MercTxDtlSearchedItmBO", "??????????BO", "");
//
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercOprsReqBO", "????????????BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercOprsRspBO", "????????????BO", "");
//        excelData.getExcelData("", "query.xlsx", "MercOprSearchedItmBO", "????????????BO", "");
//
//
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercsReqBO", "???????BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercsRspBO", "???????BO", "");
//        excelData.getExcelData("", "query.xlsx", "MercSearchedItmBO", "???????BO", "");
//
//        excelData.getExcelData("Mer", "query.xlsx", "SearchEntCustomersReqBO", "??????????BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchEntCustomersRspBO", "??????????BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchedEntCusItmBO", "??????????BO", "");
//
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercAgrReqBO", "???????Э??BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercAgrRspBO", "???????Э??BO", "");
//        excelData.getExcelData("Mer", "query.xlsx", "UsrAgrSearchItemBO", "???????Э??BO", "");

//        excelData.getExcelData("Mer", "query.xlsx", "SearchMerchShopsReqBO", "BO??????????", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMerchShopsRspBO", "BO??????????", "");
//        excelData.getExcelData("Mer", "query.xlsx", "MshpSearchedItemBO", "BO??????????", "");
//
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercShopOprsReqBO", "??????????????", "");
//        excelData.getExcelData("Mer", "query.xlsx", "SearchMercShopOprsRspBO", "??????????????", "");
//        excelData.getExcelData("Mer", "query.xlsx", "MshpOprSearchedItemBO", "??????????????", "");






//       	???ò?????????--start
//        excelData.getExcelData("mercInfo2.xlsx","MercBaseInfo","??????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercInvInfo", "?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "EntCertInfo", "?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "EntCrol", "???????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "EntHod", "??????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "EntCusInfo", "?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercBusInfo", "?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercDevInfo", "?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercDpsInfo", "?????????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercOthInfo", "??????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercPosExtInfo", "???POS?????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercCnt", "????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercShopInputInfo", "?????????????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercAcBal", "??????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercTx", "???????","BO");
//        excelData.getExcelData("mercInfo2.xlsx", "MercTxPrivDtl", "??????????","BO");

//???????? ???
//        excelData.getExcelData("", "mercInfo2.xlsx", "MerAbstractBaseReqBO", "??????????????", "");
//        excelData.getExcelData("", "mercInfo2.xlsx", "MerAbstractBaseRspBO", "??????????????", "");

//        ???ò?????????--start




        //门店开始
        excelData.getExcelData("Mer", "bug.xlsx", "MercInitAgrInfoBO", "商户协议", "");



        // 门店结束

    }

}

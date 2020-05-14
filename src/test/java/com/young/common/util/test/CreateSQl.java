package com.young.common.util.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateSQl {

    @Test
    public void createSQL() throws IOException {
        List<String> lines=new ArrayList<String>();

//        String str = "SYS_CNL,系统渠道,System channel#APP-客户端,BSP-批量,SYS-系统,CAS-收银台,BUI-运营,PSM-清结算@" +
//                "MERC_STS,商户状态,Merchant status#0-正常,1-销户,2-删除,3-待销户,4-停用,5-冻结,E-创建异常@" +
//                "WIN_STS,窗口状态,Window status#N-正常窗口,P-并行窗口,B-批量窗口,H-截止窗口";
        String str="SYS_CNL,系统渠道,System channel#APP-客户端,BSP-批量,SYS-系统,CAS-收银台,BUI-运营,PSM-清结算@" +
                "MERC_STS,商户状态,Merchant status#0-正常,1-销户,2-删除,3-待销户,4-停用,5-冻结,E-创建异常@" +
                "WIN_STS,窗口状态,Window status#N-正常窗口,P-并行窗口,B-批量窗口,H-截止窗口@" +
                "WDC_TYP,提现类型,Withdraw type#11-个人用户提现,21-企业商户提现,22-企业商户结算,31-企业用户提现@" +
                "USR_TYP,用户类型,User  type#1-个人用户,2-个人商户,5-企业用户,8-企业商户@" +
                "SEC_PAY_TYP,安全支付方式,Security payment type#0-密码支付,1-指纹支付,2-小额免密@" +
                "CAP_STS,资金状态,Capital status#0-正常,1-已经销户,2-冻结,3-待销户@" +
                "OPER_TYP,操作员类型,Operator type#1-管理人员,2-业务操作员,3-客户经理,4-技术操作员,9-其他@" +
                "LOG_MOD,登录模式,Login mode#1-普通密码登陆,2-USBKEY登陆,3-数字证书编号@" +
                "OPR_STS,操作员状态,Operator status#0-待激活,1-正常,2-锁定,3-未启用,8-过期,9-删除@" +
                "LOG_PSWD_STS,登录密码状态,Login password status#0-正常,1-锁定,2-初始化状态@" +
                "WFF_FLG,审核标识,Audit flag#Y-初始化,F-审核完成,R-审核拒绝@" +
                "OVD_PAY_FLG,逾期还款参数-逾期还款方式,Overdue repayment parameters-overdue payment method#1-整期还，不够不还。此种还款方式只支持横向冲账，可以还多个分期，可以不将逾期的分期全部结清,2-有多少还多少@" +
                "OVD_PRI_FLG,逾期还款参数-逾期还款优先级,Overdue repayment parameters-overdue repayment priority#1-横向优先，按逾期期数的顺序还款,2-纵向优先，按所有逾期期数的金额种类顺序还款：本金、欠息、罚息、复利。如果不支持部分还款，则不允许纵向优先。@" +
                "CUR_AFT_FLG,提前还款参数-当期后期标志,Overdue repayment parameters-late period sign#1-只允许当期的提前还款,2-允许当期和后期的提前还款@" +
                "PAY_CHG_FLG,还款计划调整方式，用于指明提前还款后如何调整还款计划。可选项包含,Repayment plan adjustment method#1-总期数不变，下调每期还款金额,2-每期还款金额不变，下调总期数@" +
                "CUR_INT_FLG,提前还款参数-当期利息处理方式,Overdue repayment parameters-current interest treatment#0-不收息,1-还全部利息,2-还提前还款本金对应的利息@" +
                "BRH_FLG,提前还款参数-违约金收取标志,Overdue repayment parameters-liquidated damages collection mark#Y-收取,N-不收取@" +
                "BRH_MOD,提前还款参数-违约金收取方式,Overdue repayment parameters-liquidated damages#01-收1个月利息,03-收3个月利息@" +
                "RES_MOD,重定价方式，当【是否重定价】为【Y-重定价】时,Reprice mode#1-周期重定价，以开户日为基准,2-固定日期重定价,3-每期后重定价，与结息周期相同@" +
                "VCH_NO_TYP,传票类型 ,Voucher type#0-交易流水号,1-普通BSP批次号,2-日终清算批次号 @" +
                "STL_STS,结算状态 ,Settle status#S-已结算 P-处理中 F-失败 @" +
                "TERM_STS,分期状态,Term state#1-正常,2-逾期,3-结清@" +
                "RDP_FLG,转存标志,Redeposit flag#Y-转存,N-不转存(默认)@" +
                "PRD_PRP,产品性质,Product property#0-活期,1-定期,2-定期转活期,3-定期转定期 @" +
                "ACC_RUL,记账接口类型,Account rule#01-通用记账,02-适配记账,03-长款差错通用记账,04-长款差错适配记账@" +
                "RVS_TYP,销账种类,Reversal type#0-全数销账,1-部分销账,@" +
                "DC_FLG,借贷标志,Debit and credit flag#D-借方,C-贷方@" +
                "AC_FLG,内外部账户标志,Inside and outside account flag#0-内部户,1-外部户@" +
                "EFF_STS,生效状态,Effect status#0-生效,1-失效 @" +
                "AC_STS,账户状态,Account  status#0-正常,1-已经销户,3-待销户 @" +
                "UPD_BAL_FLG,余额更新标志,Update balance flag#0-更新,1-未更新 @" +
                "RVS_TX_FLG,冲正交易标志,Reversal transaction flag#Y-冲正交易,N-正常交易 @" +
                "RVS_FLG_TYP,冲正标志,Reversal type#Y-冲正,N-正常 @" +
                "AC_DT_SW_FLG,会计日标识,Account date flag#0-未切换,1-已经切换@" +
                "BAT_STS,批量状态,Battle state#U-初始化,P-处理中,N-正常@" +
                "IS_PRIMARY,是否出批次,Is primary#Y-需要,N-不需要@" +
                "BAT_MOD,批量模式,Battle mode#1-并行模式(默认),2-串行模式@" +
                "SUM_FLG,统计标志,Sumary flag#Y-需要,N-不需要@" +
                "DO_PRE_PROC,是否需要前处理,Do previous process#Y-需要,N-不需要@" +
                "DO_AFTER_PROC,是否需要处后理,Do_after_process#Y-需要,N-不需要@" +
                "UPD_FLG,更新明细表额外数据标志,Update flag#Y-强制更新,N-选择更新,O-无需更新,@" +
                "LVL_FLG,分层标志,Level flag#0-套档,1-分层,2-固定@" +
                "\"LMT_LVL,额度层次,Limit level#1-用户级别限制,0-客户级别限制" +
                "@\"" +
                "USE_MOD,额度使用方式,Use mode#R-(默认)@" +
                "TXN_STS,交易状态,Transaction state#U-初始化, F-失败 ,S-成功 @" +
                "EFF_FLG,生效标志,Effective flag#Y-生效 ,N-失效@" +
                "SCE_FLG,是否参与场景定义 ,Scene flag#Y-参与,N-不参与 @" +
                "DATA_TYPE,字段类型 ,Data type#AMT-金额, STR-字符串, LONG-数字@" +
                "NOT_EMPTY_FLG,非空标志  ,Not empty flag#Y-不为空,N-允许为空@" +
                "DO_FLG,是否需要处理,Do  flag#N-不需要处理,Y-需要处理 @" +
                "CHKBSP_STS,差错状态,Bsp status#U-预设,F-处理失败（异常）,S-处理完成 @" +
                "LEGAL_FLG,法人标志,Legal flag#1-法人,0-非法人@" +
                "FORMAT,关联形式,Associated form#S-点差,P-百分比 @" +
                "INT_MOD_FLG,计息方法,Interest calculation method#1-动户积数计息法,2-逐笔计息法@" +
                "YEAR_BASE_FLG,计息年基数标志,Interest calculation year base flag#1-360天,2-365天,3-实际天数（360/365） @" +
                "INT_DAYS_FLG,计息期算法,Interest period algorithm#1-按实际天数,2-对年对月对日 每月按30天@" +
                "SEG_FLG,分段计息标志,Segmented interest mark#0-不分段,1-分段计息@" +
                "HEAD_TAIL_FLG,头尾利息计算方法,Head and tail interest calculation method#1-算头不算尾,2-算头又算尾,3-算尾不算头@" +
                "INT_RND_FLG,利息取整规则,Interest rounding rule#1-四舍五入,2-舍去,3-累入@" +
                "RAT_MOD,利率模式,Interest rate model#1-单一利率,2-余额分层全额累进利率(套档)@" +
                "LAY_STR_FLG,结息起始日期,Settlement start date flag#0-开户日,1-起息日,2-上次结息日,3-其他固定日,9-取系统当期日期@" +
                "LAY_END_FLG,结息截止日期,Settlement deadline flag#3-其他固定日,4-到期日,5-交易日,6-本期结息日,7-存期结束日,8-协议结束日,9-取系统当期日期@" +
                "RATE_EFF_FLG,利率生效日确定标志,Interest rate effective date flag#1-开户日,2-起息日@" +
                "DEF_FLG,默认地址标志,Default address flag#0-默认,1-不是 @" +
                "ADR_STS,地址状态 ,Address status#N-正常,D-删除，失效 @" +
                "CAN_FLG,注销是否需要处理标志,Cancle flag#Y-是,N-否 @" +
                "BIND_STS,绑定状态,Bind status#O-正常,C-关闭 @" +
                "LAY_SEQ,分层编号,Layered number#000-单一利率,001-分层利率，分层序号从此开始@" +
                "PRC_FLG,定价方式,当利率模式为1-单一利率时适用,Pricing method#1-参考定价,3-固定定价@" +
                "BAS_RAT_TYP,利率种类1,【当定价模式为1-参考定价/2-组合定价】时适用,Interest rate category 1#01-人行基准利率,02-公司执行利率,99-其他@" +
                "BAS_BUS_FLG,利率业务类型1,Interest rate business type 1#01-贷款利率@" +
                "LON_USE_TYP,贷款用途,Loan use#1-消费融资,2-其他 @" +
                "TERM_TYP,贷款期限结构标志,Loan term structure mark#0-无固定期限,1-固定期限@" +
                "DUE_NTF_STSW,到期通知方式,每个BIT位表示如下： 第1位：短信 第2位：电子邮件 第3位：电话 第4位：信函,Notice of expiration#1-选中,0-未选中@" +
                "NOTF_STSW,账单寄送方式，每个BIT位表示如下： 第1位：短信 第2位：电子邮件 第3位：电话 第4位：信函,Bill delivery method#1-选中,0-未选中@" +
                "PEN_MOD,罚息方式,Penalty method#0-不收罚息,1-未逾期本金取正常利息，逾期本金取罚息,2-只要有逾期本金，则全部本金取罚息@" +
                "IN_PEN_FLG,宽限期内计息方式,Interest-bearing method during the grace period#0-不计息,1-按正常利率计息,2-按罚息利率计息． 注：在宽限期后还款，从到期日到还款日全部按按罚息利率计息@" +
                "PEN_TYP,罚息利率取值方式,Penalty interest rate value method#1-在正常执行利率的基础上浮动,2-使用罚息利率@" +
                "FLT_FLG,当罚息利率取值方式为【在正常执行利率的基础上浮动】时，用于指定浮动方式。定义如下,Specify floating mode#1-点差浮动,2-百分比浮动@" +
                "REPAY_DT_FLG,正常还款参数-还款日标志，,Normal repayment parameter-repayment date mark#1-按放款日,2-指定还款日 @" +
                "FST_TM_FLG,正常还款参数-首期确定方式（当还款日期标志为【指定日期还款】时适用）,Normal repayment parameter-first phase determination method#1-当月,2-下月,3-足月@" +
                "FST_PAY_FLG,正常还款参数--等额本息首期处理方式（当还款日期为【等额本息】时适用）,Normal repayment parameter-equal amount of principal and interest#1-首期等额按月供公式计算的月供金额收取,2-首期不等额按实际天数计息进行调整@" +
                "ACCR_TYP,计息规则类型,Interest-bearing rule type#1-非固定期限存款利息计算规则,2-固定期限存款利息计算规则@" +
                "LON_AC_STS,约状态/计息条件/利率种类:,Interest condition#1-正常利息,2-逾期罚息,3-复利@" +
                "RES_HOL_FLG,重定价假日调整方式，当重定价日为节假日时,Repricing holiday adjustment method#0-重定价日不变,1-重定价日前置，上一工作日,2-重定价日后置，下一工作日@" +
                "RES_FLT_FLG,重定价浮动方向限制,Repricing floating direction limit#1-只能上浮,2-只能下浮,3-双边浮动@" +
                "ATO_TX_FLG,形态转移参数-自动转逾期,Morphological transfer parameter-automatic transfer overdue#1-到期自动转逾期,2-到期不自动转逾期，手工转逾期 @" +
                "OVD_MOD,形态转移参数-转逾期方式,Morphological transfer parameter-transfer overdue method#1-部分转逾期,2-全部转逾期@" +
                "TNSC_FLG,形态转移参数-转非应计标志,本金或利息逾期90天后是否需要转非应计,Morphological transfer parameter-non-accrual mark#0-不转非应计,1-转非应计,2-不转非应计@" +
                "LMT_FLG,风险分类参数-自动风险分类,Risk classification parameter-automatic risk classification#Y-根据风险等级分类参数自动分类,N-手工风险分类@" +
                "GRD_TYP,客户等级类别,Customer grade category#1-客户类型,2-KYC级别@" +
                "PRD_STS,产品状态,Product status#0-正常,1-停用(暂停销售),2-退出@" +
                "BUS_ATTR,业务性质,Business nature#1-资产业务,2-负债业务,3-中间业务,4-混合业务@" +
                "PRD_OWN,产品所有权,Product ownership#1-自有产品,2-第三方增值产品,3-无增值第三方产品@" +
                "TRI_MOD,第三方产品合作方式,Third-party product cooperation#1-合作,2-帐务管理,3-代理@" +
                "DELI_MOD,产品交付模式，定义如下,Product delivery model#1-账户类产品,2-服务类产品,3-混合类产品@" +
                "OBJ_TYP,适用对象类型,目前定义的类型如下,Applicable object type#001-国家,002-区域,003-签约渠道,004-币种,101-归属行业,102-用途,103-担保方式,104-还款方式,105-催收方式,106-到期通知方式,107-寄送方式,201-账户性质,204-可用存期,205-账户可用介质@" +
                "DFT_AC_FLG,缺省合约状态标识,Default contract status identifier flag#Y-缺省合约状态,N-非缺省合约状态@" +
                "NA_FLG,适用客户国籍标志,Applicable customer nationality mark#0-不限,1-国内,2-境外@" +
                "TRM_TYP,存款期限结构标志,Deposit term structure mark#0-无固定期限,1-固定期限@" +
                "TRM_VAL,最短存款期限，仅对固定期期限存款有意义,Minimum deposit term#1-固定期限@" +
                "HOL_FLG,节假日到期处理标志(对非固定期限存款无意义),Holiday expiration processing flag#0-不调整到期日,1-到期日前移,2-到期日后移@" +
                "HOL_FCL_FLG,账户到期日落在节假日时的处理规则,Account processing rules for sunsets during holidays#1-在假日前允许支取，利息计算到到期日,2-在假日前允许支取，利息计算到支取日,3-在假期后允许支取，利息计算到到期日@" +
                "MUL_DEP_FLG,多次存入标志,Multiple deposits#0-开户后随时存入,1-固定周期存入,2-一次性存入@" +
                "DATE_UNIT,定期续存周期单位，当定期续存标志为Y-定期续存(零存整取)时适用。,Periodic renewal period unit#Y-年,M-月,D-日@" +
                "CYC_AMT_FLG,定期续存(零存整取)时，对每次存入资金数额的限制,Restrictions on the amount of funds deposited each time during regular renewal (zero deposit and withdrawal)#1-约定金额,2-约定金额的倍数@" +
                "AUTO_AMT_FLG,自动转存时转存的资金标志,Fund transfer flag when auto-deposit#1-转存本息,2-转存本金@" +
                "DRAW_FLG,对资金支取的限制,Restrictions on withdrawal of funds#0-随时支取,1-固定周期支取,2-通知支取,3-一次性支取@" +
                "DUE_FLG,到期提前通知标志,Early notice notice#N-不通知,Y-通知@" +
                "CRD_STS,卡状态 ,Card status#N-正常@" +
                "CUS_TYP,客户类型,Customer type#P-对私,E-对公@" +
                "CUS_STS,客户状态,Customer status#0-正常,1-销户@" +
                "PER_OPR_ID_TYP,操作员ID类型,Operator id type#P-手机号,E-邮箱,O-其他@" +
                "USR_OPR_STS,用户操作员状态,User operator status#0-正常,1-已删除@" +
                "LOG_FLG,为登录标识,Whether as a login id#Y-作为登录标识,N-不作为登录标识@" +
                "OPN_MOD,开户方式,Open mode#0-手工开立,1-系统开立@" +
                "DEL_FLG,删除标志,Delete flag#0-生效 ,1-删除 @" +
                "DF_FLG,默认标志,Default flag#Y-默认,N-非默认@" +
                "MAL_TYP,模板类型,Email template type#0-普通邮件@" +
                "SM_TYP,短信类别,SMS type#0-普通短信,1-STK短信,2-WAP_PUSH@" +
                "NET_FLG,网络标志,Network flag#01-公网,02-专线@" +
                "ATH_TYP,认证类型,Authentication type#01-身份证认证,02-银行卡认证,03-人脸认证,04-学历认证,05-不动产认证@" +
                "CNL_TYP,合作通道类型,Cooperative channel type#0-银行,1-非银行@" +
                "CNL_LVL,通道等级, Channel level#1-一级通道（支持至少1个渠道） ,2- 二级通道（支持至少3个渠道),3- 三级通道（支持至少5个渠道）@" +
                "ATH_STS,认证状态,Authentication status#F-认证失败,P-部分成功,S-认证成功,U-认证初始@";
       List<String>  dctAllList= Arrays.asList(str.split("@"));
        for (String strsql: dctAllList) {
            List<String>  dctList= Arrays.asList(strsql.split("#"));
            System.out.println("dctList:"+dctList.toString());
            System.out.println("dctList 0:"+dctList.get(0).toString());
            System.out.println("dctList 1:"+dctList.get(1).toString());
            List<String>  dctNmList= Arrays.asList(dctList.get(0).split(","));


            String dctNm=dctNmList.get(0);
            String dctCNm=dctNmList.get(1);
            String dctENm=dctNmList.get(2);
            String lastStr=dctList.get(1);
            List<String>  lastStrList= Arrays.asList(lastStr.split(","));

            for (int i=0;i<lastStrList.size();i++) {
                List<String>  fldList= Arrays.asList(lastStrList.get(i).split("-"));

                String  fldKey = fldList.get(0);
                String  fldVal = fldList.get(1);
                String lastSql="INSERT INTO GROUPP.T_PUB_HLP (FLD_ORD, FLD_NM, FLD_VAL, FLD_EXP, FLD_TYP, FLD_EXP_EDESC, FLD_EXP_CDESC, TM_SMP, NOD_ID, UPD_DT, UPD_TM, UPD_OPER, UPD_DESC) VALUES ("+i+", '"+dctNm+"', '"+fldKey+"', '"+fldVal+"', 'VARCHAR', '"+dctENm+"', '"+dctCNm+"', '20190816000000', ' ', '20190816', '000000', 'SYS', 'INIT');";
                System.out.println(lastSql);
                lines.add(lastSql);
            }
        }

        FileUtils.writeLines(new File("enumSql.sql"),lines,false);

    }


}

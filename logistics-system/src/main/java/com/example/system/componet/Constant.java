package com.example.system.componet;

import com.example.system.vo.unify.Point;

import java.math.BigDecimal;

//全局常量类
public class Constant {

    //正确返回code
    public final static String SUCCESS_CODE = "200";

    //后端代码错误code
    public final static String FAILURE_JAVA_CODE = "400";

    //前端代码错误code
    public final static String FAILURE_HTML_CODE = "500";

    //注册时记住获取过验证码的用户加入的salt
    public final static String REGISTER_SALT = "sljfdk#@f&";

    //token的加密密钥（salt）
    public final static String TOKEN_SALT = "adk&d*dd)&";

    //token的请求头key
    public final static String TOKEN_HEAD = "token";

    //管理员的token请求头key
    public final static String TOKEN_HEAD_ROOT = "rootTaken";

    //管理员用户
    public final static String ROOT_USERNAME = "CWCRootCWC";

    //管理员密码
    public final static String ROOT_PASSWORD = "CWCRootCWC";

    //管理员账户在redis中的key
    public final static String ROOT_USERNAME_REDIS_KEY = "adfasjlshfodho@asf&f%sdfgj;aaf";

    //各个省会城市的地址信息
    public final static Point[] mapList = new Point[50];

    static {
        mapList[0] = new Point(new BigDecimal("116.23128"),new BigDecimal("40.22077"),"北京,北京,昌平区");
        mapList[1] = new Point(new BigDecimal("121.48941"),new BigDecimal("31.40527"),"上海,上海,宝山区");
        mapList[2] = new Point(new BigDecimal("117.30983"),new BigDecimal("39.71755"),"天津,天津,宝坻区");
        mapList[3] = new Point(new BigDecimal("106.54041"),new BigDecimal("29.40268"),"重庆,重庆,巴南区");
        mapList[4] = new Point(new BigDecimal("126.95717"),new BigDecimal("45.54774"),"黑龙江,哈尔滨,阿城区");
        mapList[5] = new Point(new BigDecimal("125.28845"),new BigDecimal("43.83327"),"吉林,长春,朝阳区");
        mapList[6] = new Point(new BigDecimal("123.46987"),new BigDecimal("41.80515"),"辽宁,沈阳,大东区");
        mapList[7] = new Point(new BigDecimal("111.62299"),new BigDecimal("40.80772"),"内蒙古,呼和浩特,回民区");
        mapList[8] = new Point(new BigDecimal("114.53952"),new BigDecimal("38.03647"),"河北,石家庄,长安区");
        mapList[9] = new Point(new BigDecimal("88.31104"),new BigDecimal("43.36378"),"新疆,乌鲁木齐,达坂城区");
        mapList[10] = new Point(new BigDecimal("103.71878"),new BigDecimal("36.10396"),"甘肃,兰州,安宁区");
        mapList[11] = new Point(new BigDecimal("101.76628"),new BigDecimal("36.6502"),"青海,西宁,城北区");
        mapList[12] = new Point(new BigDecimal("108.93425"),new BigDecimal("34.23053"),"陕西,西安,碑林区");
        mapList[13] = new Point(new BigDecimal("106.24284"),new BigDecimal("38.47314"),"宁夏,银川,金凤区");
        mapList[14] = new Point(new BigDecimal("113.6401"),new BigDecimal("34.72468"),"河南,郑州,二七区");
        mapList[15] = new Point(new BigDecimal("116.75199"),new BigDecimal("36.55358"),"山东,济南,长清区");
        mapList[16] = new Point(new BigDecimal("112.48699"),new BigDecimal("37.94036"),"山西,太原,尖草坪区");
        mapList[17] = new Point(new BigDecimal("117.30794"),new BigDecimal("31.79322"),"安徽,合肥,包河区");
        mapList[18] = new Point(new BigDecimal("114.02919"),new BigDecimal("30.58203"),"湖北,武汉,蔡甸区");
        mapList[19] = new Point(new BigDecimal("112.98626"),new BigDecimal("28.25591"),"湖南,长沙,开福区");
        mapList[20] = new Point(new BigDecimal("118.8921"),new BigDecimal("31.32751"),"江苏,南京,高淳县");
        mapList[21] = new Point(new BigDecimal("104.042824"),new BigDecimal("30.637516"),"四川,成都,武侯区");
        mapList[22] = new Point(new BigDecimal("106.62298"),new BigDecimal("26.67865"),"贵州,贵阳,白云区");
        mapList[23] = new Point(new BigDecimal("102.82147"),new BigDecimal("24.88554"),"云南,昆明,呈贡县");
        mapList[24] = new Point(new BigDecimal("108.27331"),new BigDecimal("22.78121"),"广西,南宁,江南区");
        mapList[25] = new Point(new BigDecimal("91.13775"),new BigDecimal("29.65262"),"西藏,拉萨,城关区");
        mapList[26] = new Point(new BigDecimal("120.21201"),new BigDecimal("30.2084"),"浙江,杭州,滨江区");
        mapList[27] = new Point(new BigDecimal("115.94422"),new BigDecimal("28.54538"),"江西,南昌,南昌县");
        mapList[28] = new Point(new BigDecimal("113.27324"),new BigDecimal("23.15792"),"广东,广州,白云区");
        mapList[29] = new Point(new BigDecimal("119.27345"),new BigDecimal("26.04769"),"福建,福州,仓山区");
        mapList[30] = new Point(new BigDecimal("121.530243"),new BigDecimal("25.061031"),"台湾,台北市");
        mapList[31] = new Point(new BigDecimal("110.32941"),new BigDecimal("20.02971"),"海南,海口,龙华区");
        mapList[32] = new Point(new BigDecimal("114.16546"),new BigDecimal("22.27534"),"香港,中西区");
        mapList[33] = new Point(new BigDecimal("113.54913"),new BigDecimal("22.19875"),"澳门,大堂区");
    }

}

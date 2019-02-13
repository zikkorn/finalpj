package com.tqy.cams.utils;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 字符串工具类
*/ 
public class StringUtil {

    /**
     * 获取当天日期
     * @param dateFormat 日期格式，如：yyyy-MM-dd
     * @return
     */
    public static String getCurrDateStr(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String currDate = sdf.format(date);
        return currDate;
    }

    /**
     * 空字符处理
     */
    public static String null2Blank(String value) {
        return value == null ? "" : value;
    }

    /**
     * 空数字
     */
    public static long null2Zero(Object value) {
        return value == null ? 0 : Long.valueOf(value.toString());
    }

    /**
     * 空字符判断
     */
    public static boolean isNullOrBlank(String value) {
        return (value == null || "".equals(value)) ? true : false;
    }

    /**
     * 产生验证码
     * @param length 验证码长度
     */
    public static String retrieveRandomNumber(int length) {
        return Math.abs(Math.round(Math.random()*(Math.pow(10, length-1)*9-1)+(Math.pow(10, length-1))))+"";
    }

    /**
     * 获取一个六位随机数
     * @param length 随机数长度
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 != 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 != 0 ? 97 : 65;
                val = (new StringBuilder(String.valueOf(val))).append(
                        (char) (choice + random.nextInt(26))).toString();
            } else if ("num".equalsIgnoreCase(charOrNum))
                val = (new StringBuilder(String.valueOf(val))).append(
                        String.valueOf(random.nextInt(10))).toString();
        }
        return val.toLowerCase();
    }
    /**
     * 获取时间戳
     */
    public static String getTimeStamp(String format){
        SimpleDateFormat dateFm = new SimpleDateFormat(format); //格式化当前系统日期
        return dateFm.format(new java.util.Date());
    }
    /**
     * 获取32位的字符类型的随机值
     * @return 32位的字符
     */
    public static String getRandomStrId() {
        UUID uuid = UUID.randomUUID();
        String tempId = uuid.toString().replace("-", "");
        return tempId;
    }
    /**
     * 判断字符串是否为空
     * @param string 设置字符串
     * @return boolean 返回是否为空
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() != 0;
    }

    /**
     * 按字节大小截取字符串
     */
    public static String subStr(String str,int len) {
        if(str!=null && str.getBytes().length>len){
            byte[] newValArr = new byte[len];
            System.arraycopy(str.getBytes(), 0, newValArr, 0, newValArr.length);
            str = new String(newValArr);
        }
        return str;
    }

    /**
     * 根据对象数组拼接对象数组字符串
     * @param  objArr         对象数组
     * @param  splitAfterStr  前拼接字符
     * @param  splitLastStr   后拼接字符
     * @return reStr          返回对象
     */
    public static String concatObjArr(Object[] objArr,String splitAfterStr,String splitLastStr){
        String reStr = "";
        if (splitAfterStr==null){
            splitAfterStr = " ";
        }
        if (splitLastStr==null){
            splitLastStr = " ";
        }
        if(objArr!=null && objArr.length>0){
            for(int i=0;i<objArr.length;i++){
                reStr += splitAfterStr+objArr[i]+splitLastStr;
            }
        }
        return reStr;
    }

    /**
     * 根据对象列表拼接对象数组字符串
     * @param  li          对象列表
     * @param  replaceStr  替换字符
     * @param  splitStr    分割字符
     * @return reStr       返回对象
     */
    public static String replaceObjArrToStr(List<Object> li,String replaceStr,String splitStr){
        String reStr = "";
        for(int i=0;i<li.size();i++){
            reStr+=(replaceStr+splitStr);
        }
        if(StringUtil.isNotEmpty(reStr)){
            reStr = reStr.substring(0,reStr.length()-splitStr.length());
        }
        return reStr;
    }

    /**
     * 根据对象拼接对象数组字符串
     * @param  splitAfterStr  前拼接字符
     * @param  splitLastStr   后拼接字符
     * @return reStr          返回对象
     */
    public static String beanToObjArrStr(BeanPropertySqlParameterSource beanObj, String splitAfterStr, String splitLastStr){
        String reStr = "";
        if (splitAfterStr==null){
            splitAfterStr = ":";
        }
        if (splitLastStr==null){
            splitLastStr = ";";
        }
        if(beanObj!=null){
            String[] strArr = beanObj.getReadablePropertyNames();
            for(int i=0;i<strArr.length;i++){
                reStr += strArr[i]+splitAfterStr+beanObj.getValue(strArr[i])+splitLastStr;
            }
        }
        return reStr;
    }

    /**
     * 获得客户端真实IP地址
     * @param  request     请求对象
     * @return ip          返回对象
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 通用方法-解决字符串回车换行问题
     * @param  str    处理的字符串对象
     * @return String 返回对象
     */
    public static String jointString(String str) {
        StringBuffer buf = new StringBuffer();
        for (StringTokenizer st = new StringTokenizer(str != null ? str : "","\n", false); st.hasMoreTokens(); buf.append(st.nextToken().trim()));
        return buf.toString();
    }

    /**
     * 递归将列表生成树节点对象
     * @param  tempList    	节点列表
     * @param  topObj      	根节点对象
     * @param  idName      	id对象名称
     * @param  parentIdName parentId对象名称
     * @param  childrenName children对象名称
     * @return Map<String,Object>   返回对象
     */
    @SuppressWarnings("unchecked")
    private static Map<String,Object> treeWork(List<Map<String,Object>> tempList, Map<String,Object> topObj, String idName, String parentIdName, String childrenName){
        Map<String,Object> mapObj = null;
        List<Map<String,Object>> objList = null;
        String parentId = topObj.get(idName).toString();
        List<Map<String,Object>> temp2List = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> temp3List = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < tempList.size(); i++) {
            mapObj = tempList.get(i);
            if (mapObj.get(parentIdName).equals(parentId)) {
                if (topObj.containsKey(childrenName)) {
                    ((List<Map<String,Object>>)topObj.get(childrenName)).add(mapObj);
                } else {
                    objList = new ArrayList<Map<String,Object>>();
                    objList.add(mapObj);
                    topObj.put(childrenName, objList);
                }
                temp2List.add(mapObj);
                topObj.put("leaf", false);
            } else {
                temp3List.add(mapObj);
            }
        }

        for (int i = 0; i < temp2List.size(); i++) {
            mapObj = temp2List.get(i);
            treeWork(temp3List, mapObj, idName, parentIdName, childrenName);
        }
        return topObj;
    }

    /**
     * 递归将列表生成树节点对象
     * @param  idName      	id对象名称
     * @param  parentIdName parentId对象名称
     * @param  childrenName children对象名称
     * @return List<Map<String,Object>>    返回对象
     */
    public static List<Map<String,Object>> getJsonArrForList(List<Map<String,Object>> objList,String idName,String parentIdName,String childrenName){
        Map<String,Object> obj = new HashMap<String,Object>();
        List<Map<String,Object>> topList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
        Map<String,Object> mapObj = null;
        List<Map<String,Object>> reObjList = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < objList.size(); i++) {
            obj = objList.get(i);
            obj.put("leaf", true);
            if (obj.get(parentIdName)==null||obj.get(parentIdName).equals("null")||obj.get(parentIdName).equals("")) {
                topList.add(obj);
            } else {
                tempList.add(obj);
            }
        }

        for (int i = 0; i < topList.size(); i++) {
            mapObj = topList.get(i);
            treeWork(tempList, mapObj, idName, parentIdName, childrenName);
            reObjList.add(mapObj);
        }
        return reObjList;
    }

    /**
     * MD5的32位加密
     * @param  inStr     对象
     * @return String  	  返回对象
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * [简要描述]:处理时间字符串<br/>
     * @param time 2014-11-14 17:04:58.0
     * @return 2014-11-14 17:04:58
     * @exception
     */
    public static String dealTimeStr(String time){
        // 对参数进行判断
        if (isEmpty(time)){
            return time;
        }else{
            return time.substring(0, time.length() - 2);
        }
    }
    /**
     * [简要描述]:通过","隔开的字符串获取正则表达式对象<br/>
     * @param extStr ","隔开的字符串
     * @param caseInsensitive 是否忽略大小写
     * @return
     * @exception
     */
    public static String getReg(String extStr,boolean caseInsensitive){
        String[] arr = extStr.split(",");
        String reStr = "";
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                reStr += "(.*\\"+arr[i]+")";
            }else{
                reStr += "(.*\\"+arr[i]+")|";
            }
        }
        if(caseInsensitive){
            return "^(?i)"+reStr+"$";
        }
        return "^"+reStr+"$";
    }


    /**
     * date2比date1多的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differentDays(Date startDate,Date endDate)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differentDaysByMillisecond(Date startDate,Date endDate)
    {
        if(startDate == null || endDate == null){
            return 1;
        }
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000*3600*24));
        return days;
    }

}
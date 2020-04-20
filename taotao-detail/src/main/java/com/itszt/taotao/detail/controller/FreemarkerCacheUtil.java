package com.itszt.taotao.detail.controller;

import freemarker.template.Template;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerCacheUtil {
    private FreeMarkerConfigurer configuration;
    private ThreadLocal<Map> datas =new ThreadLocal<>();   //保证每个线程使用的对象是独立的一份
                                                   //保证每个客户端的线程使用一个独立的Map
    private String cacheFTLPath;

    //设置缓存的位置
    public  void setCacheFTLPath(String cacheFTLPath){this.cacheFTLPath=cacheFTLPath;}
    //设置缓存的配置
    public void setConfiguration(FreeMarkerConfigurer configuration){
        this.configuration=configuration;
        configuration.getConfiguration().setDefaultEncoding("UTF-8");
    }

    public  void addAttribute(String key,Object value){
        Map map = datas.get();
        if (map==null) {
            map  = new HashMap();
            datas.set(map);
        }
        datas.get().put(key,value);
    }


    //返回false 说明 没有缓存目录  或者  没缓存呢           返回true说明读取了缓存文件 并返给了浏览器
    //读取缓存文件 写给浏览器                   参数二 : response,getWrite();
    public boolean processFtlCache(String ftlName, Writer out,String cacheKey) throws Exception{
        ftlName=cacheKey+ftlName;
        File dir = new File(cacheFTLPath);    //cacheFTLPath  是缓存的目录
        if (!dir.exists()) {
            dir.mkdir();
            return false;
        }
        //1.全部的模板文件
        String[] allftls=dir.list();   //列出当前文件夹的全部文件名
        boolean contains = Arrays.asList(allftls).contains(ftlName);
        //判断全部静态文件中是否包含当前需要的静态文件
        if (contains) {
            File ftl = new File(dir, ftlName);  //定位这个缓存文件
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ftl));
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuffer.append(temp);
            }
            out.write(stringBuffer.toString());
            return true;
        }
        return false;
    }

    //加载模板文件 (转储本地静态文件  返给浏览器)
    public void processFtl(String ftlName ,Writer out,String cacheKey) throws Exception{
        //参数一 : 模板文件的名字  模板二 :response.getWrite()   模板三: Item的 id 用区分不同的模板文件
        //加载模板文件
        Template t1= configuration.getConfiguration().getTemplate(ftlName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        t1.process(datas.get(),new OutputStreamWriter(byteArrayOutputStream));
        t1.setOutputEncoding("UTF-8");

        String ftlContents = byteArrayOutputStream.toString();
        //转储本地静态文件
        File dir = new File(cacheFTLPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(dir, cacheKey + ftlName)));
        bufferedWriter.write(ftlContents);
        bufferedWriter.close();

        //把动态页面数据返回给浏览器
        out.write(ftlContents);
        out.flush();

    }
}

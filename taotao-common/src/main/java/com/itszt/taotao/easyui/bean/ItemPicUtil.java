package com.itszt.taotao.easyui.bean;

import org.apache.commons.lang3.StringUtils;

public class ItemPicUtil {

    public static final String FDFS_BASE_URL="http://123.57.108.180:8888";   //访问nginx的url

    public static String genFullPath(String fdfsPath){

        boolean b = StringUtils.startsWith(fdfsPath, "/");
        if (!b) {
            return FDFS_BASE_URL+"/"+fdfsPath;    //拼接 nginxurl+图片path
        }
        return FDFS_BASE_URL+fdfsPath;

    }

    public static String genFdfsPath(String fullPath){

        return fullPath.replace(FDFS_BASE_URL,"" );

    }
}

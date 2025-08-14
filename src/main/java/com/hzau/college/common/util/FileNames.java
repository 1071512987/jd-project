package com.hzau.college.common.util;


import cn.hutool.core.lang.UUID;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/3/30 15:22
 * @Description: TODO
 */
public class FileNames {
    private static final String[] imageExtension = {".jpg", ".jpeg", ".png", ".gif"};
//    private static final String ossPath = "https://qlu-fm-search-image.oss-cn-zhangjiakou.aliyuncs.com/";

    public static String getImgName(String url) {
        String ext = "";
        for (String extItem : imageExtension) {
            if (url.contains(extItem)) {
                ext = extItem;
                break;
            }
        }

        return  UUID.fastUUID() + ext;
    }
}
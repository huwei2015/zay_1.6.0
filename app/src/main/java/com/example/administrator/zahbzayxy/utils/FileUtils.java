package com.example.administrator.zahbzayxy.utils;

public class FileUtils {

    /**
     * 判断文件类型
     * @param fileEnd 文件后缀名
     * @return 0、未知类型  1、图片 2、word 3、pdf 4、excel
     */
    public static int fileType(String fileEnd) {
        int type = 0;
        if ("jpg".equals(fileEnd) || "jpeg".equals(fileEnd) || "gif".equals(fileEnd) || "png".equals(fileEnd)
                || "bmp".equals(fileEnd) || "tiff".equals(fileEnd)) {
            type = 1;
        } else if ("docx".equals(fileEnd) || "dotx".equals(fileEnd)  || "doc".equals(fileEnd) || "dot".equals(fileEnd)
                || "pagers".equals(fileEnd)) {
            type = 2;
        } else if ("pdf".equals(fileEnd)) {
            type = 3;
        } else if ("xls".equals(fileEnd) || "xlsx".equals(fileEnd)  || "xlt".equals(fileEnd) || "xltx".equals(fileEnd)) {
            type = 4;
        }
        return type;
    }

}

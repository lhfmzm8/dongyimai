package org.example.liuhengfei.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorSource {
    private static String path_1 = "org";
    private static String path_2 = "example";
    private static String path_3 = "liuhengfei";
    private static String path = "D:\\WorkSpace\\Idea\\dongyimai-parent\\dongyimai-makecode\\src\\main\\";
    private static String srcJava = "";

    private static void setPrimaryKey(Map<String, Object> params, String primaryKey) {
        String[] keys = primaryKey.split("~");
        if (keys != null) {
            params.put("PrimaryKeyName", StringReplace.removeUpFromTwo(keys[0]));
            params.put("PrimaryKeyNameUp", StringReplace.removeUp(keys[0]));
            // 根据数据库主键类型判断生成的主键类型
            if (keys[1].startsWith("varchar")) {
                params.put("PrimaryKeyType", "String");
            }
            if (keys[1].startsWith("bigint")) {
                params.put("PrimaryKeyType", "Long");
            }
            if (keys[1].startsWith("int")) {
                params.put("PrimaryKeyType", "Integer");
            }

        }
    }

    private static Map<String, Object> setParams(String tableName, String tableComment){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Table2", StringReplace.removeUp(tableName));
        params.put("table2", StringReplace.removeUpFromTwo(tableName));
        params.put("path_1", path_1);
        params.put("path_2", path_2);
        params.put("path_3", path_3);
        params.put("package", path_1 + "." + path_2 + "." + path_3);
        params.put("comment", tableComment);
        return params;
    }

    /**
     * 生成Service接口代码
     *
     * @param tableName
     * @param tableComment
     */
    public static void makeService(String tableName, String tableComment, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        // 获取主键名称、类型
        setPrimaryKey(params, primaryKey);

        // 读取Service模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2]Service.java"), "utf-8");
            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "java\\" + path_1 + "\\" + path_2 + "\\" + path_3 + "\\service\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + "Service.java");
            try {
                // 保存源码
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("Service源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成Service实现类代码
     *
     * @param tableName
     * @param tableComment
     */
    public static void makeServiceImpl(String tableName, String tableComment, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        // 获取主键名称、类型
        setPrimaryKey(params, primaryKey);

        // 读取ServiceImpl模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2]ServiceImpl.java"), "utf-8");
            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "java\\" + path_1 + "\\" + path_2 + "\\" + path_3 + "\\impl\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + "ServiceImpl.java");
            try {
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("ServiceImpl源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成Controller接口代码
     *
     * @param tableName
     * @param tableComment
     */
    public static void makeController(String tableName, String tableComment, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        // 获取主键名称、类型
        setPrimaryKey(params, primaryKey);

        // 读取Controller模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2]Controller.java"), "utf-8");
            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "java\\" + path_1 + "\\" + path_2 + "\\" + path_3 + "\\controller\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + "Controller.java");
            try {
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("Controller源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成JsController接口代码
     *
     * @param tableName
     * @param tableComment
     */
    public static void makeJsController(String tableName, String tableComment, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        // 获取主键名称、类型
        setPrimaryKey(params, primaryKey);

        // 读取JsController模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2]Controller.js"), "utf-8");
            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "webapp\\js" + "\\controller\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + "Controller.js");
            try {
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("JsController源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成JsService接口代码
     *
     * @param tableName
     * @param tableComment
     */
    public static void makeJsService(String tableName, String tableComment, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        // 获取主键名称、类型
        setPrimaryKey(params, primaryKey);

        // 读取JsService模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2]Service.js"), "utf-8");
            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "webapp\\js" + "\\service\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + "Service.js");
            try {
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("JsService源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成Html前端页面代码
     *
     * @param tableName
     * @param tableComment
     * @param columns
     */
    public static void makeHtml(String tableName, String tableComment, List<String> columns, String primaryKey) {
        // 替换字符串中的占位符
        Map<String, Object> params = setParams(tableName, tableComment);
        params.put("key", primaryKey.split("~")[0]);
        String titleParms = "";
        String columnParms = "";
        String formParms = "";
        // 读取列集合
        for (String str : columns) {
            try {
                String titlesrc = IOUtils.toString(new FileInputStream(path + "resources\\template\\页面表格标题.txt"), "utf-8");
                String tablesrc = IOUtils.toString(new FileInputStream(path + "resources\\template\\页面表格内容.txt"), "utf-8");
                String formsrc = IOUtils.toString(new FileInputStream(path + "resources\\template\\编辑表单.nokey.txt"), "utf-8");
                if (str.indexOf("~") > 0) {
                    String[] ss = str.split("~");
                    // 根据列名更新查询条件
                    Map<String, Object> querymap = new HashMap<String, Object>();
                    if (ss.length == 3) {
                        querymap.put("columnComment", ss[2]);
                        titleParms += StringReplace.replace(querymap, titlesrc);
                    }
                    querymap.put("column2", StringReplace.removeUpFromTwo(ss[0]));
                    columnParms += StringReplace.replace(querymap, tablesrc);
                    formParms += StringReplace.replace(querymap, formsrc);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        params.put("offcntitle", titleParms);
        params.put("offcntable", columnParms);
        params.put("offcnform", formParms);

        // 读取Html模板文件
        try {
            String src = IOUtils.toString(new FileInputStream(path + "resources\\template\\[Table2].html"), "utf-8");

            // 替换模板文件里面的标记
            srcJava = StringReplace.replace(params, src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcJava != null && !"".equals(srcJava)) {
            // 创建目录保存源码
            File f1 = new File(path + "webapp" + "\\admin\\");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            // 创建源码
            File f2 = new File(f1, StringReplace.removeUp(tableName) + ".html");
            try {
                IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
                System.out.println("html源码生成OK");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}

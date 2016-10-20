package com.yukun.utils;

/**
 * Created by Arcane on 2016/10/13.
 */
public class Printer {
    // 封装的打印功能，摆脱标准打印功能需要输入的字符太长的问题
    public void out(String str) {
        System.out.println(str);
    }
    public void out(int integer) {
        System.out.println(integer);
    }

    // 扩展的打印功能，格式化的“描述：值”键值对，方便后台打印使用
    public void tout(String description, String object) {
        System.out.print(description + ":------>");
        System.out.println(object);
    }
    public void tout(String description, int integer) {
        System.out.print(description + ":------>");
        System.out.println(integer);
    }
}

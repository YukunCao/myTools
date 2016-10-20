package com.yukun.agile.utils;

import com.agile.api.*;

/**
 * Created by Arcane on 2016/10/13.
 */
public class ObjectManager {


    /*
     * 根据对象类型和ID获取对象
     */
    public static IAgileObject getObject(IAgileSession agileSession, int objectType, String number) {
        // 用来链接要获取的对象
        IAgileObject agileObject = null;

        System.out.println("正在获取对象：" + number);
        try {
            // 获取对象
            agileObject = agileSession.getObject(objectType, number);

            System.out.println("\t对象 " + number + " 获取成功！");
            return agileObject;
        } catch (APIException e) {
            System.out.println("\t获取对象 " + number + " 失败！");
            e.printStackTrace();
        }

        return null;
    }

	/*
	 * 使用编码创建对象
	 */

    /**
     * 设置列表值
     * 以字符串作为参数
     * @param dataObject
     * @param key
     * @param value
     * @throws APIException
     */
    public static void setListValue(IDataObject dataObject, Object key, String value) throws APIException {
        ICell cell = dataObject.getCell(key);	// 要设值的Cell
        IAgileList agileList = cell.getAvailableValues();    // 存放被选条目的AgileList

        // 选择相应条目
        if (value == "") { // 置空
            agileList.setSelection(new String[] {});
        } else { // 赋值
            switch (cell.getDataType()) {
                case DataTypeConstants.TYPE_SINGLELIST :    // 单列表
                    agileList.setSelection(new String[] {value});
                    break;
                case DataTypeConstants.TYPE_MULTILIST :    // 多列表
                    agileList.setSelection(value.split(";"));
                    break;
            }
        }

        // 设值
        cell.setValue(agileList);

        return;
    }

    /**
     * 设置字段值
     * @param dataObject
     * @param key
     * @param value
     * @throws APIException
     */
    public static void setFieldValue(IDataObject dataObject, Object key, String value) throws APIException {
        ICell cell = dataObject.getCell(key);    // 要设值的Cell

        // 根据字段类型设置
        switch (cell.getDataType()) {
            case DataTypeConstants.TYPE_STRING :    // 文本
                dataObject.setValue(key, value);
                break;
            case DataTypeConstants.TYPE_SINGLELIST :    // 单列表
                IAgileList signleList = cell.getAvailableValues();    // 存放被选条目的AgileList
                if (value == "") { // 置空
                    signleList.setSelection(new String[] {});
                } else { // 赋值
                    signleList.setSelection(new String[] {value});
                }
                // 设值
                cell.setValue(signleList);
                break;
            case DataTypeConstants.TYPE_MULTILIST :    // 多列表
                IAgileList multiList = cell.getAvailableValues();    // 存放被选条目的AgileList
                if (value == "") { // 置空
                    multiList.setSelection(new String[] {});
                } else { // 赋值
                    multiList.setSelection(value.split(";"));
                }
                // 设值
                cell.setValue(multiList);
                break;
        }
    }

    /**
     * 获取字段值
     * @param dataObject
     * @param key
     * @return
     * @throws APIException
     */
    public static String getFieldValue(IDataObject dataObject, Object key) throws APIException {
        String value = dataObject.getValue(key).toString();    // 列表值

        return value;
    }

    /**
     * 创建 agileObject，如果没有给编码，则试图通过其第一个自动编码来创建
     * @param agileSession
     * @param id
     * @param number
     * @return
     * @throws APIException
     */
    public static IAgileObject createObject(IAgileSession agileSession, Object id, String number) throws APIException {
        IAgileObject agileObject = null;

        if (number == null || number.equals("")) {
            String nextNumber = AutoNumberManager.getNextAutoNumberByAgileClass(agileSession, id);
            if (nextNumber != null) {
                agileObject = agileSession.createObject(id, nextNumber);
            }
        } else {
            agileObject = agileSession.createObject(id, number);
        }

        return agileObject;
    }
}

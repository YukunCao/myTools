package com.yukun.agile.utils;

import com.agile.api.*;

/**
 * Created by Arcane on 2016/10/13.
 */
public class ObjectManager {


    /*
     * 根据对象类型和ID获取对象
     */
    public IAgileObject getObject(IAgileSession agileSession, int objectType, String number) {
        // 用来链接要获取的对象
        IAgileObject object = null;

        System.out.println("正在获取对象：" + number);
        try {
            // 获取对象
            object = agileSession.getObject(objectType, number);

            System.out.println("\t对象 " + number + " 获取成功！");
            return object;
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
    public void setListValue(IDataObject dataObject, Object key, String value) throws APIException {
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

    public void setFieldValue(IDataObject dataObject, Object key, String value) throws APIException {
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

        return;
    }

    /**
     * 以String的形式获得列表字段的值
     *
     * @param dataObject
     * @param key
     * @return
     * @throws APIException
     */
    public String getListValue(IDataObject dataObject, Object key) throws APIException {
        String value = dataObject.getValue(key).toString();    // 列表值

        return value;
    }

    public String getFieldValue(IDataObject dataObject, Object key) throws APIException {
        String value = dataObject.getValue(key).toString();    // 列表值

        return value;
    }

    /**
     * 使用自动编码创建系统对象
     *
     */
    public IAgileObject createObject(IAdmin admin, IAgileSession agileSession, int objectType) {
        IAgileObject object = null;		// 用来连接要获取的对象
        String number = null;			//用来存放对象的编码

        // 实例化所需的管理器
        AutoNumberManager autoNumberManager = new AutoNumberManager();

        System.out.println("正在创建对象……");
        number = autoNumberManager.getNextAutoNumberByClassType(admin, objectType);
        try {
            object = agileSession.createObject(objectType, number);
            System.out.println("\t对象 " + number + " 创建成功！");
            return object;
        } catch (APIException e) {
            System.out.println("\t创建对象失败！");
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 使用自动编码创建自定义对象
     */
    public IAgileObject createObject(IAdmin admin, IAgileSession agileSession, String objectTypeAPI) {
        IAgileObject object = null;			// 用来连接要获取的对象
        Integer objectType = null;			// 该对象类型的标示号
        String number = null;				//用来存放对象的编码

        // 实例化所需的管理器
        AutoNumberManager autoNumberManager = new AutoNumberManager();

        System.out.println("正在创建对象……");
        try {
            // 查找所需对象类型的标示号
            IAgileClass[] classes = admin.getAgileClasses(IAdmin.CONCRETE);
            for (int i = 0; i < classes.length; i++) {
                if (classes[i].getAPIName().equals(objectTypeAPI)) {
                    objectType = (Integer)classes[i].getId();
                    break;
                }
            }

            if (objectType != null) {
                // 获取自动编码，并创建对象
                number = autoNumberManager.getNextAutoNumberByClassType(admin, objectType);
                object = agileSession.createObject(objectType, number);
                System.out.println("\t对象 " + number + " 创建成功！");
                return object;
            }
            else {
                System.out.println("/t创建对象失败：对象的类型不存在！");
            }
        } catch (APIException e) {
            System.out.println("\t创建对象失败！");
            e.printStackTrace();
        }

        return null;
    }
}

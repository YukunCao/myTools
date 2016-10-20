package com.yukun.agile.utils;

import com.agile.api.*;

/**
 * Created by Arcane on 2016/10/13.
 */
public class AutoNumberManager {

    /*
     * 获取所需类的下一个自动编码（取第一个编码方案）
     */
    public String getNextAutoNumberByClassType(IAdmin admin, int classType) {
        System.out.print("\t自动编码生成中 —— ");
        String nextAutoNumber = "";

        IAgileClass cls;
        try {
            cls = admin.getAgileClass(classType);
            IAutoNumber[] numSources = cls.getAutoNumberSources();

            nextAutoNumber = numSources[0].getNextNumber();
            System.out.println(nextAutoNumber);
            return nextAutoNumber;
        } catch (APIException e) {
            System.out.println("生成失败！");
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 根据autonumberAPI获取新的编码
     */
    public String getNextAutoNumberByAPI(IAdmin admin, String API) throws APIException {
        // 获取所求自动编码源
        INode autoNumberNode = admin.getNode(NodeConstants.NODE_AUTONUMBERS);
        IAutoNumber autoNumber = (IAutoNumber)autoNumberNode.getChildNode(API);

        // 获取下一个自动编码
        return autoNumber.getNextNumber();
    }
}

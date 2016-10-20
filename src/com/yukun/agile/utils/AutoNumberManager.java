package com.yukun.agile.utils;

import com.agile.api.*;

/**
 * Created by Arcane on 2016/10/13.
 */
public class AutoNumberManager {

    /*
     * 获取所需类的下一个自动编码
     */
    public static String getNextAutoNumberByAgileClass(IAdmin admin, int classType, String autoNumberApiName) throws APIException {
        String nextAutoNumber = null;
        IAgileClass agileClass;

        if ((autoNumberApiName != null) && (!autoNumberApiName.equals(""))) {
            agileClass = admin.getAgileClass(classType);
            IAutoNumber[] autoNumberSources = agileClass.getAutoNumberSources();

            for (IAutoNumber autoNumber : autoNumberSources) {
                if (autoNumber.getAPIName().equals(autoNumberApiName))
                    nextAutoNumber = autoNumber.getNextNumber();
            }
        } else {
            agileClass = admin.getAgileClass(classType);
            IAutoNumber[] autoNumberSources = agileClass.getAutoNumberSources();

            if (autoNumberSources.length > 0)
                nextAutoNumber = autoNumberSources[0].getNextNumber();
        }

        return nextAutoNumber;
    }

    public static String getNextAutoNumberByAgileClass(IAgileSession agileSession, Object id) throws APIException {
        String nextNumber = null;
        IAdmin admin = agileSession.getAdminInstance();
        IAgileClass agileClass = admin.getAgileClass(id);
        IAutoNumber[] autoNumberSources = agileClass.getAutoNumberSources();
        if (autoNumberSources.length > 0)
            nextNumber = autoNumberSources[0].getNextNumber();

        return nextNumber;
    }

    /**
     * 根据 apiName 获取新的编码
     * @param agileSession
     * @param apiName
     * @return
     * @throws APIException
     */
    public static String getNextAutoNumberByApiName(IAgileSession agileSession, String apiName) throws APIException {
        String resultAutoNumber = null;

        // 获取自动编码源
        IAdmin admin = agileSession.getAdminInstance();
        INode autoNumberNode = admin.getNode(NodeConstants.NODE_AUTONUMBERS);
        IAutoNumber autoNumber = (IAutoNumber) autoNumberNode.getChildNode(apiName);
        // 获取下一个自动编码
        resultAutoNumber = autoNumber.getNextNumber();

        return resultAutoNumber;
    }
}

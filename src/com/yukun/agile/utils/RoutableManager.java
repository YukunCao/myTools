package com.yukun.agile.utils;

import com.agile.api.*;
import com.yukun.utils.Printer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arcane on 2016/10/20.
 */
public class RoutableManager {
    private static Printer p = new Printer();

    /**
     * 获取可路由对象指定状态的审阅者
     * @param routable
     * @param status
     * @param reviewerType
     * @return
     * @throws APIException
     */
    public static List<IDataObject> getReviewers(IRoutable routable, IStatus status, Integer reviewerType) throws APIException {
        List<IDataObject> reviewers = new ArrayList<IDataObject>();
        ISignoffReviewer[] signoffReviewers = routable.getAllReviewers(status, reviewerType);
        for (ISignoffReviewer reviewer : signoffReviewers) {
            reviewers.add(reviewer.getReviewer());
        }

        return reviewers;
    }

    /**
     * 为变更的目标阶段设置审批人
     * @param routable
     * @param approvers
     * @param observers
     * @param acknowledgers
     * @param status
     * @throws APIException
     */
    public static void setApprovers(IRoutable routable, List<IDataObject> approvers, List<IDataObject> observers, List<IDataObject> acknowledgers, IStatus status) throws APIException {
        // 获取当前审核人
        List<IDataObject> currentApprovers = getReviewers(routable, status, WorkflowConstants.USER_APPROVER);

        if (currentApprovers.isEmpty()) {	// 当前审批人不存在
            routable.addReviewers(status, approvers, Collections.emptyList(), Collections.emptyList(), false, "");
        } else {	// 当前审批人存在
            List<IDataObject> list = new ArrayList<IDataObject>();
            list.addAll(approvers);
            // 取交集
            list.retainAll(currentApprovers);
            //获取增量审批人
            approvers.removeAll(list);
            // 获取待删审批人
            currentApprovers.removeAll(list);
            // 删除观察者
            routable.removeReviewers(status, Collections.EMPTY_LIST, approvers, approvers, "");
            // 增加审批人
            routable.addReviewers(status, approvers, observers, acknowledgers, false, "");
            // 删除多余审批人
            routable.removeReviewers(status, currentApprovers, Collections.EMPTY_LIST, Collections.EMPTY_LIST, "");
        }
    }

    /**
     * 根据状态名称获取状态
     * @param routable
     * @param statusName
     * @return
     * @throws APIException
     */
    public static IStatus getStatusByName(IRoutable routable, String statusName) throws APIException {
        IStatus resultStatus = null;
        IWorkflow workflow = routable.getWorkflow();
        IStatus[] statuses = workflow.getStates();
        for (IStatus status : statuses) {
            if (statusName.equals(status.getName())) {
                resultStatus = status;
                break;
            }
        }
        return resultStatus;
    }
}

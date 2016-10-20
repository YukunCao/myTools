package com.yukun.agile.utils;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.yukun.utils.Printer;

import java.util.HashMap;

/**
 * Created by Arcane on 2016/10/20.
 */
public class SessionManager {
    private static Printer p = new Printer();

    public static IAgileSession createSession(String url, String userName, String password) throws APIException {
        IAgileSession resultSession = null;
        AgileSessionFactory factory = null;
        HashMap<Integer, String> params = new HashMap<Integer, String>();
        params.put(AgileSessionFactory.USERNAME, userName);
        params.put(AgileSessionFactory.PASSWORD, password);

        try {
            factory = AgileSessionFactory.getInstance(url);
            resultSession = factory.createSession(params);
            p.out("\t登陆成功！");
        } catch (APIException e) {
            p.out("\t登陆服务器失败！");
            throw e;
        }

        return resultSession;
    }

    public static void closeSession(IAgileSession agileSession) {
        agileSession.close();

        p.out("与服务器断开！");
    }
}

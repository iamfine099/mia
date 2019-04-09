package com.bootdo.common.utils;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//import com.bootdo.employer.domain.EmployerDO;
//import com.bootdo.student.domain.StudentDO;
import com.bootdo.system.shiro.LoginUser;

public class ShiroUtils {
    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static LoginUser getUser() {
        Object object = getSubjct().getPrincipal();
        return (LoginUser)object;
    }
//    public static StudentDO getStudent() {
//        return getUser().getStudent();
//    }
//    public static EmployerDO getEmployer() {
//        return getUser().getEmployer();
//    }
    public static Long getUserId() {
        return getUser().getId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}

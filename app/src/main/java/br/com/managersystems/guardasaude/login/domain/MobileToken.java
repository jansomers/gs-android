package br.com.managersystems.guardasaude.login.domain;

import java.util.Date;

import br.com.managersystems.guardasaude.util.DateUtil;

/**
 * Created by Jan on 18/04/2016.
 */
public class MobileToken {

    private static BaseUser baseUser;
    private static String token;
    private static  String role;
    //dd/MM/yyyy(HH:mm:ss)
    private static Date endDate;

    public MobileToken(BaseUser baseUser, String token, Date date) {
        this.baseUser = baseUser;
        this.token = token;
        setEndDate(date);

    }

    public static BaseUser getBaseUser() {
        return baseUser;
    }

    public static String getToken() {
        return token;
    }

    public static void setEndDate(Date startDate) {
        MobileToken.endDate = DateUtil.addDays(startDate, 7);
    }

    public static Date getEndDate() {
        return endDate;
    }
}

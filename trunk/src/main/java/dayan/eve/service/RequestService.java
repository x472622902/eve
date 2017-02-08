package dayan.eve.service;

import com.alibaba.druid.util.StringUtils;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xsg on 1/14/2017.
 */
@Service
public class RequestService {

//    public Optional<Integer> getUserNumber(HttpServletRequest request) {
//        String userNumber = request.getParameter("usernumber");
//
//        Integer accountId = null;
//        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
//            accountId = Integer.valueOf(userNumber);
////        accountId = 1;
//        return Optional.ofNullable(accountId);
//    }

    public Integer getAccountId(HttpServletRequest request) {
        String userNumber = request.getParameter("usernumber");
        Integer accountId = null;
        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
            accountId = Integer.valueOf(userNumber);
        return accountId;
    }

    public Integer getUserNumber(HttpServletRequest request) throws EveException {
        String userNumber = request.getParameter("usernumber");

        Integer accountId = null;
        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
            accountId = Integer.valueOf(userNumber);
        // TODO: 1/18/2017 记得去掉 
        accountId = 1;
        if (accountId == null)
            throw new EveException(ErrorCN.Login.UN_LOGIN);
        return accountId;
    }
}

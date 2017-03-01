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

    public Integer getAccountIdValue(HttpServletRequest request) {
        String userNumber = request.getParameter("usernumber");
        Integer accountId = null;
        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
            accountId = Integer.valueOf(userNumber);
        return accountId;
    }

    public Integer getAccountId(HttpServletRequest request) throws EveException {
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

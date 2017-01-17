package dayan.eve.service;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by xsg on 1/14/2017.
 */
@Service
public class RequestService {

    public Optional<Integer> getUserNumber(HttpServletRequest request) {
        String userNumber = request.getParameter("usernumber");

        Integer accountId = null;
        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
            accountId = Integer.valueOf(userNumber);
        return Optional.ofNullable(accountId);
    }
    public Integer getAccountId(HttpServletRequest request) {
        String userNumber = request.getParameter("usernumber");
        Integer accountId = null;
        if (StringUtils.isEmpty(userNumber) && "null".equals(userNumber))
            accountId = Integer.valueOf(userNumber);
        return accountId;
    }
}

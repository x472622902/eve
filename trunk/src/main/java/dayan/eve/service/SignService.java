/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p>
 * This file is part of Dayan techology Co.ltd property.
 * <p>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.Sign;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.SignQuery;
import dayan.eve.repository.SignRepository;
import dayan.eve.web.dto.SignCheckDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static dayan.eve.model.ConstantKeys.GAOKAODATE;
import static dayan.eve.model.ConstantKeys.Sign.END_TIME;
import static dayan.eve.model.ConstantKeys.Sign.START_TIME;

@Service
@Transactional
public class SignService {

    private static final Logger LOGGER = LogManager.getLogger(SignService.class);

    private final SignRepository signRepository;

    @Autowired
    public SignService(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public SignCheckDTO check(Integer accountId) throws ParseException {
        SignCheckDTO result = new SignCheckDTO();
        result.setIsOnTime(checkTime());
        if (accountId != null) {
            Date signTime = signRepository.querySignTime(accountId);
            if (signTime != null) {
                result.setIsSigned(checkTime(signTime));
            }
        }
        return result;
    }

    /**
     * 现在时间在startTime和endTime之间则返回true
     */
    private Boolean checkTime(Date signTime) throws ParseException {
        Date now = new Date();
        Date startTime = getDate(now, START_TIME);
        Date endTime = getDate(now, END_TIME);
        return signTime.after(startTime) && signTime.before(endTime);
    }

    private Boolean checkTime() throws ParseException {
        Date now = new Date();
        Date startTime = getDate(now, START_TIME);
        Date endTime = getDate(now, END_TIME);
        return now.before(endTime) && now.after(startTime);
    }

    private Date getDate(Date date, String str) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.parse(format2.format(date) + str);
    }

    public Integer readDays() throws ParseException {
        long now = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long gaokaoDate = sdf.parse(GAOKAODATE).getTime();
        int days = (int) ((gaokaoDate - now) / (1000 * 60 * 60 * 24));
        return days < 0 ? 0 : days;
    }

    private void setRank(List<Sign> accounts) {
        int i = 1;
        for (Sign account : accounts) {
            account.setRank(i);
            i++;
        }
    }

    public void sign(SignQuery query) throws ParseException {
        if (check(query.getAccountId()).getIsSigned()) {
            Sign sign = new Sign();
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountId(query.getAccountId());
            sign.setAccountInfo(accountInfo);
            sign.setContent(query.getContent());
            signRepository.insert(sign);
            LOGGER.info("a user sign!.  {}", JSON.toJSONString(query, true));
        }
    }

    public PageResult<Sign> readAccounts(SignQuery query) throws ParseException {
        query.setLimitDate(getDate(new Date(), START_TIME));
        Integer count = signRepository.countToday();
        PageResult<Sign> result = new PageResult<>(new Pager(count, query.getPage(), query.getSize()));
        if (count > 0) {
            List<Sign> accounts = signRepository.queryByTime(query);
            setRank(accounts);
            result.setList(accounts);
        }
        return result;
    }

}

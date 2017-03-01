/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service;

import dayan.eve.config.EveProperties;
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.ClockTimer;
import dayan.eve.model.ClockTimer.Status;
import dayan.eve.model.Constants;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.ClockQuery;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.ClockRepository;
import dayan.eve.repository.CodeRepository;
import dayan.eve.web.dto.ClockRankReadDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClockService {

    private static final Logger LOGGER = LogManager.getLogger(ClockService.class);
    private EveProperties.Clock clockProperties;
    private final ClockRepository clockRepository;
    private final CodeRepository codeRepository;
    private final AccountInfoRepository accountInfoRepository;

    @Autowired
    public ClockService(CodeRepository codeRepository, ClockRepository clockRepository, AccountInfoRepository accountInfoRepository, EveProperties eveProperties) {
        this.codeRepository = codeRepository;
        this.clockRepository = clockRepository;
        this.accountInfoRepository = accountInfoRepository;
        this.clockProperties = eveProperties.getClock();
    }

    public void clockIn(ClockQuery query) {
        LOGGER.info("clock query info,{}", query);
        boolean morningChecked = checkTime(clockProperties.getInStart(), clockProperties.getInEnd());
        boolean nightChecked = checkTime(clockProperties.getOutStart(), clockProperties.getOutEnd());
        if (!morningChecked && !nightChecked) {
            throw new RuntimeException(ErrorCN.Clock.TIME_LIMIT);
        }
        query.setReadToday(true);
        List<ClockTimer> clockTimers = clockRepository.query(query);

        codeRepository.setCode(Constants.EMOJI_CODE);
        AccountInfo accountInfo = accountInfoRepository.queryOneInfo(query.getAccountId());

        if (clockTimers != null && !clockTimers.isEmpty()) {
            ClockTimer ct = clockTimers.get(0);
            if ((morningChecked && ct.getClockInTime() != null) || (nightChecked && ct.getClockOutTime() != null)) {
                throw new RuntimeException(ErrorCN.Clock.CLOCK_DONE);
            }
            Integer clockId = ct.getId();
            ClockTimer clockTimer = new ClockTimer();
            clockTimer.setClockOutContent(query.getContent());
            clockTimer.setClockOutTime(new Date());
            clockTimer.setId(clockId);
            clockTimer.setClockDaylong(true);
            clockRepository.update(clockTimer);
            // 更新用户打卡信息
            accountInfo.setClockCount(accountInfo.getClockCount() + 1);
            ClockQuery newqQuery = new ClockQuery();
            newqQuery.setAccountId(query.getAccountId());
            newqQuery.setReadYesterday(true);
            List<ClockTimer> cts = clockRepository.query(newqQuery);
            if (cts != null && !cts.isEmpty()) {
                if (cts.get(0).getClockDaylong()) {
                    accountInfo.setContinuousClockTimes(accountInfo.getContinuousClockTimes() + 1);
                } else {
                    accountInfo.setContinuousClockTimes(1);
                }
            } else {
                accountInfo.setContinuousClockTimes(1);
            }
            accountInfo.setLastClockContent(query.getContent());
            accountInfoRepository.updateInfo(accountInfo);
        } else {
            ClockTimer clockTimer = new ClockTimer();
            clockTimer.setAccountId(query.getAccountId());
            if (morningChecked) {
                clockTimer.setClockInContent(query.getContent());
                clockTimer.setClockInTime(new Date());
            } else {
                clockTimer.setClockOutContent(query.getContent());
                clockTimer.setClockOutTime(new Date());
            }

            clockRepository.insert(clockTimer);

            accountInfo.setLastClockContent(query.getContent());
            accountInfoRepository.updateInfo(accountInfo);
        }
    }


    public Status readStatus(Integer accountId) {
        Status status = new ClockTimer().new Status();
        //是否在早0点-4点之间
        boolean freeTimeChecked1 = checkTime(clockProperties.getDayStart(), clockProperties.getInStart());
        //是否在4点-8点之间
        boolean morningChecked = checkTime(clockProperties.getInStart(), clockProperties.getInEnd());
        //是否在8点-21点之间
        boolean freeTimeChecked2 = checkTime(clockProperties.getInEnd(), clockProperties.getOutStart());
//        //是否在21点-24点之间
//        boolean nightChecked = checkTime(clockProperties.getOutStart(), clockProperties.getOutEnd());
        if (freeTimeChecked1) {
            status.setIsClockAvaliable(false);
            status.setButtonContent(clockProperties.getMorningContent());
        } else if (freeTimeChecked2) {
            status.setIsClockAvaliable(false);
            status.setButtonContent(clockProperties.getEveningContent());
        } else {
            ClockQuery query = new ClockQuery();
            query.setAccountId(accountId);
            query.setReadToday(true);
            List<ClockTimer> clockTimers = clockRepository.query(query);
            if (clockTimers == null || clockTimers.isEmpty()) {
                status.setIsClockAvaliable(true);
                status.setButtonContent(clockProperties.getUnclockedContent());
            } else {
                ClockTimer ct = clockTimers.get(0);
                if (morningChecked) {
                    if (ct.getClockInTime() == null) {
                        status.setIsClockAvaliable(true);
                        status.setButtonContent(clockProperties.getUnclockedContent());
                    } else {
                        status.setIsClockAvaliable(false);
                        status.setButtonContent(clockProperties.getClockedContent());
                        status.setClockContent(ct.getClockInContent());
                    }
                } else {
                    if (ct.getClockOutTime() == null) {
                        status.setIsClockAvaliable(true);
                        status.setButtonContent(clockProperties.getUnclockedContent());
                    } else {
                        status.setIsClockAvaliable(false);
                        status.setButtonContent(clockProperties.getClockedContent());
                        status.setClockContent(ct.getClockOutContent());
                    }
                }
            }
        }
        return status;
    }

    public ClockRankReadDTO readRank(ClockQuery query) {
        Integer accountId = query.getAccountId();
        query.setAccountId(null);
        ClockRankReadDTO result = new ClockRankReadDTO();
        List<ClockTimer> list = new LinkedList<>();
        if (query.getReadTodayRank()) {
            list = clockRepository.query(query);

        } else {
            List<AccountInfo> accountInfos = clockRepository.queryAccount(query);
            for (AccountInfo accountInfo : accountInfos) {
                ClockTimer ct = new ClockTimer();
                ct.setAccountInfo(accountInfo);
                list.add(ct);
            }

        }
        if (list == null || list.isEmpty()) {
            list = Collections.emptyList();
        }
        Integer myRank;
        query.setAccountId(accountId);
        if (query.getReadTodayRank()) {
            myRank = clockRepository.queryClockRank(query);
        } else {
            myRank = clockRepository.queryAccountRank(query);
        }
        if (accountId != null) {
            AccountInfo info = accountInfoRepository.queryOneInfo(accountId);
            result.setClockCount(info.getClockCount());
            result.setContinuousClockTimes(info.getContinuousClockTimes());
        }
        result.setRank(myRank);
        result.setList(list);
        return result;
    }

    public Pager count(ClockQuery query) {
        Pager pager = new Pager();
        Integer count = clockRepository.countClock(query);
        pager.setCount(count);
        pager.setPage(query.getPage());
        pager.setSize(query.getSize());
        return pager;
    }

    /**
     * 检查是否在start至end这个时间段
     *
     * @param start 起始时间
     * @param end   结束时间
     * @return 符合时间段返回true
     */
    private Boolean checkTime(String start, String end) {
        Date now = new Date();
        Date startTime = getDate(now, start);
        Date endTime = getDate(now, end);
        assert endTime != null;
        assert startTime != null;
        return now.before(endTime) && now.after(startTime);
    }

    private Date getDate(Date date, String str) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format1.parse(format2.format(date) + " " + str);
        } catch (ParseException ex) {
            LOGGER.info(ex.getMessage());
        }
        return null;
    }

    public PageResult<ClockTimer> readClocks(ClockQuery query) {
        Integer count = 0;
        if (query.getAccountId() != null) {
            count = clockRepository.countClock(query);
        }
        PageResult<ClockTimer> pageResult = new PageResult<>(new Pager(count, query.getPage(), query.getSize()));
        if (count > 0) {
            pageResult.setList(clockRepository.queryClocks(query));
        }
        return pageResult;
    }

    public void updateContinuousCount() {
        List<Integer> accountIds = clockRepository.queryAccountIds();
        if (accountIds == null || accountIds.isEmpty()) {
            accountIds = null;
        }
        accountInfoRepository.updateContinuousClockTimes(accountIds);
    }
}

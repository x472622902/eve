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

import dayan.eve.model.Information;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.query.InformationQuery;
import dayan.eve.repository.AccountRepository;
import dayan.eve.repository.InformationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class InformationService {

    private static final Logger LOGGER = LogManager.getLogger(InformationService.class);

    @Autowired
    InformationRepository informationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EasemobService easemobService;

    public PageResult<Information> read(InformationQuery query) {
        Integer countNews = informationRepository.countNews(query);
        PageResult<Information> result = new PageResult<>(Collections.emptyList(), new Pager(countNews, query.getPage()
                , query.getSize()));
        if (countNews > 0) {
            result.setData(informationRepository.queryNews(query));
        }
        return result;
    }
    // TODO: 1/17/2017 后台推送资讯

}

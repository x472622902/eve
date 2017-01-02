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

import dayan.eve.model.account.AccountInfo;

/**
 *
 * @author xsg
 */
public interface VisitorService {

    /**
     * 创建访客（在咨询高校或者留言等行为前执行,有什么数据尽量都带上，分数，专业，毕业院校等）[原话]
     *
     * @param accountInfo
     * @param platformId
     */
    void createVisitor(AccountInfo accountInfo, Integer platformId);

    /**
     * 更新访客信息（在用户信息变更时，更新访客信息）[原话]
     *
     * @param accountInfo
     */
    void updateVisitor(AccountInfo accountInfo);

    public void createVisitor(String accountId, String schoolHashId);

}

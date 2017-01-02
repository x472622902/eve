/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dayan.easemob.entity.EasemobRequest;
import dayan.eve.model.account.Account;
import dayan.eve.model.easemob.EasemobResult;

import java.util.List;

/**
 *
 * @author leiky
 */
public interface EasemobService {

    public String token();

    public ObjectNode registUsers(Account account);

    public ObjectNode registUsers(List<Account> accounts);

    public ObjectNode sendMessage(String to, String msg);

    public ObjectNode sendMessage(String userNumber, EasemobResult result);

    public ObjectNode sendMessages(List<Integer> userNumbers, EasemobResult result);
    
    public ObjectNode sendMessages(EasemobRequest result);

}

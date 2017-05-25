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

import dayan.eve.util.ActivateEncoderV1;
import dayan.eve.model.ActivateLog;
import dayan.eve.repository.ActivateLogRepository;
import dayan.eve.web.dto.ActivateLogDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuangyd
 */
@Service
public class ActivateService {

    private static final Logger LOGGER = LogManager.getLogger();

    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    private final ActivateLogRepository activateLogRepository;

    @Autowired
    public ActivateService(ActivateLogRepository activateLogRepository) {
        this.activateLogRepository = activateLogRepository;
    }

    public String activate(ActivateLogDTO activateLogDTO) {
        LOGGER.debug("IMEI:{}", activateLogDTO.getImei());

        String deviceNumber = new ActivateEncoderV1().encrypt(activateLogDTO.getImei());

        final ActivateLog log = new ActivateLog();
        log.setDistributeId(activateLogDTO.getDistributeId());
        log.setImei(activateLogDTO.getImei());
        log.setOsType(activateLogDTO.getOsType());
        log.setOsVer(activateLogDTO.getOsVer());
        log.setProduct(activateLogDTO.getProduct());
        log.setRomType(activateLogDTO.getRomType());
        threadPool.execute(() -> activateLogRepository.insert(log));
        return deviceNumber;
    }

}

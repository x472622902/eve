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

import dayan.eve.model.Province;
import dayan.eve.model.query.ProvinceQuery;
import dayan.eve.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuangyd
 */
@Service
public class ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Province> read() {
        return provinceRepository.query(null);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Province> read(ProvinceQuery query) {
        return provinceRepository.query(query);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String readName(Integer id) {
        ProvinceQuery query = new ProvinceQuery();
        query.setId(id);
        List<Province> list = provinceRepository.query(query);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0).getName();
        }
    }

}

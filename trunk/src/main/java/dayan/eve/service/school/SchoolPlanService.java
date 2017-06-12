package dayan.eve.service.school;

import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.util.MoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xsg on 6/3/2017.
 */
@Service
public class SchoolPlanService {

    private final static Logger LOGGER = LogManager.getLogger(SchoolPlanService.class);

    private final MoUtil moUtil;

    @Autowired
    public SchoolPlanService(MoUtil moUtil) {
        this.moUtil = moUtil;
    }

    public String readPlan(String schoolHashId) {
        String resultString;
        try {
            resultString = moUtil.getSchoolPlan(schoolHashId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return resultString;
    }

    public String readSumPlan(String schoolHashId) {
        String resultString;
        try {
            resultString = moUtil.getSchoolSumPlan(schoolHashId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return resultString;
    }
}

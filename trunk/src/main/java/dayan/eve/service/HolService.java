package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.JsonResult;
import dayan.eve.model.hol.HolPersonality;
import dayan.eve.model.major.Major;
import dayan.eve.model.major.MoMajor;
import dayan.eve.model.query.HolPersonalityQuery;
import dayan.eve.model.query.HolQuery;
import dayan.eve.util.MoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xsg on 6/12/2017.
 */
@Service
public class HolService {
    private final static Logger LOGGER = LogManager.getLogger(HolService.class);
    private static final String[] personalityPercents = {"6%", "18%", "7%", "20%", "32%", "24%"};//各性格百分比

    private final MoUtil moUtil;

    @Autowired
    public HolService(MoUtil moUtil) {
        this.moUtil = moUtil;
    }

    public String readJobClass(HolQuery query) {
        String resultString;
        try {
            resultString = moUtil.getHolJobClass(query.getPersonalityCode(), query.getPage(), query.getSize());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }

        return resultString;
    }

    public String readMajorTypes(HolQuery query) {
        String resultString;
        try {
            resultString = moUtil.getHolMajorType(query.getJobClassCode(), query.getPage(), query.getSize());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }

        return resultString;
    }

    public String readMajors(HolQuery query) {
        String resultString;
        try {
            resultString = moUtil.getHolMajors(query.getMajorTypeCode(), query.getPage(), query.getSize());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        JsonResult result = JSONObject.parseObject(resultString, JsonResult.class);
        JSONObject data = (JSONObject) result.getData();
        List<MoMajor> items = JSONArray.parseArray(data.getString("majors"), MoMajor.class);
        List<Major> majors = items.stream().map(Major::new).collect(Collectors.toList());
        return JSON.toJSONString(majors, true);
    }

    private String getPercent(HolPersonalityQuery query) {
        String[] percents = {"", "", "", "", "", ""};
        percents[query.getNumOfA()] = personalityPercents[0];
        percents[query.getNumOfC()] = personalityPercents[1];
        percents[query.getNumOfE()] = personalityPercents[2];
        percents[query.getNumOfI()] = personalityPercents[3];
        percents[query.getNumOfR()] = personalityPercents[4];
        percents[query.getNumOfS()] = personalityPercents[5];
        for (int i = percents.length - 1; i >= 0; i--) {
            if (!"".equals(percents[i])) {
                return percents[i];
            }
        }
        return null;
    }

    public String readPersonality(HolPersonalityQuery query) {
        String resultString;
        try {
            resultString = moUtil.getHolPersonality(query);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        JSONObject result = JSONObject.parseObject(resultString);
        JSONArray dataArray = result.getJSONArray("data");
        HolPersonality personality = JSONObject.parseObject(dataArray.get(0).toString(), HolPersonality.class);
        personality.setSimilarity(getPercent(query));
        return JSON.toJSONString(personality, true);
    }

    public String readQuestion(Boolean isSimplified) {
        String resultString;
        try {
            resultString = moUtil.getHolQuestion(isSimplified);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }

        return resultString;
    }
}

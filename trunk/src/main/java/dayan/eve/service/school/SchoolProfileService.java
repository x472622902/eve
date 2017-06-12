package dayan.eve.service.school;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.JsonResult;
import dayan.eve.model.School;
import dayan.eve.model.SchoolTag;
import dayan.eve.util.MoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xsg on 6/2/2017.
 */
@Service
public class SchoolProfileService {
    private static final Logger LOGGER = LogManager.getLogger(SchoolProfileService.class);

    private final MoUtil moUtil;

    @Autowired
    public SchoolProfileService(MoUtil moUtil) {
        this.moUtil = moUtil;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public School readSchoolProfile(String schoolHashId) {
        LOGGER.info("read school profile,schoolId :{}", schoolHashId);
        JsonResult result;
        JsonResult schoolNamesResult;
        try {
            result = JSONObject.parseObject(moUtil.getSchoolDetail(schoolHashId), JsonResult.class);
            schoolNamesResult = JSONObject.parseObject(moUtil.getSchoolNames(schoolHashId), JsonResult.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(),ex);
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        JSONObject data = (JSONObject) result.getData();
        JSONObject namesData = (JSONObject) schoolNamesResult.getData();
        School school = coverToSchool(data);
        school.setNames(JSONArray.parseArray(namesData.getString("schoolNames"), String.class));
        return school;
    }

    private School coverToSchool(JSONObject data) {
        JSONArray tags = data.getJSONArray("tags");
        data.put("tags", null);
        School school = JSONObject.parseObject(data.toString(), School.class);
        String femaleRatio = data.getString("femaleRatio");
        String maleRatio = data.getString("maleRatio");
        JSONObject male = new JSONObject();
        male.put("sex", "男");
        male.put("ratio", maleRatio);

        JSONObject female = new JSONObject();
        female.put("sex", "女");
        female.put("ratio", femaleRatio);

        JSONArray genderRatio = new JSONArray();
        genderRatio.add(male);
        genderRatio.add(female);
        school.setGenderRaTio(genderRatio);

        List<String> imageUrls = JSONArray.parseArray(data.getJSONArray("images").toString(), String.class);
        school.setImageUrls(imageUrls);

        school.setLogoUrl(data.getString("badge"));
        List<SchoolTag> schoolTags = new LinkedList<>();
        for (Object tag : tags) {
            schoolTags.add(new SchoolTag(tag.toString()));
        }
        school.setTags(schoolTags);

        school.setStudentSource(data.getJSONArray("studentSource"));

        return school;
    }
}

package dayan.eve.service.school;

import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.util.MoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xsg on 6/2/2017.
 */
@Service
public class SchoolMajorService {
    private final MoUtil moUtil;

    @Autowired
    public SchoolMajorService(MoUtil moUtil) {
        this.moUtil = moUtil;
    }

    public String readMajors(String schoolHashId) {
        String resultString;
        try {
            resultString = moUtil.getMajorBySchoolHashId(schoolHashId);
        } catch (Exception ex) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return resultString;
    }

    public String readSchoolsByMajor(String majorHashId) {
        String resultString;
        try {
            resultString = moUtil.getSchoolByMajorHashId(majorHashId);
        } catch (Exception ex) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return resultString;
    }

    public String readMajorDetail(String majorHashId) {
        String resultString;
        try {
            resultString = moUtil.getMajorDetail(majorHashId);
        } catch (Exception ex) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return resultString;
    }
}

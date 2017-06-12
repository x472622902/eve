package dayan.eve.service.school;

import dayan.eve.repository.SchoolRepository;
import dayan.eve.web.dto.school.SchoolAdmissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xsg on 6/2/2017.
 */
@Service
public class SchoolAdmissionService {
    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolAdmissionService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SchoolAdmissionDTO readSchoolAdmission(Integer schoolId) {
        return schoolRepository.queryAdmission(schoolId);
    }
}

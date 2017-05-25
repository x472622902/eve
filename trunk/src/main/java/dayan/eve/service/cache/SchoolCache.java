package dayan.eve.service.cache;

import dayan.eve.model.query.SearchQuery;
import dayan.eve.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class SchoolCache {
    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolCache(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Cacheable(value = "allSchoolPrompt", cacheManager = "guavaCacheManager")
    public String getAllSchoolNames() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        schoolRepository.search(new SearchQuery())
                .forEach(school -> stringBuilder.append(school.getId()).append("-")
                        .append(school.getName()).append("|"));
        return stringBuilder.toString();
    }

}

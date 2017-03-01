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
package dayan.eve.util;

import dayan.eve.model.ConstantKeys;
import dayan.eve.model.School;
import dayan.eve.model.SchoolTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsg
 */
@Component
public class TagUtil {

    public Integer getTagsValueV11(List<String> tagIdList) {
        int tags = 0;
        for (String tagId : tagIdList) {
            tags += ConstantKeys.TAG_VALUES[Integer.valueOf(tagId)];
        }
        return tags;
    }

    public Integer getTagsValue(List<String> tagIdList) {
        int tags = 0;
        for (String tagId : tagIdList) {
            tags += ConstantKeys.TAG_VALUES[Integer.valueOf(tagId) - 1];
        }
        return tags;
    }

    public List<SchoolTag> getSchoolTags(Integer tagsValue) {
        List<SchoolTag> schoolTags = new ArrayList<>();
        if (tagsValue != null) {
            for (int i = 0; i < 6; i++) {
                if ((tagsValue & ConstantKeys.TAG_VALUES[i]) == ConstantKeys.TAG_VALUES[i]) {
                    SchoolTag st = new SchoolTag(ConstantKeys.SCHOOL_TAGS[i]);
                    schoolTags.add(st);
                }
            }
        }
        return schoolTags;
    }

    public void buildSchoolTags(School school) {
        if (school.getTagsValue() == null) return;
        school.setTags(getSchoolTags(school.getTagsValue()));
    }
}

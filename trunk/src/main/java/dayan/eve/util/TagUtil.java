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
package dayan.eve.util;

import dayan.eve.model.SchoolTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xsg
 */
@Component
public class TagUtil {

    static final Integer[] tagValues = {1, 2, 4, 8, 16, 32};
    static final String[] SchoolTags = {"211", "985", "研究生院", "自主招生", "国防生", "卓越计划"};

    public Integer getTagsValueV11(List<String> tagIdList) {
        int tags = 0;
        for (String tagId : tagIdList) {
            tags += tagValues[Integer.valueOf(tagId)];
        }
        return tags;
    }

    public Integer getTagsValue(List<String> tagIdList) {
        int tags = 0;
        for (String tagId : tagIdList) {
            tags += tagValues[Integer.valueOf(tagId) - 1];
        }
        return tags;
    }

    public List<SchoolTag> getSchoolTags(Integer tagsValue) {
        List<SchoolTag> schoolTags = new ArrayList<>();
        if (tagsValue != null) {
            for (int i = 0; i < 6; i++) {
                if ((tagsValue & tagValues[i]) == tagValues[i]) {
                    SchoolTag st = new SchoolTag(SchoolTags[i]);
                    schoolTags.add(st);
                }
            }
        }
        return schoolTags;
    }
}

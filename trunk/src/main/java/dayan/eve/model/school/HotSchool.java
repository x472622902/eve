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
package dayan.eve.model.school;

import dayan.eve.model.School;
import dayan.eve.model.account.FollowAccount;

import java.util.List;

/**
 *
 *
 * @author xsg
 */
public class HotSchool extends School {

    private String schoolHashId;
    private Integer recommendCount;
    private Integer followerCount;
    private List<FollowAccount> followers;

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
    }

    public Integer getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(Integer recommendCount) {
        this.recommendCount = recommendCount;
    }

    public List<FollowAccount> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowAccount> followers) {
        this.followers = followers;
    }
}

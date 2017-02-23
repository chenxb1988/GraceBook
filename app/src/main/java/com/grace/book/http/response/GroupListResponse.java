package com.grace.book.http.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb
 * 17/2/23.
 */

public class GroupListResponse extends BaseResponse {
    private List<GroupInfo> Records;

    public List<GroupInfo> getRecords() {
        return Records;
    }

    public void setRecords(List<GroupInfo> Records) {
        this.Records = Records;
    }

    public static class GroupInfo {
        private String GrouphId;
        private String GroupName;
        private String ChurchId;
        private String ChurchName;
        private String UpdateTime;

        public String getGrouphId() {
            return GrouphId;
        }

        public void setGrouphId(String GrouphId) {
            this.GrouphId = GrouphId;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getChurchId() {
            return ChurchId;
        }

        public void setChurchId(String ChurchId) {
            this.ChurchId = ChurchId;
        }

        public String getChurchName() {
            return ChurchName;
        }

        public void setChurchName(String ChurchName) {
            this.ChurchName = ChurchName;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }

    public List<String> getGroupNames() {
        List<String> list = new ArrayList<>();
        if (Records != null) {
            for (GroupInfo group : Records) {
                list.add(group.getGroupName());
            }
        }
        return list;
    }

    public String getGroupId(String name) {
        if (Records != null) {
            for (GroupInfo group : Records) {
                if (group.getGroupName().equals(name)) {
                    return group.getGrouphId();
                }
            }
        }
        return null;
    }
}

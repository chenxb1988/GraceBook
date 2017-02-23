package com.grace.book.http.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb
 * 17/2/22.
 */

public class FellowListResponse extends BaseResponse {

    private List<FellowInfo> Records;

    public List<FellowInfo> getRecords() {
        return Records;
    }

    public void setRecords(List<FellowInfo> Records) {
        this.Records = Records;
    }

    public static class FellowInfo {
        private String ChurchName;
        private String UpdateTime;
        private String ParentId;
        private String Wechat;
        private String ChurchId;

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

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getWechat() {
            return Wechat;
        }

        public void setWechat(String Wechat) {
            this.Wechat = Wechat;
        }

        public String getChurchId() {
            return ChurchId;
        }

        public void setChurchId(String ChurchId) {
            this.ChurchId = ChurchId;
        }
    }

    public List<String> getFellowNames() {
        List<String> list = new ArrayList<>();
        if (Records != null) {
            for (FellowInfo fellow : Records) {
                list.add(fellow.getChurchName());
            }
        }
        return list;
    }

    public String getFellowId(String name) {
        if (Records != null) {
            for (FellowInfo fellow : Records) {
                if (fellow.getChurchName().equals(name)) {
                    return fellow.getChurchId();
                }
            }
        }
        return null;
    }
}

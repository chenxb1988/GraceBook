package com.grace.book.http.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb
 * 17/2/22.
 */

public class FellowListResponse extends BaseResponse {
    private int TotalRecords;
    private int TotalPage;
    private List<FellowInfo> Records;

    public int getTotalRecords() {
        return TotalRecords;
    }

    public void setTotalRecords(int TotalRecords) {
        this.TotalRecords = TotalRecords;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public List<FellowInfo> getRecords() {
        return Records;
    }

    public void setRecords(List<FellowInfo> Records) {
        this.Records = Records;
    }

    public static class FellowInfo {
        private String ChurchId;
        private String ChurchName;
        private String ParentId;
        private String Wechat;
        private String UpdateTime;

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

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
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
}

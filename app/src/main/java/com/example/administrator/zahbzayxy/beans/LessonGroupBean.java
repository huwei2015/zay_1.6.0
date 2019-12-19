package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/3/15 0015.
 */
public class LessonGroupBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"List":[{"cateId":1,"catName":"主要负责人（初训）","childList":[{"zCatName":"金属非金属矿山主要负责人（初训）","zCatId":8},{"zCatName":"危化主要负责人（初训）","zCatId":10},{"zCatName":"冶金企业主要负责人（初训）","zCatId":11},{"zCatName":"烟花爆竹生产经营单位主要负责人（初训）","zCatId":12},{"zCatName":"非高危行业主要负责人（初训）","zCatId":13}]},{"cateId":2,"catName":"安全生产管理人员（初训）","childList":[{"zCatName":"金属非金属矿山安全管理人员（初训）","zCatId":16},{"zCatName":"危化安全管理人员（初训）","zCatId":17},{"zCatName":"冶金企业安全管理人员（初训）","zCatId":59},{"zCatName":"烟花爆竹生产经营单位安全管理人员（初训）","zCatId":92},{"zCatName":"非高危行业安全管理人员（初训）","zCatId":93}]},{"cateId":3,"catName":"特种作业人员（初训）","childList":[{"zCatName":"通用工种特种作业人员（初训）","zCatId":18},{"zCatName":"冶金有色行业特种作业人员（初训）","zCatId":19},{"zCatName":"金属非金属矿山特种作业人员（初训）","zCatId":20},{"zCatName":"煤矿特种作业人员（初训）","zCatId":94},{"zCatName":"司钻作业人员（初训）","zCatId":95},{"zCatName":"危化特种作业人员（初训）","zCatId":96},{"zCatName":"烟花爆竹企业特种作业（初训）","zCatId":118}]},{"cateId":97,"catName":"主要负责人（复训）","childList":[{"zCatName":"金属非金属矿山主要负责人（复训）","zCatId":99},{"zCatName":"危化主要负责人（复训）","zCatId":100},{"zCatName":"冶金企业主要负责人（复训）","zCatId":101},{"zCatName":"烟花爆竹生产经营单位主要负责人（复训）","zCatId":102},{"zCatName":"非高危行业主要负责人（复训）","zCatId":103}]},{"cateId":104,"catName":"安全生产管理人员（复训）","childList":[{"zCatName":"金属非金属矿山安全管理人员（复训）","zCatId":106},{"zCatName":"危化安全管理人员（复训）","zCatId":107},{"zCatName":"冶金企业安全管理人员（复训）","zCatId":108},{"zCatName":"烟火爆竹生产经营单位安全管理人员（复训）","zCatId":109},{"zCatName":"非高危行业安全管理人员（复训）","zCatId":110}]},{"cateId":111,"catName":"特种作业人员（复训）","childList":[{"zCatName":"通用工种特种作业人员（复训）","zCatId":112},{"zCatName":"冶金有色行业特种作业人员（复训）","zCatId":113},{"zCatName":"金属非金属矿山特种作业人员（复训）","zCatId":114},{"zCatName":"煤矿特种作业人员（复训）","zCatId":115},{"zCatName":"司钻作业人员（复训）","zCatId":116},{"zCatName":"危化特种作业人员（复训）","zCatId":117},{"zCatName":"烟花爆竹企业特种作业（复训）","zCatId":119}]}]}
     */

    private String code;
    private Object errMsg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cateId : 1
         * catName : 主要负责人（初训）
         * childList : [{"zCatName":"金属非金属矿山主要负责人（初训）","zCatId":8},{"zCatName":"危化主要负责人（初训）","zCatId":10},{"zCatName":"冶金企业主要负责人（初训）","zCatId":11},{"zCatName":"烟花爆竹生产经营单位主要负责人（初训）","zCatId":12},{"zCatName":"非高危行业主要负责人（初训）","zCatId":13}]
         */

        private java.util.List<ListBean> List;

        public java.util.List<ListBean> getList() {
            return List;
        }

        public void setList(java.util.List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            private int cateId;
            private String catName;
            /**
             * zCatName : 金属非金属矿山主要负责人（初训）
             * zCatId : 8
             */

            private java.util.List<ChildListBean> childList;

            public int getCateId() {
                return cateId;
            }

            public void setCateId(int cateId) {
                this.cateId = cateId;
            }

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }

            public java.util.List<ChildListBean> getChildList() {
                return childList;
            }

            public void setChildList(java.util.List<ChildListBean> childList) {
                this.childList = childList;
            }

            public static class ChildListBean {
                private String zCatName;
                private int zCatId;

                public String getZCatName() {
                    return zCatName;
                }

                public void setZCatName(String zCatName) {
                    this.zCatName = zCatName;
                }

                public int getZCatId() {
                    return zCatId;
                }

                public void setZCatId(int zCatId) {
                    this.zCatId = zCatId;
                }
            }
        }
    }
}

package com.maoyongxin.myapplication.ui.fgt.connection.act.bean;

import java.util.List;

public class CompanyBean {

    /**
     * code : 10000
     * charge : true
     * msg : 查询成功
     * result : {"error_code":0,"reason":"ok","result":{"baseInfo":{"id":"574","name":"成都星空三维科技有限公司","legalPersonName":"余晟睿","regLocation":"成都市郫都区德源镇（菁蓉镇创业公社）大禹东路66号8楼附-1项目工作室","email":"yusr@3d.top","phoneNumber":"18398155771","businessScope":"3d打印","websiteList":"http://www.3dgogo.com","base":null,"orgNumber":null,"regStatus":null,"estiblishTime":"","fromTime":null,"toTime":null,"city":null,"regNumber":null,"percentileScore":"0","correctCompanyId":"0","companyOrgType":"","regCapital":null,"regInstitute":null,"creditCode":null,"companyId":null,"approvedTime":null,"jsonData":null,"property4":null,"property3":null,"property5":null,"communityId":"0","communityName":null},"investorList":[],"investList":[],"staffList":[],"branchList":[]}}
     */

    private int code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * error_code : 0
         * reason : ok
         * result : {"baseInfo":{"id":"574","name":"成都星空三维科技有限公司","legalPersonName":"余晟睿","regLocation":"成都市郫都区德源镇（菁蓉镇创业公社）大禹东路66号8楼附-1项目工作室","email":"yusr@3d.top","phoneNumber":"18398155771","businessScope":"3d打印","websiteList":"http://www.3dgogo.com","base":null,"orgNumber":null,"regStatus":null,"estiblishTime":"","fromTime":null,"toTime":null,"city":null,"regNumber":null,"percentileScore":"0","correctCompanyId":"0","companyOrgType":"","regCapital":null,"regInstitute":null,"creditCode":null,"companyId":null,"approvedTime":null,"jsonData":null,"property4":null,"property3":null,"property5":null,"communityId":"0","communityName":null},"investorList":[],"investList":[],"staffList":[],"branchList":[]}
         */

        private int error_code;
        private String reason;
        private ResultBean result;

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * baseInfo : {"id":"574","name":"成都星空三维科技有限公司","legalPersonName":"余晟睿","regLocation":"成都市郫都区德源镇（菁蓉镇创业公社）大禹东路66号8楼附-1项目工作室","email":"yusr@3d.top","phoneNumber":"18398155771","businessScope":"3d打印","websiteList":"http://www.3dgogo.com","base":null,"orgNumber":null,"regStatus":null,"estiblishTime":"","fromTime":null,"toTime":null,"city":null,"regNumber":null,"percentileScore":"0","correctCompanyId":"0","companyOrgType":"","regCapital":null,"regInstitute":null,"creditCode":null,"companyId":null,"approvedTime":null,"jsonData":null,"property4":null,"property3":null,"property5":null,"communityId":"0","communityName":null}
             * investorList : []
             * investList : []
             * staffList : []
             * branchList : []
             */

            private BaseInfoBean baseInfo;
            private List<?> investorList;
            private List<?> investList;
            private List<?> staffList;
            private List<?> branchList;

            public BaseInfoBean getBaseInfo() {
                return baseInfo;
            }

            public void setBaseInfo(BaseInfoBean baseInfo) {
                this.baseInfo = baseInfo;
            }

            public List<?> getInvestorList() {
                return investorList;
            }

            public void setInvestorList(List<?> investorList) {
                this.investorList = investorList;
            }

            public List<?> getInvestList() {
                return investList;
            }

            public void setInvestList(List<?> investList) {
                this.investList = investList;
            }

            public List<?> getStaffList() {
                return staffList;
            }

            public void setStaffList(List<?> staffList) {
                this.staffList = staffList;
            }

            public List<?> getBranchList() {
                return branchList;
            }

            public void setBranchList(List<?> branchList) {
                this.branchList = branchList;
            }

            public static class BaseInfoBean {
                /**
                 * id : 574
                 * name : 成都星空三维科技有限公司
                 * legalPersonName : 余晟睿
                 * regLocation : 成都市郫都区德源镇（菁蓉镇创业公社）大禹东路66号8楼附-1项目工作室
                 * email : yusr@3d.top
                 * phoneNumber : 18398155771
                 * businessScope : 3d打印
                 * websiteList : http://www.3dgogo.com
                 * base : null
                 * orgNumber : null
                 * regStatus : null
                 * estiblishTime :
                 * fromTime : null
                 * toTime : null
                 * city : null
                 * regNumber : null
                 * percentileScore : 0
                 * correctCompanyId : 0
                 * companyOrgType :
                 * regCapital : null
                 * regInstitute : null
                 * creditCode : null
                 * companyId : null
                 * approvedTime : null
                 * jsonData : null
                 * property4 : null
                 * property3 : null
                 * property5 : null
                 * communityId : 0
                 * communityName : null
                 */

                private String id;
                private String name;
                private String legalPersonName;
                private String regLocation;
                private String email;
                private String phoneNumber;
                private String businessScope;
                private String websiteList;
                private Object base;
                private Object orgNumber;
                private Object regStatus;
                private String estiblishTime;
                private Object fromTime;
                private Object toTime;
                private Object city;
                private Object regNumber;
                private String percentileScore;
                private String correctCompanyId;
                private String companyOrgType;
                private Object regCapital;
                private Object regInstitute;
                private Object creditCode;
                private Object companyId;
                private Object approvedTime;
                private Object jsonData;
                private Object property4;
                private Object property3;
                private Object property5;
                private String communityId;
                private Object communityName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLegalPersonName() {
                    return legalPersonName;
                }

                public void setLegalPersonName(String legalPersonName) {
                    this.legalPersonName = legalPersonName;
                }

                public String getRegLocation() {
                    return regLocation;
                }

                public void setRegLocation(String regLocation) {
                    this.regLocation = regLocation;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPhoneNumber() {
                    if (phoneNumber==null)
                    {
                        return  "";
                    }
                    return phoneNumber;
                }

                public void setPhoneNumber(String phoneNumber) {
                    this.phoneNumber = phoneNumber;
                }

                public String getBusinessScope() {
                    return businessScope;
                }

                public void setBusinessScope(String businessScope) {
                    this.businessScope = businessScope;
                }

                public String getWebsiteList() {
                    return websiteList;
                }

                public void setWebsiteList(String websiteList) {
                    this.websiteList = websiteList;
                }

                public Object getBase() {
                    return base;
                }

                public void setBase(Object base) {
                    this.base = base;
                }

                public Object getOrgNumber() {
                    return orgNumber;
                }

                public void setOrgNumber(Object orgNumber) {
                    this.orgNumber = orgNumber;
                }

                public Object getRegStatus() {
                    return regStatus;
                }

                public void setRegStatus(Object regStatus) {
                    this.regStatus = regStatus;
                }

                public String getEstiblishTime() {
                    return estiblishTime;
                }

                public void setEstiblishTime(String estiblishTime) {
                    this.estiblishTime = estiblishTime;
                }

                public Object getFromTime() {
                    return fromTime;
                }

                public void setFromTime(Object fromTime) {
                    this.fromTime = fromTime;
                }

                public Object getToTime() {
                    return toTime;
                }

                public void setToTime(Object toTime) {
                    this.toTime = toTime;
                }

                public Object getCity() {
                    return city;
                }

                public void setCity(Object city) {
                    this.city = city;
                }

                public Object getRegNumber() {
                    return regNumber;
                }

                public void setRegNumber(Object regNumber) {
                    this.regNumber = regNumber;
                }

                public String getPercentileScore() {
                    return percentileScore;
                }

                public void setPercentileScore(String percentileScore) {
                    this.percentileScore = percentileScore;
                }

                public String getCorrectCompanyId() {
                    return correctCompanyId;
                }

                public void setCorrectCompanyId(String correctCompanyId) {
                    this.correctCompanyId = correctCompanyId;
                }

                public String getCompanyOrgType() {
                    return companyOrgType;
                }

                public void setCompanyOrgType(String companyOrgType) {
                    this.companyOrgType = companyOrgType;
                }

                public Object getRegCapital() {
                    return regCapital;
                }

                public void setRegCapital(Object regCapital) {
                    this.regCapital = regCapital;
                }

                public Object getRegInstitute() {
                    return regInstitute;
                }

                public void setRegInstitute(Object regInstitute) {
                    this.regInstitute = regInstitute;
                }

                public Object getCreditCode() {
                    return creditCode;
                }

                public void setCreditCode(Object creditCode) {
                    this.creditCode = creditCode;
                }

                public Object getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(Object companyId) {
                    this.companyId = companyId;
                }

                public Object getApprovedTime() {
                    return approvedTime;
                }

                public void setApprovedTime(Object approvedTime) {
                    this.approvedTime = approvedTime;
                }

                public Object getJsonData() {
                    return jsonData;
                }

                public void setJsonData(Object jsonData) {
                    this.jsonData = jsonData;
                }

                public Object getProperty4() {
                    return property4;
                }

                public void setProperty4(Object property4) {
                    this.property4 = property4;
                }

                public Object getProperty3() {
                    return property3;
                }

                public void setProperty3(Object property3) {
                    this.property3 = property3;
                }

                public Object getProperty5() {
                    return property5;
                }

                public void setProperty5(Object property5) {
                    this.property5 = property5;
                }

                public String getCommunityId() {
                    return communityId;
                }

                public void setCommunityId(String communityId) {
                    this.communityId = communityId;
                }

                public Object getCommunityName() {
                    return communityName;
                }

                public void setCommunityName(Object communityName) {
                    this.communityName = communityName;
                }
            }
        }
    }
}

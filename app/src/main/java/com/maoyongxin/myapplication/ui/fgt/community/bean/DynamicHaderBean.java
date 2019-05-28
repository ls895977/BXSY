package com.maoyongxin.myapplication.ui.fgt.community.bean;

import java.io.Serializable;
import java.util.List;

public class DynamicHaderBean implements Serializable {

/*
      code : 200
      info : {"hotChatgroup":[{"groupId":"1","groupName":"创业锦囊",
     "groupHostId":"10069","groupNote":"前辈的每个字，都是一个故事",
      "groupClassifyId":"6","groupHeaderId":"0","memberNum":"1",
     "integralNum":"269","image":"uploads/photos/order_tracking_photos/20180921/36caf27585396de6177ef24832c5f898.jpg"}
      ,{"groupId":"2","groupName":"企业交流","groupHostId":"10069","groupNote":"创业时的那些事儿","groupClassifyId":"1","groupHeaderId":"0",
     "memberNum":"1","integralNum":"12","image":"uploads/photos/order_tracking_photos/20180926/5977e4ba85c113b2af773aa7e05fb05e.jpg"}
      ,{"groupId":"3","groupName":"资源整合","groupHostId":"10069","groupNote":"企业资源，有分享才能有价值","groupClassifyId":"1",
      "groupHeaderId":"0","memberNum":"1","integralNum":"2","image":"uploads/photos/order_tracking_photos/20180926/3851d85aee8a216cec153080258af34a.jpg"},
      {"groupId":"4","groupName":"12把","groupHostId":"10070","groupNote":"11","groupClassifyId":"2","groupHeaderId":"0","memberNum":"1","integralNum":"0",
      "image":"uploads/photos/order_tracking_photos/20181128/c2618170ce68536edd70d7483db60974.jpg"}],
      "hotGambitList":[{"id":"37","uid":"10071","group_id":"1",
     "title":"关于创业锦囊话题","content":"MeOAgeWIhuS6q+WIm+S4mueahOe7j+mqjOe7meWkp+WutuW4ruWKqeaWsOeahOWIm+S4muiAhemB\nv+W8gOWIm+S4mumZt+mYsQoy44CB5YiG5Lqr5Lmf5Y+v5Lul6K6w5b2V5LiL6Ieq5bex5Yib5Lia\n54q25oCB77yM55WZ5LiL576O5aW955qE5Zue5b+GCjPjgIHlpJrlpJrlj5HluIPplKblm4rlj6/k\nu6Xmj5DljYfkvIHkuJrmm53lhYnnjoflk6bwn5iE8J+YhPCfmIQ=\n","img":"","post_num":"14","praise_num":"1","tread_num":"0","create_time":"2018-12-05 21:18:34","update_time":"2018-12-05 21:18:34","lately_time":"1551012661","is_praise_tread":"1","is_top":"1"},{"id":"35","uid":"10071","group_id":"1","title":"话题发布规范","content":"6K+d6aKY5ZyI5piv5aSn5a6255WF5omA5qyy6KiA55qE5YWs5YWx5Zy65ZCI5aaC5pyJ5Lul5LiL\n6KGM5Li65bCG6KKr5a6e6KGM5bCB5Y+35aSE55CG77yM6LCi6LCi5aSn5a626YWN5ZCI44CCCgox\n44CB5Y+R5biD5raJ6buE44CB5pq05Yqb44CB54W95Yqo5rCR5peP5oOF57uq55qE6K+d6aKYCjLj\ngIHmlYXmhI/lj5HluIPmr4HosKTjgIHmsaHolJHku5bkurrlhazlj7jlj4rlkZjlt6XnmoTor50z\n44CB5Y+R5biD5pWF5oSP6L6x6aqC5LuW5Lq6KOWwpOWFtuW9vOS/oSnnmoTor53popgKNOOAgeWP\nkeW4g+WPkeWKqOOAgeWIhuijguWbveWutuetieiogOiuuuivnemimArwn5iE8J+YhPCfmIQ=\n","img":"","post_num":"2","praise_num":"0","tread_num":"0","create_time":"2018-12-05 16:27:17","update_time":"2018-12-05 16:27:17","lately_time":"1551017681","is_praise_tread":"1","is_top":"1"},{"id":"30","uid":"10084","group_id":"1","title":"今天又收到一批商标局新发的证书，扫描存到后立即送给小主们","content":"5LuK5aSp5Y+I5pS25Yiw5LiA5om55ZWG5qCH5bGA5paw5Y+R55qE6K+B5Lmm77yM5omr5o+P5a2Y\n5Yiw5ZCO56uL5Y2z6YCB57uZ5bCP5Li75Lus8J+klw==\n","img":"uploads/photos/order_tracking_photos/20181022/9af8d32e0013f28c9a96c4822d7ae6a9.jpg","post_num":"58","praise_num":"32","tread_num":"0","create_time":"2018-10-22 15:21:30","update_time":"2018-10-22 15:21:30","lately_time":"1545567685","is_praise_tread":"1","is_top":"0"},{"id":"38","uid":"10071","group_id":"1","title":"刚刚开始创业，公司可以直接接手别人的","content":"5Zug5Li65b6I5aSa5Lq65LiN5YGa5LqG77yM5oiW6ICF5YWs5Y+45aSq5aSa5YWN6LS56L2s6K6p\n77yM5Y+v5Lul55u05o6l5o6l5omL44CC55yB5LiL5rOo5YaM44CB5byA5oi344CB5Yi756ug55qE\n6ZKx5b2T54S26KaB562+5aW95Y2P6K6u5LmL5YmN55qE5YC65Yqh5ZKM6LSj5Lu75LiN6LSf6LSj\n77yM5ZCM5pe26YKj6L655Li65LqG55yB5LiL5rOo6ZSA55qE6LS555So5Lmf5b6I5LmQ5oSP6L2s\n6K6p55qE77yM57uP5bi46YGH5Yiw\n","img":"uploads/photos/order_tracking_photos/20181206/6900b91b3499023954e3bcccfe2ca7f7.jpg","post_num":"34","praise_num":"7","tread_num":"0","create_time":"2018-12-06 01:44:59","update_time":"2018-12-06 01:44:59","lately_time":"1550940583","is_praise_tread":"1","is_top":"0"},{"id":"39","uid":"10070","group_id":"1","title":"初创公司不要请财务，找个代理记账公司省力很多，而且金钱成本只有原来十分之一","content":"6K+35Liq5Lq65LiA5Liq5pyIMzAwMOWQp+W5s+Wdh+i/meS4quagt+WtkO+8jOi/mOacieS/nemZ\nqeetie+8jOaJvuS4qumdoOiwseeahOiusOi0puWFrOWPuOS4gOS4quaciDMwMOecn+eahOmdnuW4\nuOWIkueul++8jOS7luS7rOS8muW4ruS9oOino+WGs+WQhOenjemXrumimO+8jOWkquS9jueahOWw\nseS4jeimgeaJvuS6hu+8jOS4jemdoOiwseOAgg==\n","img":"uploads/photos/order_tracking_photos/20181206/7410d5056032aa8e9685b9467d9955a7.jpg","post_num":"15","praise_num":"14","tread_num":"0","create_time":"2018-12-06 01:53:36","update_time":"2018-12-06 01:53:36","lately_time":"1545455636","is_praise_tread":"1","is_top":"0"}]}
     */

    private int code;
    private InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable {
        private List<HotChatgroupBean> hotChatgroup;
        private List<HotGambitListBean> hotGambitList;

        public List<HotChatgroupBean> getHotChatgroup() {
            return hotChatgroup;
        }

        public void setHotChatgroup(List<HotChatgroupBean> hotChatgroup) {
            this.hotChatgroup = hotChatgroup;
        }

        public List<HotGambitListBean> getHotGambitList() {
            return hotGambitList;
        }

        public void setHotGambitList(List<HotGambitListBean> hotGambitList) {
            this.hotGambitList = hotGambitList;
        }

        public static class HotChatgroupBean implements Serializable {
            /**
             * groupId : 1
             * groupName : 创业锦囊
             * groupHostId : 10069
             * groupNote : 前辈的每个字，都是一个故事
             * groupClassifyId : 6
             * groupHeaderId : 0
             * memberNum : 1
             * integralNum : 269
             * image : uploads/photos/order_tracking_photos/20180921/36caf27585396de6177ef24832c5f898.jpg
             */

            private String groupId;
            private String groupName;
            private String groupHostId;
            private String groupNote;
            private String groupClassifyId;
            private String groupHeaderId;
            private String memberNum;
            private String integralNum;
            private String image;

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getGroupHostId() {
                return groupHostId;
            }

            public void setGroupHostId(String groupHostId) {
                this.groupHostId = groupHostId;
            }

            public String getGroupNote() {
                return groupNote;
            }

            public void setGroupNote(String groupNote) {
                this.groupNote = groupNote;
            }

            public String getGroupClassifyId() {
                return groupClassifyId;
            }

            public void setGroupClassifyId(String groupClassifyId) {
                this.groupClassifyId = groupClassifyId;
            }

            public String getGroupHeaderId() {
                return groupHeaderId;
            }

            public void setGroupHeaderId(String groupHeaderId) {
                this.groupHeaderId = groupHeaderId;
            }

            public String getMemberNum() {
                return memberNum;
            }

            public void setMemberNum(String memberNum) {
                this.memberNum = memberNum;
            }

            public String getIntegralNum() {
                return integralNum;
            }

            public void setIntegralNum(String integralNum) {
                this.integralNum = integralNum;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class HotGambitListBean implements Serializable {
            /**
             * id : 37
             * uid : 10071
             * group_id : 1
             * title : 关于创业锦囊话题
             * content : MeOAgeWIhuS6q+WIm+S4mueahOe7j+mqjOe7meWkp+WutuW4ruWKqeaWsOeahOWIm+S4muiAhemB
             v+W8gOWIm+S4mumZt+mYsQoy44CB5YiG5Lqr5Lmf5Y+v5Lul6K6w5b2V5LiL6Ieq5bex5Yib5Lia
             54q25oCB77yM55WZ5LiL576O5aW955qE5Zue5b+GCjPjgIHlpJrlpJrlj5HluIPplKblm4rlj6/k
             u6Xmj5DljYfkvIHkuJrmm53lhYnnjoflk6bwn5iE8J+YhPCfmIQ=

             * img :
             * post_num : 14
             * praise_num : 1
             * tread_num : 0
             * create_time : 2018-12-05 21:18:34
             * update_time : 2018-12-05 21:18:34
             * lately_time : 1551012661
             * is_praise_tread : 1
             * is_top : 1
             */

            private String id;
            private String uid;
            private String group_id;
            private String title;
            private String content;
            private String img;
            private String post_num;
            private String praise_num;
            private String tread_num;
            private String create_time;
            private String update_time;
            private String lately_time;
            private String is_praise_tread;
            private String is_top;
            private String groupName;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPost_num() {
                return post_num;
            }

            public void setPost_num(String post_num) {
                this.post_num = post_num;
            }

            public String getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(String praise_num) {
                this.praise_num = praise_num;
            }

            public String getTread_num() {
                return tread_num;
            }

            public void setTread_num(String tread_num) {
                this.tread_num = tread_num;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getLately_time() {
                return lately_time;
            }

            public void setLately_time(String lately_time) {
                this.lately_time = lately_time;
            }

            public String getIs_praise_tread() {
                return is_praise_tread;
            }

            public void setIs_praise_tread(String is_praise_tread) {
                this.is_praise_tread = is_praise_tread;
            }

            public String getIs_top() {
                return is_top;
            }

            public void setIs_top(String is_top) {
                this.is_top = is_top;
            }


            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

        }
    }
}

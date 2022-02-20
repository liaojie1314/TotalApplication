package com.example.totalapplication.domain;

import java.io.Serializable;
import java.util.List;

public class VideoBean implements Serializable {

    private int count;
    private int total;
    private String nextPageUrl;
    private boolean adExist;
    private long date;
    private long nextPublishTime;
    private Object dialog;
    private Object topIssue;
    private int refreshCount;
    private int lastStartId;
    private List<ItemListBean> itemList;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isAdExist() {
        return adExist;
    }

    public void setAdExist(boolean adExist) {
        this.adExist = adExist;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public Object getDialog() {
        return dialog;
    }

    public void setDialog(Object dialog) {
        this.dialog = dialog;
    }

    public Object getTopIssue() {
        return topIssue;
    }

    public void setTopIssue(Object topIssue) {
        this.topIssue = topIssue;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }

    public int getLastStartId() {
        return lastStartId;
    }

    public void setLastStartId(int lastStartId) {
        this.lastStartId = lastStartId;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public class ItemListBean implements Serializable {

        private String type;
        private DataBean data;
        private Object trackingData;
        private Object tag;
        private int id;
        private int adIndex;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public Object getTrackingData() {
            return trackingData;
        }

        public void setTrackingData(Object trackingData) {
            this.trackingData = trackingData;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAdIndex() {
            return adIndex;
        }

        public void setAdIndex(int adIndex) {
            this.adIndex = adIndex;
        }

        public class DataBean implements Serializable {
            /**
             * dataType : VideoBeanForClient
             * id : 305236
             * title : GoPro 炫技滑雪，有时「重力」不作为
             * description : 片中的主角 Marcus Kleveland 是一名挪威职业滑雪运动员，擅长斜坡式和大型空中赛事。他是有史以来第一个在比赛中完成 1800 度软木塞的人。他还赢得了 2016 年 11 月的米兰世界杯。2018 年，他在冬季 X 运动会中获得 2 枚奖牌 ：Slopstyle 金牌和 Big air 第 2 名。在冬季 X 运动会之后，他在 2018 年伯顿美国公开赛 Slopestyle 中获得了第三名。在 X Games 2021 的 Big Air 赛事中，他也获得了金牌。
             * library : DAILY
             * tags : [{"id":4,"name":"运动健身","actionUrl":"eyepetizer://tag/4/?title=%E8%BF%90%E5%8A%A8%E5%81%A5%E8%BA%AB","adTrack":null,"desc":"冲浪、滑板、健身、跑酷，我过着停不下来的生活","bgPicture":"http://img.kaiyanapp.com/d9163ebc9c8ffcdccee2d7855b441d17.png?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/60ddce3880b4600474a329f772d10695.png?imageMogr2/quality/60/format/jpg","tagRecType":"IMPORTANT","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":150,"name":"青春","actionUrl":"eyepetizer://tag/150/?title=%E9%9D%92%E6%98%A5","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/34d33f611dbfe38e4d00cd4ab43212e7.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/58e3e0ec1e583a6debcc0f80d16c86fe.jpeg?imageMogr2/quality/100","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":280,"name":"滑雪","actionUrl":"eyepetizer://tag/280/?title=%E6%BB%91%E9%9B%AA","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/f13fc3b3dad6ebaa0480108461843e0b.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/f13fc3b3dad6ebaa0480108461843e0b.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":885,"name":"运动系意味","actionUrl":"eyepetizer://tag/885/?title=%E8%BF%90%E5%8A%A8%E7%B3%BB%E6%84%8F%E5%91%B3","adTrack":null,"desc":"如果想用一个风格来描述我的话，我希望是「运动系」","bgPicture":"http://img.kaiyanapp.com/5cd17bb6e12dfe709990c6db73b999c8.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/5cd17bb6e12dfe709990c6db73b999c8.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":1022,"name":"运动","actionUrl":"eyepetizer://tag/1022/?title=%E8%BF%90%E5%8A%A8","adTrack":null,"desc":"冲浪、滑板、健身、跑酷，我过着停不下来的生活","bgPicture":"http://img.kaiyanapp.com/cb87116785473e4064687036b53b5af3.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/481837cc0b1c52a3a87d6156d52cfdfa.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":681,"name":"极限运动","actionUrl":"eyepetizer://tag/681/?title=%E6%9E%81%E9%99%90%E8%BF%90%E5%8A%A8","adTrack":null,"desc":"","bgPicture":"http://img.kaiyanapp.com/3afd31213decc034438288f8c00b4fb7.gif","headerImage":"http://img.kaiyanapp.com/3afd31213decc034438288f8c00b4fb7.gif","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0},{"id":316,"name":"花式运动","actionUrl":"eyepetizer://tag/316/?title=%E8%8A%B1%E5%BC%8F%E8%BF%90%E5%8A%A8","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/de35dbc62365e90cca7560b9e3d51360.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/de35dbc62365e90cca7560b9e3d51360.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"haveReward":false,"ifNewest":false,"newestEndTime":null,"communityIndex":0}]
             * consumption : {"collectionCount":412,"shareCount":180,"replyCount":7,"realCollectionCount":128}
             * resourceType : video
             * slogan : null
             * provider : {"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"}
             * category : 运动
             * author : {"id":134,"icon":"http://img.kaiyanapp.com/fef7cdcd26a44f0a30902838320d6b52.jpeg","name":"GoPro","description":"用第一人称视角记录刺激的运动和温暖的生活。","link":"","latestReleaseTime":1644886812000,"videoNum":657,"adTrack":null,"follow":{"itemType":"author","itemId":134,"followed":false},"shield":{"itemType":"author","itemId":134,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true,"recSort":0,"expert":false}
             * cover : {"feed":"http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageMogr2/quality/60/format/jpg","detail":"http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageMogr2/quality/60/format/jpg","blurred":"http://img.kaiyanapp.com/973c0758ea732477dcf2afbe5fc59a8e.jpeg?imageMogr2/quality/60/format/jpg","sharing":null,"homepage":"http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim"}
             * playUrl : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=305236&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=11111
             * thumbPlayUrl : null
             * duration : 83
             * webUrl : {"raw":"http://www.eyepetizer.net/detail.html?vid=305236","forWeibo":"http://www.eyepetizer.net/detail.html?vid=305236"}
             * releaseTime : 1644886812000
             * playInfo : []
             * campaign : null
             * waterMarks : null
             * ad : false
             * adTrack : null
             * type : NORMAL
             * titlePgc : 运动 滑雪
             * descriptionPgc :
             * remark : null
             * ifLimitVideo : false
             * searchWeight : 0
             * brandWebsiteInfo : null
             * videoPosterBean : null
             * idx : 0
             * shareAdTrack : null
             * favoriteAdTrack : null
             * webAdTrack : null
             * date : 1644886800000
             * promotion : null
             * label : null
             * labelList : []
             * descriptionEditor : 片中的主角 Marcus Kleveland 是一名挪威职业滑雪运动员，擅长斜坡式和大型空中赛事。他是有史以来第一个在比赛中完成 1800 度软木塞的人。他还赢得了 2016 年 11 月的米兰世界杯。2018 年，他在冬季 X 运动会中获得 2 枚奖牌 ：Slopstyle 金牌和 Big air 第 2 名。在冬季 X 运动会之后，他在 2018 年伯顿美国公开赛 Slopestyle 中获得了第三名。在 X Games 2021 的 Big Air 赛事中，他也获得了金牌。
             * collected : false
             * reallyCollected : false
             * played : false
             * subtitles : []
             * lastViewTime : null
             * playlists : null
             * src : null
             * recallSource : null
             * recall_source : null
             */

            private String dataType;
            private int id;
            private String title;
            private String description;
            private String library;
            private ConsumptionBean consumption;
            private String resourceType;
            private Object slogan;
            private ProviderBean provider;
            private String category;
            private AuthorBean author;
            private CoverBean cover;
            private String playUrl;
            private Object thumbPlayUrl;
            private int duration;
            private WebUrlBean webUrl;
            private long releaseTime;
            private Object campaign;
            private Object waterMarks;
            private boolean ad;
            private Object adTrack;
            private String type;
            private String titlePgc;
            private String descriptionPgc;
            private Object remark;
            private boolean ifLimitVideo;
            private int searchWeight;
            private Object brandWebsiteInfo;
            private Object videoPosterBean;
            private int idx;
            private Object shareAdTrack;
            private Object favoriteAdTrack;
            private Object webAdTrack;
            private long date;
            private Object promotion;
            private Object label;
            private String descriptionEditor;
            private boolean collected;
            private boolean reallyCollected;
            private boolean played;
            private Object lastViewTime;
            private Object playlists;
            private Object src;
            private Object recallSource;
            private Object recall_source;
            private List<TagsBean> tags;
            private List<?> playInfo;
            private List<?> labelList;
            private List<?> subtitles;

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLibrary() {
                return library;
            }

            public void setLibrary(String library) {
                this.library = library;
            }

            public ConsumptionBean getConsumption() {
                return consumption;
            }

            public void setConsumption(ConsumptionBean consumption) {
                this.consumption = consumption;
            }

            public String getResourceType() {
                return resourceType;
            }

            public void setResourceType(String resourceType) {
                this.resourceType = resourceType;
            }

            public Object getSlogan() {
                return slogan;
            }

            public void setSlogan(Object slogan) {
                this.slogan = slogan;
            }

            public ProviderBean getProvider() {
                return provider;
            }

            public void setProvider(ProviderBean provider) {
                this.provider = provider;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public AuthorBean getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBean author) {
                this.author = author;
            }

            public CoverBean getCover() {
                return cover;
            }

            public void setCover(CoverBean cover) {
                this.cover = cover;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public Object getThumbPlayUrl() {
                return thumbPlayUrl;
            }

            public void setThumbPlayUrl(Object thumbPlayUrl) {
                this.thumbPlayUrl = thumbPlayUrl;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public WebUrlBean getWebUrl() {
                return webUrl;
            }

            public void setWebUrl(WebUrlBean webUrl) {
                this.webUrl = webUrl;
            }

            public long getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(long releaseTime) {
                this.releaseTime = releaseTime;
            }

            public Object getCampaign() {
                return campaign;
            }

            public void setCampaign(Object campaign) {
                this.campaign = campaign;
            }

            public Object getWaterMarks() {
                return waterMarks;
            }

            public void setWaterMarks(Object waterMarks) {
                this.waterMarks = waterMarks;
            }

            public boolean isAd() {
                return ad;
            }

            public void setAd(boolean ad) {
                this.ad = ad;
            }

            public Object getAdTrack() {
                return adTrack;
            }

            public void setAdTrack(Object adTrack) {
                this.adTrack = adTrack;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitlePgc() {
                return titlePgc;
            }

            public void setTitlePgc(String titlePgc) {
                this.titlePgc = titlePgc;
            }

            public String getDescriptionPgc() {
                return descriptionPgc;
            }

            public void setDescriptionPgc(String descriptionPgc) {
                this.descriptionPgc = descriptionPgc;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public boolean isIfLimitVideo() {
                return ifLimitVideo;
            }

            public void setIfLimitVideo(boolean ifLimitVideo) {
                this.ifLimitVideo = ifLimitVideo;
            }

            public int getSearchWeight() {
                return searchWeight;
            }

            public void setSearchWeight(int searchWeight) {
                this.searchWeight = searchWeight;
            }

            public Object getBrandWebsiteInfo() {
                return brandWebsiteInfo;
            }

            public void setBrandWebsiteInfo(Object brandWebsiteInfo) {
                this.brandWebsiteInfo = brandWebsiteInfo;
            }

            public Object getVideoPosterBean() {
                return videoPosterBean;
            }

            public void setVideoPosterBean(Object videoPosterBean) {
                this.videoPosterBean = videoPosterBean;
            }

            public int getIdx() {
                return idx;
            }

            public void setIdx(int idx) {
                this.idx = idx;
            }

            public Object getShareAdTrack() {
                return shareAdTrack;
            }

            public void setShareAdTrack(Object shareAdTrack) {
                this.shareAdTrack = shareAdTrack;
            }

            public Object getFavoriteAdTrack() {
                return favoriteAdTrack;
            }

            public void setFavoriteAdTrack(Object favoriteAdTrack) {
                this.favoriteAdTrack = favoriteAdTrack;
            }

            public Object getWebAdTrack() {
                return webAdTrack;
            }

            public void setWebAdTrack(Object webAdTrack) {
                this.webAdTrack = webAdTrack;
            }

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public Object getPromotion() {
                return promotion;
            }

            public void setPromotion(Object promotion) {
                this.promotion = promotion;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public String getDescriptionEditor() {
                return descriptionEditor;
            }

            public void setDescriptionEditor(String descriptionEditor) {
                this.descriptionEditor = descriptionEditor;
            }

            public boolean isCollected() {
                return collected;
            }

            public void setCollected(boolean collected) {
                this.collected = collected;
            }

            public boolean isReallyCollected() {
                return reallyCollected;
            }

            public void setReallyCollected(boolean reallyCollected) {
                this.reallyCollected = reallyCollected;
            }

            public boolean isPlayed() {
                return played;
            }

            public void setPlayed(boolean played) {
                this.played = played;
            }

            public Object getLastViewTime() {
                return lastViewTime;
            }

            public void setLastViewTime(Object lastViewTime) {
                this.lastViewTime = lastViewTime;
            }

            public Object getPlaylists() {
                return playlists;
            }

            public void setPlaylists(Object playlists) {
                this.playlists = playlists;
            }

            public Object getSrc() {
                return src;
            }

            public void setSrc(Object src) {
                this.src = src;
            }

            public Object getRecallSource() {
                return recallSource;
            }

            public void setRecallSource(Object recallSource) {
                this.recallSource = recallSource;
            }

            public Object getRecall_source() {
                return recall_source;
            }

            public void setRecall_source(Object recall_source) {
                this.recall_source = recall_source;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public List<?> getPlayInfo() {
                return playInfo;
            }

            public void setPlayInfo(List<?> playInfo) {
                this.playInfo = playInfo;
            }

            public List<?> getLabelList() {
                return labelList;
            }

            public void setLabelList(List<?> labelList) {
                this.labelList = labelList;
            }

            public List<?> getSubtitles() {
                return subtitles;
            }

            public void setSubtitles(List<?> subtitles) {
                this.subtitles = subtitles;
            }

            public class ConsumptionBean implements Serializable {
                /**
                 * collectionCount : 412
                 * shareCount : 180
                 * replyCount : 7
                 * realCollectionCount : 128
                 */

                private int collectionCount;
                private int shareCount;
                private int replyCount;
                private int realCollectionCount;

                public int getCollectionCount() {
                    return collectionCount;
                }

                public void setCollectionCount(int collectionCount) {
                    this.collectionCount = collectionCount;
                }

                public int getShareCount() {
                    return shareCount;
                }

                public void setShareCount(int shareCount) {
                    this.shareCount = shareCount;
                }

                public int getReplyCount() {
                    return replyCount;
                }

                public void setReplyCount(int replyCount) {
                    this.replyCount = replyCount;
                }

                public int getRealCollectionCount() {
                    return realCollectionCount;
                }

                public void setRealCollectionCount(int realCollectionCount) {
                    this.realCollectionCount = realCollectionCount;
                }
            }

            public class ProviderBean implements Serializable {
                /**
                 * name : YouTube
                 * alias : youtube
                 * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                 */

                private String name;
                private String alias;
                private String icon;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }

            public class AuthorBean implements Serializable {
                /**
                 * id : 134
                 * icon : http://img.kaiyanapp.com/fef7cdcd26a44f0a30902838320d6b52.jpeg
                 * name : GoPro
                 * description : 用第一人称视角记录刺激的运动和温暖的生活。
                 * link :
                 * latestReleaseTime : 1644886812000
                 * videoNum : 657
                 * adTrack : null
                 * follow : {"itemType":"author","itemId":134,"followed":false}
                 * shield : {"itemType":"author","itemId":134,"shielded":false}
                 * approvedNotReadyVideoCount : 0
                 * ifPgc : true
                 * recSort : 0
                 * expert : false
                 */

                private int id;
                private String icon;
                private String name;
                private String description;
                private String link;
                private long latestReleaseTime;
                private int videoNum;
                private Object adTrack;
                private FollowBean follow;
                private ShieldBean shield;
                private int approvedNotReadyVideoCount;
                private boolean ifPgc;
                private int recSort;
                private boolean expert;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public long getLatestReleaseTime() {
                    return latestReleaseTime;
                }

                public void setLatestReleaseTime(long latestReleaseTime) {
                    this.latestReleaseTime = latestReleaseTime;
                }

                public int getVideoNum() {
                    return videoNum;
                }

                public void setVideoNum(int videoNum) {
                    this.videoNum = videoNum;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }

                public FollowBean getFollow() {
                    return follow;
                }

                public void setFollow(FollowBean follow) {
                    this.follow = follow;
                }

                public ShieldBean getShield() {
                    return shield;
                }

                public void setShield(ShieldBean shield) {
                    this.shield = shield;
                }

                public int getApprovedNotReadyVideoCount() {
                    return approvedNotReadyVideoCount;
                }

                public void setApprovedNotReadyVideoCount(int approvedNotReadyVideoCount) {
                    this.approvedNotReadyVideoCount = approvedNotReadyVideoCount;
                }

                public boolean isIfPgc() {
                    return ifPgc;
                }

                public void setIfPgc(boolean ifPgc) {
                    this.ifPgc = ifPgc;
                }

                public int getRecSort() {
                    return recSort;
                }

                public void setRecSort(int recSort) {
                    this.recSort = recSort;
                }

                public boolean isExpert() {
                    return expert;
                }

                public void setExpert(boolean expert) {
                    this.expert = expert;
                }

                public class FollowBean implements Serializable {
                    /**
                     * itemType : author
                     * itemId : 134
                     * followed : false
                     */

                    private String itemType;
                    private int itemId;
                    private boolean followed;

                    public String getItemType() {
                        return itemType;
                    }

                    public void setItemType(String itemType) {
                        this.itemType = itemType;
                    }

                    public int getItemId() {
                        return itemId;
                    }

                    public void setItemId(int itemId) {
                        this.itemId = itemId;
                    }

                    public boolean isFollowed() {
                        return followed;
                    }

                    public void setFollowed(boolean followed) {
                        this.followed = followed;
                    }
                }

                public class ShieldBean implements Serializable {
                    /**
                     * itemType : author
                     * itemId : 134
                     * shielded : false
                     */

                    private String itemType;
                    private int itemId;
                    private boolean shielded;

                    public String getItemType() {
                        return itemType;
                    }

                    public void setItemType(String itemType) {
                        this.itemType = itemType;
                    }

                    public int getItemId() {
                        return itemId;
                    }

                    public void setItemId(int itemId) {
                        this.itemId = itemId;
                    }

                    public boolean isShielded() {
                        return shielded;
                    }

                    public void setShielded(boolean shielded) {
                        this.shielded = shielded;
                    }
                }
            }

            public class CoverBean implements Serializable {
                /**
                 * feed : http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageMogr2/quality/60/format/jpg
                 * detail : http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageMogr2/quality/60/format/jpg
                 * blurred : http://img.kaiyanapp.com/973c0758ea732477dcf2afbe5fc59a8e.jpeg?imageMogr2/quality/60/format/jpg
                 * sharing : null
                 * homepage : http://img.kaiyanapp.com/3144d29501f8e9a834fdfe8e0d24f405.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                 */

                private String feed;
                private String detail;
                private String blurred;
                private Object sharing;
                private String homepage;

                public String getFeed() {
                    return feed;
                }

                public void setFeed(String feed) {
                    this.feed = feed;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getBlurred() {
                    return blurred;
                }

                public void setBlurred(String blurred) {
                    this.blurred = blurred;
                }

                public Object getSharing() {
                    return sharing;
                }

                public void setSharing(Object sharing) {
                    this.sharing = sharing;
                }

                public String getHomepage() {
                    return homepage;
                }

                public void setHomepage(String homepage) {
                    this.homepage = homepage;
                }
            }

            public class WebUrlBean implements Serializable {
                /**
                 * raw : http://www.eyepetizer.net/detail.html?vid=305236
                 * forWeibo : http://www.eyepetizer.net/detail.html?vid=305236
                 */

                private String raw;
                private String forWeibo;

                public String getRaw() {
                    return raw;
                }

                public void setRaw(String raw) {
                    this.raw = raw;
                }

                public String getForWeibo() {
                    return forWeibo;
                }

                public void setForWeibo(String forWeibo) {
                    this.forWeibo = forWeibo;
                }
            }

            public class TagsBean implements Serializable {
                /**
                 * id : 4
                 * name : 运动健身
                 * actionUrl : eyepetizer://tag/4/?title=%E8%BF%90%E5%8A%A8%E5%81%A5%E8%BA%AB
                 * adTrack : null
                 * desc : 冲浪、滑板、健身、跑酷，我过着停不下来的生活
                 * bgPicture : http://img.kaiyanapp.com/d9163ebc9c8ffcdccee2d7855b441d17.png?imageMogr2/quality/60/format/jpg
                 * headerImage : http://img.kaiyanapp.com/60ddce3880b4600474a329f772d10695.png?imageMogr2/quality/60/format/jpg
                 * tagRecType : IMPORTANT
                 * childTagList : null
                 * childTagIdList : null
                 * haveReward : false
                 * ifNewest : false
                 * newestEndTime : null
                 * communityIndex : 0
                 */

                private int id;
                private String name;
                private String actionUrl;
                private Object adTrack;
                private String desc;
                private String bgPicture;
                private String headerImage;
                private String tagRecType;
                private Object childTagList;
                private Object childTagIdList;
                private boolean haveReward;
                private boolean ifNewest;
                private Object newestEndTime;
                private int communityIndex;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getActionUrl() {
                    return actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getBgPicture() {
                    return bgPicture;
                }

                public void setBgPicture(String bgPicture) {
                    this.bgPicture = bgPicture;
                }

                public String getHeaderImage() {
                    return headerImage;
                }

                public void setHeaderImage(String headerImage) {
                    this.headerImage = headerImage;
                }

                public String getTagRecType() {
                    return tagRecType;
                }

                public void setTagRecType(String tagRecType) {
                    this.tagRecType = tagRecType;
                }

                public Object getChildTagList() {
                    return childTagList;
                }

                public void setChildTagList(Object childTagList) {
                    this.childTagList = childTagList;
                }

                public Object getChildTagIdList() {
                    return childTagIdList;
                }

                public void setChildTagIdList(Object childTagIdList) {
                    this.childTagIdList = childTagIdList;
                }

                public boolean isHaveReward() {
                    return haveReward;
                }

                public void setHaveReward(boolean haveReward) {
                    this.haveReward = haveReward;
                }

                public boolean isIfNewest() {
                    return ifNewest;
                }

                public void setIfNewest(boolean ifNewest) {
                    this.ifNewest = ifNewest;
                }

                public Object getNewestEndTime() {
                    return newestEndTime;
                }

                public void setNewestEndTime(Object newestEndTime) {
                    this.newestEndTime = newestEndTime;
                }

                public int getCommunityIndex() {
                    return communityIndex;
                }

                public void setCommunityIndex(int communityIndex) {
                    this.communityIndex = communityIndex;
                }
            }
        }
    }
}

package com.example.totalapplication.domain;

import java.util.List;

public class UserDetail {
    private int level;
    private int listenSongs;
    private UserPointBean userPoint;
    private boolean mobileSign;
    private boolean pcSign;
    private ProfileBean profile;
    private boolean peopleCanSeeMyPlayRecord;
    private List<BindingsBean> bindings;
    private boolean adValid;
    private int code;
    private long createTime;
    private int createDays;
    private ProfileVillageInfoBean profileVillageInfo;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getListenSongs() {
        return listenSongs;
    }

    public void setListenSongs(int listenSongs) {
        this.listenSongs = listenSongs;
    }

    public UserPointBean getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(UserPointBean userPoint) {
        this.userPoint = userPoint;
    }

    public boolean isMobileSign() {
        return mobileSign;
    }

    public void setMobileSign(boolean mobileSign) {
        this.mobileSign = mobileSign;
    }

    public boolean isPcSign() {
        return pcSign;
    }

    public void setPcSign(boolean pcSign) {
        this.pcSign = pcSign;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public boolean isPeopleCanSeeMyPlayRecord() {
        return peopleCanSeeMyPlayRecord;
    }

    public void setPeopleCanSeeMyPlayRecord(boolean peopleCanSeeMyPlayRecord) {
        this.peopleCanSeeMyPlayRecord = peopleCanSeeMyPlayRecord;
    }

    public List<BindingsBean> getBindings() {
        return bindings;
    }

    public void setBindings(List<BindingsBean> bindings) {
        this.bindings = bindings;
    }

    public boolean isAdValid() {
        return adValid;
    }

    public void setAdValid(boolean adValid) {
        this.adValid = adValid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCreateDays() {
        return createDays;
    }

    public void setCreateDays(int createDays) {
        this.createDays = createDays;
    }

    public ProfileVillageInfoBean getProfileVillageInfo() {
        return profileVillageInfo;
    }

    public void setProfileVillageInfo(ProfileVillageInfoBean profileVillageInfo) {
        this.profileVillageInfo = profileVillageInfo;
    }

    public static class UserPointBean {
        private long userId;
        private int balance;
        private long updateTime;
        private int version;
        private int status;
        private int blockBalance;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBlockBalance() {
            return blockBalance;
        }

        public void setBlockBalance(int blockBalance) {
            this.blockBalance = blockBalance;
        }
    }
    public static class ProfileBean {
        private PrivacyItemUnlimitBean privacyItemUnlimit;
        private Object avatarDetail;
        private String avatarUrl;
        private boolean defaultAvatar;
        private long backgroundImgId;
        private String backgroundUrl;
        private long avatarImgId;
        private long birthday;
        private int gender;
        private String nickname;
        private boolean mutual;
        private boolean followed;
        private Object remarkName;
        private int authStatus;
        private String detailDescription;
        private ExpertsBean experts;
        private Object expertTags;
        private int djStatus;
        private int accountStatus;
        private int province;
        private int city;
        private int vipType;
        private int userType;
        private long createTime;
        private String backgroundImgIdStr;
        private String avatarImgIdStr;
        private String description;
        private long userId;
        private String signature;
        private int authority;
        private int followeds;
        private int follows;
        private boolean blacklist;
        private int eventCount;
        private int allSubscribedCount;
        private int playlistBeSubscribedCount;
        private String avatarImgId_str;
        private Object followTime;
        private boolean followMe;
        private List<?> artistIdentity;
        private int cCount;
        private boolean inBlacklist;
        private int sDJPCount;
        private int playlistCount;
        private int sCount;
        private int newFollows;

        public PrivacyItemUnlimitBean getPrivacyItemUnlimit() {
            return privacyItemUnlimit;
        }

        public void setPrivacyItemUnlimit(PrivacyItemUnlimitBean privacyItemUnlimit) {
            this.privacyItemUnlimit = privacyItemUnlimit;
        }

        public Object getAvatarDetail() {
            return avatarDetail;
        }

        public void setAvatarDetail(Object avatarDetail) {
            this.avatarDetail = avatarDetail;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public boolean isDefaultAvatar() {
            return defaultAvatar;
        }

        public void setDefaultAvatar(boolean defaultAvatar) {
            this.defaultAvatar = defaultAvatar;
        }

        public long getBackgroundImgId() {
            return backgroundImgId;
        }

        public void setBackgroundImgId(long backgroundImgId) {
            this.backgroundImgId = backgroundImgId;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public long getAvatarImgId() {
            return avatarImgId;
        }

        public void setAvatarImgId(long avatarImgId) {
            this.avatarImgId = avatarImgId;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isMutual() {
            return mutual;
        }

        public void setMutual(boolean mutual) {
            this.mutual = mutual;
        }

        public boolean isFollowed() {
            return followed;
        }

        public void setFollowed(boolean followed) {
            this.followed = followed;
        }

        public Object getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(Object remarkName) {
            this.remarkName = remarkName;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public String getDetailDescription() {
            return detailDescription;
        }

        public void setDetailDescription(String detailDescription) {
            this.detailDescription = detailDescription;
        }

        public ExpertsBean getExperts() {
            return experts;
        }

        public void setExperts(ExpertsBean experts) {
            this.experts = experts;
        }

        public Object getExpertTags() {
            return expertTags;
        }

        public void setExpertTags(Object expertTags) {
            this.expertTags = expertTags;
        }

        public int getDjStatus() {
            return djStatus;
        }

        public void setDjStatus(int djStatus) {
            this.djStatus = djStatus;
        }

        public int getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(int accountStatus) {
            this.accountStatus = accountStatus;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getBackgroundImgIdStr() {
            return backgroundImgIdStr;
        }

        public void setBackgroundImgIdStr(String backgroundImgIdStr) {
            this.backgroundImgIdStr = backgroundImgIdStr;
        }

        public String getAvatarImgIdStr() {
            return avatarImgIdStr;
        }

        public void setAvatarImgIdStr(String avatarImgIdStr) {
            this.avatarImgIdStr = avatarImgIdStr;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public int getFolloweds() {
            return followeds;
        }

        public void setFolloweds(int followeds) {
            this.followeds = followeds;
        }

        public int getFollows() {
            return follows;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public boolean isBlacklist() {
            return blacklist;
        }

        public void setBlacklist(boolean blacklist) {
            this.blacklist = blacklist;
        }

        public int getEventCount() {
            return eventCount;
        }

        public void setEventCount(int eventCount) {
            this.eventCount = eventCount;
        }

        public int getAllSubscribedCount() {
            return allSubscribedCount;
        }

        public void setAllSubscribedCount(int allSubscribedCount) {
            this.allSubscribedCount = allSubscribedCount;
        }

        public int getPlaylistBeSubscribedCount() {
            return playlistBeSubscribedCount;
        }

        public void setPlaylistBeSubscribedCount(int playlistBeSubscribedCount) {
            this.playlistBeSubscribedCount = playlistBeSubscribedCount;
        }

        public String getAvatarImgId_str() {
            return avatarImgId_str;
        }

        public void setAvatarImgId_str(String avatarImgId_str) {
            this.avatarImgId_str = avatarImgId_str;
        }

        public Object getFollowTime() {
            return followTime;
        }

        public void setFollowTime(Object followTime) {
            this.followTime = followTime;
        }

        public boolean isFollowMe() {
            return followMe;
        }

        public void setFollowMe(boolean followMe) {
            this.followMe = followMe;
        }

        public List<?> getArtistIdentity() {
            return artistIdentity;
        }

        public void setArtistIdentity(List<?> artistIdentity) {
            this.artistIdentity = artistIdentity;
        }

        public int getcCount() {
            return cCount;
        }

        public void setcCount(int cCount) {
            this.cCount = cCount;
        }

        public boolean isInBlacklist() {
            return inBlacklist;
        }

        public void setInBlacklist(boolean inBlacklist) {
            this.inBlacklist = inBlacklist;
        }

        public int getsDJPCount() {
            return sDJPCount;
        }

        public void setsDJPCount(int sDJPCount) {
            this.sDJPCount = sDJPCount;
        }

        public int getPlaylistCount() {
            return playlistCount;
        }

        public void setPlaylistCount(int playlistCount) {
            this.playlistCount = playlistCount;
        }

        public int getsCount() {
            return sCount;
        }

        public void setsCount(int sCount) {
            this.sCount = sCount;
        }

        public int getNewFollows() {
            return newFollows;
        }

        public void setNewFollows(int newFollows) {
            this.newFollows = newFollows;
        }

        public static class PrivacyItemUnlimitBean {
            private boolean area;
            private boolean college;
            private boolean age;
            private boolean villageAge;

            public boolean isArea() {
                return area;
            }

            public void setArea(boolean area) {
                this.area = area;
            }

            public boolean isCollege() {
                return college;
            }

            public void setCollege(boolean college) {
                this.college = college;
            }

            public boolean isAge() {
                return age;
            }

            public void setAge(boolean age) {
                this.age = age;
            }

            public boolean isVillageAge() {
                return villageAge;
            }

            public void setVillageAge(boolean villageAge) {
                this.villageAge = villageAge;
            }
        }

        public static class ExpertsBean {
        }
    }

    public static class ProfileVillageInfoBean {
        private String title;
        private Object imageUrl;
        private String targetUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Object imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }
    }

    public static class BindingsBean {
        private int expiresIn;
        private int refreshTime;
        private long bindingTime;
        private Object tokenJsonStr;
        private boolean expired;
        private String url;
        private long userId;
        private long id;
        private int type;

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public int getRefreshTime() {
            return refreshTime;
        }

        public void setRefreshTime(int refreshTime) {
            this.refreshTime = refreshTime;
        }

        public long getBindingTime() {
            return bindingTime;
        }

        public void setBindingTime(long bindingTime) {
            this.bindingTime = bindingTime;
        }

        public Object getTokenJsonStr() {
            return tokenJsonStr;
        }

        public void setTokenJsonStr(Object tokenJsonStr) {
            this.tokenJsonStr = tokenJsonStr;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}

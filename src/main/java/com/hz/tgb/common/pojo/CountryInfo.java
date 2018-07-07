package com.hz.tgb.common.pojo;

 /**
 * 国家信息
 * Created by hezhao on 2018/7/3 15:36.
 */
public class CountryInfo {
    /** 英文名称 */
    private String englishName;
    /** 中文名称 */
    private String chineseName;
    /** 国际域名缩写 */
    private String shortName;
    /** 电话代码 */
    private String phoneCode;
    /** 时差 */
    private String timeDifference;

    public CountryInfo() {
    }

    public CountryInfo(String englishName, String chineseName, String shortName, String phoneCode, String timeDifference) {
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.shortName = shortName;
        this.phoneCode = phoneCode;
        this.timeDifference = timeDifference;
    }

    @Override
    public String toString() {
        return "CountryInfo{" +
                "englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", timeDifference=" + timeDifference +
                '}';
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
}
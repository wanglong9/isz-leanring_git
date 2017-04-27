package com.e7code.common.api.bean;

/**
 * Created by ssr on 2017/3/21.
 */
public enum MatchType {
    /** 等于 */
    EQ,
    /** 不等于 */
    NQ,
    /** 小于 */
    LT,
    /** 小于等于 */
    LTE,
    /** 大于 */
    GT,
    /** 大于等于 */
    GTE,
    /** in */
    In,
    /** not in */
    NotIn,
    /** like %value% */
    Like,
    /** like value% */
    LeftLike,
    /** like %value */
    RightLike,
    /** is null */
    IsNull,
    /** is not null */
    IsNotNull;

    public static MatchType getByIgnoreCaseName(String name) {
        if(name == null || name.trim().isEmpty()) {
            return MatchType.EQ;
        }

        for (MatchType matchType : MatchType.values()) {
            if(matchType.name().equalsIgnoreCase(name)) {
                return matchType;
            }
        }
        return null;
    }
}

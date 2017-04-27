package com.e7code.common.api.bean;

/**
 * Created by ssr on 2017/3/14.
 */
public class MatchItem {
    private String property;
    private Object value;
    private MatchType matchType;

    public static MatchItem instance(String property, Object value, MatchType matchType) {
        return new MatchItem(property, value, matchType);
    }
    public MatchItem(String property, Object value, MatchType matchType) {
        this.property = property;
        this.value = value;
        this.matchType = matchType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    @Override
    public String toString() {
        return "MatchItem{" +
                "property='" + property + '\'' +
                ", value=" + value +
                ", matchType=" + matchType +
                '}';
    }
}

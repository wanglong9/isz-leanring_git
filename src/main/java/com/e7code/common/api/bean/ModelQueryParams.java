package com.e7code.common.api.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssr on 2017/3/22.
 */
public class ModelQueryParams implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int pageNum = 1; 		        // 当前页码；
    protected int pageSize = 15;	        // 每页条数；
    protected String sortName;	            // 排序列，多个以逗号分隔；
    protected String sortOrder;	            // 排序方向，多个以逗号分隔；
    protected List<MatchItem> matchItems = new ArrayList<>();

    /***
     *  添加查询条件（等于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams add(String property, Object value) {
        return add(property, value, MatchType.EQ);
    }

    /***
     *  添加查询条件
     * @param property    Model属性名
     * @param value         属性值
     * @param matchType     匹配类型
     * @return this
     */
    public ModelQueryParams add(String property, Object value, MatchType matchType) {
        matchItems.add(MatchItem.instance(property, value, matchType));
        return this;
    }

    /***
     *  添加查询条件（等于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams equal(String property, Object value) {
        return add(property, value, MatchType.EQ);
    }

    /***
     *  添加查询条件（不等于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams notEqual(String property, Object value) {
        return add(property, value, MatchType.NQ);
    }

    /***
     *  添加查询条件（小于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams lessThan(String property, Object value) {
        return add(property, value, MatchType.LT);
    }

    /***
     *  添加查询条件（小于等于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams lessThanEqual(String property, Object value) {
        return add(property, value, MatchType.LTE);
    }

    /***
     *  添加查询条件（大于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams greaterThan(String property, Object value) {
        return add(property, value, MatchType.GT);
    }

    /***
     *  添加查询条件（大于等于）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams greaterThanEqual(String property, Object value) {
        return add(property, value, MatchType.GTE);
    }

    /***
     *  添加查询条件（in）
     * @param property    Model属性名
     * @param value         属性值数组
     * @return this
     */
    public ModelQueryParams in(String property, Object[] value) {
        return add(property, value, MatchType.In);
    }

    /***
     *  添加查询条件（not in）
     * @param property    Model属性名
     * @param value         属性值数组
     * @return this
     */
    public ModelQueryParams notIn(String property, Object[] value) {
        return add(property, value, MatchType.NotIn);
    }

    /***
     *  添加查询条件（like %value%）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams like(String property, String value) {
        return add(property, value, MatchType.Like);
    }

    /***
     *  添加查询条件（like value%）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams leftLike(String property, String value) {
        return add(property, value, MatchType.LeftLike);
    }

    /***
     *  添加查询条件（like %value）
     * @param property    Model属性名
     * @param value         属性值
     * @return this
     */
    public ModelQueryParams rightLike(String property, Object value) {
        return add(property, value, MatchType.RightLike);
    }

    /***
     *  添加属性为null查询条件
     * @param property    Model属性名
     * @return this
     */
    public ModelQueryParams isNull(String property) {
        return add(property, null, MatchType.IsNull);
    }
    /***
     *  添加属性为isNotNull查询条件
     * @param property    Model属性名
     * @return this
     */
    public ModelQueryParams isNotNull(String property) {
        return add(property, null, MatchType.IsNotNull);
    }

    //----------------------------------------------------------------
    /** 获取：当前页码*/
    public int getPageNum() {
        return pageNum;
    }
    /** 设置：当前页码*/
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    /** 获取：每页条数*/
    public int getPageSize() {
        return pageSize;
    }
    /** 设置：每页条数*/
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /** 获取：排序列，多个以逗号分隔*/
    public String getSortName() {
        return sortName;
    }
    /** 设置：排序列，多个以逗号分隔*/
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    /** 获取：排序方向，多个以逗号分隔*/
    public String getSortOrder() {
        return sortOrder;
    }
    /** 设置：排序方向，多个以逗号分隔*/
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    /** 获取：匹配条件列表 */
    public List<MatchItem> getMatchItems() {
        return matchItems;
    }
    /** 设置：匹配条件列表 */
    public void setMatchItems(List<MatchItem> matchItems) {
        this.matchItems = matchItems;
    }

    @Override
    public String toString() {
        return "ModelQueryParams{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortName='" + sortName + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", matchItems=" + matchItems +
                '}';
    }
}
package com.e7code.common.mvc.bean;

import com.e7code.common.api.bean.MatchItem;
import com.e7code.common.api.bean.MatchType;
import com.e7code.common.api.bean.ModelQueryParams;
import com.e7code.common.core.support.DateHelper;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ssr on 2017/3/22.
 */
public class HttpQueryParams implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer pageNum; 		        // 当前页码；
    protected Integer pageSize;	            // 每页条数；
    protected String sortName;	            // 排序列，多个以逗号分隔；
    protected String sortOrder;	            // 排序方向，多个以逗号分隔；
    protected List<MatchItem> matchItems = new ArrayList<>();

    /***
     *  添加查询条件
     * @param property    Model属性名
     * @param value         属性值
     * @param matchType     匹配类型
     * @return this
     */
    public HttpQueryParams add(String property, Object value, MatchType matchType) {
        matchItems.add(MatchItem.instance(property, value, matchType));
        return this;
    }

    /***
     * HttpQueryParams转换成ModelQueryParams
     * @param modelClass
     * @return
     */
    public ModelQueryParams toModelQueryParams(Class<?> modelClass) {
        ModelQueryParams params = new ModelQueryParams();
        if(this.pageNum != null) params.setPageNum(this.getPageNum());
        if(this.pageSize != null) params.setPageSize(this.getPageSize());
        if(this.sortName != null) params.setSortName(this.getSortName());
        if(this.sortOrder != null) params.setSortOrder(this.getSortOrder());

        for (MatchItem item: matchItems) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(modelClass, item.getProperty());
            if(propertyDescriptor == null ) {
                continue;
            }

            if (item.getMatchType() == MatchType.IsNull) {
                params.isNull(item.getProperty());
                continue;
            } else if(item.getMatchType() == MatchType.IsNotNull) {
                params.isNotNull(item.getProperty());
                continue;
            } else if(item.getMatchType() == MatchType.Like || item.getMatchType() == MatchType.LeftLike || item.getMatchType() == MatchType.RightLike) {
                params.add(item.getProperty(), item.getValue(), item.getMatchType());
                continue;
            }

            Object value = null;
            if(item.getMatchType() == MatchType.In || item.getMatchType() == MatchType.NotIn) {
                value = getArrayValue(item, propertyDescriptor);
            } else {
                value = getSingleValue(item, propertyDescriptor);
            }

            if(value != null) {
                params.add(item.getProperty(), value, item.getMatchType());
            }
        }

        return params;
    }


    private Object getSingleValue(MatchItem item, PropertyDescriptor propertyDescriptor){
        String strValue = (String)item.getValue();
        Object value = null;
        if(propertyDescriptor.getPropertyType().isAssignableFrom(String.class)) {
            value = item.getValue();
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Short.class)) {
            value = Short.parseShort(strValue);
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Integer.class)) {
            value = Integer.parseInt(strValue);
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Long.class)) {
            value = Long.parseLong(strValue);
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Double.class)) {
            value = Double.parseDouble(strValue);
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(BigDecimal.class)) {
            value = BigDecimal.valueOf(Double.parseDouble(strValue));
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Date.class)) {
            value =  DateHelper.parse(strValue);
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Boolean.class)) {
            if("1".equals(strValue) || "true".equalsIgnoreCase(strValue)) {
                value = true;
            }
            if("0".equals(strValue) || "false".equalsIgnoreCase(strValue)) {
                value = false;
            }
        } else if(propertyDescriptor.getPropertyType().isEnum()) {
            for (Object e : propertyDescriptor.getPropertyType().getEnumConstants()) {
                if(strValue.equalsIgnoreCase(e.toString())) {
                    value = e;
                    break;
                }
            }
        }
        return value;
    }

    private Object getArrayValue(MatchItem item, PropertyDescriptor propertyDescriptor){
        String[] strValues = (String[])item.getValue();
        Object[] value = null;
        if(propertyDescriptor.getPropertyType().isAssignableFrom(String.class)) {
            value = strValues;
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Short.class)) {
            value = new Short[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] = Short.parseShort(strValues[i]);
            }
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Integer.class)) {
            value = new Integer[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] = Integer.parseInt(strValues[i]);
            }
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Long.class)) {
            value = new Long[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] =  Long.parseLong(strValues[i]);
            }
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Double.class)) {
            value = new Double[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] =  Double.parseDouble(strValues[i]);
            }
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(BigDecimal.class)) {
            value = new BigDecimal[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] =  BigDecimal.valueOf(Double.parseDouble(strValues[i]));
            }
        } else if(propertyDescriptor.getPropertyType().isAssignableFrom(Date.class)) {
            value = new Date[strValues.length];
            for (int i=0; i<strValues.length; i++) {
                value[i] =  DateHelper.parse(strValues[i]);
            }
        } else if(propertyDescriptor.getPropertyType().isEnum()) {
            value = new Object[strValues.length];
            for(int i=0; i<strValues.length; i++) {
                String strValue = strValues[i];
                for (Object e : propertyDescriptor.getPropertyType().getEnumConstants()) {
                    if(strValue.equalsIgnoreCase(e.toString())) {
                        value[i] = e;
                        break;
                    }
                }
            }

        }
        return value;
    }


    //------ get set -------------------------------
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<MatchItem> getMatchItems() {
        return matchItems;
    }

    public void setMatchItems(List<MatchItem> matchItems) {
        this.matchItems = matchItems;
    }

    @Override
    public String toString() {
        return "HttpQueryParams{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortName='" + sortName + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", matchItems=" + matchItems +
                '}';
    }
}

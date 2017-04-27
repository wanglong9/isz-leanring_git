package com.e7code.common.mvc.handler;

import com.e7code.common.mvc.bean.HttpQueryParams;
import com.e7code.common.api.bean.MatchType;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by ssr on 2017/3/21.
 */
public class HttpQueryParamsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().equals(HttpQueryParams.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpQueryParams params = new HttpQueryParams();

        for (String pname : webRequest.getParameterMap().keySet()) {
            //参数值数组
            String[] pvalues = webRequest.getParameterValues(pname);
            if(pvalues == null || pvalues.length == 0 || (pvalues.length == 1 && pvalues[0].trim().length() == 0)) {
                continue;
            }

            //分页-页码参数
            if("pageNum".equals(pname) && pvalues[0] != null && pvalues[0].length() > 0) {
                int pageNum = 0;
                try {
                    pageNum = Integer.parseInt(pvalues[0]);
                } catch (Exception e){}
                if(pageNum > 0) {
                    params.setPageNum(pageNum);
                }
                continue;
            }

            //分页-每页条数参数
            if("pageSize".equals(pname) && pvalues[0] != null && pvalues[0].length() > 0) {
                int pageSize = 0;
                try {
                    pageSize = Integer.parseInt(pvalues[0]);
                } catch (Exception e){}
                if(pageSize > 0) {
                    params.setPageSize(pageSize);
                }
                continue;
            }

            //排序名称参数
            if("sortName".equals(pname) && pvalues[0] != null && pvalues[0].trim().length() > 0) {
                params.setSortName(pvalues[0]);
                continue;
            }

            //排序方向参数
            if("sortOrder".equals(pname) && pvalues[0] != null && pvalues[0].trim().length() > 0) {
                params.setSortOrder(pvalues[0]);
                continue;
            }

            /*
             * 表单参数名称以下划线分隔，左边是Model属性名称，右边是匹配类型
             * 例如： username_eq :username = value
             *        username_like : username like %value%
             * 更多匹配规则：
             * {@link com.e7code.crud.base.MatchType}
             */
            String[] arr = pname.split("_");
            if(arr.length != 2) {
                continue;
            }

            MatchType matchType = MatchType.getByIgnoreCaseName(arr[1]);
            if(matchType == null) {
                continue;
            }

            if(matchType == MatchType.IsNull || matchType == MatchType.IsNotNull) {
                if("1".equals(pvalues[0]) || "true".equalsIgnoreCase(pvalues[0])) {
                    params.add(arr[0], pvalues, matchType);
                }
                continue;
            }

            if(matchType == MatchType.In || matchType == MatchType.NotIn) {
                params.add(arr[0], pvalues, matchType);
                continue;
            }

            params.add(arr[0], pvalues[0], matchType);
        }

        return params;
    }
}

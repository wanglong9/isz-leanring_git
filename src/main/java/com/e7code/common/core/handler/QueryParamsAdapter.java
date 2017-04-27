package com.e7code.common.core.handler;

import com.e7code.common.api.bean.ModelQueryParams;
import com.e7code.common.api.bean.MatchItem;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssr on 2017/3/22.
 */
public class QueryParamsAdapter<T> extends ModelQueryParams {
    private static final long serialVersionUID = 1L;

    public static String SORT_DESC = "desc";
    public static String SORT_SEPARATOR = ",";

    @SuppressWarnings("unused")
    private QueryParamsAdapter(){}

    public QueryParamsAdapter(ModelQueryParams params) {
        BeanUtils.copyProperties(params, this);
    }

    /***
     * 获取排序对象
     * @return Sort对象
     */
    public Sort getSort(){
        if(sortName == null || sortOrder == null || "".equals(sortName.trim()) || "".equals(sortOrder.trim())) {
            return null;
        }

        String[] names = sortName.split(SORT_SEPARATOR);
        String[] orders = sortOrder.split(SORT_SEPARATOR);

        if(names.length != orders.length) {
            return null;
        }

        Sort sort = null;
        for (int i=0; i<names.length; i++) {
            Sort temp = new Sort(SORT_DESC.equalsIgnoreCase(orders[i]) ? Sort.Direction.DESC : Sort.Direction.ASC, names[i]);
            if(sort == null) {
                sort = temp;
            } else {
                sort.and(temp);
            }
        }

        return sort;
    }

    /***
     * 获取分页排序对象
     * @return PageRequest对象
     */
    public PageRequest getPageRequest() {
        Sort sort = getSort();
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, sort);
        return pageRequest;
    }

    /***
     * 获取查询条件
     * @return Specification对象
     */
    public Specification<T> getSpecification() {
        return new Specification<T>() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                if(matchItems.size() == 0) {
                    return cb.conjunction();
                }

                List<Predicate> predicates = new ArrayList<Predicate>();
                for (MatchItem item: matchItems) {
                    Path property = root.get(item.getProperty());

                    switch (item.getMatchType()) {
                        case EQ:
                            predicates.add(cb.equal(property, item.getValue()));
                            break;
                        case NQ:
                            predicates.add(cb.notEqual(property, item.getValue()));
                            break;
                        case LT:
                            predicates.add(cb.lessThan(property, (Comparable)item.getValue()));
                            break;
                        case LTE:
                            predicates.add(cb.lessThanOrEqualTo(property, (Comparable)item.getValue()));
                            break;
                        case GT:
                            predicates.add(cb.greaterThan(property, (Comparable)item.getValue()));
                            break;
                        case GTE:
                            predicates.add(cb.greaterThanOrEqualTo(property, (Comparable)item.getValue()));
                            break;
                        case In:
                            if(item.getValue() == null || !item.getValue().getClass().isArray()) break;

                            List<Predicate> ins = new ArrayList<Predicate>();
                            for (Object obj: (Object[]) item.getValue()) {
                                ins.add(cb.equal(root.get(item.getProperty()), obj));
                            }
                            predicates.add(cb.or(ins.toArray(new Predicate[ins.size()])));
                            break;
                        case NotIn:
                            if(item.getValue() == null || !item.getValue().getClass().isArray()) break;

                            List<Predicate> nis = new ArrayList<Predicate>();
                            for (Object obj: (Object[]) item.getValue()) {
                                nis.add(cb.notEqual(root.get(item.getProperty()), obj));
                            }
                            predicates.add(cb.and(nis.toArray(new Predicate[nis.size()])));
                            break;
                        case Like:
                            predicates.add(cb.like(property, "%" + item.getValue() + "%"));
                            break;
                        case LeftLike:
                            predicates.add(cb.like(property, item.getValue() + "%"));
                            break;
                        case RightLike:
                            predicates.add(cb.like(property, "%" + item.getValue()));
                            break;
                        case IsNull:
                            predicates.add(cb.isNull(root.get(item.getProperty())));
                            break;
                        case IsNotNull:
                            predicates.add(cb.isNotNull(root.get(item.getProperty())));
                            break;
                    }
                }

                if (predicates.size() > 0) {
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                } else {
                    return cb.conjunction();
                }
            }
        };
    }
}

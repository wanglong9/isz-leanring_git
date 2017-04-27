package com.e7code.common.core.manager;


import com.e7code.common.api.bean.BaseEntity;
import com.e7code.common.api.bean.ModelQueryParams;
import com.e7code.common.api.bean.PageData;
import com.e7code.common.api.exception.ValidateException;
import com.e7code.common.core.handler.QueryParamsAdapter;
import com.e7code.common.api.constant.ErrorCodes;
import com.e7code.common.core.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.transaction.Transactional;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ssr on 2017/3/11.
 */
public abstract class BaseManager<T extends BaseEntity, ID extends Serializable> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected BaseRepository<T, ID> repository;

    /***
     * 添加：ID为null时添加，createTime自动设置，version自动设置
     * 更新：ID不为null时更新，createTime不更新；
     *       version为null时，更新时不做乐观锁！
     * @param t
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void save(T t) {
        logger.debug("save t:{}", t);

        if(t.getId() == null) {
            repository.save(t);
        } else {
            T dbT = repository.findOne((ID)t.getId());
            if(dbT == null) throw new ValidateException(ErrorCodes.DATA_NOT_FOUND);

            //如果更新是未传version，忽略乐观锁，取数据库里的version
            if(t.getVersion() == null) {
                t.setVersion(dbT.getVersion());
            }

            try{
                repository.save(t);
            }catch (ObjectOptimisticLockingFailureException e) {
                throw new ValidateException(ErrorCodes.DATA_OPTIMISTIC_LOCKING_FAILURE);
            }
        }
    }

    /***
     * 更新指定属性；
     * @param t 数据对象
     * @param properties 要更新的属性列表
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void update4properties(T t, String... properties) {
        logger.debug("update4properties t:{}, properties:{}", t, properties);

        T dbT = repository.findOne((ID)t.getId());
        if(dbT == null) throw new ValidateException(ErrorCodes.DATA_NOT_FOUND);

        for (String property :properties) {
            PropertyDescriptor source = BeanUtils.getPropertyDescriptor(t.getClass(), property);
            PropertyDescriptor target = BeanUtils.getPropertyDescriptor(dbT.getClass(), property);

            if(source == null || target == null) {
                throw new  ValidateException(ErrorCodes.PROPERTY_NOT_FOUND);
            }

            try{
                Object value = source.getReadMethod().invoke(t);
                target.getWriteMethod().invoke(dbT,value);
            } catch (Exception e) {
                throw new  ValidateException(ErrorCodes.PROPERTY_COPY_ERROR);
            }
        }

        repository.save(dbT);
    }

    /***
     * 更新忽略列表之外的所有属性
     * @param t 数据对象
     * @param ignoreProperties 排除属性列表
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void update4ignoreProperties(T t, String... ignoreProperties) {
        logger.debug("update4ignoreProperties t:{},ignoreProperties:{}", t, ignoreProperties);

        T dbT = repository.findOne((ID)t.getId());
        if(dbT == null) throw new ValidateException(ErrorCodes.DATA_NOT_FOUND);

        BeanUtils.copyProperties(t, dbT, ignoreProperties);

        try{
            repository.save(dbT);
        }catch (ObjectOptimisticLockingFailureException e) {
            throw new ValidateException(ErrorCodes.DATA_OPTIMISTIC_LOCKING_FAILURE);
        }
    }

    /***
     * 通过ID删除数据
     * @param id
     */
    @Transactional
    public void delete(ID id) {
        logger.debug("delete id:{}", id);

        repository.delete(id);
    }

    /***
     * 通过ID获取数据对象
     * @param id ID
     * @return 数据对象
     */
    public T get(ID id) {
        logger.debug("get id:{}", id);

        return repository.findOne(id);
    }

    /***
     * 查询所有数据
     * @param queryParams 查询条件对象
     * @return 查询结果列表
     */
    public List<T> queryAll(ModelQueryParams queryParams) {
        logger.debug("queryAll queryParams:{}", queryParams);

        QueryParamsAdapter<T> adapter =  new QueryParamsAdapter<T>(queryParams);

        Sort sort = adapter.getSort();

        if(sort == null) {
            return this.repository.findAll(adapter.getSpecification());
        } else {
            return this.repository.findAll(adapter.getSpecification(), sort);
        }
    }

    /***
     * 分页查询
     * @param queryParams 查询条件
     * @return 分页数据
     */
    public PageData<T> queryPage(ModelQueryParams queryParams) {
        logger.debug("queryPage queryParams:{}", queryParams);

        QueryParamsAdapter<T> adapter = new QueryParamsAdapter<T>(queryParams);

        Page<T> page = this.repository.findAll(adapter.getSpecification(), adapter.getPageRequest());
        return new PageData<T>(page.getTotalElements(), page.getContent());
    }

    /***
     * 统计数据条数
     * @param queryParams 查询条件
     * @return 数据条数
     */
    public long count(ModelQueryParams queryParams){
        logger.debug("count queryParams:{}", queryParams);

        QueryParamsAdapter<T> adapter = new QueryParamsAdapter<T>(queryParams);

        return this.repository.count(adapter.getSpecification());
    }
}

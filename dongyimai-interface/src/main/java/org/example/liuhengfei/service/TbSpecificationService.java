package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbSpecification;

import java.util.List;
import java.util.Map;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbSpecificationService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSpecification> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbSpecification tbSpecification);

	/**
	 * 返回列表选项
	 */
	List<Map> selectOptions();

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbSpecification findOne(Long id);

    /**
     * 增加、修改
     */
    void save(TbSpecification tbSpecification);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Long[] ids);

}

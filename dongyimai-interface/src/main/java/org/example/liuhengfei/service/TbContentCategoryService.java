package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbContentCategory;

import java.util.List;
/**
 * 内容分类服务层接口
 * @author Administrator
 *
 */
public interface TbContentCategoryService {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<TbContentCategory> findAll();
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum,int pageSize,TbContentCategory tbContentCategory);

/**
 * 根据ID获取实体
 * @param id
 * @return
 */
TbContentCategory findOne(Long id);

	/**
	 * 增加
	*/
	int add(TbContentCategory tbContentCategory);

	/**
	 * 修改
	 */
	int update(TbContentCategory tbContentCategory);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	int delete(Long[] ids);
	
}

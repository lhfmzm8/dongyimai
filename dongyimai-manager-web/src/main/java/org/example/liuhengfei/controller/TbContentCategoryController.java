package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbContentCategory;
import org.example.liuhengfei.service.TbContentCategoryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内容分类controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("TbContentCategoryController")
public class TbContentCategoryController {

	@Reference
	private TbContentCategoryService tbContentCategoryService;

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("findAll")
	public List<TbContentCategory> findAll(){
		return tbContentCategoryService.findAll();
	}

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("findPage")
	public PageResult findPage(int pageNum,int pageSize,@RequestBody TbContentCategory tbContentCategory){
		return tbContentCategoryService.findPage(pageNum, pageSize, tbContentCategory);
	}

/**
 * 获取实体
 * @param id
 * @return
 */
@RequestMapping("findOne")
public TbContentCategory findOne (Long id){
		return tbContentCategoryService.findOne(id);
		}

	/**
	 * 增加
	 * @param tbContentCategory
	 * @return
	 */
	@RequestMapping("add")
	public Result add(@RequestBody TbContentCategory tbContentCategory){
		try {
			tbContentCategoryService.add(tbContentCategory);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
		return new Result(true, "添加成功");
	}

	/**
	 * 修改
	 * @param tbContentCategory
	 * @return
	 */
	@RequestMapping("update")
	public Result update(@RequestBody TbContentCategory tbContentCategory){
		try {
			tbContentCategoryService.update(tbContentCategory);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
		return new Result(true, "修改成功");
	}

	/**
	 * 批量删除
	 * @param  ids
	 * @return
	 */
	@RequestMapping("delete")
	public Result delete(Long[] ids){
		try {
			tbContentCategoryService.delete(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
		return new Result(true, "删除成功");
	}

}

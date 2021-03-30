package [package].controller;

import com.alibaba.dubbo.config.annotation.Reference;
import [path_1].[path_2].[path_3].dto.PageResult;
import [path_1].[path_2].[path_3].dto.Result;
import [path_1].[path_2].[path_3].pojo.[Table2];
import [path_1].[path_2].[path_3].service.[Table2]Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * [comment]controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("[Table2]Controller")
public class [Table2]Controller {

	@Reference(timeout = 60000)
	private [Table2]Service [table2]Service;

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("findAll")
	public List<[Table2]> findAll(){
		return [table2]Service.findAll();
	}

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("findPage")
	public PageResult findPage(int pageNum,int pageSize,@RequestBody [Table2] [table2]){
		return [table2]Service.findPage(pageNum, pageSize, [table2]);
	}

/**
 * 获取实体
 * @param [PrimaryKeyName]
 * @return
 */
@RequestMapping("findOne")
public [Table2] findOne ([PrimaryKeyType] [PrimaryKeyName]){
		return [table2]Service.findOne([PrimaryKeyName]);
		}

	/**
	 * 增加、修改
	 * @param [table2]
	 * @return
	 */
	@RequestMapping("save")
	public Result save(@RequestBody [Table2] [table2]){
		try {
			[table2]Service.save([table2]);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
		return new Result(true, "操作成功");
	}

	/**
	 * 批量删除
	 * @param  [PrimaryKeyName]s
	 * @return
	 */
	@RequestMapping("delete")
	public Result delete([PrimaryKeyType][] [PrimaryKeyName]s){
		try {
			[table2]Service.delete([PrimaryKeyName]s);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
		return new Result(true, "删除成功");
	}

}

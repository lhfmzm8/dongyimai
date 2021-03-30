package [package].service;

import org.example.liuhengfei.dto.PageResult;
import [path_1].[path_2].[path_3].pojo.[Table2];

import java.util.List;
/**
 * [comment]服务层接口
 * @author Administrator
 *
 */
public interface [Table2]Service {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<[Table2]> findAll();
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum,int pageSize,[Table2] [table2]);

/**
 * 根据ID获取实体
 * @param [PrimaryKeyName]
 * @return
 */
[Table2] findOne([PrimaryKeyType] [PrimaryKeyName]);

	/**
	 * 增加、修改
	*/
	void save([Table2] [table2]);
	
	/**
	 * 批量删除
	 * @param [PrimaryKeyName]s
	 */
	void delete([PrimaryKeyType][] [PrimaryKeyName]s);
	
}

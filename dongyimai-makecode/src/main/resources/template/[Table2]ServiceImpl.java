package [package].impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import [path_1].[path_2].[path_3].dto.PageResult;
import [path_1].[path_2].[path_3].mapper.[Table2]Mapper;
import [path_1].[path_2].[path_3].pojo.[Table2];
import [package].service.[Table2]Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * [comment]服务实现层
 *
 * @author Administrator
 */
@Service
public class [Table2]ServiceImpl implements [Table2]Service{

@Resource
private [Table2]Mapper [table2]Mapper;

/**
 * 查询全部
 */
@Override
public List<[Table2]>findAll(){
        return [table2]Mapper.selectByItems(null);
        }

/**
 * 按分页查询
 */
@Override
public PageResult findPage(int pageNum,int pageSize,[Table2] [table2]){
        PageHelper.startPage(pageNum,pageSize);
        Page<[Table2]>page=(Page<[Table2]>)[table2]Mapper.selectByItems([table2]);
        return new PageResult(page.getTotal(),page.getResult());
        }

/**
 * 根据ID获取实体
 *
 * @param [PrimaryKeyName]
 * @return
 */
@Override
public [Table2] findOne ([PrimaryKeyType] [PrimaryKeyName]){
        return [table2]Mapper.selectByPrimaryKey([PrimaryKeyName]);
        }
/**
 * 增加、修改
 */
@Override
public void save([Table2] [table2]){
    if ([table2].get[PrimaryKeyNameUp]() == null) {
        [table2]Mapper.insertSelective([table2]);
        } else {
        [table2]Mapper.updateByPrimaryKey([table2]);
        }
}

/**
 * 批量删除
 */
@Override
public void delete([PrimaryKeyType][] [PrimaryKeyName]s){
        return [table2]Mapper.deleteByPrimaryKey([PrimaryKeyName]s);
        }

        }

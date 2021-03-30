package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbSeller;
import org.example.liuhengfei.service.TbSellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbSellerControllerShop")
public class TbSellerControllerShop {

    @Reference(timeout = 60000)
    private TbSellerService tbSellerService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbSeller> findAll() {
        return tbSellerService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbSeller tbSeller) {
        return tbSellerService.findPage(pageNum, pageSize, tbSeller);
    }

    /**
     * 获取实体
     *
     * @param sellerId
     * @return
     */
    @RequestMapping("findOne")
    public TbSeller findOne(String sellerId) {
        return tbSellerService.findOne(sellerId);
    }

    /**
     * 增加
     *
     * @param tbSeller
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody TbSeller tbSeller) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode(tbSeller.getPassword());
            tbSeller.setPassword(newPassword);
            tbSeller.setStatus("0");
            tbSellerService.add(tbSeller);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
        return new Result(true, "添加成功");
    }

    /**
     * 修改
     *
     * @param tbSeller
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody TbSeller tbSeller) {
        try {
            tbSellerService.update(tbSeller);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
        return new Result(true, "修改成功");
    }

    /**
     * 批量删除
     *
     * @param sellerIds
     * @return
     */
    @RequestMapping("delete")
    public Result delete(String[] sellerIds) {
        try {
            tbSellerService.delete(sellerIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}

//服务层
app.service("TbGoodsService", function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.post("/TbGoodsController/findAll");
    }
    //分页
    this.findPage = function (pageNum, pageSize, tbGoods) {
        return $http.post("/TbGoodsController/findPage?pageNum=" + pageNum + "&pageSize=" + pageSize, tbGoods);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.post("/TbGoodsController/findOne?id=" + id);
    }
    //批量修改审核状态
    this.updateStatus = function (ids, status) {
        return $http.post("/TbGoodsController/updateStatus?ids=" + ids + "&status=" + status);
    }
    //删除
    this.delete = function (ids) {
        return $http.post("/TbGoodsController/delete?ids=" + ids);
    }
});
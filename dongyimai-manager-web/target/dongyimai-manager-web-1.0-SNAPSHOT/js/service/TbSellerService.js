//服务层
app.service("TbSellerService", function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.post("/TbSellerController/findAll");
    }
    //分页
    this.findPage = function (pageNum, pageSize, tbSeller) {
        return $http.post("/TbSellerController/findPage?pageNum=" + pageNum + "&pageSize=" + pageSize, tbSeller);
    }
    //查询实体
    this.findOne = function (sellerId) {
        return $http.post("/TbSellerController/findOne?sellerId=" + sellerId);
    }
    //注册
    this.register = function (tbSeller) {
        return $http.post("/TbSellerController/add", tbSeller);
    }
    //修改
    this.update = function (tbSeller) {
        return $http.post("/TbSellerController/update", tbSeller);
    }
    //删除
    this.delete = function (sellerIds) {
        return $http.post("/TbSellerController/delete?sellerIds=" + sellerIds);
    }
});
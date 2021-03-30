//服务层
app.service("TbGoodsServiceShop", function ($http) {

    //分页
    this.findPage = function (pageNum, pageSize, tbGoods) {
        return $http.post("/TbGoodsControllerShop/findPage?pageNum=" + pageNum + "&pageSize=" + pageSize, tbGoods);
    }
    //获取实体
    this.findOne = function (id) {
        return $http.post("/TbGoodsControllerShop/findOne?id=" + id);
    }
    //增加、修改
    this.save = function (tbGoods) {
        return $http.post("/TbGoodsControllerShop/save", tbGoods);
    }
    //删除
    this.delete = function (ids) {
        return $http.post("/TbGoodsControllerShop/delete?ids=" + ids);
    }
});
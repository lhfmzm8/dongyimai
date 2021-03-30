//商品类目服务层
app.service("TbItemCatServiceShop", function ($http) {

    //查询所有
    this.findAll = function () {
        return $http.post("/TbItemCatControllerShop/findAll");
    }
    //查询实体
    this.findOne = function (id) {
        return $http.post("/TbItemCatControllerShop/findOne?id=" + id);
    }
    //根据父ID查询
    this.findByParentId = function (parentId) {
        return $http.post("/TbItemCatControllerShop/findByParentId?parentId=" + parentId);
    }
});
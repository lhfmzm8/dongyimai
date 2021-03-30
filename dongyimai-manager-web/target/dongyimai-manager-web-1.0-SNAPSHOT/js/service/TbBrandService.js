//服务层
app.service("TbBrandService", function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.post("/TbBrandController/findAll");
    }
    //分页
    this.findPage = function (pageNum, pageSize, tbBrand) {
        return $http.post("/TbBrandController/findPage?pageNum=" + pageNum + "&pageSize=" + pageSize, tbBrand);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.post("/TbBrandController/findOne", id);
    }
    //增加、修改
    this.save = function (tbBrand) {
        let url;
        if (tbBrand.id == null) {
            url = "/TbBrandController/add";
        } else {
            url = "/TbBrandController/update"
        }
        return $http.post(url, tbBrand);
    }
    //删除
    this.delete = function (ids) {
        return $http.post("/TbBrandController/delete?ids=" + ids);
    }
});
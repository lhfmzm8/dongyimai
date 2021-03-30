app.service("TbContentServicePortal", function ($http) {
    //根据分类ID查询
    this.findByCategoryId = function (categoryId) {
        return $http.post("/TbContentControllerPortal/findByCategoryId?categoryId=" + categoryId);
    }
})
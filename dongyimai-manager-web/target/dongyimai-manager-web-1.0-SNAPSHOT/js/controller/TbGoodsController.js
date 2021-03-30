//控制层
app.controller("TbGoodsController", function ($scope, $controller, TbGoodsService, TbItemCatService,
                                              TbBrandService, TbTypeTemplateService) {
    $controller("BaseController", {$scope: $scope});//继承

    $scope.auditStatus = ["未审核", "审核通过", "审核驳回"];
    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        TbGoodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分类
    $scope.selectItemCats = function () {
        $scope.itemCats = {};
        TbItemCatService.findAll().success(function (response) {
            for (let i = 0; i < response.length; i++) {
                $scope.itemCats[response[i].id] = response[i].name;
            }
        })
    }

    //品牌
    $scope.selectBrands = function () {
        $scope.brands={};
        TbBrandService.findAll().success(function (response) {
            for (let i = 0; i < response.length; i++) {
                $scope.brands[response[i].id] = response[i].name;
            }
        })
    }

    //模板
    $scope.selectTypeTemplates = function () {
        $scope.typeTemplates={};
        TbTypeTemplateService.findAll().success(function (response) {
            for (let i = 0; i < response.length; i++) {
                $scope.typeTemplates[response[i].id] = response[i].name;
            }
        })
    }

    //分页
    $scope.searchEntity = {};//定义搜索对象
    $scope.findPage = function () {
        TbGoodsService.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        TbGoodsService.findOne(id).success(function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        TbGoodsService.save($scope.entity).success(function (response) {
                if (response.success) {
                    //重新查询
                    $scope.findPage();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }

    //批量删除
    $scope.delete = function () {
        //获取选中的复选框
        TbGoodsService.delete($scope.keys).success(function (response) {
            if (response.success) {
                $scope.findPage();//刷新列表
                $scope.keys = [];
            } else {
                alert(response.message);
            }
        })
    }

    //批量更改审核状态
    $scope.updateStatus = function (status) {
        TbGoodsService.updateStatus($scope.keys, status).success(function (response) {
            if (response.success) {
                $scope.findPage();//刷新列表
                $scope.keys = [];
            } else {
                alert(response.message);
            }
        })
    }

});
//控制层
app.controller("TbTypeTemplateController", function ($scope, $controller, TbTypeTemplateService) {
    $controller("BaseController", {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        TbTypeTemplateService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.searchEntity = {};//定义搜索对象
    $scope.findPage = function () {
        TbTypeTemplateService.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //获取规格列表选项
    $scope.tbSpecificationList = {date: []};
    $scope.selectTbSpecificationOptions = function () {
        TbTypeTemplateService.selectTbSpecificationOptions().success(function (response) {
            $scope.tbSpecificationList = {date: response}
        })
    }

    //获取品牌列表选项
    $scope.tbBrandList = {date: []};
    $scope.selectTbBrandOptions = function () {
        TbTypeTemplateService.selectTbBrandOptions().success(function (response) {
            $scope.tbBrandList = {date: response}
        })
    }

    //查询实体
    $scope.findOne = function (id) {
        TbTypeTemplateService.findOne(id).success(function (response) {
                $scope.entity = response;
                $scope.entity.customAttributeItems = JSON.parse(response.customAttributeItems);
                // 更新select2-model
                $scope.entity.brandIds = JSON.parse(response.brandIds);
                $scope.entity.specIds = JSON.parse(response.specIds);
            }
        )
    }

    //保存
    $scope.save = function () {
        TbTypeTemplateService.save($scope.entity).success(function (response) {
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
        TbTypeTemplateService.delete($scope.keys).success(function (response) {
            if (response.success) {
                $scope.findPage();//刷新列表
                $scope.keys = [];
            } else {
                alert(response.message);
            }
        })
    }

});
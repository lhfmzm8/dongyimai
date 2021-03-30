//商品类目控制层
app.controller("TbItemCatController", function ($scope, $controller, TbItemCatService) {
    $controller("BaseController", {$scope: $scope});//继承

    //面包屑导航
    $scope.grade = 1;//定义分类的层级，商品分类一共3级，默认是第一级
    $scope.setGrade = function (grade) {
        $scope.grade = Number(grade);
    }

    //根据层级判断，如果是第一级，则无需面包屑；如果是第二级，则需要显示第一级的面包屑；如果是第三级，则前两级都要显示
    $scope.selectItems = function (parentItem) {
        if ($scope.grade === 1) {
            $scope.itemGrade2 = null;
            $scope.itemGrade3 = null;
        } else if ($scope.grade === 2) {
            $scope.itemGrade2 = parentItem;
            $scope.itemGrade3 = null;
        } else {
            $scope.itemGrade3 = parentItem;
        }
        $scope.findByParentId(parentItem.id);
    }

    //查询实体
    $scope.findOne = function (id) {
        TbItemCatService.findOne(id).success(function (response) {
                $scope.entity = response;
            }
        );
    }

    //根据父ID查询
    $scope.findByParentId = function (parentId) {
        TbItemCatService.findByParentId(parentId).success(function (response) {
            $scope.list = response;
        })
    }

    //查询模板选项
    $scope.tbTypeTemplateOptions={data:[]};
    $scope.findTbTypeTemplateOptions = function () {
		TbItemCatService.findTbTypeTemplateOptions().success(function (response) {
            $scope.tbTypeTemplateOptions= {data:response};
        })
    }

    //保存
    $scope.save = function () {
        if ($scope.entity.id == null) {
            if ($scope.grade === 1) {
                $scope.entity.parentId = 0;
            } else if ($scope.grade === 2) {
                $scope.entity.parentId = $scope.itemGrade2.id;
            } else {
                $scope.entity.parentId = $scope.itemGrade3.id;
            }
        }
        TbItemCatService.save($scope.entity).success(function (response) {
                if (response.success) {
                    //重新查询
                    $scope.findByParentId($scope.entity.parentId);//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }

    //批量删除
    $scope.delete = function () {
        //获取选中的复选框
        TbItemCatService.delete($scope.keys).success(function (response) {
            if (response.success) {
                if ($scope.grade === 1) {
                    $scope.findByParentId(0);
                } else if ($scope.grade === 2) {
                    $scope.findByParentId($scope.itemGrade2.id);
                } else {
                    $scope.findByParentId($scope.itemGrade3.id);
                }
                $scope.keys = [];
            } else {
                alert(response.message);
            }
        })
    }

});
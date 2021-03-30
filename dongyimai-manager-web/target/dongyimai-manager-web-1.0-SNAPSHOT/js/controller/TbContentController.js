//控制层
app.controller("TbContentController", function ($scope, $controller, TbContentService, TbContentCategoryService, UploadService) {
    $controller("BaseController", {$scope: $scope});//继承

    $scope.status = ["无效", "有效"];
    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        TbContentService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.searchEntity = {};//定义搜索对象
    $scope.findPage = function () {
        TbContentService.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        TbContentService.findOne(id).success(function (response) {
                $scope.entity = response;
            }
        );
    }

    //获取全部内容分类
    $scope.selectContentCategories = function () {
        $scope.contentCategoryNames = {};
        TbContentCategoryService.findAll().success(function (response) {
            $scope.contentCategories = response;
            for (let i = 0; i < response.length; i++) {
                $scope.contentCategoryNames[response[i].id]=response[i].name;
            }
        })
    }

    //保存
    $scope.save = function () {
        TbContentService.save($scope.entity).success(function (response) {
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
        TbContentService.delete($scope.keys).success(function (response) {
            if (response.success) {
                $scope.findPage();//刷新列表
                $scope.keys = [];
            } else {
                alert(response.message);
            }
        })
    }

    //上传文件
    $scope.uploadFile = function () {
        UploadService.uploadFile().success(function (response) {
            if (response.success) {//如果上传成功，取出url
                $scope.entity.pic = response.message;//设置文件地址
            } else {
                alert(response.message);
            }
        })
    }

});
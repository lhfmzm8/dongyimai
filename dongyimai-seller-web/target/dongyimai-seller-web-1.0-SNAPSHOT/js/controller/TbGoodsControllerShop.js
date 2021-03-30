//控制层
app.controller("TbGoodsControllerShop", function ($scope, $controller, $location, TbGoodsServiceShop, UploadServiceShop, TbItemCatServiceShop, TbTypeTemplateServiceShop) {
    $controller("BaseController", {$scope: $scope});//继承

    //定义页面实体结构
    $scope.entity = {tbGoodsDesc: {itemImages: [], specificationItems: []}};
    $scope.auditStatus = ["未审核", "审核通过", "审核驳回"];

    //分页
    $scope.searchEntity = {};//定义搜索对象
    $scope.findPage = function () {
        TbGoodsServiceShop.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //获取实体
    $scope.findOne = function () {
        let id = $location.search()["id"];
        if (id != null && id !== "") {
            TbGoodsServiceShop.findOne(id).success(function (response) {
                if (response != null) {
                    $scope.entity = response;
                    //向富文本编辑器添加商品介绍
                    editor.html($scope.entity.tbGoodsDesc.introduction);
                    //显示图片列表
                    $scope.entity.tbGoodsDesc.itemImages = JSON.parse($scope.entity.tbGoodsDesc.itemImages);
                    //显示扩展属性
                    $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse($scope.entity.tbGoodsDesc.customAttributeItems);
                    //规格
                    $scope.entity.tbGoodsDesc.specificationItems = JSON.parse($scope.entity.tbGoodsDesc.specificationItems);
                    //SKU
                    for (let i = 0; i < $scope.entity.sKUList.length; i++) {
                        $scope.entity.sKUList[i].spec = JSON.parse($scope.entity.sKUList[i].spec);
                    }
                } else {
                    alert("非法操作");
                }
            });
        }
    }

    //保存
    $scope.save = function () {
        $scope.entity.tbGoodsDesc.introduction = editor.html();
        TbGoodsServiceShop.save($scope.entity).success(function (response) {
                if (response.success) {
                    alert(response.message);
                    location.href = "goods.html";
                } else {
                    alert(response.message);
                }
            }
        );
    }

    //添加图片列表
    $scope.addImages = function () {
        $scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);
    }
    //从列表删除
    $scope.removeImages = function (index) {
        $scope.entity.tbGoodsDesc.itemImages.splice(index, 1);
    }

    //上传文件
    $scope.uploadFile = function () {
        UploadServiceShop.uploadFile().success(function (response) {
            if (response.success) {//如果上传成功，取出url
                $scope.image_entity.url = response.message;//设置文件地址
            } else {
                alert(response.message);
            }
        })
    }

    //商品分类
    $scope.selectItemCats = function () {
        $scope.itemCats = {};
        TbItemCatServiceShop.findAll().success(function (response) {
            for (let i = 0; i < response.length; i++) {
                $scope.itemCats[response[i].id] = response[i].name;
            }
        })
    }

    //一级分类
    $scope.selectItemCats1 = function () {
        TbItemCatServiceShop.findByParentId(0).success(function (response) {
            $scope.itemCats1 = response;
        })
    }

    //二级分类
    $scope.$watch("entity.category1Id", function (newValue) {
        if (newValue != null) {
            TbItemCatServiceShop.findByParentId(newValue).success(function (response) {
                $scope.itemCats2 = response;
            })
        }
    })

    //三级分类
    $scope.$watch("entity.category2Id", function (newValue) {
        if (newValue != null) {
            TbItemCatServiceShop.findByParentId(newValue).success(function (response) {
                $scope.itemCats3 = response;
            })
        }
    })

    //商品模板
    $scope.$watch("entity.category3Id", function (newValue) {
        if (newValue != null) {
            TbItemCatServiceShop.findOne(newValue).success(function (response) {
                $scope.entity.typeTemplateId = response.typeId;
            })
        }
    })

    //商品品牌、扩展属性、规格
    $scope.$watch("entity.typeTemplateId", function (newValue) {
        if (newValue != null) {
            TbTypeTemplateServiceShop.findOne(newValue).success(function (response) {
                //品牌
                $scope.brands = JSON.parse(response.brandIds);
                //扩展属性
                if ($location.search()["id"] == null) {
                    $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
                }
            });
            //规格
            TbTypeTemplateServiceShop.findTbSpecifications(newValue).success(function (response) {
                $scope.specs = response;
            })
        }
    })

    //选择规格选项
    $scope.updateSpecAttribute = function ($event, specName, optionName) {
        let spec = $scope.searchObjectByKey($scope.entity.tbGoodsDesc.specificationItems, "attributeName", specName);
        if (spec == null) {
            $scope.entity.tbGoodsDesc.specificationItems.push({
                "attributeName": specName,
                "attributeValue": [optionName]
            });
        } else {
            //正选
            if ($event.target.checked) {
                spec.attributeValue.push(optionName);
                //反选
            } else {
                //移除规格选项
                spec.attributeValue.splice(spec.attributeValue.indexOf(optionName), 1);
                //如果规格选项全部移除，则移除规格
                if (spec.attributeValue.length === 0) {
                    $scope.entity.tbGoodsDesc.specificationItems.splice($scope.entity.tbGoodsDesc.specificationItems.indexOf(spec), 1);
                }
            }
        }
    }

    //根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue = function (specName, optionName) {
        let options = $scope.entity.tbGoodsDesc.specificationItems;
        let spec = $scope.searchObjectByKey(options, "attributeName", specName);
        if (spec != null) {
            return spec.attributeValue.indexOf(optionName) >= 0;
        } else {
            return false;
        }
    }

    //创建SKU列表
    $scope.createSKUList = function () {
        $scope.entity.sKUList = [{"spec": {}, "price": 0, "num": 0, "status": "0", "isDefault": "0"}];
        let specs = $scope.entity.tbGoodsDesc.specificationItems;
        for (let i = 0; i < specs.length; i++) {
            let temp = [];
            for (let j = 0; j < $scope.entity.sKUList.length; j++) {
                for (let k = 0; k < specs[i].attributeValue.length; k++) {
                    let newSKU = JSON.parse(JSON.stringify($scope.entity.sKUList[j]));
                    newSKU.spec[specs[i].attributeName] = specs[i].attributeValue[k];
                    temp.push(newSKU);
                }
            }
            $scope.entity.sKUList = temp;
        }
    }

    //批量删除
    $scope.delete=function(){
        //获取选中的复选框
        TbGoodsServiceShop.delete($scope.keys).success(function(response){
            if(response.success){
                $scope.findPage();//刷新列表
                $scope.keys=[];
            } else {
                alert(response.message);
            }
        })
    }

});
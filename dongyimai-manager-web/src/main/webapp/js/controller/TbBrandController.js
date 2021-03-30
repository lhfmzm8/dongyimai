//控制层
app.controller("TbBrandController",function($scope,$controller,TbBrandService){
	$controller("BaseController",{$scope:$scope});//继承

	//读取列表数据绑定到表单中
	$scope.findAll=function(){
		TbBrandService.findAll().success(
			function(response){
				$scope.list=response;
			}
		);
	}

	//分页
	$scope.searchEntity = {};//定义搜索对象
	$scope.findPage=function(){
		TbBrandService.findPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).
		success(function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
	}

	//查询实体
	$scope.findOne=function(id){
		TbBrandService.findOne(id).success(function(response){
				$scope.entity= response;
			}
		);
	}

	//保存
	$scope.save=function(){
		TbBrandService.save($scope.entity).success(function(response){
				if(response.success){
					//重新查询
					$scope.findPage();//重新加载
				}else{
					alert(response.message);
				}
			}
		);
	}

	//批量删除
	$scope.delete=function(){
		//获取选中的复选框
		TbBrandService.delete($scope.keys).success(function(response){
			if(response.success){
				$scope.findPage();//刷新列表
				$scope.keys=[];
			} else {
				alert(response.message);
			}
		})
	}

});
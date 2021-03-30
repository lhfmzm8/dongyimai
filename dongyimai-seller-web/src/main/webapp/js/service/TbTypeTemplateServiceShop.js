//服务层
app.service("TbTypeTemplateServiceShop",function($http){

	//获取规格列表选项
	this.findTbSpecifications=function (id) {
		return $http.post("/TbTypeTemplateControllerShop/findTbSpecifications?id="+id);
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbTypeTemplateControllerShop/findOne?id="+id);
	}
});
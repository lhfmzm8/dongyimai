//商品类目服务层
app.service("TbItemCatService",function($http){
	//查询所有
	this.findAll = function () {
		return $http.post("/TbItemCatController/findAll");
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbItemCatController/findOne?id="+id);
	}
	//根据父ID查询
	this.findByParentId = function (parentId) {
		return $http.post("/TbItemCatController/findByParentId?parentId=" + parentId);
	}
	//查询模板选项
	this.findTbTypeTemplateOptions=function () {
		return $http.post("/TbTypeTemplateController/findTbTypeTemplateOptions")
	}
	//增加、修改
	this.save=function (tbItemCat) {
		return $http.post("/TbItemCatController/save",tbItemCat);
	}
	//删除
	this.delete=function(ids){
		return $http.post("/TbItemCatController/delete?ids="+ids);
	}
});
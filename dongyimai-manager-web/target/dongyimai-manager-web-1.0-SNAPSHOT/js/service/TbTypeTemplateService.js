//服务层
app.service("TbTypeTemplateService",function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.post("/TbTypeTemplateController/findAll");
	}
	//分页 
	this.findPage=function(pageNum,pageSize,tbTypeTemplate){
		return $http.post("/TbTypeTemplateController/findPage?pageNum="+pageNum+"&pageSize="+pageSize,tbTypeTemplate);
	}
	//获取规格列表选项
	this.selectTbSpecificationOptions=function () {
		return $http.post("/TbSpecificationController/selectOptions")
	}
	//获取品牌列表选项
	this.selectTbBrandOptions=function () {
		return $http.post("/TbBrandController/selectOptions")
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbTypeTemplateController/findOne?id="+id);
	}
	//增加、修改
	this.save=function (tbTypeTemplate) {
		return $http.post("/TbTypeTemplateController/save",tbTypeTemplate);
	}
	//删除
	this.delete=function(ids){
		return $http.post("/TbTypeTemplateController/delete?ids="+ids);
	}
});
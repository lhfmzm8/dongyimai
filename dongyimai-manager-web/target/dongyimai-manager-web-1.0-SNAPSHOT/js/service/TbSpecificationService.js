//服务层
app.service("TbSpecificationService",function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.post("/TbSpecificationController/findAll");
	}
	//分页 
	this.findPage=function(pageNum,pageSize,tbSpecification){
		return $http.post("/TbSpecificationController/findPage?pageNum="+pageNum+"&pageSize="+pageSize,tbSpecification);
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbSpecificationController/findOne?id="+id);
	}
	//增加、修改
	this.save=function (tbSpecification) {
		return $http.post("/TbSpecificationController/save",tbSpecification);
	}
	//删除
	this.delete=function(ids){
		return $http.post("/TbSpecificationController/delete?ids="+ids);
	}
});
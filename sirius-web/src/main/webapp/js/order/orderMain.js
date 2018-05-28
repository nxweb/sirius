var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken
jQuery(function($){
	ctx = $('#ctx').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	var queryParams = {};
	queryParams[csrfParameter]=csrfToken;
	
	$('#orderDataGrid').datagrid({    
	    url:ctx+'/busOrder/queryTodoOrders',
	    queryParams:queryParams,
	    loadMsg:'数据装载中......',
		iconCls : 'icon-mylist',
		rownumbers : true,
		singleSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20, 30],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : true,
	    columns:[[    
	        {field:'id',title:'id',width:100,checkbox:true},    
	        {field:'orderNo',title:'订单编号',width:200},
	        {field:'taskVo.assignee',title:'任务状态',width:90,
        	 formatter: function(value,row,index) {
				  if(value) {
					  return "<span style='color:green;'>待办理</span>";
				  }else {
					  return "<span style='color:red;'>待签收</span>";
				  }
			  }},
	        {field:'status',title:'审批环节',width:90},
	        {field:'customerName',title:'客户名',width:200},
	        {field:'amount',title:'金额',width:100},
	        {field:'remark',title:'备注',width:100}
	    ]],
	    toolbar: '#orderDataGridToolbar'
	});  
	
	clearSelected = function(){
		$('#orderDataGrid').datagrid('unselectAll');
	}
	
	addOrEditOrderTab = function(){
		parent.addTab('新增订单','/busOrder/myOrderCommonPage');
	};
	
	clearOrderCondition = function(){
		$('#queryOrderForm').clearForm();
	};
	
	queryOrderData = function(){
		var formData={};
		var formDataArr = $('#queryOrderForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#orderDataGrid').datagrid('load',formData);
	};
});

window.top["reload_order_todolist"]=function(){
	$('#orderDataGrid').datagrid("reload");
};
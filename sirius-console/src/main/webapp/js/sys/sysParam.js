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
	
	$('#sysParamDataGrid').datagrid({    
	    url:ctx+'/sysParam/queryPagedSysParams',
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
	        {field:'paramKey',title:'参数名称',width:200},
	        {field:'paramValue',title:'参数值',width:200},
	        {field:'paramRemark',title:'参数说明',width:100}
	    ]],
	    toolbar: '#sysParamDataGridToolbar'
	});  
	
	clearSelected = function(){
		$('#sysParamDataGrid').datagrid('unselectAll');
	}
	
	removeSysParam = function(){
		var selectedRows = $('#sysParamDataGrid').datagrid('getSelections');
		var id=0;
		var paramKey = '';
		if(!selectedRows || selectedRows.length<=0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			id=selectedRows[0].id;
			paramKey = selectedRows[0].paramKey;
		}
		
		$.messager.confirm('系统确认', '确认删除吗？', function(r){
			if (r){
				var removeParams = {};
				removeParams[csrfParameter]=csrfToken;
				removeParams['id']=id;
				removeParams['paramKey']=paramKey;
				
				$.ajax({
					url:ctx+'/sysParam/removeSysParamById',
					type:'post',
					data:removeParams,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','删除失败了！','error');
						querySysParamData();
					},
					success:function(res){
						if(res && res.code==200){
							$.messager.alert('系统消息','删除成功！','info');
							$('#sysParamDataGrid').datagrid('load');
						}else{
							$.messager.alert('系统消息','删除失败！','error');	
						}
					}
				});
			}
		});
	};
	
	addOrEditSysParamDialog = function(operationType){
		var title;
		if(operationType=='add'){
			title = '新增参数';
			$('#sysParamForm').form('clear');
			$('#sysParamForm input[name="'+csrfParameter+'"]').val(csrfToken);
		}else if(operationType=='edit'){
			title = '修改参数';
			var selectedRows = $('#sysParamDataGrid').datagrid('getSelections');
			if(!selectedRows || selectedRows.length==0){
				$.messager.alert('系统消息','请选择数据！','warning');
				return;
			}else if(selectedRows.length>1){
				$.messager.alert('系统消息','请选择一条数据！','warning');
				return;
			}else{
				$('#sysParamForm').form('load',selectedRows[0]);
			}
		}
		
		$('#sysParamDiv').show().dialog({    
		    title: title,    
		    width: 500,    
		    height: 220,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					addOrModifySysParam();
                }
			},{
				text:'取消',
				handler:function(){
					$('#sysParamDiv').dialog('close');
				}
			}]
		});    
		$('#sysParamDiv').dialog('refresh');
	};
	
	addOrModifySysParam = function(){
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        	    $('#sysParamDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#sysParamDataGrid').datagrid('reload');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/sysParam/addOrModifySysParam',
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
                //clearForm: true,        // clear all form fields after successful submit 
		        //resetForm: true        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		}; 
		 
		$('#sysParamForm').ajaxSubmit(options);
	};
	
	clearSysParamQueryCondition = function(){
		$('#querySysParamConditionForm').clearForm();
	};
	
	querySysParamData = function(){
		var formData={};
		var formDataArr = $('#querySysParamConditionForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#sysParamDataGrid').datagrid('load',formData);
	};
});
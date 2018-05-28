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
	
	$('#resourceDg').datagrid({    
	    url:ctx+'/secuResource/queryPagedSecuResources',
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
	        {field:'resourceName',title:'名称',width:200},
	        {field:'resourceCode',title:'编码',width:200},
	        {field:'resourceType',title:'类型',width:100,
	         formatter: function(value,row,index){
	        	 if(value && value==10){
	        		 return '菜单';
	        	 }else if(value && value==20){
	        		 return '操作';
	        	 }else if(value && value==30){
	        		 return '独立页面';
	        	 }
	         }
	        },
	        {field:'resourceUrl',title:'路径',width:410},
	        {field:'resourceDesc',title:'说明',width:160}
	    ]],
	    toolbar: '#resourceDgToolbar'
	});  
	
	var dataDictionaryResourceType = loadDataDictionary('resourceType');
	
	$('#queryConditionResForm #resourceType').combobox({    
	    valueField:'attributeValue',    
	    textField:'attributeMeaning',
	    panelHeight:'70',
	    editable:false,
	    data:dataDictionaryResourceType
	});
	
	$('#resourceForm #resourceType').combobox({    
	    valueField:'attributeValue',    
	    textField:'attributeMeaning',
	    panelHeight:'70',
	    editable:false,
	    data:dataDictionaryResourceType
	});
	
	clearSelected = function(){
		$('#resourceDg').datagrid('unselectAll');
	}
	
	removeResource = function(){
		var selectedRows = $('#resourceDg').datagrid('getSelections');
		var resourceId=0;
		if(!selectedRows || selectedRows.length==0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			resourceId=selectedRows[0].id;
		}
		
		$.messager.confirm('系统确认', '确认删除吗？', function(r){
			if (r){
				var removeParams = {};
				removeParams[csrfParameter]=csrfToken;
				removeParams['resourceId']=resourceId;
				
				$.ajax({
					url:ctx+'/secuResource/removeResourceById',
					type:'post',
					data:removeParams,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','删除资源失败了！','error');
					},
					success:function(res){
						if(res && res.code==200){
							$('#resourceDg').datagrid('reload');
							console.log('删除资源成功');
						}else{
							$.messager.alert('系统消息','删除资源失败了！','error');	
						}
					}
				});
			}
		});
	};
	
	addOrEditResourceDialog = function(operationType){
		var title;
		if(operationType=='add'){
			title = '新增资源';
			$('#resourceForm').form('clear');
			$('#resourceForm input[name="'+csrfParameter+'"]').val(csrfToken);
		}else if(operationType=='edit'){
			title = '修改资源';
			var selectedRows = $('#resourceDg').datagrid('getSelections');
			if(!selectedRows || selectedRows.length==0){
				$.messager.alert('系统消息','请选择数据！','warning');
				return;
			}else if(selectedRows.length>1){
				$.messager.alert('系统消息','请选择一条数据！','warning');
				return;
			}else{
				$('#resourceForm').form('load',selectedRows[0]);
			}
		}
		
		$('#resourceDiv').show().dialog({    
		    title: title,    
		    width: 500,    
		    height: 220,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					addOrModifyResource();
                }
			},{
				text:'取消',
				handler:function(){
					$('#resourceDiv').dialog('close');
				}
			}]
		});    
		$('#resourceDiv').dialog('refresh');
	};
	
	var addOrModifyResource = function(){
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        	    $('#resourceDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#resourceDg').datagrid('reload');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/secuResource/addOrModifyResource',
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
                //clearForm: true,        // clear all form fields after successful submit 
		        //resetForm: true        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		}; 
		 
		$('#resourceForm').ajaxSubmit(options);
	};
	
	clearQueryCondition = function(){
		$('#queryConditionResForm').clearForm();
		$('#queryConditionResForm #resourceType').combobox('clear');
	};
	
	queryData = function(){
		var formData={};
		var formDataArr = $('#queryConditionResForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#resourceDg').datagrid('load',formData);
	};
});
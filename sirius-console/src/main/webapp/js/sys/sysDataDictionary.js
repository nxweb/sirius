var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken;
jQuery(function($){
	ctx = $('#ctx').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	var queryParams = {};
	queryParams[csrfParameter]=csrfToken;
	
	$('#sysDataDictionaryDataGrid').datagrid({    
	    url:ctx+'/sysDataDictionary/queryPagedSysDataDictionary',
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
	        {field:'dicName',title:'字典名称',width:200},
	        {field:'dicMeaning',title:'字典含义',width:200},
	        {field:'attributeValue',title:'属性值',width:200},
	        {field:'attributeMeaning',title:'属性值含义',width:200},
	        {field:'remark',title:'备注',width:200}
	    ]],
	    toolbar: '#sysDataDictionaryDataGridToolbar'
	});  
	
	clearSelected = function(){
		$('#sysDataDictionaryDataGrid').datagrid('unselectAll');
	}
	
	removeSysParam = function(){
		var selectedRows = $('#sysDataDictionaryDataGrid').datagrid('getSelections');
		var id=0;
		var dicName = '';
		if(!selectedRows || selectedRows.length<=0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			id=selectedRows[0].id;
			dicName = selectedRows[0].dicName;
		}
		
		$.messager.confirm('系统确认', '确认删除吗？', function(r){
			if (r){
				var removeParams = {};
				removeParams[csrfParameter]=csrfToken;
				removeParams['id']=id;
				removeParams['dicName']=dicName;
				
				$.ajax({
					url:ctx+'/sysDataDictionary/removeSysDataDictionaryById',
					type:'post',
					data:removeParams,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','删除失败了！','error');
						querySysDataDictionary();
					},
					success:function(res){
						if(res && res.code==200){
							$.messager.alert('系统消息','删除成功！','info');
							$('#sysDataDictionaryDataGrid').datagrid('load');
						}else{
							$.messager.alert('系统消息','删除失败！','error');	
						}
					}
				});
			}
		});
	};
	
	addOrEditSysDataDictionaryDialog = function(operationType){
		var title;
		if(operationType=='add'){
			title = '新增数据字典';
			$('#sysDataDictionaryForm').form('clear');
			$('#sysDataDictionaryForm input[name="'+csrfParameter+'"]').val(csrfToken);
		}else if(operationType=='edit'){
			title = '修改数据字典';
			var selectedRows = $('#sysDataDictionaryDataGrid').datagrid('getSelections');
			if(!selectedRows || selectedRows.length==0){
				$.messager.alert('系统消息','请选择数据！','warning');
				return;
			}else if(selectedRows.length>1){
				$.messager.alert('系统消息','请选择一条数据！','warning');
				return;
			}else{
				$('#sysDataDictionaryForm').form('load',selectedRows[0]);
			}
		}
		
		$('#sysDataDictionaryDiv').show().dialog({    
		    title: title,    
		    width: 500,    
		    height: 220,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					addOrModifySysDataDictionary();
                }
			},{
				text:'取消',
				handler:function(){
					$('#sysDataDictionaryDiv').dialog('close');
				}
			}]
		});    
		$('#sysDataDictionaryDiv').dialog('refresh');
	};
	
	addOrModifySysDataDictionary = function(){
		var options = { 
	        beforeSubmit:  function(){
	        }, 
	        success:function(data){
	        	if(data && data.code==200){
	        	    $('#sysDataDictionaryDiv').dialog('close');
	        	    $.messager.alert('系统消息','处理成功！','info');
	        	    $('#sysDataDictionaryDataGrid').datagrid('reload');
	        	}else{
	        		$.messager.alert('系统消息','处理失败！','error');
	        	}
	        },  
	        error:function(){
	        	$.messager.alert('系统消息','处理失败了！','error');
	        },
	        url: ctx+'/sysDataDictionary/addOrModifySysDataDictionary',
	        type:'post', 
	        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
            //clearForm: true,        // clear all form fields after successful submit 
	        //resetForm: true        // reset the form after successful submit 
	        // $.ajax options can be used here too, for example: 
	        timeout:3000 
		}; 
		 
		$('#sysDataDictionaryForm').ajaxSubmit(options);
	};
	
	clearSysDataDictionaryCondition = function(){
		$('#querySysDataDictionaryForm').clearForm();
	};
	
	querySysDataDictionary = function(){
		var formData={};
		var formDataArr = $('#querySysDataDictionaryForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#sysDataDictionaryDataGrid').datagrid('load',formData);
	};
});
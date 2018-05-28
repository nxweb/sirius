var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken;
var orderId;
var taskDefinitionKey;
var taskId;
jQuery(function($){
	ctx = $('#ctx').val();
	orderId = $('#orderId').val();
	taskDefinitionKey = $('#taskDefinitionKey').val();
	taskId = $('#taskId').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	loadApprovalHistory = function(){
		var queryParams = {};
		queryParams[csrfParameter]=csrfToken;
		queryParams['orderId']=orderId;
		
		$('#approvalHistory').datagrid({
	        url : ctx + '/busOrder/queryOrderApprovalHistory',
	        queryParams:queryParams,
	        loadMsg:'数据装载中......',
			iconCls : 'icon-mylist',
			rownumbers : true,
			border : true,
			columns : 
			[ [{
		    	title : '审批节点',
				field : 'taskName',
				width: 80
			  }, {
				title : '审批人',
				field : 'approvalUser',
				width: 70
			  }, {
				title : '审批结果',
				field : 'approvalType',
				formatter: function(value,row,index) {
					if(value == 'pass') {
						return "<span style='color:green;'>通过</span>";
					}else if(value == 'reject') {
						return "<span style='color:red;'>驳回</span>";
					}else if(value == 'refuse') {
						return "<span style='color:red;'>拒绝</span>";
					}else if(value == 'abandon') {
						return "<span style='color:red;'>废弃</span>";
					}else if(value == 'activate') {
						return "<span style='color:green;'>激活</span>";
					}else {
						return value;
					}
				},
				width: 65
			  }, {
				  title: '审批时间',
				  field: 'approvalTime',
				  width: 130,
				  formatter: function(value,row,index) {
					  return formatDateTimebox(value);
				  }
			  }, {
				  title: '审批意见',
				  field: 'fullMessage',
				  width: 300
			  },{
				  title: '审批附件',
				  field: 'attachments',
				  formatter: function(value,row,index) {
					  if(value && value.length>0){
						  return '<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="viewAttachments('+JSON.stringify(value).replace(/"/g, '&quot;')+');">查看</a>';  
					  }else{
						  return '';
					  }
				  },
				  width: 60
			  }
			]],
			onLoadSuccess:function(data){
		    	if(data && data.total>0){
		    		$('#approvalHistory').datagrid('resize',
		    				{
		    			height:80+(data.total)*25
		    				});
		    	}
		    }
	    });
	};
	
	viewAttachments = function(files){
		var atthtml ='';
		$('#attachmentsDialogUl').html('');
		$.each(files,function(i,file){
			var suffix = file.fileUrl.substr(file.fileUrl.lastIndexOf(".") + 1);
			if(suffix){
				suffix = suffix.toLowerCase();
			}
			if ("jpg" == suffix || "jpeg" == suffix  || "png" == suffix) {
				atthtml=atthtml + '<li style="display: inline-block;position:relative;margin-right:8px;"><img style="width: 100px" src="'+file.fileFullUrl.replace(/\s/g,"%20")+'" alt="'+file.fileName+'" fileUrl="'+file.fileUrl+'"></li>';
			}else{
				var fileFullUrl = file.fileFullUrl;
				var reg = /\//g;
				fileFullUrl = fileFullUrl.replace(reg, "\/");
				fileFullUrl.trim();
				atthtml=atthtml + "<li style='display: inline-block;position:relative;margin-right:8px;'><a href='javascript:void(0);' onclick='window.open(\"" + fileFullUrl + "\", \"" + '_blank' + "\")'>" + file.fileName + "</a></li>";
			}
		});
		$('#attachmentsDialogUl').append(atthtml);
		$('#attachmentsDialogUl').viewer();
		
		$('#attachmentsDialog').show().dialog({    
		    title: '查看附件',    
		    width: 400,    
		    height: 240,    
		    closed: false,    
		    cache: false,    
		    modal: true   
		});    
		$('#attachmentsDialog').dialog('refresh');
	};
	
	submitApproval = function(){
		if(!taskId && !orderId){//新增
			var isValid = $('#orderAddOrEditForm').form('validate');
			if(!isValid){
				$.messager.alert('提示信息', '请检查数据填写！','warning');
				return;
			}
			
			$.messager.progress();
			var formData={};
			var formDataArr1 = $('#orderAddOrEditForm').serializeArray();
			$.each(formDataArr1, function() {
				formData[this.name] = this.value;
			});
			
			var formData2={};
			var formDataArr2 = $('#approvalForm').serializeArray();
			$.each(formDataArr2, function() {
				formData2[this.name] = this.value;
			});
			formData['approvalComment']=formData2;
			
			var approvalAttachments =[];
			$('#attachementsUl img').each(function(){
				var fileFullUrl = $(this).attr('src');
				var fileName = $(this).attr('alt');
				var fileUrl = $(this).attr('fileUrl');
				approvalAttachments.push({'fileFullUrl':fileFullUrl,'fileName':fileName,'fileUrl':fileUrl});
			});
			formData['approvalAttachments']=approvalAttachments;
			
			var headers = {};
			headers[csrfHeader] = csrfToken;
			
			$.ajax({
				'url':ctx + '/busOrder/addOrder',
				'headers': headers,
				'type':'post',
				'data':JSON.stringify(formData),
				'contentType':'application/json;charset=UTF-8',
				'dataType':'json',
				'error':function(){
					$.messager.progress('close');
					$.messager.alert('提示信息', '出错了！','warning');
				},
				'success':function(res){
					$.messager.progress('close');
					if(res && res.code==200){
						$.messager.alert('提示信息', '提交成功！','info',function(){
							window.top.reload_order_todolist.call();
							parent.removeTab("新增订单");
						});
					}else{
						$.messager.alert('提示信息', '提交失败！','info');
					}
				}
			});
		}else if(taskDefinitionKey=='saSubmit'){//员工申请
			var isValid = $('#orderAddOrEditForm').form('validate');
			if(!isValid){
				$.messager.alert('提示信息', '请检查数据填写！','warning');
				return;
			}
			
			$.messager.progress();
			var formData={};
			var formDataArr1 = $('#orderAddOrEditForm').serializeArray();
			$.each(formDataArr1, function() {
				formData[this.name] = this.value;
			});
			
			var formData2={};
			var formDataArr2 = $('#approvalForm').serializeArray();
			$.each(formDataArr2, function() {
				formData2[this.name] = this.value;
			});
			formData['approvalComment']=formData2;
			
			var approvalAttachments =[];
			$('#attachementsUl img').each(function(){
				var fileFullUrl = $(this).attr('src');
				var fileName = $(this).attr('alt');
				var fileUrl = $(this).attr('fileUrl');
				approvalAttachments.push({'fileFullUrl':fileFullUrl,'fileName':fileName,'fileUrl':fileUrl});
			});
			formData['approvalAttachments']=approvalAttachments;
			
			$.ajax({
				'url':ctx + '/busOrder/submitOrderApproval',
				'type':'post',
				'data':JSON.stringify(formData),
				'contentType':'application/json;charset=UTF-8',
				'dataType':'json',
				'error':function(){
					$.messager.progress('close');
					$.messager.alert('提示信息', '出错了！','warning');
				},
				'success':function(res){
					$.messager.progress('close');
					if(res && res.code==200){
						$.messager.alert('提示信息', '提交成功！','info',function(){
							window.top.reload_order_todolist.call();
							parent.removeTab("审批订单");
						});
					}else{
						$.messager.alert('提示信息', '提交失败！','warning');
					}
				}
			});
		}else{
			$.messager.progress();
			var formData={};
			
			var formDataArr1 = $('#commisssionApplyForm').serializeArray();
			$.each(formDataArr1, function() {
				formData[this.name] = this.value;
			});
			
			var formData2={};
			var formDataArr2 = $('#approvalCommissionForm').serializeArray();
			$.each(formDataArr2, function() {
				formData2[this.name] = this.value;
			});
			formData['approvalComment']=formData2;
			
			var approvalAttachments =[];
			$('#attachementsUl img').each(function(){
				var fileFullUrl = $(this).attr('src');
				var fileName = $(this).attr('alt');
				var fileUrl = $(this).attr('fileUrl');
				approvalAttachments.push({'fileFullUrl':fileFullUrl,'fileName':fileName,'fileUrl':fileUrl});
			});
			formData['approvalAttachments']=approvalAttachments;
			
			var tdfkey = $('.reject_select option:selected').attr('tdfkey');
			console.log(tdfkey);
			formData['destActivityId']=tdfkey;
			$.ajax({
				'url':ctx + '/busChannelCommissionApplication/submitCommissionApproval',
				'type':'post',
				'data':JSON.stringify(formData),
				'contentType':'application/json;charset=UTF-8',
				'dataType':'json',
				'error':function(){
					$.messager.progress('close');
					$.messager.alert('提示信息', '出错了！','warning');
				},
				'success':function(res){
					$.messager.progress('close');
					if(res && res.code==200){
						$.messager.alert('提示信息', '处理成功！','info',function(){
							if(!taskId && commissionApprovalId){
							    window.top.reload_comission_refuseOrAbandon.call();
							    parent.removeTab("渠道咨询服务费-激活");
							}else{
								window.top.reload_comission_todolist.call();
								parent.removeTab("渠道咨询服务费-审批");
							}
						});
					}else{
						$.messager.alert('提示信息', '提交失败！','warning');
					}
				}
			});
		}
	};
	
	if(orderId){
		if(taskDefinitionKey=='saSubmit'){
			$('.modify').show();
			$('.view').hide();
		}else{
			$('.modify').hide();
			$('.view').show();
		}
		loadApprovalHistory();
	}else{
		$('.modify').show();
		$('.view').hide();
	}
});
var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken;
var processDefinitionId;
jQuery(function($){
	ctx = $('#ctx').val();
	processDefinitionId = $('#processDefinitionId').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	var imageUrl =ctx+ '/processDefinition/generateDiagramImageProcessDefinition?processDefinitionId=' + processDefinitionId;
	
	$(".trace_img").attr("src", imageUrl);
    
    //加载各节点信息，最终实现，在点击图片上的各节点时，出现高亮    
    setTimeout(function(){    
        loadProcessTrace(processDefinitionId);    
    },200);
    
    /**   
     * 加载流程中各节点的信息    
     * @param pid : 流程定义的key   
     */    
    loadProcessTrace = function(processDefinitionId){   
    	var traceParams = {};
    	traceParams[csrfParameter]=csrfToken;
    	traceParams['processDefinitionId']=processDefinitionId;
    	
    	$.ajax({
			url:ctx+'/processDefinition/getProcessTrace',
			type:'post',
			data:traceParams,
			dataType:'json',
			error:function(){
				$.messager.alert('系统消息','加载流程图节点信息失败了！','error');
			},
			success:function(res){
				if(res && res.code==200){
					var infos = res.data;
					var positionHtml = '';
					$.each(infos,function(){    
						var $positionDiv = $('<div/>', {
			                'class': 'activity-attr'
			            }).css({
			                position: 'absolute',
			                left: (this.x - 1 + 12),
			                top: (this.y + 1),
			                width: (this.width - 2),
			                height: (this.height - 2),
			                backgroundColor: 'black',
			                opacity: 0,
			                zIndex: $.fn.qtip.zindex - 1
			            });
			            
			        	$positionDiv.insertAfter($(".img_content"));
			        	var vars ={};
			        	vars['任务标识']= this.actId;
			        	vars['任务名称']= this.name;
			        	vars['任务所属角色']= this.role;
			        	
			        	$positionDiv.data("vars", vars);
		            });

                    $(positionHtml).insertAfter($(".img_content"));
			        
			        $('.activity-attr').each(function(i, v) {
			            $(this).qtip({
		                    content: function() {
		                        var vars = $(this).data('vars');
		                        var tipContent = "<table class='need-border' cellpadding='5' cellspacing='0' style='font-size:10px;'>";
		                        $.each(vars, function(varKey, varValue) {
		                            if (varValue) {
		                                if("任务所属角色" == varKey || "任务处理人" == varKey) {
		                                	tipContent += "<tr style='background-color:#E5ECF9;'><td class='label' style='text-align: right;font-weight: bold;'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
		                                }else {
		                                	tipContent += "<tr><td class='label' style='text-align: right;font-weight: bold;'>" + varKey + "</td><td>" + varValue + "<td/></tr>";	                                	
		                                }
		                            }
		                        });
		                        tipContent += "</table>";
		                        return tipContent;
		                    },
		                    position: {
		                        at: 'bottom left',
		                        adjust: {
		                            x: 3
		                        }
		                    }
		                });
			        });
				}else{
					$.messager.alert('系统消息','加载流程图节点信息失败！','error');	
				}
			}
		});  
    };
});
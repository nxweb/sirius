var ctx;
jQuery(function($){
	ctx = $('#ctx').val();
	
	var iWidth = window.innerWidth;
	var iHeight = window.innerHeight;
	$(".images_1").css("height",iHeight+"px")
	$(".images_1 img").css("width",iWidth+"px");
	$(".images_1 img").css("height",iHeight+"px");
	var index = 0;
	
	var timer = setInterval(function(){							
		index++;
		$(".images_1 img").eq(index).fadeIn(1000).siblings().fadeOut(1000);
		console.log($(".images_1 img").eq(index).attr("alt"))			
		if (index == 2 ) {
			console.log(2111)
			index = -1;
		}
	},5000);
	
	changeImg = function(){
		var cTime = new Date().getTime();
		$('#validateCodeImg').attr('src',ctx+'/home/getKaptcha?t='+cTime);
	};
});
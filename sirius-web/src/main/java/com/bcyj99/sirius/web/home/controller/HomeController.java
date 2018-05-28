package com.bcyj99.sirius.web.home.controller;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 这个是master
 * @author hp
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private Producer kaptchaProducer; 

	@RequestMapping("/loginPage")
	public String loginPage(Model model,HttpServletRequest request){
		String username = (String) request.getAttribute("username");
		String password = (String) request.getAttribute("password");
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		return "/common/login";
	}
	
	@RequestMapping("/reloginPage")
	public void reloginPage(HttpServletResponse response,Model model,HttpServletRequest request){
		PrintWriter out = null;
		String ctx = request.getContextPath();
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.println("<html>");      
	        out.println("<script>");      
	        out.println("window.open('"+ctx+"/home/loginPage','_top')");      
	        out.println("</script>");      
	        out.println("</html>");
	        out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/indexPage")
	public String indexPage(){
		return "/common/index";
	}
	
	@RequestMapping("/getKaptcha")  
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{  
        response.setDateHeader("Expires",0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String capText = kaptchaProducer.createText();  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        BufferedImage bi = kaptchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  
	
	@RequestMapping("/accessDeniedPage")
	public String accessDeniedPage(){
		return "/common/accessDenied";
	}
}

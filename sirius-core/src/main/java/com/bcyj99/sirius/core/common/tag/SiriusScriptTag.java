package com.bcyj99.sirius.core.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.bcyj99.sirius.core.common.utils.MyPropertyPlaceholderConfigurer;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;

public class SiriusScriptTag extends SimpleTagSupport {
	/**
	 * JS路径
	 */
	private String src;

	/**
	 * 字符集
	 */
	private String charset;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 类型
	 */
	private String type;
	

	@Override
	public void doTag() throws JspException, IOException {
		JspContext ctx = getJspContext();
		JspWriter out = ctx.getOut();
		out.print("<script ");
		if (SiriusStringUtils.isNotEmpty(type))
		{
			out.print(" type=\"");
			out.print(type);
			out.print("\"");
		}
		else
		{
			out.print(" type=\"text/javascript\"");
		}
		out.print(" src=\"");

		String version = (String) MyPropertyPlaceholderConfigurer.getProperty("version.no");
		if (src != null && src.indexOf("?") < 0)
		{
			out.print(src);
			out.print("?version=");
			out.print(version);
		}
		else
		{
			out.print(src);
			out.print("&version=");
			out.print(version);
		}

		out.print("\"");
		if (SiriusStringUtils.isNotEmpty(charset))
		{
			out.print(" charset=\"");
			out.print(charset);
			out.print("\"");
		}
		if (SiriusStringUtils.isNotEmpty(language))
		{
			out.print(" language=\"");
			out.print(language);
			out.print("\"");
		}
		out.print("></script>");
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

package com.miaxis.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * 禁止客户端缓存
 *
 */
public class CacheFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Cache-Control","Public");
		res.setDateHeader("Expires", 0);
		filter.doFilter(request, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
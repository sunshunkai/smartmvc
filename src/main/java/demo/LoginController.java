package demo;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

public class LoginController {

	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println("LoginController的toLogin方法");
		return "login";
	}

	@RequestMapping("/login.do")
	public String login() {
		System.out.println("LoginController的login方法");

		/*
		 * 视图名如果以"redirect:"开头， 表示重定向。
		 */
		return "redirect:toWelcome.do";
	}

	@RequestMapping("/toWelcome.do")
	public String toWelcome() {
		return "welcome";
	}

}

package demo;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

public class LoginController {

	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println("LoginController��toLogin����");
		return "login";
	}

	@RequestMapping("/login.do")
	public String login() {
		System.out.println("LoginController��login����");

		/*
		 * ��ͼ�������"redirect:"��ͷ�� ��ʾ�ض���
		 */
		return "redirect:toWelcome.do";
	}

	@RequestMapping("/toWelcome.do")
	public String toWelcome() {
		return "welcome";
	}

}

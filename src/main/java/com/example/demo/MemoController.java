package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class MemoController {
	
	//ホーム画面にアクセスされたとき
	@RequestMapping("/home")
	public String sample(Model model) {
		model.addAttribute("title1", "忘れ物防止メモ帳");
		model.addAttribute("title2", "体調-超-管理");
		return "home";
	}
	
	
	
}

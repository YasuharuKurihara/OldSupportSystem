package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.EntForm;

@Controller
public class MemoController {
	
	//ホーム画面にアクセスされたとき
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title1", "忘れ物防止メモ帳");
		model.addAttribute("title2", "体調-超-管理");
		return "home";
	}
	
	@RequestMapping("/memo/view")
	public String view(Model model) {

		List<EntForm> list = memodao.searchDb();
		model.addAttribute("dbList", list);
		model.addAttribute("title", "メモ一覧");
		return "view";
	}
	
}

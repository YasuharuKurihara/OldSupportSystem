package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.MemoDao;
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
	
	private final MemoDao memodao;
	
	@Autowired
	public MemoController(MemoDao memodao) {
		this.memodao = memodao;
	}
	
	//メモ帳機能にアクセスされたとき
	@RequestMapping("/memo/view")
	public String view(Model model, Input input) {
		//名前が決まれば変更
		
		EntForm entForm = new EntForm();
		entForm.setMemo(input.getMemo());
		entForm.setTime(input.getTime());
		memodao.insertDb(entForm);
		
		List<EntForm> list = memodao.searchDb();
		model.addAttribute("dbList", list);
		model.addAttribute("title", "メモ一覧");
		return "memo/view";
	}
	
	
	@RequestMapping("/memo/add")
	public String add(Model model, Input input) {
		model.addAttribute("title", "メモ　新規作成");
		return "/memo/add";
	}
	
	@RequestMapping("/memo/addConfirm")
	public String addConfirm(@Validated Input input, Model model,BindingResult result) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "メモ　新規作成");
			return "memo/add";
		}
		return "memo/addConfirm";
	}
	
	
}
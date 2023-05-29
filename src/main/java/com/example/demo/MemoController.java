package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.MemoDao;
import com.example.demo.entity.EntForm;
@Controller
public class MemoController {
	
	//ホーム画面にアクセスされたとき
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "忘れ物防止メモ帳");
//		model.addAttribute("title2", "体調-超-管理");
		return "home";
	}
	
	private final MemoDao memodao;
	
	@Autowired
	public MemoController(MemoDao memodao) {
		this.memodao = memodao;
	}
	
	//メモ帳機能にアクセスされたとき
	@RequestMapping("/view")
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
	
	
<<<<<<< HEAD
	@RequestMapping("/add")
	public String add(Model model) {
=======
	@RequestMapping("/memo/add")
	public String add(Model model,Input input) {
>>>>>>> 0516985c1a4ec57a41d6ae1864da31238540049c
		model.addAttribute("title", "メモ　新規作成");
		return "/memo/add";
	}
	
	@RequestMapping("/addConfirm")
	public String addConfirm(Model model,@Validated Input input, BindingResult result) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "メモ　新規作成");
			return "memo/add";
		}
		return "memo/addConfirm";
	}
	
	@RequestMapping("/delete/{id}")
	public String delconfirm(@PathVariable Long id) {
		memodao.deleteDb(id);
		return "redirect:/view";
	}
	
	@RequestMapping("/deleteConfirm/{id}")
	public String destory(@PathVariable Long id) {
		memodao.deleteDb(id);
		return "redirect:/view";
	}
}
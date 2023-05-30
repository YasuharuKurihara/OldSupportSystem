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
	public String home(Model model, Input input) {
		model.addAttribute("title1", "忘れ物防止メモ帳");
		return "home";
	}

	private final MemoDao memodao;

	@Autowired
	public MemoController(MemoDao memodao) {
		this.memodao = memodao;
	}

	//メモ帳機能にアクセスされたとき
	@RequestMapping("/memo/view")
	public String view(Input input, Model model) {
		List<EntForm> list = memodao.searchDb();
		model.addAttribute("dbList", list);
		model.addAttribute("title", "メモ一覧");
		return "memo/view";
	}

	@RequestMapping("/memo/add")
	public String add(Model model, Input input) {
		model.addAttribute("title", "メモ 新規作成");
		return "/memo/add";
	}

	@RequestMapping("/memo/addConfirm")
	public String addConfirm(@Validated Input input, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("title", "入力内容　確認");
			return "memo/add";
		}

		model.addAttribute("title", "入力内容　確認");

		EntForm entForm = new EntForm();

		entForm.setMemo(input.getMemo());
		entForm.setTime(input.getTime());

		memodao.insertDb(entForm);
		return "memo/addConfirm";
	}

	@RequestMapping("/memo/addCancel")
	public String addCancel(Model model, Input input) {
		model.addAttribute("title", "メモ 新規作成");
		return "/memo/addCancel";
	}

	@RequestMapping("/del/{id}")
	public String del(@PathVariable Long id, Model model) {

		model.addAttribute("title", "メモ 削除確認画面");
		return "/memo/delete";
	}

	@RequestMapping("/deleteConfirm/{id}")
	public String destory(@PathVariable Long id, Model model) {
		memodao.deleteDb(id);
		return "redirect:/view";
	}

	//更新画面の表示(SELECT)
	@RequestMapping("/edit/{id}")
	public String editView(@PathVariable Long id, Model model) {

		//DBからデータを1件取ってくる(リストの形)
		List<EntForm> list = memodao.selectOne(id);

		//リストから、オブジェクトだけをピックアップ
		EntForm entformdb = list.get(0);

		//スタンバイしているViewに向かって、データを投げる
		model.addAttribute("form", entformdb);
		model.addAttribute("title", "編集");
		return "memo/edit";
	}

	@RequestMapping("/editCancel/")
	public String editCancel(Model model, Input input) {
		model.addAttribute("title", "編集キャンセル確認");
		return "/memo/editCancel";
	}

	//(要)修正箇所　SpringBoot実行時エラーが起こる
	//	@RequestMapping("/editComfirm/{id}/exe")
	//	public String editConfirm(@PathVariable Long id, Model model, Input input) {
	//		model.addAttribute("title", "編集確認画面");
	//		//一覧画面へリダイレクト
	//		return "memo/editComfirm";
	//	}

	@RequestMapping("/editConfirm/{id}")
	public String editComfirm(@PathVariable Long id, Model model, Input input) {

		model.addAttribute("title", "編集内容確認");

		return "memo/editConfirm";
	}

	//更新処理(UPDATE)
	@RequestMapping("/editConfirm/{id}/exe")
	public String editExe(@PathVariable Long id, Model model, Input input) {
		//フォームの値をエンティティに入れ直し
		EntForm entform = new EntForm();
		System.out.println(input.getMemo());//取得できているかの確認
		System.out.println(input.getTime());
		entform.setMemo(input.getMemo());
		entform.setTime(input.getTime());
		//更新の実行
		memodao.updateDb(id, entform);
		//一覧画面へリダイレクト
		return "redirect:/memo/view";
	}

}
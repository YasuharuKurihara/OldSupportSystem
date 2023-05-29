package com.example.demo;

import jakarta.validation.constraints.NotBlank;

public class Input {
	@NotBlank(message="文字を入力してください")
	private String memo;
	
	@NotBlank(message="文字を入力してください")
	private String time;
	


	public String getMemo() {
		return memo;
	}

	public void getMemo(String memo) {
		this.memo = memo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
}

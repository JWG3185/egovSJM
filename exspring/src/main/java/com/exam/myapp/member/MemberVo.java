package com.exam.myapp.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

// Vo: value object, DB에 있는 레코드 하나를 지칭
public class MemberVo {
	
	//표준 명세 Bean Validation 2.0 (JSR 380) 이상부터 지원되는 어노테이션도 있음
	//그럴경우 pom.xml에서 버전 업그레이드 해주기
	//@NotEmpty @NotBlank @Email
	//@Positive @PositiveOrZero @Negative @NegativeOrZero
	//@Future @Past
	
	@NotNull @Size(min = 1)
	private String memId;
	@NotNull @Size(min = 1)
	private String memPass;
	@NotNull @Size(min = 1)
	private String memName;
	
	@PositiveOrZero
	private int memPoint;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPass() {
		return memPass;
	}
	public void setMemPass(String memPass) {
		this.memPass = memPass;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public int getMemPoint() {
		return memPoint;
	}
	public void setMemPoint(int memPoint) {
		this.memPoint = memPoint;
	}
	
}

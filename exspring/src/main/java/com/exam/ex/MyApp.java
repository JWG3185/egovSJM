package com.exam.ex;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//이 클래스의 객체를 생성하여 ma 라는 이름으로 스프링에 등록(value = 생략 가능)
//()부분 다 생략시 클래스이름 첫글자만 소문자로 바꾼 후 등록됨
@Component("ma")
public class MyApp {
	//@Autowired, @Inject, @Resource 스프링에 등록된 객체를 이 변수(속성)에 주입(저장)
	//@Autowired, @Inject: 타입으로 먼저 찾은 후, 이름으로 찾음, 이름을 따로 지정 안할시 변수 이름 찾음
	//@Resource: 이름으로 먼저 찾음, 이름이 맞는게 없으면 타입을 일치하는 것을 찾음
	@Autowired
	private MyService myService;
	
	public void say() {
		System.out.println(myService.getMessage());
	}

	public MyService getMyService() {
		return myService;
	}

	public void setMyService(MyService myService) {
		this.myService = myService;
	}
	
}

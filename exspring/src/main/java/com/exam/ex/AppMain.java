package com.exam.ex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
	
	public static void main(String[] args) {
		//MyApp, MyServiceKo 를 사용하여,
		//콘솔에 "안녕!😁"이 출력되도록 구현
//		MyApp app = new MyApp();
//		app.setMyService(new MyServiceKo());
//		app.say();
		
		//콘솔에 "Hi!😁"이 출력되도록 구현
//		app.setMyService(new MyServiceEn());
//		app.say();
		
		//스프링 == (IoC/DI AOP 기능을 가진) 객체컨테이너 == BeanFactory == ApplicationContext
		//클래스패스 상의 XML파일로부터 설정을 읽어서, 스프링 객체 컨테이너를 생성
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/exam/ex/context.xml");
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
		
		//스프링에 ma라는 이름으로 등록되어 있는 객체를 가져오기
		MyApp app = (MyApp) ctx.getBean("ma");
		//context.xml 설정 파일에서 ma의 myService의 property의 ref를 바꾸기만 하면 값이 바뀜
		//-> 의존성 주입(DI) -> 제어의 역전(IoC: Inversion of Control)
		
		app.say();
	}
}

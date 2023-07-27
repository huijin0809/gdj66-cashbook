package com.goodee.cash.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.ICashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CashController {
	@Autowired
	private ICashService cashService;
	
	@GetMapping("/cashbookCalendar")
	public String calendar(HttpServletRequest session, Model model,
							@RequestParam(required = false, name = "targetYear") Integer targetYear,
							@RequestParam(required = false, name = "targetMonth") Integer targetMonth) {
		// required를 false로 주어야 null값 검사가 가능 // null일시 오늘 날짜 달력 출력
		
		log.debug("\u001B[31m" + "CashController.calendar() param targetYear : " + targetYear + "\u001B[0m");
		log.debug("\u001B[31m" + "CashController.calendar() param targetMonth : " + targetMonth + "\u001B[0m");
		
		// 세션에서 로그인된 memeberId 추출 // 구현 예정..
		// session.getAttribute("loginMember");
		String memberId = "user1";
		
		// 요청된 매개값을 담아 서비스 호출
		Map<String, Object> resultMap = cashService.getCalendar(memberId, targetYear, targetMonth);
		log.debug("\u001B[31m" + "CashController.calendar() resultMap : " + resultMap.toString() + "\u001B[0m");
		
		// view로 넘기기 위해 Model에 담기
		model.addAttribute("resultMap", resultMap);
		
		return "calendar";
	}
}

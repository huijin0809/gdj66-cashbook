package com.goodee.cash.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.cash.mapper.CashMapper;
import com.goodee.cash.vo.Cashbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CashService implements ICashService {
	@Autowired
	private CashMapper cashMapper;
	
	// 달력 출력 + 해당 날짜의 cashbook 정보 조회
	public Map<String, Object> getCalendar(String memberId, Integer targetYear, Integer targetMonth) {
		
		// 첫번째 모델값 구하기 // 달력 출력
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1); // 오늘 날짜의 1일로 셋팅
		
		// 매개값으로 요청 날짜가 넘어왔다면
		if(targetYear != null && targetMonth != null) {
			firstDate.set(Calendar.YEAR, targetYear);
			firstDate.set(Calendar.MONTH, targetMonth);
			// API에서 알아서 month가 12가 들어오면 내년으로 넘어가고, -1이 들어오면 작년으로 넘어감
		}
		// API에서 자동으로 바뀐 년도와 월 정보를 다시 셋팅 // view로 넘기기 위해!
		targetYear = firstDate.get(Calendar.YEAR);
		targetMonth = firstDate.get(Calendar.MONTH);
					
		// 요청된 날짜의 1일의 요일을 이용하여 달력에 출력될 시작 공백 수를 구한다
		int beginBlank = firstDate.get(Calendar.DAY_OF_WEEK) - 1;
		// log.debug("beginBlank : " + beginBlank);
		
		// 요청된 날짜의 월의 마지막 일(30,31 등...) 구하기
		int lastDate = firstDate.getActualMaximum(Calendar.DATE);
		
		// 마지막 일 이후에 출력될 마지막 공백 수를 구한다
		int endBlank = 0;
		if((beginBlank + lastDate) % 7 != 0) { // 출력될 총 칸수가 7로 나누어떨어지지 않으면..
			endBlank = 7 - ((beginBlank + lastDate) % 7);
			// 부족한 칸 수만큼 공백 수를 더해주면 7로 나누어떨어지게 된다
		}
		int totalTd = beginBlank + lastDate + endBlank; // 출력될 총 칸수
		
		// 전월 마지막 일 구하기
		Calendar preDate = Calendar.getInstance();
		preDate.set(Calendar.YEAR, targetYear);
		preDate.set(Calendar.MONTH, targetMonth - 1);
		int preEndDate = preDate.getActualMaximum(Calendar.DATE);
				
		// Map에 담아 넘기기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("targetYear", targetYear);
		resultMap.put("targetMonth", targetMonth);
		resultMap.put("beginBlank", beginBlank);
		resultMap.put("lastDate", lastDate);
		resultMap.put("endBlank", endBlank);
		resultMap.put("totalTd", totalTd);
		resultMap.put("preEndDate", preEndDate);
		resultMap.put("memberId", memberId);
		
		// 두번째 모델값 구하기 // cashbook 정보 조회
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("targetYear", targetYear);
		paramMap.put("targetMonth", targetMonth + 1); // targetMonth에 +1을 해주어야한다
		paramMap.put("memberId", memberId);
		List<Cashbook> cashbookList = cashMapper.selectCashbookListByMonth(paramMap);
		// Map에 담아 넘기기
		resultMap.put("cashbookList", cashbookList);
		
		log.debug("\u001B[31m" + "CashService.getCalendar() resultMap : " + resultMap.toString() + "\u001B[0m");
		
		return resultMap;
	}
}

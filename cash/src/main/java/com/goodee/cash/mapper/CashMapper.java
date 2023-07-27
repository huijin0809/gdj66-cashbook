package com.goodee.cash.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.cash.vo.Cashbook;

@Mapper
public interface CashMapper {
	List<Cashbook> selectCashbookListByMonth(Map<String, Object> paramMap);
}

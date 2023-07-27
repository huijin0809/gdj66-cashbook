package com.goodee.cash.vo;

import lombok.Data;

@Data
public class Cashbook {
	private int cashbookNo;
	private String memberId;
	private String category;
	private String cashbookDate;
	private int price;
	private String memo;
	private String updatedate;
	private String createdate;
}

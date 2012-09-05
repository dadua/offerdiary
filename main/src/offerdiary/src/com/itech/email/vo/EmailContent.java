package com.itech.email.vo;

public class EmailContent {

	
	public static final String SELECT_SUBHEADING="span#mainHeadingBelowHeader";
	public static final String SELECT_SALUTATION="span#salutation";
	public static final String SELECT_NAME="span#name";
	public static final String SELECT_PARA1="span#messageParagraph1";
	public static final String SELECT_PARA2="span#messageParagraph2";
	public static final String SELECT_PARA3="span#messageParagraph3";
	
	private static final String EMPTY="";
	
	private String subHeading=EMPTY;
	private String salutation=EMPTY;
	private String name=EMPTY;
	private String para1=EMPTY;
	private String para2=EMPTY;
	private String para3=EMPTY;
	
	public String getSubHeading() {
		return subHeading;
	}
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPara1() {
		return para1;
	}
	public void setPara1(String para1) {
		this.para1 = para1;
	}
	public String getPara2() {
		return para2;
	}
	public void setPara2(String para2) {
		this.para2 = para2;
	}
	public String getPara3() {
		return para3;
	}
	public void setPara3(String para3) {
		this.para3 = para3;
	}
}

package com.itech.redwine.parser;

public class RedWineOffer {
 
 private String merchantName;
 private String description;
 private String expiryDate;

 @Override
 public String toString(){
	 String toString ="[Merchant Name : "+merchantName+"; Description : "+description+"; Expiry Date: "+expiryDate+"]";
	 return toString;
 }
 
 public String getMerchantName() {
	return merchantName;
}
public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
}
public String getDescription() {
	return description;
}
public void setDescription(String discriptionn) {
	this.description = discriptionn;
}
public String getExpiryDate() {
	return expiryDate;
}
public void setExpiryDate(String expiryDate) {
	this.expiryDate = expiryDate;
}
}

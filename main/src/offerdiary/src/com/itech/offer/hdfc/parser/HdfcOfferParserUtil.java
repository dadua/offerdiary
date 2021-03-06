package com.itech.offer.hdfc.parser;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;

public class HdfcOfferParserUtil {

	private static final Logger logger = Logger.getLogger(HdfcOfferParserUtil.class);

	public static String getMerchantNameFrom (String fullOfferText) {
		String merchantName = null;

		//Hardcoding to be removed after fixing issue.
		if (fullOfferText.startsWith("Jabong.com")) {
			return "Jabong.com";
		}
		if(null != fullOfferText){
			Pattern pattern = Pattern.compile("^(.*?)[\\-:].*");
			Matcher matcher = pattern.matcher(fullOfferText);
			if (matcher.find())
			{
				merchantName = matcher.group(1);
				merchantName = merchantName.trim();
			}
		}
		if (CommonUtilities.isNotEmpty(merchantName) && merchantName.startsWith("Visa")) {
			return "Visa";
		}
		return merchantName;
	}

	public static String getOfferDescriptionFrom (String fullOfferText) {
		String offerDescription = null;
		if(null != fullOfferText){
			Pattern pattern = Pattern.compile("^.*?[\\-:](.*)");
			Matcher matcher = pattern.matcher(fullOfferText);
			if (matcher.find())
			{
				offerDescription = matcher.group(1);
			}
		}
		if (offerDescription == null) {
			offerDescription = fullOfferText;
		}
		offerDescription = offerDescription.trim();
		return offerDescription;
	}

	/**
	 * 
	 * @param validityText e.g. "Offer Valid upto 30th September 2013" , "Offer Period Valid upto 30th September 2013",
	 * "Offer valid from 01st March 2013 to 28th June 2013"
	 * @return
	 */
	public static Date getExpiryDateFrom(String validityText) {
		if (CommonUtilities.isNullOrEmpty(validityText)) {
			return null;
		}
		String dateText = getDateText(validityText);
		if (CommonUtilities.isNullOrEmpty(dateText)) {
			return null;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			java.util.Date parsedDate = dateFormat.parse(dateText);
			long epochtimeUTCInMillis = parsedDate.getTime();
			Date date = new Date(epochtimeUTCInMillis);
			return date;

		} catch (Exception e) {
			logger.error("error in parsing date: " + dateText, e);
		}
		return null;
	}

	private static String getDateText(String validityText) {
		validityText = validityText.trim();
		String dateText = "";
		Pattern pattern = Pattern.compile(".*?(\\d+..\\s+[a-zA-Z]*\\s+\\d+)$");
		Matcher matcher = pattern.matcher(validityText);
		if (matcher.find()) {
			dateText = matcher.group(1);
		}
		dateText = dateText.replace("Sept ", "September ");
		return dateText.replace("1st ", "1 ").replace("nd", "").replace("rd", "").replace("th", "");
	}

}

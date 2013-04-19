package com.itech.offer.hdfc.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HdfcOfferParserUtil {

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

}

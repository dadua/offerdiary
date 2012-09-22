package com.itech.redwine.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itech.common.CommonUtilities;

public class RedWineParserUtil {

	/**
	 * http://redanar.com/mobile/badge/view/88
	 * here id is 88
	 * */
	public static String getIdFromCard(String cardUrl) {
		if (CommonUtilities.isNullOrEmpty(cardUrl)) {
			return null;
		}
		String IdSelectorRegEx = ".*/(\\d+)$";
		Pattern pattern = Pattern.compile(IdSelectorRegEx);
		Matcher matcher = pattern.matcher(cardUrl);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}

}

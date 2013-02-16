package com.itech.offer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;

public class OfferSearchFilterUtil {

	public static final String FREFIX_CREATION_FILTER = "addedInLast";
	public static final String PREFIX_EXPIRY_FILTER = "expiringInNext";
	public static final String SUFFIX_DAYS_UNIT = "Days";
	private static final Logger logger = Logger.getLogger(OfferSearchFilterUtil.class);

	public static int getNumberOfDays(String uniqueFilter) {
		String daysSelectionRegEx = "";
		if (uniqueFilter.startsWith(PREFIX_EXPIRY_FILTER)) {
			daysSelectionRegEx += PREFIX_EXPIRY_FILTER;
		}

		if (uniqueFilter.startsWith(FREFIX_CREATION_FILTER)) {
			daysSelectionRegEx += FREFIX_CREATION_FILTER;
		}
		String numberOfDays = "";
		daysSelectionRegEx += "(\\d+).*";
		Pattern pattern = Pattern.compile(daysSelectionRegEx);
		Matcher matcher = pattern.matcher(uniqueFilter);
		if (matcher.matches()) {
			numberOfDays = matcher.group(1);
		}
		if (CommonUtilities.isNotEmpty(numberOfDays)) {
			try {
				return Integer.parseInt(numberOfDays);
			} catch (NumberFormatException e) {
				logger.warn("Unable to find number in filter - " + uniqueFilter, e);
			}
		}
		logger.warn("Unable to find number in filter - " + uniqueFilter);
		return 0;
	}

	public static boolean isExpiryFilter(String uniqueFilter) {
		if (CommonUtilities.isNullOrEmpty(uniqueFilter)) {
			return false;
		}

		if (uniqueFilter.startsWith(PREFIX_EXPIRY_FILTER)) {
			return true;
		}
		return false;
	}

	public static boolean isCreationFilter(String uniqueFilter) {
		if (CommonUtilities.isNullOrEmpty(uniqueFilter)) {
			return false;
		}

		if (uniqueFilter.startsWith(FREFIX_CREATION_FILTER)) {
			return true;
		}
		return false;
	}

}

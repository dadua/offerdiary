package com.itech.offer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itech.offer.vo.OfferVO;

public class OfferExtractUtils {


	private static final List<Pattern> validOfferPatterns = initValidOfferPatterns();

	private final List<Pattern> invalidOfferPatterns = initInvalidOfferPatterns();

	private static List<Pattern> initValidOfferPatterns () {
		int flags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE;
		Pattern[] validOfferPatterns = new Pattern[] {
				Pattern.compile(".*offer\\s.*", flags),
				Pattern.compile(".*discount\\s.*", flags),
				Pattern.compile(".*%\\s+off\\s.*", flags),
				Pattern.compile(".*deals?\\s.*", flags),
				Pattern.compile(".*Buy\\s+\\d+\\s+get\\s+\\d+\\s+free.*", flags),
				Pattern.compile(".*code\\s.*", flags)
		};
		return Arrays.asList(validOfferPatterns);
	}



	private List<Pattern> initInvalidOfferPatterns() {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * 
	 * @param offerDescription
	 * @return if the given offerDescription is an offer
	 */
	public static boolean isOffer(String offerDescription) {

		for (Pattern pattern: getValidOfferPatterns()) {
			Matcher matcher = pattern.matcher(offerDescription);

			//Pattern.compile(".*(^Product)\\scode\\s.*").matcher("Having Code Book23234").find();
			if (matcher.find()) {
				System.out.println(matcher.groupCount());
				System.out.println(matcher.group(0));
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * @param offerDescription
	 * @return offerVO object if offer is detected else null
	 */
	public static OfferVO getOfferVO (String offerDescription) {
		return null;
	}



	public List<Pattern> getInvalidOfferPatterns() {
		return invalidOfferPatterns;
	}



	public static List<Pattern> getValidOfferPatterns() {
		return validOfferPatterns;
	}

}

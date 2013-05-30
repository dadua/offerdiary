package com.itech.offer.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.offer.vo.OfferVO;
import com.itech.web.WebConstatnts;

public class OfferViewUtils {

	public static String getOfferHtmlFrom(OfferVO offer, boolean isSharedOffer) {
		Document doc = Jsoup.parse(CommonFileUtilities.getWebResourceAsString("offers/templates/offerDetailTemplate.html"));

		Element offerTemplate$ = doc.select(".offer_detail_ui_template").first();

		offerTemplate$.removeClass("offer_detail_ui_template");
		offerTemplate$.removeClass("hide");
		offerTemplate$.attr("id", "offer_" + offer.getId());


		if (offer.getTargetVendor()!=null) {
			offerTemplate$.select(".vendorName").html(offer.getTargetVendor().getName());
			offerTemplate$.select(".vendorOnOfferNameVal").html(offer.getTargetVendor().getName());
			offerTemplate$.select(".vendorUrl").html(offer.getTargetVendor().getSiteUrl()==null? "": offer.getTargetVendor().getSiteUrl()).attr("href", offer.getTargetVendor().getSiteUrl()==null? "": offer.getTargetVendor().getSiteUrl());
			if (!CommonUtilities.isNullOrEmpty(offer.getTargetVendor().getLogoUrl())) {
				if (offer.getTargetVendor().isAbsoluteLogoUrl()) {
					offerTemplate$.select(".targetVendor-logoUrl").attr("alt", offer.getTargetVendor().getName()).attr("src", offer.getTargetVendor().getLogoUrl());
				} else {
					offerTemplate$.select(".targetVendor-logoUrl").attr("alt", offer.getTargetVendor().getName()).attr("src", WebConstatnts.getAbsoluteURL("/images/stores/"+ offer.getTargetVendor().getLogoUrl()));
				}
			} else {
				offerTemplate$.select(".targetVendor-logoUrl").attr("alt", offer.getTargetVendor().getName()).attr("src", WebConstatnts.getAbsoluteURL("/images/stores/blankVendor.png"));
				offerTemplate$.select(".vendorOnOfferNameVal").removeClass("hide");
			}
		} else {
			offerTemplate$.select(".targetVendor-logoUrl").attr("src", WebConstatnts.getAbsoluteURL("/images/stores/defaultVendor.jpg"));
		}

		if (offer.getExpiryDateInMillis()!= 0) {
			offerTemplate$.select(".offerExpiryDate").html(getReadableDate(offer));
			setDaysToExpireDataToTemplate$(offer, offerTemplate$);
		} else {
			offerTemplate$.select(".expiryDate").addClass("hide");
		}

		if (!CommonUtilities.isNullOrEmpty(offer.getSource())) {
			offerTemplate$.select(".offerSourceContainer").removeClass("hide").select(".offerSourceVal").html(offer.getSource());
		}
		offerTemplate$.select(".offerTrash").attr("id", "offerTrash_" + offer.getId());
		offerTemplate$.select(".offerShare").attr("id", "offerShare_" + offer.getId());
		offerTemplate$.select(".offerDetail").attr("id", "offerDetail_" + offer.getId());

		boolean isOfferDescriptionPresent = !CommonUtilities.isNullOrEmpty(offer.getDescription());

		if (!CommonUtilities.isNullOrEmpty(offer.getOfferLink())) {
			offerTemplate$.select(".offerUrl").attr("href", offer.getOfferLink());
			offerTemplate$.select(".offerUrl").attr("target", "_blank");
			if (!isOfferDescriptionPresent) {
				offerTemplate$.select(".offerUrl").html(offer.getOfferLink());
			} else {
				offerTemplate$.select(".offerUrl").html(offer.getDescription());
			}
		} else if (isOfferDescriptionPresent) {
			offerTemplate$.select(".offerDesc").html(offer.getDescription());
		}

		if (!CommonUtilities.isNullOrEmpty(offer.getOfferCode())) {
			offerTemplate$.select(".offerCodeVal").html(offer.getOfferCode());
		} else {
			offerTemplate$.select(".offerCode").addClass("hide");
		}

		if (isSharedOffer) {
			offerTemplate$.select(".offerTrash").addClass("hide");
			offerTemplate$.select(".offerAdd").first().parent().attr("href", "addSharedOfferToWallet.do?accessCode=" + offer.getId());
		} else {
			offerTemplate$.select(".offerTrash").attr("id", "offerTrash_" + offer.getId());
			offerTemplate$.select(".offerAdd").addClass("hide");
		}
		//TODO: Fix comments
		//offerTemplate$.select("#oDfferdFbComment").attr("href", window.location.href);
		return offerTemplate$.toString();
	}

	private static void setDaysToExpireDataToTemplate$(OfferVO offer,
			Element offerTemplate$) {

		Long daysToExpire = getDaysToExpire(offer);
		String labelClass = getExpiryDateBasedClass(offer);
		String formattedTimeToExpire = getFormattedTimeToExpire(offer);
		if (daysToExpire < 0) {
			formattedTimeToExpire = "Expired";
		}
		offerTemplate$.select(".daysToExpire").html(formattedTimeToExpire).addClass(labelClass).removeClass("hide");

	}

	private static String getReadableDate(OfferVO offer) {
		long expiryDateInMillis = offer.getExpiryDateInMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
		return dateFormat.format(new Date(expiryDateInMillis));
	}

	public static Long getDaysToExpire(OfferVO offer) {
		Long timeMillis = System.currentTimeMillis();
		Long expiryTime = offer.getExpiry().getTime();
		Long millisToExpire = expiryTime - timeMillis;
		Long days = (millisToExpire)/(1000*60*60*24);
		return days;
	}


	public static String getExpiryDateBasedClass(OfferVO offer) {
		Long daysToExpire = getDaysToExpire(offer);
		if(daysToExpire < 0 ) {
			return "";
		} else if(daysToExpire < 15) {
			return "label-important";
		} else if (daysToExpire < 45) {
			return "label-warning";
		}
		/*
		else if (daysToExpire < 90) {
			return "label-info";
		} else if (daysToExpire < 120) {
			return "label-inverse";
		}
		 */
		else {
			return "label-success";
		}
	}

	public static String getFormattedTimeToExpire(OfferVO offer) {
		Long daysToExpire = getDaysToExpire(offer);
		if (daysToExpire < 30) {
			return getFormattedDaysToExpire(daysToExpire);
		} else if (daysToExpire < 365) {
			return getFormattedMonthsToExpire(daysToExpire);
		} else {
			return getFormattedYearsToExpire(daysToExpire);
		}
	}


	public String getCompactFormattedTimeToExpire(String timeToExpireString) {
		int lengthToTrimString = 16;
		if (timeToExpireString.length() > lengthToTrimString) {
			String displayedTimeToExpire = timeToExpireString.substring(0, lengthToTrimString);
			String hiddenPartOfTimeToExpire = timeToExpireString.substring(lengthToTrimString);
			displayedTimeToExpire += "<span class=\"hiddenPartOfTime\" style=\"display:none\">";
			displayedTimeToExpire += hiddenPartOfTimeToExpire;
			displayedTimeToExpire += "</span>";
			displayedTimeToExpire += "<a class=\"daysToExpireDetails\" href=\"#\" title=\""+timeToExpireString + "\">...</a>";
			return displayedTimeToExpire;
		} else {
			return timeToExpireString;
		}
	}

	private static String getFormattedYearsToExpire(Long daysToExpire) {
		long yearsToExpire = daysToExpire/365;
		return yearsToExpire + (yearsToExpire==1?" year, ":" years, ") + getFormattedMonthsToExpire(daysToExpire%365);
	}

	private static String getFormattedMonthsToExpire(Long daysToExpire) {
		long monthsToExpire = daysToExpire/30;
		return monthsToExpire + (monthsToExpire==1?" month, ":" months, ") + getFormattedDaysToExpire(daysToExpire%30);
	}

	private static String getFormattedDaysToExpire(Long daysToExpire) {
		return daysToExpire + (daysToExpire==1?" day":" days") + " to expire";
	}


}

package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;

public class NewUserSubscriptionEmail extends EmailMessage{

	private final Logger logger = Logger.getLogger(NewUserRegistrationEmail.class);
	private static final String DEFAULT_SUBSCRIPTION_SUBJECT="OfferDiary Subscription !";
	private static final String DEFAULT_NEW_USER_ADDED_SUBSCRIPTION_MESSAGE="Hear More about OfferDo pretty shortly ! ";
	private String SUBSCRIPTION_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\new_user_email.html";
	private Document docHTML = null;

	public NewUserSubscriptionEmail(String messageContent, String toEmailId,  List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public NewUserSubscriptionEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public NewUserSubscriptionEmail(){
		super();
	}

	@Override
	public String getMailContent() {
		return SUBSCRIPTION_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		//docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		//SUBSCRIPTION_EMAIL_HTML = docHTML.toString();
	    SUBSCRIPTION_EMAIL_HTML = getHTMLForNewUserSubscriptionTemporary();
	}

	@Override
	public void setContentInMessageHTML(String message) {
//		if(null == message || message.isEmpty()) {
//			message = DEFAULT_NEW_USER_ADDED_SUBSCRIPTION_MESSAGE;
//		}
//		if(null != docHTML){
//			Element para = docHTML.select("#mail-message").first();
//			para.html("<singleline label=\"Title\">"+message+"</singleline>");
//			SUBSCRIPTION_EMAIL_HTML = docHTML.toString();
//		}

	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_SUBSCRIPTION_SUBJECT;
	}

	
	
	private String getHTMLForNewUserSubscriptionTemporary() {
		StringBuilder htmlString = new StringBuilder();
		htmlString.append(addCompliance());
		htmlString.append(addStyle());
		htmlString.append(addRestHTML());
		return htmlString.toString();
	}

	private String addRestHTML() {
		StringBuilder restHtml = new StringBuilder();
		restHtml.append("<table width='100%'><tbody><tr><td align='center' valign='top'><div style='width:662px;text-align:left; border:15px solid #f5f5f5;border-radius:10px 10px 10px 10px'>");
		restHtml.append("<div style='background-color:#D1CECE;border-radius:10px 10px 0 0'><div style='float:left;'><img src='http://s.offerdiary.com/images/logo_tag.png' alt='Offer Diary' style='width:186px;min-height:30px;color:#fff;font-size:30px;line-height:30px;margin:16px 30px'>"
				+ "</div><div style='float:right; color:#0088CC; font-family: Georgia, \"Times New Roman\", Times, serif; padding:30px 30px 0px 0px;'>Never Miss an Offer !"
				+ "</div><div style='clear:both; line-height:0px;'>&nbsp;</div><div style='clear:both'></div></div>");	
		
		restHtml.append("<div style='border:1px solid #939292;background:#fff;padding:14px 34px 28px;font-family: Georgia, \"Times New Roman\", Times, serif;color:#5a5a5a'>"
				+ "<table align='center'><tr><td colspan='2' style='text-align:center'><span style='font-family:Arial;font-family:Georgia;font-size:22px;text-decoration:none; color:#0088CC'>"
				+ "Welcome to Offer Diary !!</span></td></tr></table>");
		
		restHtml.append("<div style='font-family: Georgia, \"Times New Roman\", Times, serif;font-size:19px; line-height:34px;'><div><div style='float:left'>" 
				+ "<strong>Dear Subscriber,</strong></div><div style='clear:both; line-height:10px;'>&nbsp;</div></div><div style='clear:both; line-height:0px'>"
				+"&nbsp;</div>");
		
		restHtml.append("<div style='font-family: calibri, Arial, Helvetica, sans-serif;font-size:16px;line-height:1.3em;padding-top:0;margin-top:0; padding-right:10px'>"
				+ "Thank you for signing up for OfferDiary closed beta. You have taken the first step to manage your offers and loyalty credits effectively."
				+ "We are working out to bring the best offer management solution to you with OfferDiary and will send you a sign up invitation soon!"
				+ "<div style='clear:both; line-height:14px'>&nbsp;</div>Wait for it... We are sure, that you are going to love it!<br />"
				+ "<div style='clear:both; line-height:14px'>&nbsp;</div><br />Thank you,<br />The OfferDiary Team<br /><a href='http://www.offerdiary.com' target='_blank' style='color:#0088CC;'>"
				+ "www.offerdiary.com</a></div>");
		
		restHtml.append("<div style='clear:both; line-height:10px;'></div></div><div style='clear:both; height:10px;'></div>"
				+ "<div align='center' style='font-family: calibri, Arial, Helvetica, sans-serif;font-size:12px; margin:10px 0; padding:0 40px'>"
				+ "<a href='http://www.facebook.com/pages/Offer-Diary/130234047120531' target='_blank' style='color:#0088CC;'>"
				+ "Like us on Facebook</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='http://twitter.com/offerdiary' target='_blank' style='color:#0088CC;'>"
				+ "Follow us on Twitter</a></div></div></div></td></tr></tbody></table></body></html>");
		
		return restHtml.toString();
	}

	
	private String addStyle() {
		StringBuilder styleHTML = new StringBuilder();
		styleHTML.append("<body style='font-family:calibri;'>");
		styleHTML.append("<style type='text/css'>");
		styleHTML.append("	.button a{background:#72ac48;color:#fff;font-size:16px;font-weight:normal;border-radius:3px;text-decoration:none;display:block;width:236px; cursor:pointer}");
		styleHTML.append("	.button a:hover{background:#80bf52;color:#fff;font-size:16px; font-weight:normal;border-radius:3px;text-decoration:none;display:block;width:236px; cursor:pointer}");
		styleHTML.append("	.follow_button a{margin:0;border:0 none;padding:0;background:#72ac48;color:#fff;font-size:10px;padding:3px 4px;font-weight:700;border-radius:3px;text-decoration:none;float:left}");
		styleHTML.append("	.follow_button a:hover{margin:0;border:0 none;padding:0;background:#80bf52;color:#fff;font-size:10px;padding:3px 4px;font-weight:700;border-radius:3px;text-decoration:none;float:left}");
		styleHTML.append("</style>");
		return styleHTML.toString();
	}

	private String addCompliance() {
		StringBuilder htmlComplianceString = new StringBuilder();
		htmlComplianceString.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		htmlComplianceString.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		htmlComplianceString.append("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head>");
		return htmlComplianceString.toString();
	}
	
}

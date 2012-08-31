package com.itech.email.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.itech.common.db.PersistableEntity;
import com.itech.email.vo.Email.EmailType;

@Entity
@Table(name=EmailMessagesModelConstants.TABLE_EMAIL_MESSAGES)
public class EmailMessages extends PersistableEntity {


	public EmailMessages() {
		super();
	}
	public EmailMessages(String fromAddress, String toAddress, String subject, String messageContent, EmailType type, EmailStatus status){
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.subject = subject;
		this.messageContent = messageContent;
		emailType = type;
		this.status = status;
	}

	public enum EmailStatus {
		INPROGRESS, PENDING, EXPIRED;
	}

	public enum EmailPriority{
		HIGH, MEDIUM, LOW
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=EmailMessagesModelConstants.COL_ID)
	private Long id;

	@Column(name=EmailMessagesModelConstants.COL_TO_ADDRESS)
	private String toAddress;

	@Column(name=EmailMessagesModelConstants.COL_FROM_ADDRESS)
	private String fromAddress;

	@Column(name=EmailMessagesModelConstants.COL_SUBJECT)
	private String subject;

	@Column(name=EmailMessagesModelConstants.COL_MESSAGE_CONTENT)
	@Type(type="text")
	private String messageContent;

	@Column(name=EmailMessagesModelConstants.COL_CREATION_TIME)//, nullable = false, columnDefinition = "date default sysdate")
	private Date creationTime = new Date(System.currentTimeMillis());

	@Column(name=EmailMessagesModelConstants.COL_LAST_TRY_TIME)
	private Date lastTryTime;

	@Column(name=EmailMessagesModelConstants.COL_TRY_COUNT)//, nullable = false, columnDefinition = "int default 0")
	private Integer tryCount = 0;

	@Enumerated(EnumType.STRING)
	@Column(name=EmailMessagesModelConstants.COL_STATUS)
	private EmailStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name=EmailMessagesModelConstants.COL_PRIORITY)
	private EmailPriority priority;

	@Enumerated(EnumType.STRING)
	@Column(name=EmailMessagesModelConstants.COL_EMAIL_TYPE)
	private EmailType emailType;

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastTryTime() {
		return lastTryTime;
	}

	public void setLastTryTime(Date lastTryTime) {
		this.lastTryTime = lastTryTime;
	}

	public EmailStatus getStatus() {
		return status;
	}

	public void setStatus(EmailStatus status) {
		this.status = status;
	}

	public EmailPriority getPriority() {
		return priority;
	}

	public void setPriority(EmailPriority priority) {
		this.priority = priority;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public void updateTryCount() {
		tryCount = tryCount + 1;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}
}

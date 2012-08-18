package com.itech.email.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.itech.common.db.PersistableEntity;

public class EmailMessages extends PersistableEntity {

	public enum EmailStatus {
		SEND, NOTSEND, EXPIRED;
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
	private String messageContent;
	
	@Column(name=EmailMessagesModelConstants.COL_CREATION_TIME)
	private Date creationTime;
	
	@Column(name=EmailMessagesModelConstants.COL_LAST_TRY_TIME)
	private Date lastTryTime;
	
	@Column(name=EmailMessagesModelConstants.COL_TRY_COUNT)
	private Integer tryCount;
	
	@Enumerated(EnumType.STRING)
	@Column(name=EmailMessagesModelConstants.COL_STATUS)
	private EmailStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name=EmailMessagesModelConstants.COL_PRIORITY)
	private EmailPriority priority;
	
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
}

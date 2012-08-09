package com.itech.coupon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;
import com.itech.coupon.model.constants.InterestedUserSubscriptionModelConstants;


@Entity
@Table(name="INTERESTED_USERS_SUBSCRIPTION")
public class InterestedUserSubscription extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=InterestedUserSubscriptionModelConstants.COL_ID)
	private Long id;
	
	@Column(name=InterestedUserSubscriptionModelConstants.COL_EMAIL_ID)
	private String emailId;
	
	@Column(name=InterestedUserSubscriptionModelConstants.COL_SUBSCRIPTION_TIME)
	private Date susbscriptionTime;
	
	@Override
	public boolean isTransient() {
		return getId() == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getSusbscriptionTime() {
		return susbscriptionTime;
	}

	public void setSusbscriptionTime(Date susbscriptionTime) {
		this.susbscriptionTime = susbscriptionTime;
	}

}

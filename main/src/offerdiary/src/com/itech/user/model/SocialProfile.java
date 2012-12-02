package com.itech.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itech.user.constants.SocialProfileModelConstants;


@Entity
@Table(name=SocialProfileModelConstants.TABLE_SOCIAL_PROFILES)
public class SocialProfile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=SocialProfileModelConstants.COL_ID)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name=SocialProfileModelConstants.COL_SOCIAL_PROFILE_TYPE)
	private  LinkedAccountType socialProfileType = LinkedAccountType.FACEBOOK;

	@Column(name=SocialProfileModelConstants.COL_UNIQUE_ID)
	private String uniqueId;

	@Column(name=SocialProfileModelConstants.COL_NAME)
	private String name;

	@Column(name=SocialProfileModelConstants.COL_EMAIL_ID)
	private String emailId;

	@Column(name=SocialProfileModelConstants.COL_IMAGE_URL)
	private String imageUrl;

	@Column(name=SocialProfileModelConstants.COL_PROFILE_URL)
	private String profileUrl;

	@ManyToOne
	@JoinColumn(name=SocialProfileModelConstants.COL_LINKED_ACCOUNT_ID)
	private LinkedAccount linkedAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LinkedAccountType getSocialProfileType() {
		return socialProfileType;
	}

	public void setSocialProfileType(LinkedAccountType socialProfileType) {
		this.socialProfileType = socialProfileType;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public LinkedAccount getLinkedAccount() {
		return linkedAccount;
	}

	public void setLinkedAccount(LinkedAccount linkedAccount) {
		this.linkedAccount = linkedAccount;
	}
}

package com.itech.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itech.user.constants.SocialProfileConnectionModelConstants;

@Entity
@Table(name = SocialProfileConnectionModelConstants.TABLE_SOCIAL_PROFILE_CONNECTION)
public class SocialProfileConnection {

	@Id
	@GeneratedValue
	@Column(name = SocialProfileConnectionModelConstants.COL_ID)
	private long id;

	@ManyToOne
	@JoinColumn(name = SocialProfileConnectionModelConstants.COL_SOCIAL_PROFILE_1_ID)
	private SocialProfile socialProfile1;

	@ManyToOne
	@JoinColumn(name = SocialProfileConnectionModelConstants.COL_SOCIAL_PROFILE_2_ID)
	private SocialProfile socialProfile2;

	@Enumerated(EnumType.STRING)
	@Column(name=SocialProfileConnectionModelConstants.COL_SOCIAL_PROFILE_TYPE)
	private  LinkedAccountType socialProfileType = LinkedAccountType.FACEBOOK;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SocialProfile getSocialProfile1() {
		return socialProfile1;
	}

	public void setSocialProfile1(SocialProfile socialProfile1) {
		this.socialProfile1 = socialProfile1;
	}

	public SocialProfile getSocialProfile2() {
		return socialProfile2;
	}

	public void setSocialProfile2(SocialProfile socialProfile2) {
		this.socialProfile2 = socialProfile2;
	}

	public LinkedAccountType getSocialProfileType() {
		return socialProfileType;
	}

	public void setSocialProfileType(LinkedAccountType socialProfileType) {
		this.socialProfileType = socialProfileType;
	}


}

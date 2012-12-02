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

import org.hibernate.annotations.Type;

import com.itech.common.db.PersistableEntity;
import com.itech.user.constants.LinkedAccountModelConstants;


@Entity
@Table(name=LinkedAccountModelConstants.TABLE_LINKED_ACCOUNTS)
public class LinkedAccount extends PersistableEntity{
	public enum LinkedAccountStatus {
		ACTIVE, INACTIVE;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=LinkedAccountModelConstants.COL_ID)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name=LinkedAccountModelConstants.COL_LINKED_ACCOUNT_TYPE)
	private LinkedAccountType linkedAccountType;

	@Enumerated(EnumType.STRING)
	@Column(name=LinkedAccountModelConstants.COL_LINKED_ACCOUNT_STATUS)
	private LinkedAccountStatus linkedAccountStatus = LinkedAccountStatus.ACTIVE;

	@Column(name=LinkedAccountModelConstants.COL_UNIQUE_ID)
	private String uniqueId;

	@Column(name=LinkedAccountModelConstants.COL_ACCESS_TOKEN)
	@Type(type="text")
	private String accessToken;

	@ManyToOne
	@JoinColumn(name=LinkedAccountModelConstants.COL_USER_ID)
	private User user;

	@Column(name=LinkedAccountModelConstants.COL_OD_VENDOR_KEY)
	private String odVendorKey;//If Linked Account of Type OD_VENDOR_BASED

	@Column(name=LinkedAccountModelConstants.COL_USED_FOR_LOGIN)
	private Boolean usedForLogin = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LinkedAccountType getLinkedAccountType() {
		return linkedAccountType;
	}

	public void setLinkedAccountType(LinkedAccountType linkedAccountType) {
		this.linkedAccountType = linkedAccountType;
	}

	public LinkedAccountStatus getLinkedAccountStatus() {
		return linkedAccountStatus;
	}

	public void setLinkedAccountStatus(LinkedAccountStatus linkedAccountStatus) {
		this.linkedAccountStatus = linkedAccountStatus;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOdVendorKey() {
		return odVendorKey;
	}

	public void setOdVendorKey(String odVendorKey) {
		this.odVendorKey = odVendorKey;
	}

	public Boolean getUsedForLogin() {
		return usedForLogin;
	}

	public void setUsedForLogin(Boolean usedForLogin) {
		this.usedForLogin = usedForLogin;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}




}

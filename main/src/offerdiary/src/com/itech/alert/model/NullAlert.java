package com.itech.alert.model;


public class NullAlert extends Alert {
	public enum NullALertReason {
		TRIGGER_NEXT_TIME, NO_OBJECT_FOR_ALERT
	}

	private NullALertReason nullALertReason;
	private final NullALertReason nullALertReason2;

	public NullAlert(NullALertReason nullALertReason) {
		nullALertReason2 = nullALertReason;
	}

	public NullALertReason getNullALertReason() {
		return nullALertReason;
	}

	public void setNullALertReason(NullALertReason nullALertReason) {
		this.nullALertReason = nullALertReason;
	}
}

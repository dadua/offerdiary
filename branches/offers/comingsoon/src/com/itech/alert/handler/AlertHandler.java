package com.itech.alert.handler;

import com.itech.alert.model.Alert;

public interface AlertHandler {
	public boolean handles(Alert alert);
	public void handle(Alert alert);
}

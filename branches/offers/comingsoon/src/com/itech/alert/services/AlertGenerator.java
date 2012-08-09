package com.itech.alert.services;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfig;

public interface AlertGenerator {
	Alert createAlert(AlertConfig alertConfig);
	boolean handles(AlertConfig alertConfig);
}

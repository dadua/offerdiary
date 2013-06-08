package com.itech.flower.manager;

import com.itech.common.services.CommonBaseManager;

public class FlowerManagerImpl extends CommonBaseManager implements FlowerManager{
	private FlowerManager flowerManager;

	public FlowerManager getFlowerManager() {
		return flowerManager;
	}

	public void setFlowerManager(FlowerManager flowerManager) {
		this.flowerManager = flowerManager;
	}
}

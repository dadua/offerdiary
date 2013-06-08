package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.Flower;

public interface FlowerDAO extends CommonBaseDAO<Flower>{
	Flower getFlowerFor(String name);

	List<Flower> searchFlowerBy(String searchString);

}

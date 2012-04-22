package com.itech.alert.dao;

import java.sql.Date;
import java.util.List;

import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertConfigModelConstant;
import com.itech.common.test.CommonTestDao;

public class TestAlertConfigDAOImpl extends CommonTestDao<AlertConfig>{

	private AlertConfigDAO alertConfigDAO;
	private final String [] ignoreColumns ={"ALERT_TYPE","CREATION_TIME"};
	private final String [] ignoreColumnsWithIDAndTriggerTime ={"ID","ALERT_TYPE","CREATION_TIME","TRIGGER_TIME"};
	@Override
	public void setUp() throws Exception {
		super.setUp();
		alertConfigDAO = new AlertConfigDAOImpl();
		alertConfigDAO.setConnection(con);
	}


	public void testGetAllAlertConfigWithStatus () throws Exception{
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		List<AlertConfig> activeAlertConfigs = alertConfigDAO.getAllAlertConfigWithStatus(AlertConfig.ActivationStatus.ACTIVE);
		assertEquals(3, activeAlertConfigs.size());
	}

	public void testGetAllAlertConfigExpiredWithStatusSuspended() throws Exception{
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		List<AlertConfig> activeAlertConfigs = alertConfigDAO.getAllAlertConfigExpiredWithStatus(AlertConfig.ActivationStatus.SUSPENDED);
		assertEquals(1, activeAlertConfigs.size());
	}

	public void testDeleteForDataType() throws Exception {
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		alertConfigDAO.deleteForDataType("offer", 4);
		QueryDataSet tableData = getQueryDataSet(AlertConfigModelConstant.TABLE_ALERT_CONFIG, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertConfigDAOImpl_deletedOfferType_out.xml");
		compareDataSets(expectedTableData,tableData, AlertConfigModelConstant.TABLE_ALERT_CONFIG,ignoreColumns);
	}


	public void testDelete() throws Exception{
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		long id = 104;
		AlertConfig alertConfig = getALertConfig(id);
		alertConfigDAO.delete(alertConfig);
		QueryDataSet tableData = getQueryDataSet(AlertConfigModelConstant.TABLE_ALERT_CONFIG, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertConfigDAOImpl_deletedOfferType_out.xml");
		compareDataSets(expectedTableData,tableData, AlertConfigModelConstant.TABLE_ALERT_CONFIG,ignoreColumns);
	}

	public void testGetById() throws Exception{
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		AlertConfig alertConfig = alertConfigDAO.getById(103);
		assertEquals(103, alertConfig.getId());
	}


	public void testDeleteById() throws Exception {
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		long id = 104;
		alertConfigDAO.delete(id);
		QueryDataSet tableData = getQueryDataSet(AlertConfigModelConstant.TABLE_ALERT_CONFIG, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertConfigDAOImpl_deletedOfferType_out.xml");
		compareDataSets(expectedTableData,tableData, AlertConfigModelConstant.TABLE_ALERT_CONFIG,ignoreColumns);
	}

	public void testAddAlertConfig () throws Exception {
		executeCleanInsert("TestAlertConfigDAOImpl_NewAlertConfigExists_in.xml");
		long id = 110;
		AlertConfig alertConfig = getALertConfig(id);
		alertConfigDAO.addOrUpdate(alertConfig);
		QueryDataSet tableData = getQueryDataSet(AlertConfigModelConstant.TABLE_ALERT_CONFIG, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertConfigDAOImpl_addedAlertConfig_out.xml");
		compareDataSets(expectedTableData,tableData, AlertConfigModelConstant.TABLE_ALERT_CONFIG,ignoreColumnsWithIDAndTriggerTime);
	}

	private AlertConfig getALertConfig(long id) {
		AlertConfig alertconfig = new AlertConfig();
		alertconfig.setId(id);
		alertconfig.setDataId(4);
		alertconfig.setDataType("offer");
		alertconfig.setStatus(AlertConfig.ActivationStatus.ACTIVE);
		long dateInMilliseconds = 1340340010000L;
		alertconfig.setTriggerTime(new Date(dateInMilliseconds));
		return alertconfig;
	}

	private String getSelectAllQuery() {
		return "select * from "+ AlertConfigModelConstant.TABLE_ALERT_CONFIG ;
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}

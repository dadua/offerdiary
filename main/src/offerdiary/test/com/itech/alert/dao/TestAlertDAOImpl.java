package com.itech.alert.dao;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertModelConstant;
import com.itech.common.test.CommonTestDao;
import com.itech.coupon.model.User;

public class TestAlertDAOImpl extends CommonTestDao<Alert> {

	private AlertDAO alertDAO;
	private final String [] ignoreColsOtherThanIDUserIDAndStatus = {"ALERT_TYPE","CREATION_TIME","MESSAGE","HTML_MESSAGE"};
	private final String [] ignoreColsOtherThanUserIDAndStatus = {"ID","ALERT_TYPE","CREATION_TIME","MESSAGE","HTML_MESSAGE"};
	@Override
	public void setUp() throws Exception {
		super.setUp();
		alertDAO = new AlertDAOImpl();
		((AlertDAOImpl)alertDAO).setConnection(con);
	}

	public void testMarkAlertsRead() throws Exception {
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		User user = new User();
		user.setId(649283l);
		alertDAO.markAlertsRead(user);
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_updateReadStatus_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanIDUserIDAndStatus);
	}

	public void testDeleteByIds() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		List<Long> idList = new ArrayList<Long>();
		idList.add(new Long(103));
		idList.add(new Long(104));
		alertDAO.deleteByIds(idList);
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_DeletedByIDs_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanIDUserIDAndStatus);
	}

	public void testGetAlertsForDataType() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		String dataType="deal";
		long dataId= 301;
		Alert alert = getExpectedAlert(103);
		List<Alert> expectedAlertList = new ArrayList<Alert>();
		expectedAlertList.add(alert);
		List<Alert> outListAlert = alertDAO.getAlertsForDataType(dataType, dataId);
		assertEquals(expectedAlertList.get(0).getUser().getId(), outListAlert.get(0).getUser().getId());
	}

	public void testGetAlertsForuser() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		User user = new User();
		user.setId(649283l);
		List<Alert> outListAlert = alertDAO.getAlertsForUser(user);

		Alert alert = getExpectedAlert(103);
		List<Alert> expectedAlertList = new ArrayList<Alert>();
		expectedAlertList.add(alert);
		assertEquals(expectedAlertList.get(0).getUser().getId(), outListAlert.get(0).getUser().getId());
	}

	public void testDeleteAlertsFor() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		String dataType="deal";
		long dataId= 301;
		alertDAO.deleteAlertsFor(dataType, dataId);
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_DeleteAlertsFor_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanIDUserIDAndStatus);
	}

	public void testAddOrUpdateAlert() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		Alert alert = getExpectedAlert(new Long(105));
		alertDAO.addOrUpdate(alert);
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_AddSingleAlert_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanUserIDAndStatus);
	}

	public void testDeleteAlert() throws Exception{
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		alertDAO.delete(new Long(104));
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_DeleteAlert_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanIDUserIDAndStatus);
	}

	public void testGetById() throws Exception {
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		long id = 103;
		Alert alert = alertDAO.getById(id);
		assertEquals(id, alert.getId());
	}

	private Alert getExpectedAlert(long id) {
		Alert alert = new Alert();
		alert.setId(id);
		alert.setDataType("deal");
		alert.setDataId(301);
		User user = new User();
		user.setId(649283L);
		alert.setUser(user);
		alert.setAlertStatus(Alert.AlertStatus.READ);
		return alert;
	}

	private String getSelectAllQuery() {
		return "select * from "+AlertModelConstant.TABLE_ALERT;
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}

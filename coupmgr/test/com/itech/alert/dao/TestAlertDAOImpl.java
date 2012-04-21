package com.itech.alert.dao;

import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertModelConstant;
import com.itech.common.test.CommonTestDao;
import com.itech.coupon.model.User;

public class TestAlertDAOImpl extends CommonTestDao<Alert> {

	private AlertDAO alertDAO;
	private final String [] ignoreColsOtherThanUserIDAndStatus = {"DATA_TYPE","DATA_ID","ALERT_TYPE","CREATION_TIME","MESSAGE","HTML_MESSAGE"};
	@Override
	public void setUp() throws Exception {
		super.setUp();
		alertDAO = new AlertDAOImpl();
		alertDAO.setConnection(con);
	}

	public void testMarkAlertsRead() throws Exception {
		executeCleanInsert("TestAlertDAOImpl_NewAlertExists_in.xml");
		User user = new User();
		user.setId(649283);
		alertDAO.markAlertsRead(user);
		QueryDataSet tableData = getQueryDataSet(AlertModelConstant.TABLE_ALERT, getSelectAllQuery());
		IDataSet expectedTableData = getFlatXmlDataSet("TestAlertDAOImpl_updateReadStatus_out.xml");
		compareDataSets(expectedTableData,tableData, AlertModelConstant.TABLE_ALERT, ignoreColsOtherThanUserIDAndStatus);
	}

	private String getSelectAllQuery() {
		return "select * from "+AlertModelConstant.TABLE_ALERT;
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}

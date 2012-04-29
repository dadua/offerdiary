package com.itech.coupon.dao;

import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;

import com.itech.common.test.CommonTestDao;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.constants.CouponModelConstants;

public class TestCouponDAOImpl extends CommonTestDao<Coupon>{
	private CouponDAO couponDAO;

	private final String [] ignoreCols = {"ID", "NAME", "PASSWORD",
			"LOCATION", "LANGUAGE", "EMAIL_ID"};
	@Override
	public void setUp() throws Exception {
		super.setUp();
		couponDAO = new CouponDAOImpl();
		((CouponDAOImpl)couponDAO).setConnection(con);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddOrUpdate() throws Exception {
		executeCleanInsert("TestCouponDAOImpl_UserExists_in.xml");
		Coupon coupon = new Coupon();
		couponDAO.addOrUpdate(coupon);
		QueryDataSet tableData = getQueryDataSet(CouponModelConstants.TABLE_COUPON , getSelectAllQuery());

		IDataSet expectedTableData = getFlatXmlDataSet("TestUserDAOImpl_addOrUpdateUser_out.xml");

		compareDataSets(expectedTableData,tableData, CouponModelConstants.TABLE_COUPON,ignoreCols);
	}

	private String getSelectAllQuery(){
		return "select * from " + CouponModelConstants.TABLE_COUPON ;
	}

}

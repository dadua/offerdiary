package com.itech.student.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.student.model.Student;

public class StudentDAOImpl extends HibernateCommonBaseDAO<Student> implements StudentDAO {

	@Override
	protected Class getEntityClass() {
		return Student.class;
	}

	@Override
	public Student getStudentFor(String name) {
		String hql = "select f from " + getEntityClassName() + " f where name=:name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Student> searchStudentBy(String searchString) {
		String hql = "select f from " + getEntityClassName() + " f where name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", '%' + searchString + '%');
		List result = query.list();
		return result;
	}

}

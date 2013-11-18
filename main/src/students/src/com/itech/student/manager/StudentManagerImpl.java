package com.itech.student.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.student.dao.StudentDAO;
import com.itech.student.model.Student;

public class StudentManagerImpl extends CommonBaseManager implements StudentManager{
	private StudentDAO studentDAO;

	@Override
	public void addOrUpdate(Student student) {
		getStudentDAO().addOrUpdate(student);
	}

	@Override
	public void delete(Student student) {
		getStudentDAO().delete(student);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			getStudentDAO().delete(id);
		}
	}


	@Override
	public Student getStudentByName(String name) {
		return getStudentDAO().getStudentFor(name);
	}

	@Override
	public Student getFlowerById(long id) {
		return getStudentDAO().getById(id);
	}


	@Override
	public List<Student> searchStudentFor(String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		return getStudentDAO().searchStudentBy(searchString);
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

}

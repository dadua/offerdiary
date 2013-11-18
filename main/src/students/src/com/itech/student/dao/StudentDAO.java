package com.itech.student.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.student.model.Student;

public interface StudentDAO extends CommonBaseDAO<Student>{
	Student getStudentFor(String name);

	List<Student> searchStudentBy(String searchString);

}

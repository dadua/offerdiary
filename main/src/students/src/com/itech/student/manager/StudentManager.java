package com.itech.student.manager;

import java.util.List;

import com.itech.student.model.Student;

public interface StudentManager {

	public void addOrUpdate(Student flower);
	public void delete(Student flower);
	public void delete(List<Long> Ids);
	public Student getStudentByName(String name);
	public Student getFlowerById(long id);
	public List<Student> searchStudentFor(String searchString);



}

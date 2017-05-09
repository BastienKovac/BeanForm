package com.beans.form.model;

public class Student {

	private String firstName;
	private String lastName;
	private Long studentNumber;
	private int age;
	private StudentCursus followedCursus;
	private boolean registered;

	public Student() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Long studentNumber) {
		this.studentNumber = studentNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public StudentCursus getFollowedCursus() {
		return followedCursus;
	}

	public void setFollowedCursus(StudentCursus followedCursus) {
		this.followedCursus = followedCursus;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

}

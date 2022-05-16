package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.entity.Class;
import com.entity.Student;
import com.entity.Subject;
import com.entity.Teacher;

public class DbRetrieve {

	private DataSource dataSource;

	public DbRetrieve(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() {

		List<Student> students = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM students";
			stmt = con.createStatement();

			res = stmt.executeQuery(query);

			while (res.next()) {

				int id = res.getInt("id");
				String firstName = res.getString("fname");
				String lastName = res.getString("lname");
				int age = res.getInt("age");
				int aclass = res.getInt("aclass");

				Student tempStudent = new Student(id, firstName, lastName, age, aclass);
				students.add(tempStudent);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return students;

	}

public List<Teacher> getTeachers() {

		List<Teacher> teachers = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM teachers";
			stmt = con.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {

				int id = res.getInt("id");
				String firstName = res.getString("fname");
				String lastName = res.getString("lname");
				int age = res.getInt("age");

				Teacher temp = new Teacher(id, firstName, lastName, age);
				teachers.add(temp);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return teachers;

	}

public List<Subject> getSubjects() {

		List<Subject> subjects = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM subjects";
			stmt = con.createStatement();
			res = stmt.executeQuery(query);

			while (res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				String shortcut = res.getString("shortcut");
				Subject temp = new Subject(id, name,shortcut);
				subjects.add(temp);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return subjects;

	}

public List<Class> getClasses() {

		List<Class> classes = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM classes";
			stmt = con.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				int id = res.getInt("id");
				int section = res.getInt("section");
				int subject = res.getInt("subject");
				int teacher = res.getInt("teacher");
				String time = res.getString("time");

				Teacher tempTeacher = loadTeacher(teacher);
				Subject tempSubject = loadSubject(subject);

				String teacher_name = tempTeacher.getFname() + " " + tempTeacher.getLname();
				Class temp = new Class(id, section, teacher_name, tempSubject.getName(), time);
				classes.add(temp);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return classes;

	}

public Teacher loadTeacher(int teacherId) {

		Teacher theTeacher = null;

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			con = dataSource.getConnection();
			String query = "SELECT * FROM teachers WHERE id = " + teacherId;
			stmt = con.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				int id = res.getInt("id");
				String fname = res.getString("fname");
				String lname = res.getString("lname");
				int age = res.getInt("age");
				theTeacher = new Teacher(id, fname, lname, age);

			}

		} catch (Exception e) {

		} finally {
			close(con, stmt, res);
		}
		return theTeacher;

	}

public Subject loadSubject(int subjectId) {

		Subject theSubject = null;

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM subjects WHERE id = " + subjectId;
			stmt = con.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				String shortcut = res.getString("shortcut");

				theSubject = new Subject(id, name,shortcut);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return theSubject;

	}

public Class loadClass(int classId) {

		Class theClass = null;

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {

			con = dataSource.getConnection();
			String query = "SELECT * FROM class WHERE id = " + classId;
			stmt = con.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				int id = res.getInt("id");
				int section = res.getInt("section");
				int subject = res.getInt("subject");
				int teacher = res.getInt("teacher");
				String time = res.getString("time");

				Teacher tempTeacher = loadTeacher(teacher);
				Subject tempSubject = loadSubject(subject);

				String teacher_name = tempTeacher.getFname() + " " + tempTeacher.getLname();

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return theClass;

	}

public List<Student> loadClassStudents(int classId) {

		List<Student> students = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			con = dataSource.getConnection();

			String query = "SELECT * FROM students WHERE class = " + classId;
			stmt = con.createStatement();
			res = stmt.executeQuery(query);

			while (res.next()) {
				int id = res.getInt("id");
				String firstName = res.getString("fname");
				String lastName = res.getString("lname");
				int age = res.getInt("age");
				int aclass = res.getInt("class");

				Student tempStudent = new Student(id, firstName, lastName, age, aclass);
				students.add(tempStudent);

			}

		} catch (Exception e) {
		} finally {
			close(con, stmt, res);
		}
		return students;

	}

private void close(Connection con, Statement stmt, ResultSet res) {

		try {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

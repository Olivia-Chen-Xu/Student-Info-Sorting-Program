//Data is imported from students.dat
//Linear search and binary search included, comment out/uncomment the search method
//to choose which method to be implemented

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class StudentInfoSortingAlgo {
	public static void main(String[] args) throws IOException {
		List studentArray = new List(100);
		studentArray.getList();
		studentArray.display("UNSORTED LIST OF STUDENTS");
		studentArray.pause();

		studentArray.gpaSort();
		studentArray.display("STUDENTS SORTED IN DESCENDING ORDER BY GPA");
		studentArray.pause();

		studentArray.ageSort();
		studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY AGE");
		studentArray.pause();

		studentArray.idSort();
		studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY ID#");
		studentArray.pause();

		int studentID = getID();
		int index = studentArray.search(studentID);

		if (index == -1)
			System.out.println("There is no student with an ID# of " + studentID + ".\n");
		else {
			studentArray.displayStudent(index); // displays the information for the found student
			studentArray.delete(index); // remove the same student from the array
			studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY ID# WITHOUT STUDENT# " + studentID);
			studentArray.pause();
		}
	}

	public static int getID() {
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter the 6-digit ID of the student.  { 100000 - 999999 }  -->  ");
		return input.nextInt();
	}
}

class Person {
	public String name;
	public int id;
	public int age;
	public double gpa;

	Person(String n, int ID, int a, double g) {
		name = n;
		id = ID;
		age = a;
		gpa = g;
	}
}

class List {
	private Person student[]; // stores array elements
	private int capacity; // actual array capacity
	private int size; // number of elements in the array

	public List(int c) {
		capacity = c;
		size = 0;
		student = new Person[capacity];
	}

	public void getList() throws IOException {
		FileReader inFile = new FileReader("students.dat");
		BufferedReader inStream = new BufferedReader(inFile);
		String s1, s2, s3, s4;
		int age, id;
		double gpa;
		int index = 0;
		while (((s1 = inStream.readLine()) != null) && ((s2 = inStream.readLine()) != null)
				&& ((s3 = inStream.readLine()) != null) && ((s4 = inStream.readLine()) != null)) {
			String name = s1;
			id = Integer.parseInt(s2);
			age = Integer.parseInt(s3);
			gpa = Double.parseDouble(s4);

			student[index] = new Person(name, id, age, gpa);
			index++;
		}
		inStream.close();
		size = index;
	}

	private String spaces(String name) {
		int tab = 24 - name.length();
		String temp = "";
		for (int j = 1; j <= tab; j++)
			temp += " ";
		return temp;
	}

	public void display(String listInfo) {
		DecimalFormat output = new DecimalFormat("0.000");
		System.out.println("\nDISPLAYING " + listInfo);
		System.out.println("\nStudent ID#     Student Name            Age         GPA");
		System.out.println("============================================================");

		for (int k = 0; k < size; k++)
			System.out.println(student[k].id + "          " + student[k].name + spaces(student[k].name) + student[k].age
					+ "          " + output.format(student[k].gpa));
	}

	public void pause() {
		Scanner input = new Scanner(System.in);
		String dummy;
		System.out.println("\nPress <Enter> to continue.");
		dummy = input.nextLine();
	}

	public void displayStudent(int index) {
		System.out.println("Name: " + student[index].name);
		System.out.println("Age:  " + student[index].age);
		System.out.println("GPA:  " + student[index].gpa

		);
	}

	private void swap(int x, int y) {
		Person temp = student[x];
		student[x] = student[y];
		student[y] = temp;
	}

	public void gpaSort() {
		for (int k = 0; k < size; k++) {
			int elements = k + 1;
			int index = indexSearchGPA(student[k].gpa, elements);
			insertNr(student[k], elements, index);
		}
		for (int k = 0; k < size / 2; k++) {
			swap(k, size - 1 - k);
		}
	}

	public void ageSort() {
		for (int k = 0; k < size; k++) {
			int elements = k + 1;
			int index = indexSearchAge(student[k].age, elements);
			insertNr(student[k], elements, index);
		}
	}

	public void idSort() {
		for (int k = 0; k < size; k++) {
			int elements = k + 1;
			int index = indexSearchID(student[k].id, elements);
			insertNr(student[k], elements, index);
		}
	}

	private int indexSearchGPA(double nr, int elements) {
		int index = 0;
		while (index < elements && nr > student[index].gpa)
			index++;
		return index;
	}

	private int indexSearchAge(int nr, int elements) {
		int index = 0;
		while (index < elements && nr > student[index].age)
			index++;
		return index;
	}

	private int indexSearchID(int nr, int elements) {
		int index = 0;
		while (index < elements && nr > student[index].id)
			index++;
		return index;
	}

	private void insertNr(Person nr, int elements, int index) {
		for (int k = elements - 1; k > index; k--)
			student[k] = student[k - 1];
		student[index] = nr;
	}

	// linear search
	/*
	 * public int search(int studentID) { boolean found = false; int k = 0; while (k
	 * < size && !found) { if (student[k].id == studentID) found = true; else k++; }
	 * if (found) return k; else return -1; }
	 */

	// binary search
	public int search(int studentID) {
		boolean found = false;
		int lo = 0;
		int hi = size - 1;
		int mid = 0;
		while (lo <= hi && !found) {
			mid = (lo + hi) / 2;
			if (student[mid].id == studentID)
				found = true;
			else {
				if (studentID > student[mid].id)
					lo = mid + 1;
				else
					hi = mid - 1;
			}
		}
		if (found)
			return mid;
		else
			return -1;
	}

	public void delete(int index) {
		if (index != -1) {
			for (int k = index; k < size - 1; k++)
				student[k] = student[k + 1];
			size--;
		}

	}
}

package tud.bp.group32.generatorutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tud.bp.group32.student.StudentControlfile;

public final class StudentControlFileIterator
        implements Iterator<List<StudentControlfile>> {
    /**
     * List of students to iterate over.
     */
    private LinkedList<StudentControlfile> students;
    /**
     * List of sizes of the student lists.
     */
    private LinkedList<Integer> sizes;
    /**
     * Sum of all students in the iterator.
     */
    private int sum = 0;

    /**
     * Constructor for the StudentControlFileIterator.
     * @param actualStudents List of students to iterate over.
     * @param sizesOfStudentLists List of sizes of the student lists.
     */
    public StudentControlFileIterator(
            final List<StudentControlfile> actualStudents,
            final List<Integer> sizesOfStudentLists) {
        this.students = new LinkedList<>(actualStudents);
        this.sizes = new LinkedList<>(sizesOfStudentLists);
        sizes.forEach(amount -> sum += amount);
    }

    @Override
    public boolean hasNext() {
        return !students.isEmpty();
    }

    @Override
    public List<StudentControlfile> next() {
        if (!hasNext()) {
            return null;
        }
        List<StudentControlfile> result = new LinkedList<>();
        int actualSize = sizes.removeFirst();
        while (result.size() != actualSize) {
            result.add(students.removeFirst());
        }
        return result;
    }

    /**
     * Fetches the next student in the iterator without removing it.
     * @return the next student in the iterator.
     */
    public List<StudentControlfile> peekNext() {
        if (!hasNext()) {
            return null;
        }
        List<StudentControlfile> result = new LinkedList<>();
        int actualSize = sizes.getFirst();
        int idx = 0;
        while (result.size() != actualSize) {
            result.add(students.get(idx));
            idx++;
        }
        return result;
    }

    /**
     * Getter for the sizes of the student lists.
     * @return the sizes of the student lists.
     */
    public LinkedList<Integer> getSizes() {
        return sizes;
    }

    /**
     * Fetches the sum of all students in the iterator.
     * @return the sum of all students in the iterator.
     */
    public int getSum() {
        return sum;
    }
}

package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    // Our collections for storing records
    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> feePayments = new ArrayList<>();
    private static final List<LibraryBook> libraryBooks = new ArrayList<>();
    private static final List<BookLoan> bookLoans = new ArrayList<>();

    public static void main(String[] args) {
        String choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    registerDepartment();
                    break;
                case "2":
                    registerStudent();
                    break;
                case "3":
                    registerTeacher();
                    break;
                case "4":
                    registerAdministrator();
                    break;
                case "5":
                    registerCourse();
                    break;
                case "6":
                    registerClassroom();
                    break;
                case "7":
                    enrollStudent();
                    break;
                case "8":
                    recordAssessment();
                    break;
                case "9":
                    recordFeePayment();
                    break;
                case "10":
                    registerLibraryBook();
                    break;
                case "11":
                    borrowBook();
                    break;
                case "12":
                    returnBook();
                    break;
                case "13":
                    viewAllRecords();
                    break;
                case "14":
                    searchById();
                    break;
                case "15":
                    updateRecord();
                    break;
                case "16":
                    studentAcademicSummary();
                    break;
                case "17":
                    studentPaymentHistory();
                    break;
                case "18":
                    viewAvailableBooks();
                    break;
                case "19":
                    demonstratePolymorphism();
                    break;
                case "0":
                    System.out.println("Thank you for using AUCA School Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (!choice.equals("0"));
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("==============================================");
        System.out.println("       AUCA_ID SCHOOL MANAGEMENT");
        System.out.println("==============================================");
        System.out.println("1.  Register Department");
        System.out.println("2.  Register Student");
        System.out.println("3.  Register Teacher");
        System.out.println("4.  Register Administrator");
        System.out.println("5.  Register Course");
        System.out.println("6.  Register Classroom");
        System.out.println("7.  Enroll Student");
        System.out.println("8.  Record Assessment");
        System.out.println("9.  Record Fee Payment");
        System.out.println("10. Register Library Book");
        System.out.println("11. Borrow Book");
        System.out.println("12. Return Book");
        System.out.println("13. View All Records");
        System.out.println("14. Search by ID");
        System.out.println("15. Update Record");
        System.out.println("16. Student Academic Summary");
        System.out.println("17. Student Payment History");
        System.out.println("18. View Available Books");
        System.out.println("19. Demonstrate Polymorphism");
        System.out.println("0.  Exit");
        System.out.println("==============================================");
    }

    public static void registerDepartment() {
        // The labels are displayed to the user in the registration form.
        // The kinds tell the program how each value should be checked.
        String[] labels = {
            "Department ID [String, unique, 4-30]",
            "Department name [String, 2-60]",
            "Department code [String, unique]",
            "Office location [String]",
            "Contact email [valid email]",
            "Head teacher [select existing teacher; optional]"
        };
        String[] kinds = {
            "ID",
            "NAME",
            "CODE",
            "TEXT",
            "EMAIL",
            "REL_OPTIONAL_TEACHER"
        };
        Object[] d = newData(6);
        if (!runSmartForm("DEPARTMENT REGISTRATION", labels, kinds, d))return;
        Department x = new Department((String) d[0], (String) d[1], (String) d[2], (String) d[3], (String) d[4], (Teacher) d[5]);
        departments.add(x);
        System.out.println("Department "+x.getDepartmentId()+" registered successfully.");
        x.printDetails();
    }

    // ==================================================
    // 5. REGISTER STUDENT
    // ==================================================

    public static void registerStudent() {
        String[] fields = new String[18];
        Department selectedDepartment = null;
        int step = 1;
        while (true) {
            printStudentStep(step);
            int first = step == 1 ? 0 : step == 2 ? 6 : 12;
            int last = step == 1 ? 6 : step == 2 ? 12 : 18;
            boolean goBack = false;
            boolean cancelled = false;
            for (int i = first;  i < last;  i++) {
                if (i == 7) {
                    DepartmentResult result = chooseStudentDepartment(selectedDepartment);
                    if (result.cancelled) {
                        cancelled = true;
                        break;
                    }
                    selectedDepartment = result.department;
                    fields[i] = selectedDepartment.getDepartmentId();
                    continue;
                }
                String value = promptStudentField(i, fields[i]);
                if (value == null) {
                    cancelled = true;
                    break;
                }
                if (value.equalsIgnoreCase("BACK")) {
                    goBack = true;
                    break;
                }
                fields[i] = value;
            }
            if (cancelled) {
                System.out.println("Student registration cancelled. No record was saved.");
                return;
            }
            if (goBack) {
                if (step == 1) {
                    System.out.println("You are already at the first step.");
                } else {
                    step--;
                }
                continue;
            }
            if (step < 3) {
                step++;
                continue;
            }
            // Step 4: complete review and confirmation.
            while (true) {
                printStudentReview(fields, selectedDepartment);
                System.out.println("[1] Save student   [2] Edit field   [0] Cancel");
                String choice = readCommand("Select option: ");
                if (choice.equals("0") || choice.equalsIgnoreCase("CANCEL")) {
                    System.out.println("Student registration cancelled. No record was saved.");
                    return;
                }
                if (choice.equals("2")) {
                    int fieldNumber = readInt("Enter field number to edit (01-18): ");
                    if (fieldNumber < 1 || fieldNumber > 18) {
                        System.out.println("Choose a field number from 01 to 18.");
                        continue;
                    }
                    int index = fieldNumber - 1;
                    if (index == 7) {
                        DepartmentResult result = chooseStudentDepartment(selectedDepartment);
                        if (result.cancelled) {
                            System.out.println("Department selection cancelled; review remains unchanged.");
                        } else {
                            selectedDepartment = result.department;
                            fields[7] = selectedDepartment.getDepartmentId();
                        }
                    } else {
                        String edited = promptStudentField(index, fields[index]);
                        if (edited == null) {
                            System.out.println("Edit cancelled; review remains unchanged.");
                        } else if (!edited.equalsIgnoreCase("BACK")) {
                            fields[index] = edited;
                        }
                    }
                    continue;
                }
                if (!choice.equals("1")) {
                    System.out.println("Select 1, 2 or 0.");
                    continue;
                }
                String error = validateStudentBeforeSave(fields, selectedDepartment);
                if (error != null) {
                    System.out.println("Cannot save: " + error);
                    continue;
                }
                Student student = new Student(
                fields[0], fields[1], fields[2], fields[3], fields[4],
                LocalDate.parse(fields[5]), fields[6], selectedDepartment,
                LocalDate.parse(fields[8]), fields[9], fields[10], fields[11],
                fields[12], fields[13], LocalDate.parse(fields[14]),
                Integer.parseInt(fields[15]), fields[16],
                fields[17].equalsIgnoreCase("YES")
                );
                students.add(student);
                System.out.println("Student " + student.getStudentNumber() + " registered successfully.");
                student.printDetails();
                System.out.println("[1] Register another student   [2] View student   [0] Main menu");
                String after = readCommand("Select option: ");
                if (after.equals("1")) {
                    registerStudent();
                } else if (after.equals("2")) {
                    student.printDetails();
                }
                return;
            }
        }
    }

    private static void printStudentStep(int step) {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| STUDENT REGISTRATION                         Step " + step + " of 4       |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Personal details    [2] Academic details               |");
        System.out.println("| [3] Student details     [4] Review & confirm               |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("Enter the requested value. Type BACK to return; CANCEL to discard.");
        if (step == 1) System.out.println("[STEP 1/4] PERSONAL DETAILS");
        if (step == 2) System.out.println("[STEP 2/4] ACADEMIC DETAILS");
        if (step == 3) System.out.println("[STEP 3/4] STUDENT DETAILS");
    }

    private static String promptStudentField(int index, String oldValue) {
        String[] labels = {
            "01 Person ID [String | unique | 4-30 chars]",
            "02 First name [String | 2-60 chars]",
            "03 Last name [String | 2-60 chars]",
            "04 Email [String | valid email]",
            "05 Phone [String | 10-15 digits]",
            "06 Date of birth [LocalDate | YYYY-MM-DD]",
            "07 Member number [String | unique | 4-30 chars]",
            "08 Department [select a saved Department]",
            "09 Join date [LocalDate | YYYY-MM-DD]",
            "10 Academic status [1 ACTIVE / 2 INACTIVE]",
            "11 Institutional email [valid email | unique]",
            "12 Advisor name [String | 2-120 chars]",
            "13 Student number [String | unique | 4-30 chars]",
            "14 Program name [String | 2-120 chars]",
            "15 Admission date [LocalDate | YYYY-MM-DD]",
            "16 Study year [int | 1-6]",
            "17 Guardian phone [String | 10-15 digits]",
            "18 Scholarship [1 YES / 2 NO]"
        };
        while (true) {
            System.out.print(labels[index] + (oldValue == null ? " : " : " (current: " + oldValue + ") : "));
            String value = scanner.nextLine().trim();
            if (value.equalsIgnoreCase("CANCEL") || value.equals("0")) return null;
            if (value.equalsIgnoreCase("BACK")) return "BACK";
            if (value.isEmpty() && oldValue != null) return oldValue;
            if (value.isEmpty()) {
                System.out.println("This field is required.");
                continue;
            }
            String error = validateStudentField(index, value);
            if (error == null) {
                if (index == 9) return (value.equals("1") || value.equalsIgnoreCase("ACTIVE")) ? "ACTIVE" : "INACTIVE";
                if (index == 17) return (value.equals("1") || value.equalsIgnoreCase("YES")) ? "YES" : "NO";
                return value;
            }
            System.out.println("Invalid value: " + error);
        }
    }

    private static String validateStudentField(int index, String value) {
        switch (index) {
            case 0:
                if (value.length() < 4 || value.length() > 30) return "Person ID must contain 4-30 characters.";
                if (idExists(value)) return "Person ID " + value + " already exists.";
                return null;
            case 1: case 2:
                return value.length() < 2 || value.length() > 60 ? "Name must contain 2-60 characters." : null;
            case 3: case 10:
                if (value.length() > 100 || !isValidEmail(value)) return "Enter a valid email address (maximum 100 characters).";
                if (emailExists(value)) return "This email address is already registered.";
                return null;
            case 4: case 16:
                return value.matches("\\+?\\d{10,15}") ? null : "Phone must contain 10-15 digits, optionally starting with +.";
            case 5:
                try {
                LocalDate date = LocalDate.parse(value);
                return date.isBefore(LocalDate.now()) ? null : "Date of birth must be in the past.";
            } catch (DateTimeParseException ex) {
                return "Use YYYY-MM-DD, for example 2004-05-16.";
            }
            case 6:
                if (value.length() < 4 || value.length() > 30) return "Member number must contain 4-30 characters.";
                return memberNumberExists(value) ? "Member number already exists." : null;
            case 8: case 14:
                try {
                LocalDate date = LocalDate.parse(value);
                if (date.isAfter(LocalDate.now())) return "Date cannot be in the future.";
                if (index == 14 && date.isBefore(LocalDate.parse("1900-01-01"))) return "Enter a valid admission date.";
                return null;
            } catch (DateTimeParseException ex) {
                return "Use YYYY-MM-DD, for example 2026-09-01.";
            }
            case 9:
                if (value.equals("1") || value.equalsIgnoreCase("ACTIVE")
                        || value.equals("2") || value.equalsIgnoreCase("INACTIVE")) {
                    return null;
                }
                return "Choose 1 ACTIVE or 2 INACTIVE.";
            case 11: case 13:
                return value.length() < 2 || value.length() > 120 ? "Enter 2-120 characters." : null;
            case 12:
                if (value.length() < 4 || value.length() > 30) return "Student number must contain 4-30 characters.";
                return studentNumberExists(value) ? "Student number " + value + " already exists." : null;
            case 15:
                try {
                int year = Integer.parseInt(value);
                return year >= 1 && year <= 6 ? null : "Study year must be between 1 and 6.";
            } catch (NumberFormatException ex) {
                return "Enter a whole number from 1 to 6.";
            }
            case 17:
                if (value.equals("1") || value.equalsIgnoreCase("YES") || value.equals("2") || value.equalsIgnoreCase("NO")) return null;
                return "Choose 1 YES or 2 NO.";
            default: return null;
        }
    }

    private static String validateStudentBeforeSave(String[] f, Department department) {
        if (department == null || !departments.contains(department)) return "Select a valid registered Department.";
        for (int i = 0;  i < f.length;  i++) {
            if (i == 7) continue;
            String error = validateStudentField(i, f[i]);
            // The record's own existing values are allowed during final recheck.
            if (error != null) return "Field " + String.format("%02d", i + 1) + ": " + error;
        }
        if (f[9] == null || f[9].isEmpty()) return "Academic status is required.";
        if (f[3] != null && f[10] != null && f[3].equalsIgnoreCase(f[10])) {
            return "Personal and institutional email addresses must be different.";
        }
        return null;
    }

    private static boolean idExistsForStudentBeingEdited(String id) {
        return false;
    }

    private static void printStudentReview(String[] f, Department department) {
        System.out.println("\n[STEP 4/4] REVIEW & CONFIRM");
        String[] labels = {
            "Person ID",
            "First name",
            "Last name",
            "Email",
            "Phone",
            "Date of birth",
            "Member number",
            "Department",
            "Join date",
            "Academic status",
            "Institutional email",
            "Advisor name",
            "Student number",
            "Program name",
            "Admission date",
            "Study year",
            "Guardian phone",
            "Scholarship"
        };
        System.out.println("\n+---------------------- REVIEW STUDENT ----------------------+");
        System.out.println("PERSON");
        for (int i = 0;  i < 6;  i++) System.out.printf("%02d %-20s : %s%n", i + 1, labels[i], f[i]);
        System.out.println("ACADEMIC MEMBER");
        for (int i = 6;  i < 12;  i++) {
            String value = i == 7 ? "[" + department.getDepartmentId() + "] " + department.getDepartmentName() : f[i];
            System.out.printf("%02d %-20s : %s%n", i + 1, labels[i], value);
        }
        System.out.println("STUDENT");
        for (int i = 12;  i < 18;  i++) System.out.printf("%02d %-20s : %s%n", i + 1, labels[i], f[i]);
        System.out.println("+------------------------------------------------------------+");
    }

    private static DepartmentResult chooseStudentDepartment(Department current) {
        while (true) {
            System.out.println("\n-------------- CHOOSE DEPARTMENT --------------");
            if (departments.isEmpty()) System.out.println("No Departments are registered yet.");
            for (int i = 0;  i < departments.size(); i++) {
                Department d = departments.get(i);
                System.out.println((i + 1) + ". [" + d.getDepartmentId() + "] " + d.getDepartmentName());
            }
            System.out.println((departments.size() + 1) + ". [+] Register a new Department");
            System.out.println("0. Cancel student registration");
            String choice = readCommand("Select option: ");
            if (choice.equals("0") || choice.equalsIgnoreCase("CANCEL")) return new DepartmentResult(null, true);
            try {
                int selected = Integer.parseInt(choice);
                if (selected >= 1 && selected <= departments.size()) return new DepartmentResult(departments.get(selected - 1), false);
                if (selected == departments.size() + 1) {
                    int before = departments.size();
                    registerDepartment();
                    if (departments.size() > before) return new DepartmentResult(departments.get(departments.size() - 1), false);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Enter a valid numbered selection.");
            }
        }
    }

    private static String readCommand(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static boolean emailExists(String email) {
        for (Person p  :  getAllPeople()) if (p.getEmail().equalsIgnoreCase(email)) return true;
        for (Student s  :  students) if (s.getInstitutionalEmail().equalsIgnoreCase(email)) return true;
        for (Teacher t  :  teachers) if (t.getInstitutionalEmail().equalsIgnoreCase(email)) return true;
        return false;
    }

    private static List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);
        return people;
    }

    private static boolean memberNumberExists(String number) {
        for (Student s  :  students) if (s.getMemberNumber().equalsIgnoreCase(number)) return true;
        for (Teacher t  :  teachers) if (t.getMemberNumber().equalsIgnoreCase(number)) return true;
        return false;
    }

    private static boolean studentNumberExists(String number) {
        for (Student s  :  students) if (s.getStudentNumber().equalsIgnoreCase(number)) return true;
        return false;
    }

    private static class DepartmentResult {
        private final Department department;
        private final boolean cancelled;
        private DepartmentResult(Department department, boolean cancelled) {
            this.department = department;
            this.cancelled = cancelled;
        }
    }

    // ==================================================
    // 6. REGISTER TEACHER
    // ==================================================
    // ================================================================
    // SHARED FOUR-STEP SMART FORM ENGINE (Sections 12 and 18)
    // ================================================================
    private static final String SMART_BACK = "__BACK__";
    private static final String SMART_CANCEL = "__CANCEL__";

    // This method displays the common form steps, collects values and checks them before saving.
    // All registration forms use this method so they behave in the same way.
    private static boolean runSmartForm(String title, String[] labels, String[] kinds, Object[] data) {
        int step = 1;
        int chunk = (labels.length + 2) / 3;
        while (true) {
            System.out.println("\n+------------------------------------------------------------+");
            System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
            System.out.printf("| %-43s Step %d of 4       |%n", title, step);
            System.out.println("+------------------------------------------------------------+");
            System.out.println("| [1] Details       [2] Related/extra details                |");
            System.out.println("| [3] Final details [4] Review & confirm                      |");
            System.out.println("+------------------------------------------------------------+");
            System.out.println("Enter values separately. Type BACK to go back; CANCEL to discard.");
            int from = (step - 1) * chunk;
            int to = Math.min(labels.length, from + chunk);
            boolean back = false;
            for (int i = from;  i < to;  i++) {
                Object value = readSmartField(i, labels[i], kinds[i], data[i], kinds, data);
                if (value == SMART_CANCEL) {
                    System.out.println(title + " cancelled. No record was saved.");
                    return false;
                }
                if (value == SMART_BACK) {
                    back = true;
                    break;
                }
                data[i] = value;
            }
            if (back) {
                if (step > 1) step--;
                else System.out.println("You are already at Step 1.");
                continue;
            }
            if (step < 3) {
                step++;
                continue;
            } while (true) {
                System.out.println("\n[STEP 4/4] REVIEW & CONFIRM - " + title);
                System.out.println("--------------------------------------------------------------");
                for (int i = 0;  i < labels.length;  i++)
                System.out.printf("%02d %-28s : %s%n", i + 1, labels[i], smartDisplay(data[i]));
                System.out.println("--------------------------------------------------------------");
                System.out.println("[1] Save record   [2] Edit field   [0] Cancel");
                String choice = readCommand("Select option: ");
                if (choice.equals("0") || choice.equalsIgnoreCase("CANCEL")) {
                    System.out.println(title + " cancelled. No record was saved.");
                    return false;
                }
                if (choice.equals("2")) {
                    int field = readInt("Field number to edit (1-" + labels.length + "): ");
                    if (field < 1 || field > labels.length) {
                        System.out.println("Choose a field from 1 to " + labels.length + ".");
                        continue;
                    }
                    int index = field - 1;
                    Object updated = readSmartField(index, labels[index], kinds[index], data[index], kinds, data);
                    if (updated == SMART_CANCEL || updated == SMART_BACK)
                    System.out.println("Edit cancelled; previous value retained.");
                    else data[index] = updated;
                    continue;
                }
                if (!choice.equals("1")) {
                    System.out.println("Choose 1, 2 or 0.");
                    continue;
                }
                String error = validateSmartData(kinds, data);
                if (error != null) {
                    System.out.println("Cannot save: " + error);
                    continue;
                }
                return true;
            }
        }
    }

    private static Object readSmartField(int index, String label, String kind,
            Object oldValue, String[] kinds, Object[] data) {
        if (kind.startsWith("REL_")) {
            return selectSmartRelation(kind, label);
        }

        while (true) {
            System.out.print(String.format("%02d %s%s: ", index + 1, label,
                    oldValue == null ? "" : " (current: " + smartDisplay(oldValue) + ")"));

            String value = scanner.nextLine().trim();

            if (value.equalsIgnoreCase("BACK")) {
                return SMART_BACK;
            }
            if (value.equalsIgnoreCase("CANCEL")) {
                return SMART_CANCEL;
            }

            // A return date is optional while the book is still on loan.
            // Check this before the old-value condition so the user can clear it.
            if (kind.equals("RETURN_DATE") && value.isEmpty()) {
                return null;
            }

            if (value.isEmpty() && oldValue != null) {
                return oldValue;
            }

            String error = smartFieldError(kind, value, null);
            if (error == null) {
                Object newValue = normalizeSmartValue(kind, value);

                // Check date relationships immediately, before accepting the value.
                if (newValue instanceof LocalDate) {
                    error = smartDateRelationshipError(
                            index, kind, (LocalDate) newValue, kinds, data);
                }
                if (error == null) {
                    error = smartCrossFieldError(index, kind, newValue, kinds, data);
                }
            }

            if (error == null) {
                return normalizeSmartValue(kind, value);
            }

            System.out.println("Invalid " + label + ": " + error);
        }
    }

    // Checks values that depend on another field while the user is entering them.
    private static String smartCrossFieldError(int index, String kind,
            Object enteredValue, String[] kinds, Object[] data) {

        if (kind.equals("DOUBLE") || kind.equals("DOUBLE_NONNEG")) {
            int maximumIndex = -1;
            int obtainedIndex = -1;
            for (int i = 0; i < kinds.length; i++) {
                if (kinds[i].equals("DOUBLE")) maximumIndex = i;
                if (kinds[i].equals("DOUBLE_NONNEG")) obtainedIndex = i;
            }
            if (maximumIndex >= 0 && obtainedIndex >= 0) {
                Object maximum = index == maximumIndex ? enteredValue : data[maximumIndex];
                Object obtained = index == obtainedIndex ? enteredValue : data[obtainedIndex];
                if (maximum instanceof Double && obtained instanceof Double
                        && (Double) obtained > (Double) maximum) {
                    return "obtained score cannot exceed maximum score.";
                }
            }
        }

        if (kind.equals("INT_NONNEG")) {
            int totalIndex = -1;
            int availableIndex = -1;
            for (int i = 0; i < kinds.length; i++) {
                if (kinds[i].equals("INT_NONNEG")) {
                    if (totalIndex == -1) totalIndex = i;
                    else availableIndex = i;
                }
            }
            if (totalIndex >= 0 && availableIndex >= 0) {
                Object total = index == totalIndex ? enteredValue : data[totalIndex];
                Object available = index == availableIndex ? enteredValue : data[availableIndex];
                if (total instanceof Integer && available instanceof Integer
                        && (Integer) available > (Integer) total) {
                    return "available copies cannot exceed total copies.";
                }
            }
        }
        return null;
    }

    // Checks dates that depend on another date in the same form.
    // This is especially important for borrowed, due and returned dates.
    private static String smartDateRelationshipError(int index, String kind,
            LocalDate enteredDate, String[] kinds, Object[] data) {

        int borrowedIndex = -1;
        int dueIndex = -1;
        int returnedIndex = -1;

        for (int i = 0; i < kinds.length; i++) {
            if (kinds[i].equals("DATE")) {
                borrowedIndex = i;
            } else if (kinds[i].equals("FUTURE_DATE")) {
                dueIndex = i;
            } else if (kinds[i].equals("RETURN_DATE")) {
                returnedIndex = i;
            }
        }

        // Borrowed date and due date identify the date relationship in a BookLoan form.
        // Returned date is optional and is normally set later by Return Book.
        if (borrowedIndex == -1 || dueIndex == -1) {
            return null;
        }

        LocalDate borrowedDate = null;
        LocalDate dueDate = null;
        LocalDate returnedDate = null;

        if (index == borrowedIndex) {
            borrowedDate = enteredDate;
        } else if (data[borrowedIndex] instanceof LocalDate) {
            borrowedDate = (LocalDate) data[borrowedIndex];
        }

        if (index == dueIndex) {
            dueDate = enteredDate;
        } else if (data[dueIndex] instanceof LocalDate) {
            dueDate = (LocalDate) data[dueIndex];
        }

        if (returnedIndex != -1) {
            if (index == returnedIndex) {
                returnedDate = enteredDate;
            } else if (data[returnedIndex] instanceof LocalDate) {
                returnedDate = (LocalDate) data[returnedIndex];
            }
        }

        if (borrowedDate != null && dueDate != null
                && !dueDate.isAfter(borrowedDate)) {
            if (index == borrowedIndex) {
                return "borrowed date must be before the already entered due date.";
            }
            return "due date must be after borrowed date.";
        }

        if (borrowedDate != null && returnedDate != null
                && returnedDate.isBefore(borrowedDate)) {
            if (index == borrowedIndex) {
                return "borrowed date cannot be after the already entered return date.";
            }
            return "return date cannot be before borrowed date.";
        }

        if (returnedDate != null && returnedDate.isAfter(LocalDate.now())) {
            return "return date cannot be in the future.";
        }

        return null;
    }
    private static Object normalizeSmartValue(String kind, String value) {
        if (kind.equals("STATUS")) return value.equals("1") || value.equalsIgnoreCase("ACTIVE") ? "ACTIVE" : "INACTIVE";
        if (kind.equals("YESNO") || kind.equals("ACTIVE_BOOL"))
        return value.equals("1") || value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("TRUE");
        if (kind.equals("INT") || kind.startsWith("INT_")) return Integer.valueOf(value);
        if (kind.equals("DOUBLE") || kind.equals("DOUBLE_NONNEG")) return Double.valueOf(value);
        if (kind.equals("MONEY")) return new BigDecimal(value);
        if (kind.equals("DATE") || kind.equals("DOB")
                || kind.equals("FUTURE_DATE") || kind.equals("RETURN_DATE")) {
            return LocalDate.parse(value);
        }
        return value;
    }

    private static String smartFieldError(String kind, String value, Object current) {
        if (value == null || value.trim().isEmpty()) return "this field is required.";
        if (kind.equals("ID")) {
            if (value.length() < 4 || value.length() > 30) return "must contain 4-30 characters.";
            if (idExists(value)) return "ID already exists.";
        }
        if (kind.equals("NAME")) return value.length() < 2 || value.length() > 60 ? "must contain 2-60 characters." : null;
        if (kind.equals("TEXT")) return value.length() < 2 || value.length() > 120 ? "must contain 2-120 characters." : null;
        if (kind.equals("EMAIL")) {
            if (value.length() > 100 || !isValidEmail(value)) return "enter a valid email address (maximum 100 characters).";
            if (emailExists(value)) return "email address is already registered.";
        }
        if (kind.equals("PHONE")) return value.matches("\\+?\\d{10,15}") ? null : "use 10-15 digits, optionally starting with +.";
        if (kind.equals("DOB")) {
            try {
                return LocalDate.parse(value).isBefore(LocalDate.now()) ? null : "date of birth must be in the past; use YYYY-MM-DD.";
            } catch (DateTimeParseException ex) {
                return "use YYYY-MM-DD (example: 2004-05-16).";
            }
        }
        if (kind.equals("DATE") || kind.equals("FUTURE_DATE")) {
            try {
                LocalDate d = LocalDate.parse(value);
                if (kind.equals("DATE") && d.isAfter(LocalDate.now())) return "date cannot be in the future.";
                return null;
            } catch (DateTimeParseException ex) {
                return "use YYYY-MM-DD (example: 2026-09-01).";
            }
        }
        if (kind.equals("STATUS")) {
            boolean validStatus = value.equals("1") || value.equals("2")
                    || value.equalsIgnoreCase("ACTIVE") || value.equalsIgnoreCase("INACTIVE");
            return validStatus ? null : "choose 1 ACTIVE or 2 INACTIVE.";
        }
        if (kind.equals("YESNO") || kind.equals("ACTIVE_BOOL")) {
            boolean validAnswer = value.equals("1") || value.equals("2")
                    || value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("NO")
                    || value.equalsIgnoreCase("TRUE") || value.equalsIgnoreCase("FALSE");
            return validAnswer ? null : "choose 1 YES or 2 NO.";
        }
        if (kind.equals("INT") || kind.startsWith("INT_")) {
            try {
                int n = Integer.parseInt(value);
                if (kind.equals("INT_POS") && n <= 0) return "must be greater than zero.";
                if (kind.equals("INT_NONNEG") && n<0) return "cannot be negative.";
                if (kind.equals("INT_1_8") && (n<1 || n>8)) return "must be between 1 and 8.";
                if (kind.equals("INT_1_6") && (n<1 || n>6)) return "must be between 1 and 6.";
                return null;
            } catch (NumberFormatException ex){
                return "enter a whole number.";
            }
        }
        if (kind.equals("DOUBLE")) {
            try {
                double number = Double.parseDouble(value);
                return Double.isFinite(number) && number > 0
                        ? null : "must be a finite number greater than zero.";
            } catch (NumberFormatException ex){
                return "enter a valid number.";
            }
        }
        if (kind.equals("DOUBLE_NONNEG")) {
            try {
                double number = Double.parseDouble(value);
                return Double.isFinite(number) && number >= 0
                        ? null : "must be a finite number that is zero or greater.";
            } catch (NumberFormatException ex){
                return "enter a valid number.";
            }
        }
        if (kind.equals("MONEY")) {
            try {
                return new BigDecimal(value).compareTo(BigDecimal.ZERO)>0 ? null : "amount must be greater than zero.";
            } catch (NumberFormatException ex){
                return "enter a valid monetary amount.";
            }
        }
        if (kind.equals("CODE")) {
            if (value.length()<2 || value.length()>30) return "must contain 2-30 characters.";
            if (departmentCodeExists(value)) return "department code already exists.";
        }
        if (kind.equals("COURSE_CODE")) {
            if (value.length()<2 || value.length()>30) return "must contain 2-30 characters.";
            for (Course c : courses) if (c.getCourseCode().equalsIgnoreCase(value)) return "course code already exists.";
        }
        if (kind.equals("MEMBER")) {
            if (value.length() < 4 || value.length() > 30) return "must contain 4-30 characters.";
            return memberNumberExists(value) ? "member number already exists." : null;
        }
        if (kind.equals("STUDENT_NO")) {
            if (value.length() < 4 || value.length() > 30) return "must contain 4-30 characters.";
            return studentNumberExists(value) ? "student number already exists." : null;
        }
        if (kind.equals("EMPLOYEE_NO")) {
            for (Teacher t : teachers) if (t.getEmployeeNumber().equalsIgnoreCase(value)) return "employee number already exists.";
            return value.length()<4 || value.length()>30 ? "must contain 4-30 characters." : null;
        }
        if (kind.equals("STAFF_NO")) {
            for (Administrator a : administrators) if (a.getStaffNumber().equalsIgnoreCase(value)) return "staff number already exists.";
            return value.length()<4 || value.length()>30 ? "must contain 4-30 characters." : null;
        }
        if (kind.equals("RECEIPT")) return receiptExists(value) ? "receipt number already exists." : null;
        if (kind.equals("ISBN")) return isbnExists(value) ? "ISBN already exists." : null;
        if (kind.equals("PAYMENT_METHOD")) {
            return allowed(value, "CASH", "MOBILE MONEY", "BANK TRANSFER", "CARD")
                    ? null : "choose CASH, MOBILE MONEY, BANK TRANSFER or CARD.";
        }
        if (kind.equals("ASSESSMENT_TYPE")) {
            return allowed(value, "QUIZ", "ASSIGNMENT", "CAT", "MIDTERM", "FINAL",
                    "PROJECT", "PRESENTATION", "EXAM")
                    ? null : "choose QUIZ, ASSIGNMENT, CAT, MIDTERM, FINAL, PROJECT, PRESENTATION or EXAM.";
        }
        if (kind.equals("SEMESTER")) {
            return allowed(value, "SEMESTER 1", "SEMESTER 2", "SEMESTER 3",
                    "SEMESTER I", "SEMESTER II", "SEMESTER III", "1", "2", "3")
                    ? null : "use Semester 1, Semester 2 or Semester 3.";
        }
        if (kind.equals("RETURN_DATE")) {
            try {
                LocalDate date = LocalDate.parse(value);
                if (date.isAfter(LocalDate.now())) {
                    return "return date cannot be in the future.";
                }
                return null;
            } catch (DateTimeParseException ex) {
                return "use YYYY-MM-DD or leave blank if not returned.";
            }
        }
        return null;
    }

    private static boolean allowed(String value, String... options) {
        for (String option : options) if (option.equalsIgnoreCase(value)) return true;
        return false;
    }

    private static String validateSmartData(String[] kinds, Object[] data) {
        // First, check every individual field.
        for (int i = 0; i < kinds.length; i++) {
            String kind = kinds[i];
            Object value = data[i];

            if (kind.startsWith("REL_")) {
                if (value == null && !kind.equals("REL_OPTIONAL_TEACHER")) {
                    return "Select a valid " + kind.substring(4).toLowerCase() + ".";
                }
                continue;
            }

            if (kind.equals("RETURN_DATE") && value == null) {
                continue;
            }

            String error = smartFieldError(kind, String.valueOf(value), null);
            if (error != null) {
                return String.format("Field %02d: %s", i + 1, error);
            }
        }

        // Next, check rules that compare two or more fields.
        for (int i = 0; i < kinds.length; i++) {
            if (kinds[i].equals("EMAIL")) {
                for (int j = i + 1; j < kinds.length; j++) {
                    if (kinds[j].equals("EMAIL")
                            && String.valueOf(data[i]).equalsIgnoreCase(String.valueOf(data[j]))) {
                        return "Personal and institutional email addresses must be different.";
                    }
                }
            }

            if (kinds[i].equals("INT_NONNEG") && i + 1 < kinds.length
                    && kinds[i + 1].equals("INT_NONNEG")
                    && data[i] instanceof Integer && data[i + 1] instanceof Integer
                    && (Integer) data[i + 1] > (Integer) data[i]) {
                return "Available copies cannot exceed total copies.";
            }

            if (kinds[i].equals("DATE") && i + 1 < kinds.length
                    && kinds[i + 1].equals("FUTURE_DATE")
                    && data[i] instanceof LocalDate && data[i + 1] instanceof LocalDate
                    && !((LocalDate) data[i + 1]).isAfter((LocalDate) data[i])) {
                return "Due date must be after borrowed date.";
            }

            if (kinds[i].equals("DATE") && i + 2 < kinds.length
                    && kinds[i + 2].equals("RETURN_DATE")
                    && data[i] instanceof LocalDate && data[i + 2] instanceof LocalDate
                    && ((LocalDate) data[i + 2]).isBefore((LocalDate) data[i])) {
                return "Return date cannot be before borrowed date.";
            }

            if (kinds[i].equals("RETURN_DATE") && data[i] instanceof LocalDate
                    && ((LocalDate) data[i]).isAfter(LocalDate.now())) {
                return "Return date cannot be in the future.";
            }

            if (kinds[i].equals("DOUBLE") && i + 1 < kinds.length
                    && (kinds[i + 1].equals("DOUBLE") || kinds[i + 1].equals("DOUBLE_NONNEG"))
                    && data[i] instanceof Double && data[i + 1] instanceof Double
                    && (Double) data[i + 1] > (Double) data[i]) {
                return "Obtained score cannot exceed maximum score.";
            }
        }

        // Do not allow a student to have two active enrollments in the same semester.
        if (kinds.length == 6 && kinds[1].equals("REL_STUDENT")
                && kinds[2].equals("REL_COURSE") && kinds[4].equals("SEMESTER")
                && kinds[5].equals("STATUS") && data[1] instanceof Student
                && data[2] instanceof Course
                && "ACTIVE".equalsIgnoreCase(String.valueOf(data[5]))) {

            for (Enrollment enrollment : enrollments) {
                if (enrollment.isActive()
                        && enrollment.getStudent() == data[1]
                        && enrollment.getCourse() == data[2]
                        && enrollment.getSemester().equalsIgnoreCase(String.valueOf(data[4]))) {
                    return "An active enrollment already exists for this student, course and semester.";
                }
            }
        }

        return null;
    }

    private static String smartDisplay(Object value) {
        if (value == null) return "Not returned / None";
        if (value instanceof Department){
            Department d = (Department)value;
            return "["+d.getDepartmentId()+"] "+d.getDepartmentName();
        }
        if (value instanceof Teacher){
            Teacher t = (Teacher)value;
            return "["+t.getEmployeeNumber()+"] "+t.getDisplayName();
        }
        if (value instanceof Student){
            Student s = (Student)value;
            return "["+s.getStudentNumber()+"] "+s.getDisplayName();
        }
        if (value instanceof Course){
            Course c = (Course)value;
            return "["+c.getCourseCode()+"] "+c.getCourseTitle();
        }
        if (value instanceof Enrollment){
            Enrollment e = (Enrollment)value;
            return "["+e.getEnrollmentId()+"] "+e.getStudent().getDisplayName()+" - "+e.getCourse().getCourseTitle();
        }
        if (value instanceof LibraryBook){
            LibraryBook b = (LibraryBook)value;
            return "["+b.getIsbn()+"] "+b.getTitle()+" (available: "+b.getAvailableCopies()+")";
        }
        return String.valueOf(value);
    }

    private static Object selectSmartRelation(String kind, String label) {
        while (true) {
            if (kind.equals("REL_DEPARTMENT")) {
                if (departments.isEmpty()) {
                    System.out.println("No Department exists. Register one now to continue.");
                    if (!nested("Department", 0)) return SMART_CANCEL;
                }
                System.out.println("\nChoose Department:");
                for (int i = 0; i<departments.size();i++) System.out.println((i + 1)+". "+smartDisplay(departments.get(i)));
                System.out.println("0. Cancel this form");
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return SMART_CANCEL;
                if (n >= 1 && n <= departments.size())return departments.get(n - 1);
            } else if (kind.equals("REL_TEACHER") || kind.equals("REL_OPTIONAL_TEACHER")) {
                if (teachers.isEmpty()) {
                    if (kind.equals("REL_OPTIONAL_TEACHER")) return null;
                    System.out.println("No Teacher exists. Register one now to continue.");
                    if (!nested("Teacher", 0)) return SMART_CANCEL;
                }
                if (kind.equals("REL_OPTIONAL_TEACHER")) System.out.println("0. No head teacher");
                System.out.println("\nChoose Teacher:");
                for (int i = 0; i<teachers.size();i++) System.out.println((i + 1)+". "+smartDisplay(teachers.get(i)));
                System.out.println("0. "+(kind.equals("REL_OPTIONAL_TEACHER")?"No head teacher":"Cancel this form"));
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return kind.equals("REL_OPTIONAL_TEACHER")?null:SMART_CANCEL;
                if (n >= 1 && n <= teachers.size())return teachers.get(n - 1);
            } else if (kind.equals("REL_STUDENT")) {
                if (students.isEmpty()) {
                    System.out.println("No Student exists. Register one now to continue.");
                    if (!nested("Student", 0))return SMART_CANCEL;
                }
                System.out.println("\nChoose Student:");
                for (int i = 0; i<students.size();i++)System.out.println((i + 1)+". "+smartDisplay(students.get(i)));
                System.out.println("0. Cancel this form");
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return SMART_CANCEL;
                if (n >= 1 && n <= students.size())return students.get(n - 1);
            } else if (kind.equals("REL_COURSE")) {
                if (courses.isEmpty()) {
                    System.out.println("No Course exists. Register one now to continue.");
                    if (!nested("Course", 0))return SMART_CANCEL;
                }
                System.out.println("\nChoose Course:");
                for (int i = 0; i<courses.size();i++)System.out.println((i + 1)+". "+smartDisplay(courses.get(i)));
                System.out.println("0. Cancel this form");
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return SMART_CANCEL;
                if (n >= 1 && n <= courses.size())return courses.get(n - 1);
            } else if (kind.equals("REL_ENROLLMENT")) {
                List<Enrollment> active = new ArrayList<>();
                for (Enrollment e : enrollments)if (e.isActive())active.add(e);
                if (active.isEmpty()) {
                    System.out.println("No active Enrollment exists. Register one now to continue.");
                    if (!nested("Enrollment", 0))return SMART_CANCEL;
                    continue;
                }
                System.out.println("\nChoose active Enrollment:");
                for (int i = 0; i<active.size();i++)System.out.println((i + 1)+". "+smartDisplay(active.get(i)));
                System.out.println("0. Cancel this form");
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return SMART_CANCEL;
                if (n >= 1 && n <= active.size())return active.get(n - 1);
            } else if (kind.equals("REL_BOOK")) {
                List<LibraryBook> available = new ArrayList<>();
                for (LibraryBook b : libraryBooks)if (b.getAvailableCopies()>0)available.add(b);
                if (available.isEmpty()) {
                    System.out.println("No available LibraryBook exists. Register a book now to continue.");
                    if (!nested("LibraryBook", 0))return SMART_CANCEL;
                    continue;
                }
                System.out.println("\nChoose available LibraryBook:");
                for (int i = 0; i<available.size();i++)System.out.println((i + 1)+". "+smartDisplay(available.get(i)));
                System.out.println("0. Cancel this form");
                int n = readSmartSelection();
                if (n == -1)return SMART_BACK;
                if (n == -2){
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == 0)return SMART_CANCEL;
                if (n >= 1 && n <= available.size())return available.get(n - 1);
            }
            System.out.println("Invalid selection. Try again.");
        }
    }

    private static int readSmartSelection() {
        String value = readCommand("Select (BACK to previous step, 0 to cancel): ");
        if (value.equalsIgnoreCase("BACK")) return -1;
        if (value.equalsIgnoreCase("CANCEL")) return 0;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return -2;
        }
    }

    private static boolean nested(String type, int depth) {
        if (depth>5) {
            System.out.println("Nested registration limit reached.");
            return false;
        }
        switch (type) {
            case "Department": {
                int n = departments.size();
                registerDepartment();
                return departments.size()>n;
            }
            case "Student": {
                int n = students.size();
                registerStudent();
                return students.size()>n;
            }
            case "Teacher": {
                int n = teachers.size();
                registerTeacher();
                return teachers.size()>n;
            }
            case "Course": {
                int n = courses.size();
                registerCourse();
                return courses.size()>n;
            }
            case "Enrollment": {
                int n = enrollments.size();
                enrollStudent();
                return enrollments.size()>n;
            }
            case "LibraryBook": {
                int n = libraryBooks.size();
                registerLibraryBook();
                return libraryBooks.size()>n;
            }
            default:return false;
        }
    }

    private static Object[] newData(int n) {
        return new Object[n];
    }

    public static void registerTeacher() {
        String[] labels = {
            "Person ID [String, unique, 4-30]",
            "First name [2-60]",
            "Last name [2-60]",
            "Personal email [valid, unique]",
            "Phone [10-15 digits]",
            "Date of birth [YYYY-MM-DD, past]",
            "Member number [unique, 4-30]",
            "Department [select registered Department]",
            "Join date [YYYY-MM-DD]",
            "Academic status [1 ACTIVE / 2 INACTIVE]",
            "Institutional email [valid, unique]",
            "Advisor name [2-120]",
            "Employee number [unique, 4-30]",
            "Specialization [2-120]",
            "Hire date [YYYY-MM-DD]",
            "Salary [positive amount]",
            "Office number [2-120]",
            "Employment type [2-120]"
        };
        String[] kinds = {
            "ID",
            "NAME",
            "NAME",
            "EMAIL",
            "PHONE",
            "DOB",
            "MEMBER",
            "REL_DEPARTMENT",
            "DATE",
            "STATUS",
            "EMAIL",
            "TEXT",
            "EMPLOYEE_NO",
            "TEXT",
            "DATE",
            "MONEY",
            "TEXT",
            "TEXT"
        };
        Object[] d = newData(18);
        if (!runSmartForm("TEACHER REGISTRATION", labels, kinds, d))return;
        LocalDate join = (LocalDate) d[8],
        hire = (LocalDate) d[14];
        if (join.isAfter(LocalDate.now()) || hire.isAfter(LocalDate.now())){
            System.out.println("Join and hire dates cannot be in the future. Please restart the form.");
            return;
        }
        Teacher x = new Teacher(
                (String) d[0], (String) d[1], (String) d[2], (String) d[3],
                (String) d[4], (LocalDate) d[5], (String) d[6], (Department) d[7],
                join, (String) d[9], (String) d[10], (String) d[11],
                (String) d[12], (String) d[13], hire, (BigDecimal) d[15],
                (String) d[16], (String) d[17]
        );
        teachers.add(x);
        System.out.println("Teacher "+x.getEmployeeNumber()+" registered successfully.");
        x.printDetails();
    }

    // ==================================================
    // 7. REGISTER ADMINISTRATOR
    // ==================================================

    public static void registerAdministrator() {
        String[] labels = {
            "Person ID [String, unique, 4-30]",
            "First name [2-60]",
            "Last name [2-60]",
            "Email [valid, unique]",
            "Phone [10-15 digits]",
            "Date of birth [YYYY-MM-DD, past]",
            "Staff number [unique, 4-30]",
            "Job title [2-120]",
            "Assigned office [2-120]",
            "Employment date [YYYY-MM-DD]",
            "Access level [positive integer]",
            "Active [1 YES / 2 NO]"
        };
        String[] kinds = {
            "ID",
            "NAME",
            "NAME",
            "EMAIL",
            "PHONE",
            "DOB",
            "STAFF_NO",
            "TEXT",
            "TEXT",
            "DATE",
            "INT_POS",
            "ACTIVE_BOOL"
        };
        Object[] d = newData(12);
        if (!runSmartForm("ADMINISTRATOR REGISTRATION", labels, kinds, d))return;
        Administrator x = new Administrator(
                (String) d[0], (String) d[1], (String) d[2], (String) d[3],
                (String) d[4], (LocalDate) d[5], (String) d[6], (String) d[7],
                (String) d[8], (LocalDate) d[9], (Integer) d[10], (Boolean) d[11]
        );
        administrators.add(x);
        System.out.println("Administrator "+x.getStaffNumber()+" registered successfully.");
        x.printDetails();
    }

    // ==================================================
    // 8. REGISTER COURSE
    // ==================================================

    public static void registerCourse() {
        String[] labels = {
            "Course ID [String, unique, 4-30]",
            "Course code [String, unique]",
            "Course title [2-120]",
            "Credit hours [integer, 1-6]",
            "Department [select registered Department]",
            "Assigned teacher [select registered Teacher]"
        };
        String[] kinds = {
            "ID",
            "COURSE_CODE",
            "TEXT",
            "INT_1_6",
            "REL_DEPARTMENT",
            "REL_TEACHER"
        };
        Object[] d = newData(6);
        if (!runSmartForm("COURSE REGISTRATION", labels, kinds, d))return;
        Course x = new Course((String) d[0], (String) d[1], (String) d[2], (Integer) d[3], (Department) d[4], (Teacher) d[5]);
        courses.add(x);
        System.out.println("Course "+x.getCourseCode()+" registered successfully.");
        x.printDetails();
    }

    // ==================================================
    // 9. REGISTER CLASSROOM
    // ==================================================

    public static void registerClassroom() {
        String[] labels = {
            "Classroom ID [String, unique, 4-30]",
            "Building name [2-120]",
            "Room number [2-30]",
            "Capacity [positive integer]",
            "Floor number [zero or greater]",
            "Available [1 YES / 2 NO]"
        };
        String[] kinds = {
            "ID",
            "TEXT",
            "TEXT",
            "INT_POS",
            "INT_NONNEG",
            "ACTIVE_BOOL"
        };
        Object[] d = newData(6);
        if (!runSmartForm("CLASSROOM REGISTRATION", labels, kinds, d))return;
        Classroom x = new Classroom((String) d[0], (String) d[1], (String) d[2], (Integer) d[3], (Integer) d[4], (Boolean) d[5]);
        classrooms.add(x);
        System.out.println("Classroom "+x.getClassroomId()+" registered successfully.");
        x.printDetails();
    }

    // ==================================================
    // 10. ENROLL STUDENT
    // ==================================================

    public static void enrollStudent() {
        String[] labels = {
            "Enrollment ID [String, unique, 4-30]",
            "Student [select registered Student]",
            "Course [select registered Course]",
            "Enrollment date [YYYY-MM-DD]",
            "Semester [Semester 1/2/3]",
            "Enrollment status [1 ACTIVE / 2 INACTIVE]"
        };
        String[] kinds = {
            "ID",
            "REL_STUDENT",
            "REL_COURSE",
            "DATE",
            "SEMESTER",
            "STATUS"
        };
        Object[] d = newData(6);
        if (!runSmartForm("ENROLLMENT REGISTRATION", labels, kinds, d))return;
        Student student = (Student) d[1];
        Course course = (Course) d[2];
        String semester = (String) d[4];
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)
                    && e.getSemester().equalsIgnoreCase(semester) && e.isActive()) {
                System.out.println("An active enrollment already exists for this student, course and semester.");
                return;
            }
        }
        Enrollment x = new Enrollment((String) d[0], student, course, (LocalDate) d[3], semester, (String) d[5]);
        enrollments.add(x);
        System.out.println("Enrollment "+x.getEnrollmentId()+" saved successfully.");
        x.printDetails();
    }

    // ==================================================
    // 11. RECORD ASSESSMENT
    // ==================================================

    public static void recordAssessment() {
        String[] labels = {
            "Assessment ID [String, unique, 4-30]",
            "Enrollment [select active Enrollment]",
            "Assessment type [QUIZ/ASSIGNMENT/CAT/MIDTERM/FINAL/PROJECT]",
            "Maximum score [positive number]",
            "Obtained score [0 to maximum]",
            "Assessment date [YYYY-MM-DD]"
        };
        String[] kinds = {
            "ID",
            "REL_ENROLLMENT",
            "ASSESSMENT_TYPE",
            "DOUBLE",
            "DOUBLE_NONNEG",
            "DATE"
        };
        Object[] d = newData(6);
        if (!runSmartForm("ASSESSMENT REGISTRATION", labels, kinds, d))return;
        if ((Double) d[4]>(Double) d[3]){
            System.out.println("Obtained score cannot exceed maximum score. Re-enter the assessment.");
            recordAssessment();
            return;
        }
        Assessment x = new Assessment((String) d[0], (Enrollment) d[1], (String) d[2], (Double) d[3], (Double) d[4], (LocalDate) d[5]);
        assessments.add(x);
        System.out.println("Assessment "+x.getAssessmentId()+" saved successfully.");
        x.printDetails();
    }

    // ==================================================
    // 12. RECORD FEE PAYMENT
    // ==================================================

    public static void recordFeePayment() {
        String[] labels = {
            "Payment ID [String, unique, 4-30]",
            "Student [select registered Student]",
            "Amount [BigDecimal, greater than zero]",
            "Payment date [YYYY-MM-DD]",
            "Payment method [CASH/MOBILE MONEY/BANK TRANSFER/CARD]",
            "Receipt number [String, unique]"
        };
        String[] kinds = {
            "ID",
            "REL_STUDENT",
            "MONEY",
            "DATE",
            "PAYMENT_METHOD",
            "RECEIPT"
        };
        Object[] d = newData(6);
        if (!runSmartForm("FEE PAYMENT REGISTRATION", labels, kinds, d))return;
        FeePayment x = new FeePayment((String) d[0], (Student) d[1], (BigDecimal) d[2], (LocalDate) d[3], (String) d[4], (String) d[5]);
        feePayments.add(x);
        System.out.println("Payment "+x.getPaymentId()+" saved successfully.");
        x.printDetails();
    }

    // ==================================================
    // 13. REGISTER LIBRARY BOOK
    // ==================================================

    public static void registerLibraryBook() {
        String[] labels = {
            "Book ID [String, unique, 4-30]",
            "ISBN [String, unique]",
            "Title [2-120]",
            "Author [2-120]",
            "Total copies [zero or greater]",
            "Available copies [zero to total copies]"
        };
        String[] kinds = {
            "ID",
            "ISBN",
            "TEXT",
            "TEXT",
            "INT_NONNEG",
            "INT_NONNEG"
        };
        Object[] d = newData(6);
        if (!runSmartForm("LIBRARY BOOK REGISTRATION", labels, kinds, d))return;
        if ((Integer) d[5]>(Integer) d[4]){
            System.out.println("Available copies cannot exceed total copies. Please restart book registration.");
            return;
        }
        LibraryBook x = new LibraryBook((String) d[0], (String) d[1], (String) d[2], (String) d[3], (Integer) d[4], (Integer) d[5]);
        libraryBooks.add(x);
        System.out.println("Library book "+x.getBookId()+" saved successfully.");
        x.printDetails();
    }

    // ==================================================
    // 14. BORROW BOOK
    // ==================================================

    public static void borrowBook() {
        String[] labels = {
            "Loan ID [String, unique, 4-30]",
            "Student [select registered Student]",
            "Library book [select book with available stock]",
            "Borrowed date [YYYY-MM-DD, today or earlier]",
            "Due date [YYYY-MM-DD, after borrowed date]"
        };
        String[] kinds = {
            "ID",
            "REL_STUDENT",
            "REL_BOOK",
            "DATE",
            "FUTURE_DATE"
        };
        Object[] d = newData(5);
        if (!runSmartForm("BOOK LOAN REGISTRATION", labels, kinds, d))return;
        LibraryBook book = (LibraryBook) d[2];
        LocalDate borrowed = (LocalDate) d[3],
        due = (LocalDate) d[4];
        if (book.getAvailableCopies() <= 0){
            System.out.println("No available copies remain. Loan was not saved.");
            return;
        }
        if (!due.isAfter(borrowed)){
            System.out.println("Due date must be after borrowed date. Loan was not saved.");
            return;
        }
        // A new loan has not been returned yet, so returnedDate must start as null.
        BookLoan x = new BookLoan((String) d[0], (Student) d[1], book, borrowed, due, null);

        // Stock changes only after all validation and successful commit.
        bookLoans.add(x);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        System.out.println("Book loan "+x.getLoanId()+" saved successfully.");
        x.printDetails();
    }

    // ==================================================
    // 15. RETURN BOOK
    // ==================================================

    public static void returnBook() {
        System.out.println("\n========== RETURN BOOK ==========");
        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan loan  :  bookLoans) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }
        if (activeLoans.isEmpty()) {
            System.out.println("There are no active book loans.");
            return;
        }
        for (int i = 0;  i < activeLoans.size(); i++) {
            System.out.println((i + 1) + ". "
            + activeLoans.get(i).getRegistrationSummary()
            + " | Loan ID: " + activeLoans.get(i).getLoanId());
        }
        int choice;
        while (true) {
            choice = readInt("Select loan to return: ");
            if (choice >= 1 && choice <= activeLoans.size()) {
                break;
            }
            System.out.println("Invalid selection. Choose a loan number from the list.");
        }
        BookLoan loan = activeLoans.get(choice - 1);
        LocalDate returnedDate;
        while (true) {
            returnedDate = readDate("Return Date (yyyy-MM-dd): ");

            if (returnedDate.isBefore(loan.getBorrowDate())) {
                System.out.println("Return date cannot be before borrowing date. Please enter it again.");
                continue;
            }

            if (returnedDate.isAfter(LocalDate.now())) {
                System.out.println("Return date cannot be in the future. Please enter it again.");
                continue;
            }

            break;
        }
        if (!readYesNo("Confirm return of this book? yes/no: ")) {
            System.out.println("Return cancelled. Book stock was not changed.");
            return;
        }
        loan.setReturnedDate(returnedDate);
        LibraryBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        System.out.println("Book returned successfully!");
        loan.printDetails();
    }

    // ==================================================
    // 16. VIEW ALL RECORDS
    // ==================================================

    public static void viewAllRecords() {
        System.out.println("\n========== ALL REGISTERED RECORDS ==========");
        printCollection("DEPARTMENTS", departments);
        printCollection("STUDENTS", students);
        printCollection("TEACHERS", teachers);
        printCollection("ADMINISTRATORS", administrators);
        printCollection("COURSES", courses);
        printCollection("CLASSROOMS", classrooms);
        printCollection("ENROLLMENTS", enrollments);
        printCollection("ASSESSMENTS", assessments);
        printCollection("FEE PAYMENTS", feePayments);
        printCollection("LIBRARY BOOKS", libraryBooks);
        printCollection("BOOK LOANS", bookLoans);
    }
    private static void printCollection(
    String title,
    List<? extends Identifiable> records) {
        System.out.println("\n--- " + title + " ---");
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (Identifiable record  :  records) {
            if (record instanceof com.auca.interfaces.Registrable) {
                System.out.println(((com.auca.interfaces.Registrable) record).getRegistrationSummary());
            } else {
                System.out.println(record.getDisplayName() + " [" + record.getId() + "]");
            }
            System.out.println("--------------------------------------");
        }
    }

    // ==================================================
    // 17. SEARCH RECORD BY ID
    // ==================================================

    public static void searchById() {
        System.out.println("\n========== SEARCH BY ID ==========");
        String id = readRequired("Enter record ID: ");
        List<Identifiable> records = getAllRecords();
        for (Identifiable record  :  records) {
            if (record.getId().equalsIgnoreCase(id)) {
                System.out.println("\nRecord found:");
                System.out.println(record);
                return;
            }
        }
        System.out.println("No record found with ID: " + id);
    }

    // ==================================================
    // 18. UPDATE RECORD
    // ==================================================

    public static void updateRecord() {
        System.out.println("\n========== UPDATE RECORD ==========");
        String id = readRequired("Enter record ID (IDs cannot be changed): ");
        Identifiable found = null;
        for (Identifiable record  :  getAllRecords()) if (record.getId().equalsIgnoreCase(id)) {
            found = record;
            break;
        }
        if (found == null) {
            System.out.println("Record not found.");
            return;
        }
        System.out.println("\nCurrent record:\n" + found);
        System.out.println("Select a mutable field. The change is applied only after confirmation.");
        if (found instanceof Student) {
            Student x = (Student)found;
            System.out.println("1 First name  2 Last name  3 Personal email  4 Phone  5 Institutional email");
            System.out.println("6 Advisor  7 Program  8 Study year  9 Guardian phone  10 Academic status  11 Scholarship");
            int c = readInt("Field: ");
            switch (c) {
                case 1: {
                    String v = readName("New first name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setFirstName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2: {
                    String v = readName("New last name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setLastName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3: {
                    String v = readEmail("New personal email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4: {
                    String v = readPhone("New phone: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setPhoneNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5: {
                    String v = readEmail("New institutional email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setInstitutionalEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 6: {
                    String v = readRequired("New advisor name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAdvisorName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 7: {
                    String v = readRequired("New program name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setProgramName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 8: {
                    int v = readBoundedInt("New study year (1-6): ", 1, 6);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setStudyYear(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 9: {
                    String v = readPhone("New guardian phone: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setGuardianPhone(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 10: {
                    String v = readStatus();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAcademicStatus(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 11: {
                    boolean v = readYesNo("Scholarship? yes/no: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setScholarshipStatus(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default: System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Teacher) {
            Teacher x = (Teacher)found;
            System.out.println("1 First name  2 Last name  3 Personal email  4 Phone  5 Institutional email");
            System.out.println("6 Advisor  7 Specialization  8 Salary  9 Office number  10 Employment type  11 Department");
            int c = readInt("Field: ");
            switch (c) {
                case 1:{
                    String v = readName("New first name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setFirstName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readName("New last name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setLastName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    String v = readEmail("New personal email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    String v = readPhone("New phone: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setPhoneNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    String v = readEmail("New institutional email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setInstitutionalEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 6:{
                    String v = readRequired("New advisor: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAdvisorName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 7:{
                    String v = readRequired("New specialization: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setSpecialization(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 8:{
                    BigDecimal v = readPositiveMoney("New salary: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setSalary(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 9:{
                    String v = readRequired("New office number: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setOfficeNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 10:{
                    String v = readRequired("New employment type: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEmploymentType(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 11:{
                    Department v = selectDepartment();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setDepartment(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Administrator) {
            Administrator x = (Administrator)found;
            System.out.println("1 First name  2 Last name  3 Email  4 Phone  5 Job title  6 Assigned office  7 Access level  8 Active");
            int c = readInt("Field: ");
            switch (c) {
                case 1:{
                    String v = readName("New first name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setFirstName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readName("New last name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setLastName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    String v = readEmail("New email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    String v = readPhone("New phone: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setPhoneNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    String v = readRequired("New job title: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setJobTitle(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 6:{
                    String v = readRequired("New assigned office: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAssignedOffice(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 7:{
                    int v = readBoundedInt("New access level (1+): ", 1, Integer.MAX_VALUE);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAccessLevel(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 8:{
                    boolean v = readYesNo("Active? yes/no: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setActive(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Department) {
            Department x = (Department)found;
            System.out.println("1 Name  2 Department code  3 Office location  4 Contact email  5 Head teacher");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    String v = readName("New department name: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setDepartmentName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readUniqueDepartmentCode();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setDepartmentCode(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    String v = readRequired("New office location: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setOfficeLocation(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    String v = readEmail("New contact email: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCantactEmail(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    Teacher v = teachers.isEmpty()?null:selectTeacher();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setHeadTeacher(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Course) {
            Course x = (Course)found;
            System.out.println("1 Course code  2 Title  3 Credit hours  4 Department  5 Assigned teacher");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    String v = readUniqueCourseCode();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCourseCode(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readRequired("New title: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCourseTitle(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    int v = readBoundedInt("Credit hours (1-6): ", 1, 6);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCreditHours(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    Department v = selectDepartment();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setDepartment(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    Teacher v = selectTeacher();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAssignedTeacher(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Classroom) {
            Classroom x = (Classroom)found;
            System.out.println("1 Building  2 Room  3 Capacity  4 Floor  5 Available");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    String v = readRequired("New building: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setBuildingName(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readRequired("New room: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setRoomNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    int v = readBoundedInt("Capacity: ", 1, Integer.MAX_VALUE);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCapacity(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    int v = readBoundedInt("Floor: ", 0, Integer.MAX_VALUE);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setFloorNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    boolean v = readYesNo("Available? yes/no: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAvailable(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Enrollment) {
            Enrollment x = (Enrollment)found;
            System.out.println("1 Student  2 Course  3 Enrollment date  4 Semester  5 Status");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    Student v = selectStudent();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setStudent(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    Course v = selectCourse();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setCourse(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    LocalDate v = readDateNotFuture("Enrollment date YYYY-MM-DD: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEnrollmentDate(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    String v = readSemester();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setSemester(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    String v = readStatus();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEnrollmentStatus(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof Assessment) {
            Assessment x = (Assessment)found;
            System.out.println("1 Enrollment  2 Assessment type  3 Maximum score  4 Obtained score  5 Assessment date");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    Enrollment v = selectEnrollment();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setEnrollment(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readAssessmentType();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAssessmentType(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    double v = readPositiveDouble("Maximum score: ");
                    while (v<x.getObtainedScore()){
                        System.out.println("Maximum score cannot be below the obtained score.");
                        v = readPositiveDouble("Maximum score: ");
                    }
                    final double value = v;
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setMaximumScore(value);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    double v = readBoundedDouble("Obtained score (0-"+x.getMaximumScore()+"): ", 0, x.getMaximumScore());
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setObtainedScore(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    LocalDate v = readDateNotFuture("Assessment date: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAssessmentDate(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof FeePayment) {
            FeePayment x = (FeePayment)found;
            System.out.println("1 Student  2 Amount  3 Payment date  4 Method  5 Receipt number");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    Student v = selectStudent();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setStudent(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    BigDecimal v = readPositiveMoney("Amount: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAmount(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    LocalDate v = readDateNotFuture("Payment date: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setPaymentDate(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    String v = readPaymentMethod();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setPaymentMethod(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    String v = readUniqueReceipt();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setRecieptNumber(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof LibraryBook) {
            LibraryBook x = (LibraryBook)found;
            System.out.println("1 ISBN  2 Title  3 Author  4 Total copies  5 Available copies");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    String v = readUniqueIsbn();
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setIsbn(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    String v = readRequired("New title: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setTitle(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 3:{
                    String v = readRequired("New author: ");
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAuthor(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 4:{
                    int v = readBoundedInt("Total copies: ", x.getAvailableCopies(), Integer.MAX_VALUE);
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setTotalCopies(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 5:{
                    int v = readBoundedInt("Available copies: ", 0, x.getTotalCopies());
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setAvailableCopies(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        } else if (found instanceof BookLoan) {
            BookLoan x = (BookLoan)found;
            System.out.println("1 Due date  2 Return date");
            int c = readInt("Field: ");
            switch (c){
                case 1:{
                    LocalDate v;
                    while (true) {
                        v = readDate("Due date: ");
                        if (v.isAfter(x.getBorrowDate())) {
                            break;
                        }
                        System.out.println("Due date must be after borrowed date. Please enter it again.");
                    }
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setDueDate(v);
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                case 2:{
                    if (x.getReturnedDate() != null){
                        System.out.println("This loan has already been returned; it cannot be returned twice.");
                        return;
                    }
                    LocalDate v;
                    while (true) {
                        v = readDate("Return date: ");
                        if (v.isBefore(x.getBorrowDate())) {
                            System.out.println("Return date cannot be before borrowed date. Please enter it again.");
                            continue;
                        }
                        if (v.isAfter(LocalDate.now())) {
                            System.out.println("Return date cannot be in the future. Please enter it again.");
                            continue;
                        }
                        break;
                    }
                    if (readYesNo("Confirm this change? yes/no: ")) {
                        x.setReturnedDate(v);
                        x.getBook().setAvailableCopies(
                                x.getBook().getAvailableCopies() + 1
                        );
                        System.out.println("Record updated successfully.\n" + found);
                    } else {
                        System.out.println("Update cancelled. The record was not changed.");
                    }
                    break;
                }
                default:System.out.println("Invalid field.");
                    return;
            }
        }
    }

    private static String readName(String prompt) {
        while (true){
            String v = readRequired(prompt);
            if (v.length() >= 2 && v.length() <= 60)return v;
            System.out.println("Use 2-60 characters.");
        }
    }

    private static int readBoundedInt(String prompt, int min, int max){
        while (true){
            int v = readInt(prompt);
            if (v >= min && v <= max)return v;
            System.out.println("Enter a value from "+min+" to "+max+".");
        }
    }

    private static double readPositiveDouble(String prompt){
        while (true){
            double v = readDouble(prompt);
            if (v>0)return v;
            System.out.println("Value must be greater than zero.");
        }
    }

    private static double readBoundedDouble(String prompt, double min, double max){
        while (true){
            double v = readDouble(prompt);
            if (v >= min && v <= max)return v;
            System.out.println("Enter a value from "+min+" to "+max+".");
        }
    }

    private static BigDecimal readPositiveMoney(String prompt){
        while (true){
            BigDecimal v = readMoney(prompt);
            if (v.compareTo(BigDecimal.ZERO)>0)return v;
            System.out.println("Amount must be greater than zero.");
        }
    }

    private static String readStatus(){
        while (true){
            String v = readRequired("Status (1 ACTIVE / 2 INACTIVE): ");
            if (v.equals("1") || v.equalsIgnoreCase("ACTIVE"))return "ACTIVE";
            if (v.equals("2") || v.equalsIgnoreCase("INACTIVE"))return "INACTIVE";
            System.out.println("Choose 1 or 2.");
        }
    }

    private static String readSemester(){
        while (true){
            String v = readRequired("Semester (Semester 1/2/3): ");
            if (allowed(v, "SEMESTER 1", "SEMESTER 2", "SEMESTER 3", "SEMESTER I", "SEMESTER II", "SEMESTER III", "1", "2", "3"))return v;
            System.out.println("Invalid semester.");
        }
    }

    private static String readAssessmentType(){
        while (true){
            String v = readRequired("Assessment type: ");
            if (allowed(v, "QUIZ", "ASSIGNMENT", "CAT", "MIDTERM", "FINAL", "PROJECT", "PRESENTATION", "EXAM"))return v.toUpperCase();
            System.out.println("Invalid assessment type.");
        }
    }

    private static String readPaymentMethod(){
        while (true){
            String v = readRequired("Payment method: ");
            if (allowed(v, "CASH", "MOBILE MONEY", "BANK TRANSFER", "CARD"))return v.toUpperCase();
            System.out.println("Invalid payment method.");
        }
    }

    private static String readUniqueDepartmentCode(){
        while (true){
            String v = readRequired("New department code: ");
            if (!departmentCodeExists(v))return v;
            System.out.println("Department code already exists.");
        }
    }

    private static String readUniqueCourseCode(){
        while (true){
            String v = readRequired("New course code: ");
            boolean exists = false;
            for (Course c : courses)if (c.getCourseCode().equalsIgnoreCase(v))exists = true;
            if (!exists)return v;
            System.out.println("Course code already exists.");
        }
    }

    private static String readUniqueReceipt(){
        while (true){
            String v = readRequired("New receipt number: ");
            if (!receiptExists(v))return v;
            System.out.println("Receipt already exists.");
        }
    }

    private static String readUniqueIsbn(){
        while (true){
            String v = readRequired("New ISBN: ");
            if (!isbnExists(v))return v;
            System.out.println("ISBN already exists.");
        }
    }

    // ==================================================
    // 19. STUDENT ACADEMIC SUMMARY
    // ==================================================

    public static void studentAcademicSummary() {
        System.out.println("\n========== STUDENT ACADEMIC SUMMARY ==========");
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        Student student = selectStudent();
        System.out.println("\nStudent Information:");
        student.printDetails();
        System.out.println("\nEnrollments:");
        boolean foundEnrollment = false;
        for (Enrollment enrollment  :  enrollments) {
            if (enrollment.getStudent().getId()
            .equalsIgnoreCase(student.getId())) {
                foundEnrollment = true;
                System.out.println(enrollment);
                System.out.println("Assessments:");
                boolean foundAssessment = false;
                for (Assessment assessment  :  assessments) {
                    if (assessment.getEnrollment().getId()
                    .equalsIgnoreCase(enrollment.getId())) {
                        foundAssessment = true;
                        System.out.println(assessment);
                    }
                }
                if (!foundAssessment) {
                    System.out.println("No assessments recorded.");
                }
                System.out.println("--------------------------------------");
            }
        }
        if (!foundEnrollment) {
            System.out.println("This student has no enrollments.");
        }
    }

    // ==================================================
    // 20. STUDENT PAYMENT HISTORY
    // ==================================================

    public static void studentPaymentHistory() {
        System.out.println("\n========== STUDENT PAYMENT HISTORY ==========");
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        Student student = selectStudent();
        BigDecimal total = BigDecimal.ZERO;
        boolean found = false;
        System.out.println("\nPayments for " + student.getDisplayName());
        for (FeePayment payment  :  feePayments) {
            if (payment.getStudent().getId()
            .equalsIgnoreCase(student.getId())) {
                found = true;
                System.out.println(payment);
                total = total.add(payment.getAmount());
                System.out.println("--------------------------------------");
            }
        }
        if (!found) {
            System.out.println("No payments found for this student.");
        }
        System.out.println("Total Payments: " + total);
    }

    // ==================================================
    // 21. VIEW AVAILABLE BOOKS
    // ==================================================

    public static void viewAvailableBooks() {
        System.out.println("\n========== AVAILABLE LIBRARY BOOKS ==========");
        boolean found = false;
        for (LibraryBook book  :  libraryBooks) {
            if (book.getAvailableCopies() > 0) {
                found = true;
                System.out.println(book);
                System.out.println("--------------------------------------");
            }
        }
        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    // ==================================================
    // 22. DEMONSTRATE POLYMORPHISM
    // ==================================================

    public static void demonstratePolymorphism() {
        System.out.println("\n========== POLYMORPHISM DEMONSTRATION ==========");
        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);
        System.out.println("\n--- Person Polymorphism ---");
        if (people.isEmpty()) {
            System.out.println("Register people first.");
        } else {
            for (Person person  :  people) {
                System.out.println(person.getRoleDescription());
                System.out.println(person.getRegistrationSummary());
                System.out.println("--------------------------------------");
            }
        }
        System.out.println("\n--- Identifiable Polymorphism ---");
        List<Identifiable> records = getAllRecords();
        if (records.isEmpty()) {
            System.out.println("No records available.");
        } else {
            for (Identifiable record  :  records) {
                System.out.println(
                "ID: " + record.getId()
                + " | Name: " + record.getDisplayName()
                + " | Active: " + record.isActive()
                );
            }
        }
    }

    // ==================================================
    // 23. GET ALL RECORDS AS IDENTIFIABLE OBJECTS
    // ==================================================

    private static List<Identifiable> getAllRecords() {
        List<Identifiable> records = new ArrayList<>();
        records.addAll(departments);
        records.addAll(students);
        records.addAll(teachers);
        records.addAll(administrators);
        records.addAll(courses);
        records.addAll(classrooms);
        records.addAll(enrollments);
        records.addAll(assessments);
        records.addAll(feePayments);
        records.addAll(libraryBooks);
        records.addAll(bookLoans);
        return records;
    }

    // ==================================================
    // 24. CHECK DUPLICATE IDS
    // ==================================================

    private static boolean idExists(String id) {
        for (Identifiable record  :  getAllRecords()) {
            if (record.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private static String readUniqueId(String message) {
        String id = readRequired(message);
        while (idExists(id)) {
            System.out.println("This ID already exists.");
            id = readRequired(message);
        }
        return id;
    }

    private static boolean departmentCodeExists(String code) {
        for (Department department  :  departments) {
            if (department.getDepartmentCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    private static boolean receiptExists(String receipt) {
        for (FeePayment payment  :  feePayments) {
            if (payment.getRecieptNumber().equalsIgnoreCase(receipt)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isbnExists(String isbn) {
        for (LibraryBook book  :  libraryBooks) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }

    // ==================================================
    // 25. SHARED INPUT METHODS
    // ==================================================

    private static String readRequired(String message) {
        String value;
        do {
            System.out.print(message);
            value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("This field is required.");
            }
        } while (value.isEmpty());
        return value;
    }

    private static boolean isValidEmail(String email) {
        return email.matches(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
    }

    private static String readEmail(String message) {
        String email = readRequired(message);
        while (!isValidEmail(email)) {
            System.out.println("Enter a valid email address.");
            email = readRequired(message);
        }
        return email;
    }

    private static String readPhone(String message) {
        String phone = readRequired(message);
        while (!phone.matches("\\+?\\d{10,15}")) {
            System.out.println("Phone must contain 10 to 15 digits, optionally starting with +.");
            phone = readRequired(message);
        }
        return phone;
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(
                scanner.nextLine().trim()
                );
                if (Double.isFinite(value)) {
                    return value;
                }
                System.out.println("Enter a finite number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static BigDecimal readMoney(String message) {
        while (true) {
            try {
                System.out.print(message);
                return new BigDecimal(
                scanner.nextLine().trim()
                );
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid monetary amount.");
            }
        }
    }

    private static LocalDate readDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(
                scanner.nextLine().trim()
                );
            } catch (DateTimeParseException e) {
                System.out.println(
                "Invalid date. Use yyyy-MM-dd, for example 2026-11-17."
                );
            }
        }
    }

    // Reads a valid date that is today or earlier.
    // Used when updating enrollment, assessment and payment dates.
    private static LocalDate readDateNotFuture(String message) {
        while (true) {
            LocalDate date = readDate(message);

            if (!date.isAfter(LocalDate.now())) {
                return date;
            }

            System.out.println("Date cannot be in the future. Please enter it again.");
        }
    }

    private static boolean readYesNo(String message) {
        while (true) {
            String answer = readRequired(message);
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Please answer yes or no.");
        }
    }

    // ==================================================
    // 26. SELECT EXISTING RECORDS
    // ==================================================

    private static Department selectDepartment() {
        System.out.println("\nAvailable Departments:");
        for (int i = 0;  i < departments.size(); i++) {
            Department department = departments.get(i);
            System.out.println((i + 1) + ". "
            + department.getDepartmentName()
            + " (" + department.getDepartmentId() + ")");
        } while (true) {
            int choice = readInt("Select Department Number: ");
            if (choice >= 1 && choice <= departments.size()) {
                return departments.get(choice - 1);
            }
            System.out.println("Invalid department selection.");
        }
    }

    private static Teacher selectTeacher() {
        System.out.println("\nAvailable Teachers:");
        for (int i = 0;  i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            System.out.println((i + 1) + ". "
            + teacher.getDisplayName()
            + " (" + teacher.getId() + ")");
        } while (true) {
            int choice = readInt("Select Teacher Number: ");
            if (choice >= 1 && choice <= teachers.size()) {
                return teachers.get(choice - 1);
            }
            System.out.println("Invalid teacher selection.");
        }
    }

    private static Student selectStudent() {
        System.out.println("\nAvailable Students:");
        for (int i = 0;  i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". "
            + student.getDisplayName()
            + " (" + student.getId() + ")");
        } while (true) {
            int choice = readInt("Select Student Number: ");
            if (choice >= 1 && choice <= students.size()) {
                return students.get(choice - 1);
            }
            System.out.println("Invalid student selection.");
        }
    }

    private static Course selectCourse() {
        System.out.println("\nAvailable Courses:");
        for (int i = 0;  i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println((i + 1) + ". "
            + course.getCourseTitle()
            + " (" + course.getCourseCode() + ")");
        } while (true) {
            int choice = readInt("Select Course Number: ");
            if (choice >= 1 && choice <= courses.size()) {
                return courses.get(choice - 1);
            }
            System.out.println("Invalid course selection.");
        }
    }

    private static Enrollment selectEnrollment() {
        System.out.println("\nAvailable Enrollments:");
        for (int i = 0;  i < enrollments.size(); i++) {
            System.out.println((i + 1) + ". "
            + enrollments.get(i).getRegistrationSummary());
        } while (true) {
            int choice = readInt("Select Enrollment Number: ");
            if (choice >= 1 && choice <= enrollments.size()) {
                return enrollments.get(choice - 1);
            }
            System.out.println("Invalid enrollment selection.");
        }
    }

    private static LibraryBook selectBook() {
        System.out.println("\nAvailable Books:");
        for (int i = 0;  i < libraryBooks.size(); i++) {
            LibraryBook book = libraryBooks.get(i);
            System.out.println((i + 1) + ". "
            + book.getTitle()
            + " | Available: " + book.getAvailableCopies());
        } while (true) {
            int choice = readInt("Select Book Number: ");
            if (choice >= 1 && choice <= libraryBooks.size()) {
                return libraryBooks.get(choice - 1);
            }
            System.out.println("Invalid book selection.");
        }
    }
}

import java.util.*;

import java.text.*;

public class Placement {
    static Date date = new Date();
    public static SimpleDateFormat ft = new SimpleDateFormat("dd'th' MMMM yyyy',' kk':'mm a");

    public static class student {
        int rollNo;
        public String name;
        String branch;
        Float cgpa;
        String status = "not placed";
        ArrayList<company> offers = new ArrayList<company>(10);
        private boolean isRegistered = false;
        PlacementCell placement = new PlacementCell();

        // student class
        public student() {

        }

        public student(String name, int rollno, Float cgpa, String branch) {
            this.name = name;
            this.branch = branch;
            this.cgpa = cgpa;
            this.rollNo = rollno;
        }

        public void display() {
            System.out.println("Your Details are : ");
            System.out.println("Name : " + this.name);
            System.out.println("Roll no : " + this.rollNo);
            System.out.println("CGPA : " + this.cgpa);
            System.err.println("branch : " + this.branch);
        }

        public void RegisterForPlacementDrive() {
            placement.students.add(this);
            System.out.println("successfully registered on " + ft.format(date));
            display();
            isRegistered = true;
        }

        public void RegisterForCompany(String CompanyName) {
            //System.out.println(CompanyName);
            if (!isRegistered) {
                System.out.println("Please register first");
                return;
            }
            int i = 0;
            while (i < placement.companies.size()) {
                if (placement.companies.get(i).name.equals(CompanyName)) {
                    System.out.println("Triggered");
                    break;
                }
                else{
                    System.out.println(placement.companies.get(i).name == CompanyName);
                    System.out.println(CompanyName+" "+placement.companies.get(i).name);
                    i++;

                }
                
            }
            if (i == placement.companies.size()) {
                System.out.println("Company do not exist");

            } else {
                if (placement.companies.get(i).cgpa <= this.cgpa
                        && (this.status == "not placed" || (this.status == "offered"
                                && (3 * (offers.get(offers.size() - 1).CTC) < placement.companies.get(i).CTC)))) {
                    placement.companies.get(i).SelectedStudents.add(this);
                    offers.add(placement.companies.get(i));
                    System.out.println("Successfully registered for" + placement.companies.get(i).role + "role at "
                            + placement.companies.get(i).name);
                } else {
                    System.out.println("Not eligible for the company");
                }
            }

        }

        public void GetAllAvailaibleCompanies() {

            if (placement.companies.isEmpty()) {
                System.out.println("No company availaible at the moment ,Try after some time");
                return;
            }
            for (int i = 0; i < placement.companies.size(); i++) {
                System.out.println("Company Name : " + placement.companies.get(i).name);
                System.out.println("Role offered : " + placement.companies.get(i).getRole());
                System.out.println("Company's CTC offered : " + placement.companies.get(i).getCTC() + " LPA");
                System.out.println("Cgpa required : " + placement.companies.get(i).getCGPA());
            }
        }

        public void getStatus() {
            if (offers.isEmpty()) {
                System.out.println("You do not have any offers currently");
            } else {
                System.out.println(
                        "You have a offer from " + offers.get(offers.size() - 1).name + ".Please accept the offer");
            }
        }

        public void updateCgpa(Float sgpa) {
            this.cgpa = sgpa;
            System.out.println("Your cgpa just got updated to " + this.cgpa);
        }

        public void acceptOffer() {
            this.status = "placed";
            System.out.println("Congralutions on being a part of " + offers.get(offers.size() - 1).name);
        }

        public void rejectOffer() {

            if (offers.size() == 1) {
                this.status = "blocked";
                placement.blocked++;
                System.out.println("You have been blocked from the placement drive for rejecting offers");
            } else {
                System.out.println("Offer by " + offers.get(offers.size() - 1) + " is rejected successfully");
                offers.remove(offers.size() - 1);

            }

        }

    }

    // placement cell
    public static class PlacementCell {
        Date CompanyOpening = null;
        Date CompanyClosing = null;
        Date studentOpening = null;
        Date studentClosing = null;
        static int blocked=0;
        static ArrayList<company> companies = new ArrayList<company>(10);
        static ArrayList<student> students = new ArrayList<student>(10);

        void OpenCompanyRegistration() {
            Scanner sc = new Scanner(System.in);
            if (CompanyOpening == null) {
                System.out.println("FILL THE FOLLOWING DETAILS : " + "\n" + "1. Set opening date :");
                String datein = sc.nextLine();
                this.CompanyOpening = getDateInp(datein);
                System.out.println("\n" + "Set closing date : ");
                String dateout = sc.nextLine();
                this.CompanyClosing = getDateInp(dateout);

                System.out.println("Registration opened successfully for company registrations " + "\n"
                        + "Opening date is : " + ft.format(this.CompanyOpening) + "\n" + "Closing date is : "
                        + ft.format(this.CompanyClosing));

            }
        }

        void OpenStudentsRegistration() {
            Scanner sc = new Scanner(System.in);
            if (studentOpening == null && CompanyOpening != null) {
                System.out.println("FILL THE FOLLOWING DETAILS : " + "\n" + "1. Set opening date :");
                String datein = sc.nextLine();
                this.studentOpening = getDateInp(datein);
                if (studentOpening.compareTo(CompanyClosing) < 0) {
                    System.out.println(
                            "Date of students registration opening should be after the the closing of companies registration");
                    this.OpenStudentsRegistration();
                }
                System.out.println("\n" + "Set closing date : ");
                String dateout = sc.nextLine();
                this.studentClosing = getDateInp(dateout);

                System.out.println("Registration opened successfully for student registrations " + "\n"
                        + "Opening date is : " + ft.format(this.studentOpening) + "\n" + "Closing date is : "
                        + ft.format(this.studentClosing));

            }
        }

        int getNumberOfStudentsRegistered() {
            return this.students.size();
        }

        int getNumberOfCompniesRegistered() {
            return this.companies.size();
        }

        void getStudentDetails(String name) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).name.equals(name)) {
                    students.get(i).display();
                    return;
                }
            }
            System.out.println(name + " have not registered");
        }

        void getCompanyDetails(String name) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).name.equals(name)) {
                    companies.get(i).display();
                    return;
                }
            }
            System.out.println(name + " have not registered");

        }

        Float getAvgPackage() {
            float sum = 0;
            float num = 0;
            for (int i = 0; i < companies.size(); i++) {
                sum += companies.get(i).CTC * companies.get(i).SelectedStudents.size();
                num += companies.get(i).SelectedStudents.size();
            }
            return sum / num;
        }

        void getCompanyProcessResults(String name) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).name.equals(name)) {
                    System.out.println("Following Students have been selected : ");
                    for (int j = 0; j < companies.get(i).SelectedStudents.size(); j++) {
                        System.out.println(companies.get(i).SelectedStudents.get(j).name + " have been selected for "
                                + companies.get(i).role);
                    }
                    return;
                }
            }
            System.out.println("Company do not exist in the registered list");
        }
    }

    // company class
    public static class company {
        // CTC SHOULD BE INT, CGPA SHOULD BE Float
        String name;
        private String role;
        private int CTC;
        private Float cgpa;

        void display() {
            System.out.println("Company Name : " + this.name);
            System.out.println("Role : " + this.role);
            System.out.println("CTC offered : " + this.CTC);
            System.out.println("cgpa needed : " + this.cgpa);

        }

        public static ArrayList<student> SelectedStudents = new ArrayList<student>(10);

        public company() {

        }

        public company(String name, String role, int CTC, Float cgpa) {
            this.name = name;
            this.role = role;
            this.CTC = CTC;
            this.cgpa = cgpa;

        }

        void RegisterToInstituteDrive() {
            PlacementCell placement = new PlacementCell();
            placement.companies.add(this);
            System.out.println("Successfully registered on " + ft.format(date));
        }

        void GetSelectedStudents() {
            for(int i=0;i<this.SelectedStudents.size();i++){
                System.out.println("Name : "+this.SelectedStudents.get(i).name+"("+this.SelectedStudents.get(i).rollNo);
            }

        }

        String getRole() {
            return this.role;
        }

        int getCTC() {
            return this.CTC;
        }

        Float getCGPA() {
            return this.cgpa;
        }

        void update(String role) {
            this.role = role;
            System.out.println("Role updated successfully to" + this.role);
        }

        void update(int CTC) {
            this.CTC = CTC;
            System.out.println("package updated successfully to" + this.CTC + "LPA");
        }

        void update(Float cgpa) {
            this.cgpa = cgpa;
            System.out.println("cgpa updated successfully to" + this.cgpa);
        }
    }

    public static Date getDateInp(String datein) {
        Date date2 = null;
        try {
            // Parsing the String
            date2 = ft.parse(datein);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to future builder : ");
            System.out.println("1. Enter the Application ");
            System.out.println("2. Exit the Application ");
            PlacementCell placement = new PlacementCell();
            ArrayList<company> companies = new ArrayList<company>();
            ArrayList<student> students = new ArrayList<student>();

            int choice = sc.nextInt();
            if (choice == 1) {
                while (true) {
                    System.out.println("Enter the mode : ");
                    System.out.println("1.Enter as Student Mode");
                    System.out.println("2.Enter as Company Mode");
                    System.out.println("3.Enter as Placement Cell Mode");
                    System.out.println("4.Return To Main Application");
                    int choice1 = sc.nextInt();
                    if (choice1 == 1) {
                        System.out.print(
                                "Choose the Student Query to perform-\n1) Enter as a Student(Give Student Name, and Roll No.)\n2) Add students\n3) Back\n");
                        int studentQuery = sc.nextInt();
                        sc.nextLine();
                        if (studentQuery == 1) {
                            String name = sc.nextLine();
                            int rollno = sc.nextInt();
                            int i = 0;
                            while (i < students.size()) {
                                if (students.get(i).rollNo == rollno) {
                                    break;
                                }
                            }
                            if (i == students.size()) {
                                System.out.println("Not found");

                            } else {
                                while (true) {
                                    System.out.println("Welcome " + students.get(i).name);
                                    System.out.println(
                                            "1) Register For Placement Drive\n2) Register For Company\n3) Get All available companies\n4) Get Current Status\n5) Update CGPA\n6) Accept offer\n7) Reject offer\n8) Back\n");
                                    int XstudentQuery = sc.nextInt();
                                    sc.nextLine();
                                    if (XstudentQuery == 1) {
                                        students.get(i).RegisterForPlacementDrive();
                                    } else if (XstudentQuery == 2) {
                                        String companyName = sc.nextLine();

                                        students.get(i).RegisterForCompany(companyName);
                                    } else if (XstudentQuery == 3) {
                                        students.get(i).GetAllAvailaibleCompanies();
                                    } else if (XstudentQuery == 4) {
                                        students.get(i).getStatus();

                                    } else if (XstudentQuery == 5) {
                                        Float sgpa = sc.nextFloat();
                                        students.get(i).updateCgpa(sgpa);

                                    } else if (XstudentQuery == 6) {
                                        students.get(i).acceptOffer();

                                    } else if (XstudentQuery == 7) {

                                    } else if (XstudentQuery == 8) {
                                        break;
                                    } else {
                                        System.out.println("Wrong input try again");
                                    }

                                }
                            }
                        } else if (studentQuery == 2) {
                            System.out.println("Number of students to add : ");
                            int noOfStudentsToBeAdded = sc.nextInt();
                            sc.nextLine();
                            for (int i = 0; i < noOfStudentsToBeAdded; i++) {
                                System.out.println("Details of student " + (i + 1)); // correct it
                                String name = sc.nextLine();
                                int rollNo = sc.nextInt();
                                Float cgpa = sc.nextFloat();
                                sc.nextLine();
                                String branch = sc.nextLine();
                                System.out.println(name + " " + rollNo + " " + cgpa + " " + branch);
                                student tempStudent = new student(name, rollNo, cgpa, branch);
                                students.add(tempStudent);

                            }
                        } else if (studentQuery == 3) {
                            break;
                        } else {
                            System.out.println("Wrong input try again");
                        }
                    } else if (choice1 == 2) {
                        while (true) {
                            System.out.print(
                                    "1) Add Company and Details\n2) Choose Company\n3) Get Available Companies\n4) Back\n");// start
                                                                                                                            // from
                                                                                                                            // here
                            int companyQuery = sc.nextInt();
                            if (companyQuery == 1) {
                                sc.nextLine();
                                String name = sc.nextLine();
                                String role = sc.nextLine();

                                int CTC = sc.nextInt();
                                Float cgpa = sc.nextFloat();
                                company companyx = new company(name, role, CTC, cgpa);
                                companies.add(companyx);

                            } else if (companyQuery == 2) {

                                for (int i = 0; i < companies.size(); i++) {
                                    System.out.println((i + 1) + "." + companies.get(i).name);

                                }
                                if(companies.size()==0){
                                    System.out.println("No companies avalaible");
                                    break;
                                }
                                int companyChosen = sc.nextInt();
                                if ((companyChosen - 1) < companies.size()) {
                                    while (true) {
                                        System.out.println("Welcome" + companies.get(companyChosen - 1).name);
                                        System.out.print(
                                                "1) Update Role\n2) Update Package\n3) Update CGPA criteria\n4) Register To Institute Drive\n5) Back\n");
                                        int XcompanyQuery = sc.nextInt();
                                        if (XcompanyQuery == 1) {
                                            sc.nextLine();
                                            String updatedRole = sc.nextLine();
                                            companies.get(companyChosen - 1).update(updatedRole);
                                        } else if (XcompanyQuery == 2) {
                                            int updatedPackage = sc.nextInt();
                                            companies.get(companyChosen - 1).update(updatedPackage);
                                        } else if (XcompanyQuery == 3) {
                                            Float updatedCgpa = sc.nextFloat();
                                            companies.get(companyChosen - 1).update(updatedCgpa);
                                        } else if (XcompanyQuery == 4) {
                                            companies.get(companyChosen - 1).RegisterToInstituteDrive();
                                        } else if (XcompanyQuery == 5) {
                                            break;
                                        } else {
                                            System.out.println("Wrong input try again");
                                        }

                                    }
                                } else {
                                    System.out.println("wrong input");
                                }

                            } else if (companyQuery == 3) {
                                for (int i = 0; i < companies.size(); i++) {
                                    System.out.println((i + 1) + "." + companies.get(i).name);

                                }
                            } else if (companyQuery == 4) {
                                break;
                            } else {
                                System.out.println("Wrong input try again");
                            }
                        }

                    } else if (choice1 == 3) {
                        while (true) {
                            System.out.print(
                                    "1) Open Student Registrations\n2) Open Company Registrations\n3) Get Number of Student Registrations\n4) Get Number of Company Registration\n5) Get Number of Offered/Unoffered/Blocked Students\n6) Get Student Details\n7) Get Company Details\n8) Get Average Package\n9) Get Company Process Results\n10) Back\n");
                            int PlacementCellChoice = sc.nextInt();
                            if (PlacementCellChoice == 1) {
                                placement.OpenStudentsRegistration();
                            } else if (PlacementCellChoice == 2) {
                                placement.OpenCompanyRegistration();
                            } else if (PlacementCellChoice == 3) {
                                System.out.println("Number of students registered are : "+placement.getNumberOfStudentsRegistered());

                            } else if (PlacementCellChoice == 4) {
                                System.out.println("Number of companies registered are : "+placement.getNumberOfCompniesRegistered());

                            } else if (PlacementCellChoice == 5) {
                                int offered=0;
                                int Unoffered=students.size();
                                int blocked=placement.blocked;
                                for(int i=0;i<companies.size();i++){
                                    offered+=companies.get(i).SelectedStudents.size();

                                }
                                System.out.println("Number of offered students are : "+offered+"\n"+"Number of unoffered students are : "+Unoffered+"\n"+"Number of blocked students are : "+blocked);


                            } else if (PlacementCellChoice == 6) {
                                sc.nextLine();
                                String name=sc.nextLine();
                                placement.getStudentDetails(name);

                            } else if (PlacementCellChoice == 7) {
                                sc.nextLine();
                                String name=sc.nextLine();
                                placement.getCompanyDetails(name);

                            } else if (PlacementCellChoice == 8) {
                                System.out.println("Average package for this year placement is : "+placement.getAvgPackage());

                            } else if (PlacementCellChoice == 9) {
                                sc.nextLine();
                                String name = sc.nextLine();
                                placement.getCompanyProcessResults(name);
                            } else if (PlacementCellChoice == 10) {
                                break;
                            } else {
                                System.out.println("Wrong input try again");
                            }

                        }
                    } else if (choice1 == 4) {
                        break;
                    } else {
                        System.out.println("Wrong input try again");
                    }
                }

            } else if (choice == 2) {

                break;
            } else {
                System.out.println("Wrong input");
            }

        }
        System.out.println("Thankyou for using future builder");

    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;


public class StaffManagement extends JFrame implements DAO {
    private JPanel panelStaffManagement;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnDelete;
    private JButton btnFind;
    private JButton btnOpen;
    private JButton btnExit;
    private JLabel lblFullName;
    private JLabel lblAge;
    private JLabel lblEmail;
    private JLabel lblSalary;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtStaffId;
    private JTextField txtAge;
    private JTextField txtSalary;
    private JButton btnRewind;
    private JButton btnPrevious;
    private JButton btnNext;
    private JButton btnForward;
    private JLabel lblRecord;
    private JTable tblInformation;
    private JLabel lblStaffManagement;
    private JLabel lblStaffID;
    private JLabel lblClock;
    private JButton btnEmail;
    private JButton btnID;
    private JButton btnName;
    private JButton btnSalary;
    private JButton btnAge;
    private JButton btnHack;

    ArrayList<Employee> list = new ArrayList<>();
    String regexAge = "^[0-9]";
    String regexID = "^\\w+[0-9]{2}$";
    String regexFullName = "^[a-zA-Z\\s]*$";
    String regexEmail = "\\w+@\\w+(\\.\\w+){1,2}";
    String regexSalary = "^[1-9]+[0-9]*$";

    public StaffManagement() {
        initComponent();
        createTable();
        showTable();
        clickTable();
        groupClickButtonMain();
        groupClickButtonSort();
    }

    //Section process Button ------------------------------------------------------------------------------
    public void clickTable() {
        tblInformation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showTableInformationWhenClickOnTable();
            }
        });
    }

    public void groupClickButtonMain() {
        btnHack.addActionListener(e -> {
            Hack();
            showTable();
            tblInformation.setBackground(Color.green);
        });
        btnNew.addActionListener(e -> {
            resetFormInput();
            showTable();
        });
        btnSave.addActionListener(e -> {
            int index = tblInformation.getSelectedRow();
            if (index == -1) {
                addStaff();
                showTable();
            } else {
                updateInformation();
                showTable();
            }
        });
        btnDelete.addActionListener(e -> {
            delete();
            resetFormInput();
            showTable();
        });
        btnFind.addActionListener(e -> {
            findByID();
            showTable();
        });
        btnOpen.addActionListener(e -> {
            readFile();
            showTable();
        });
        btnExit.addActionListener(e -> {
            saveFile();
            exit();
        });
        btnRewind.addActionListener(e -> {
            rewindRecord();
            showTable();
        });

        btnNext.addActionListener(e -> {
            nextRecord();
            showTable();
        });
        btnForward.addActionListener(e -> {
            forwardRecord();
            showTable();
        });
        btnPrevious.addActionListener(e -> {
            previousRecord();
            showTable();
        });
    }


    public void groupClickButtonSort() {
        btnSortByName();
        btnSortByEmail();
        btnSortBySalary();
        btnSortByAge();
        btnSortByID();
    }

    private void btnSortByName() {
        btnName.addActionListener(e -> {
            choiceOption(sortByName);
        });
    }

    private void btnSortByEmail() {
        btnEmail.addActionListener(e -> {
            choiceOption(sortByEmail);
        });
    }

    private void btnSortBySalary() {
        btnSalary.addActionListener(e -> {
            choiceOption(sortBySalary);
        });
    }

    private void btnSortByID() {
        btnID.addActionListener(e -> {
            choiceOption(sortByID);
        });
    }

    private void btnSortByAge() {
        btnAge.addActionListener(e -> {
            choiceOption(sortByAge);
        });
    }


    //Section process bugs
    public void validateFormText() {
//        bugs empty
        if (txtFullName.getText().isEmpty() || txtAge.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtSalary.getText().isEmpty() || txtStaffId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Information isn't empty",
                    "Form Invalidate", JOptionPane.ERROR_MESSAGE);
        } else {
//            bugs ID
            if ((txtStaffId.getText().length() != 7)) {
                JOptionPane.showMessageDialog(this, "Staff ID must have length max = 7",
                        "Staff ID Invalidate", JOptionPane.ERROR_MESSAGE);
                txtStaffId.setText(txtStaffId.getText().substring(0, 7));
            }
            if (!txtStaffId.getText().matches(regexID)) {
                JOptionPane.showMessageDialog(this,
                        " Staff ID's format PS19496",
                        "Staff ID Invalidate", JOptionPane.ERROR_MESSAGE);
                txtStaffId.setText("");
            }
//            bugs fullName
            if (!(txtFullName.getText().matches(regexFullName))) {
                JOptionPane.showMessageDialog(this,
                        " Full name must be string,isn't number or special character ",
                        "Fullname Invalidate", JOptionPane.ERROR_MESSAGE);
                txtFullName.setText("");
            }
//            bugs Age
            if (Integer.parseInt(txtAge.getText()) > 60 || Integer.parseInt(txtAge.getText()) < 20) {
                JOptionPane.showMessageDialog(this, "Age must be between 20 to 60",
                        "Age Invalidate", JOptionPane.ERROR_MESSAGE);
                txtAge.setText("");
            }
            if (txtAge.getText().matches(regexAge)) {
                JOptionPane.showMessageDialog(this,
                        "Age must be number,isn't string special character ",
                        "Age Invalidate", JOptionPane.ERROR_MESSAGE);
                txtAge.setText("");
            }
//            bugs email
            if (!txtEmail.getText().matches(regexEmail)) {
                JOptionPane.showMessageDialog(this, "Email must have a@gmail.com or same",
                        "Email Invalidate", JOptionPane.ERROR_MESSAGE);
                txtEmail.setText("");
            }
            //bugs salary
            if (!(txtSalary.getText().matches(regexSalary))) {
                JOptionPane.showMessageDialog(this,
                        "Salary must have number,is not string or character", "Salary Invalidate",
                        JOptionPane.ERROR_MESSAGE);
                resetFormInput();
            }
            if ((Double.parseDouble(txtSalary.getText()) < 5000000)) {
                JOptionPane.showMessageDialog(this, "Salary is more than 5.000.000",
                        "Salary Invalidate", JOptionPane.ERROR_MESSAGE);
                txtSalary.setText("");
            }
            if ((Double.parseDouble(txtSalary.getText()) < 5000000)
                    || (txtStaffId.getText().length() != 7) || !(txtEmail.getText().matches(regexEmail))
                    || (Integer.parseInt(txtAge.getText()) > 60 || Integer.parseInt(txtAge.getText()) < 20)) {
                tblInformation.setModel(null);
            }
        }
    }


    public void processDuplicate() {
        boolean flag = false;
        int count = 0;
        for (Employee staff1 : list) {
            if (txtStaffId.getText().equalsIgnoreCase(staff1.getID())) {
                count++;
                JOptionPane.showMessageDialog(this, "Staff ID is exist", "Staff ID Invalidate", JOptionPane.ERROR_MESSAGE);
                list.remove(staff1);
                break;
            }
        }
    }


    // Section process features
    Comparator<Employee> sortByName = Comparator.comparing(Employee::getFullName);


    Comparator<Employee> sortByEmail = Comparator.comparing(Employee::getEmail);


    Comparator<Employee> sortByID = Comparator.comparing(Employee::getID);


    Comparator<Employee> sortByAge = (o1, o2) -> (o1.getAge() - o2.getAge());


    Comparator<Employee> sortBySalary = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            return (int) (o1.getSalary() - o2.getSalary());
        }
    };


    public void saveFile() {
        JFileChooser chooser = new JFileChooser();
        int x = chooser.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            String filePathName = chooser.getSelectedFile().getAbsoluteFile().toString();
            try {
                FileOutputStream fileName = new FileOutputStream(filePathName);
                ObjectOutputStream saveObj = new ObjectOutputStream(fileName);
                saveObj.writeObject(list);
                fileName.close();
                saveObj.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void exit() {
        int choose = JOptionPane.showConfirmDialog(this, "Do you want to exit ???", "Exit", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void readFile() {
        JFileChooser chooser = new JFileChooser();
        int x = chooser.showOpenDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            String filePathName = chooser.getSelectedFile().getAbsoluteFile().toString();
            try {
                FileInputStream fileName = new FileInputStream(filePathName);
                ObjectInputStream readObj = new ObjectInputStream(fileName);
                list = (ArrayList<Employee>) readObj.readObject();
                fileName.close();
                readObj.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateInformation() {
        try {
            int index = tblInformation.getSelectedRow();
            Employee staff = list.get(index);
            if (txtFullName.getText().isEmpty() || txtAge.getText().isEmpty() || txtEmail.getText().isEmpty()
                    || txtSalary.getText().isEmpty() || txtStaffId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Information isn't empty",
                        "Form Invalidate", JOptionPane.ERROR_MESSAGE);
            } else {
                int choose = JOptionPane.showConfirmDialog(this, "Do you want to edit ?", "Edit", JOptionPane.YES_NO_OPTION);
                if (choose == JOptionPane.YES_OPTION) {
                    if ((txtStaffId.getText().length() != 7)) {
                        JOptionPane.showMessageDialog(this, "Staff ID must have length max = 7",
                                "Staff ID Invalidate", JOptionPane.ERROR_MESSAGE);
                        txtStaffId.setText(txtStaffId.getText().substring(0, 7));
                    }

                    if (!(txtFullName.getText().matches(regexFullName))) {
                        JOptionPane.showMessageDialog(this,
                                " Full name must be string,isn't number or special character ",
                                "Fullname Invalidate", JOptionPane.ERROR_MESSAGE);
                    }
                    if (Integer.parseInt(txtAge.getText()) > 60 || Integer.parseInt(txtAge.getText()) < 20) {
                        JOptionPane.showMessageDialog(this, "Age must be between 20 to 60",
                                "Age Invalidate", JOptionPane.ERROR_MESSAGE);

                    }
                    if (!txtEmail.getText().matches(regexEmail)) {
                        JOptionPane.showMessageDialog(this, "Email must have a@gmail.com or same",
                                "Email Invalidate", JOptionPane.ERROR_MESSAGE);
                    }

                    if ((Double.parseDouble(txtSalary.getText()) < 5000000)
                            || (txtStaffId.getText().length() != 7) || !(txtEmail.getText().matches(regexEmail))
                            || (Integer.parseInt(txtAge.getText()) > 60 || Integer.parseInt(txtAge.getText()) < 20)) {
                        tblInformation.setModel(null);
                    }
                    staff.setID(txtStaffId.getText());
                    staff.setFullName(txtFullName.getText());
                    staff.setAge(Integer.parseInt(txtAge.getText()));
                    staff.setEmail(txtEmail.getText());
                    staff.setSalary(Double.parseDouble(txtSalary.getText()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        int index = tblInformation.getSelectedRow();
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to delete?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_NO_OPTION) {
            list.remove(index);
            JOptionPane.showMessageDialog(this, "Finish Complete");
        }
    }


    public void previousRecord() {
        int index = tblInformation.getSelectedRow();
        if (index > 0) {
            index--;
            lblRecord.setText("Record:" + (index + 1) + "/" + tblInformation.getRowCount());
            setIndexTableWhenClickButtonDirectional();
            tblInformation.setBackground(Color.green);
        }
    }

    public void setIndexTableWhenClickButtonDirectional() {
        try {
            int index = tblInformation.getSelectedRow();
            txtStaffId.setText(list.get(index).getID());
            txtFullName.setText(list.get(index).getFullName());
            txtEmail.setText(list.get(index).getEmail());
            txtAge.setText(String.valueOf(list.get(index).getAge()));
            txtSalary.setText(String.valueOf(list.get(index).getSalary()));
        } catch (Exception ignored) {

        }
    }

    public void rewindRecord() {
        try {
            int index = tblInformation.getSelectedRow();
            if (index >= 0) {
                index = 0;
                lblRecord.setText("Record:" + (index + 1) + "/" + tblInformation.getRowCount());
                setIndexTableWhenClickButtonDirectional();
                tblInformation.setBackground(Color.white);
            }
        } catch (Exception ignored) {

        }
    }

    public void nextRecord() {
        try {
            int index = tblInformation.getSelectedRow();
            if (index < list.size() - 1) {
                index++;
                lblRecord.setText("Record:" + (index + 1) + "/" + tblInformation.getRowCount());
                setIndexTableWhenClickButtonDirectional();
                tblInformation.setBackground(Color.white);
            }
        } catch (Exception ignored) {

        }
    }

    public void addStaff() {
        try {
            validateFormText();
            String Fullname = txtFullName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String ID = txtStaffId.getText();
            String email = txtEmail.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            Employee staff = new Employee(ID, Fullname, email, age, salary);
            processDuplicate();
            list.add(staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forwardRecord() {
        try {
            int index = tblInformation.getSelectedRow();
            if (index <= list.size() - 1) {
                txtStaffId.setText(list.get(list.size() - 1).getID());
                txtFullName.setText(list.get(tblInformation.getRowCount() - 1).getFullName());
                txtEmail.setText(list.get(tblInformation.getRowCount() - 1).getEmail());
                txtAge.setText(String.valueOf(list.get(tblInformation.getRowCount() - 1).getAge()));
                txtSalary.setText(String.valueOf(list.get(tblInformation.getRowCount() - 1).getSalary()));
                lblRecord.setText("Record:" + (tblInformation.getRowCount()) + "/" + tblInformation.getRowCount());
            }
        } catch (Exception ignored) {

        }
    }

    private void Hack() {
        list.add(new Employee("1234561", "vu thanh nam", "nam@gmail.com", 26, 897789789));
        list.add(new Employee("3123123", "tran van an", "antran@gmail.com", 21, 121232112));
        list.add(new Employee("4123123", "Than Hoang Loc", "locth5@fe.edu.vn", 22, 41231321));
        list.add(new Employee("5343413", "ngo chi tan", "ThayDayJAV@gmail.com", 23, 34132321));
        list.add(new Employee("4534534", "Christiano ronaldo", "Cr7@gmail.com", 24, 443244234));
        list.add(new Employee("9876981", "MEssi", "M10@gmail.com", 25, 89978979));
        list.add(new Employee("3123232", "Anh Nhu anh", "metrai@gmail.com", 28, 45657576));
        list.add(new Employee("5435344", "Be Suong", "SuongBinhDin@gmail.com", 29, 132132133));
        list.add(new Employee("8789711", "Nhan vat Giau ten Vu The Hai", "giautenHAiVu@gmail.com", 21, 56757763));
        list.add(new Employee("6789982", "Tran chi dung", "dungtran@gmail.com", 21, 8975477));
        list.add(new Employee("8797122", "Nguyen Tri Dinh", "dinhnguyentri@gmail.com", 26, 69779933));
        btnHack.setVisible(false);
    }

    public void resetFormInput() {
        txtStaffId.setText("");
        txtFullName.setText("");
        txtAge.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }

    public void showTableInformationWhenClickOnTable() {
        int index = tblInformation.getSelectedRow();
        Employee staff = list.get(index);
        txtStaffId.setText(staff.getID());
        txtFullName.setText(staff.getFullName());
        txtAge.setText(String.valueOf(staff.getAge()));
        txtEmail.setText(staff.getEmail());
        txtSalary.setText(String.valueOf(staff.getSalary()));
        lblRecord.setText("Record:" + (index + 1) + "/" + tblInformation.getRowCount());
        tblInformation.setSelectionBackground(Color.CYAN);
    }

    // Group initial UI -----------------------------------------------------------------------------------
    public void createTable() {
        DefaultTableModel columnsName = new DefaultTableModel();
        columnsName.addColumn("ID");
        columnsName.addColumn("FULLNAME");
        columnsName.addColumn("AGE");
        columnsName.addColumn("EMAIL");
        columnsName.addColumn("SALARY");
        tblInformation.setModel(columnsName);
    }

    @Override
    public void showTable() {
        DefaultTableModel model = (DefaultTableModel) tblInformation.getModel();
        model.setRowCount(0);

        for (Employee staff : list) {
//            Object[] rowsValue = new Object[]{staff.getID(), staff.getFullName(), String.valueOf(staff.getAge()), staff.getEmail(), String.valueOf(staff.getSalary())};
            Vector rowsValue = new Vector();
            rowsValue.addElement(staff.getID().toUpperCase());
            rowsValue.addElement(staff.getFullName().toUpperCase());
            rowsValue.addElement(String.valueOf(staff.getAge()));
            rowsValue.addElement(staff.getEmail());
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            String formatSalary = format.format(staff.getSalary());
            rowsValue.addElement(String.valueOf(formatSalary));
            model.addRow(rowsValue);
        }
    }

    @Override
    public void choiceOption(Comparator comparator) {
        int choice = JOptionPane.showConfirmDialog(this, "Sort By ASC= YES \nSort By DESC = NO",
                "Sort Asc or Desc", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_NO_OPTION) {
            list.sort(comparator);
            JOptionPane.showMessageDialog(this, "Sort Complete");
            showTable();
        }else if (choice == JOptionPane.NO_OPTION){
            Collections.reverse(list);
            JOptionPane.showMessageDialog(this, "Sort Complete");
            showTable();
        }
    }


    public void initComponent() {
        setTitle("Staff Management");
        setContentPane(panelStaffManagement);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        MyThread thread = new MyThread(lblClock);
        thread.start();
    }


    public static void main(String[] args) {
        StaffManagement staff = new StaffManagement();
        staff.setVisible(true);
    }

    @Override
    public void findByID() {
        boolean flag = false;
        for (Employee staff : list) {
            if (txtStaffId.getText().equalsIgnoreCase(staff.getID())) {
                txtStaffId.setText(staff.getID());
                txtEmail.setText(staff.getEmail());
                txtFullName.setText(staff.getFullName());
                txtAge.setText(String.valueOf(staff.getAge()));
                Locale locale = new Locale("vi", "VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                String formatSalary = format.format(staff.getSalary());
                txtSalary.setText(String.valueOf(formatSalary));
                flag = true;
                break;
            }
        }
        if (flag == false) {
            JOptionPane.showMessageDialog(this, "khong co");
        }
    }
}

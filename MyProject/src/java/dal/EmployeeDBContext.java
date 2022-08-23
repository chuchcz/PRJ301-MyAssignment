/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.DateTimeHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;

import model.RequestForLeave;
import model.TimeSheet;

/**
 *
 * @author Ngo Tung Son
 */
public class EmployeeDBContext extends DBContext {

    public ArrayList<Employee> getEmployees(Date begin, Date end) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "SELECT e.eid,e.ename,ISNULL(t.tid,-1) tid,t.checkin,t.checkout\n"
                    + "                    FROM Employee e\n"
                    + "                    	LEFT JOIN (SELECT * FROM Timesheet WHERE \n"
                    + "                    	checkin >= ?\n"
                    + "                    	AND\n"
                    + "                    	checkin < ?) t \n"
                    + "                    	ON e.eid = t.eid";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(begin)));
            stm.setTimestamp(2, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(end)));
            ResultSet rs = stm.executeQuery();
            Employee curEmp = new Employee();
            curEmp.setId(-1);
            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != curEmp.getId()) {
                    curEmp = new Employee();
                    curEmp.setId(eid);
                    curEmp.setName(rs.getString("ename"));
                    employees.add(curEmp);
                }
                int tid = rs.getInt("tid");
                if (tid != -1) {
                    TimeSheet t = new TimeSheet();
                    t.setEmployee(curEmp);
                    t.setId(tid);
                    t.setCheckin(DateTimeHelper.getDateFrom(rs.getTimestamp("checkin")));
                    t.setCheckout(DateTimeHelper.getDateFrom(rs.getTimestamp("checkout")));
                    curEmp.getTimesheets().add(t);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RequestForLeave leave = new RequestForLeave();
        leave.setId(1);
        leave.setReason(1);
        leave.setEmployee(employees.get(1));
        Date d = new Date();
        d = DateTimeHelper.removeTime(d);
        leave.setFrom(DateTimeHelper.addDays(d, 3));
        leave.setTo(DateTimeHelper.addDays(d, 5));
        employees.get(1).getLeaves().add(leave);
        
        return employees;
    }
}
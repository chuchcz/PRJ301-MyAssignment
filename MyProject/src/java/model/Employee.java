/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author hacon
 */
public class Employee {
    private int id;
    private String name;
    private ArrayList<Working> workings = new ArrayList<>();
    
    public int getNumberOfWorkingDays()
    {
        return workings.size();
    }
    
    public float getNumberOfWorkingHours()
    {
        float sum = 0;
        for (Working working : workings) {
            sum+= working.getWorkingHours();
        }
        return sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Working> getWorkings() {
        return workings;
    }

    public void setWorkings(ArrayList<Working> workings) {
        this.workings = workings;
    }
    
    
    
}

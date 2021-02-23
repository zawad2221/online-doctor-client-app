package com.example.onlinedoctor.patient;

public class PatientRepository {
    public static PatientRepository instance;
    public static PatientRepository getInstance(){
        if(instance==null){
            instance = new PatientRepository();
        }
        return instance;
    }
}

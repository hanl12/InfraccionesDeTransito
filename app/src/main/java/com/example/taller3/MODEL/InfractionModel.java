package com.example.taller3.MODEL;


//Car Model
public class InfractionModel {

    private int id;
    private String carLicensePLate;
    private String carBrand;
    private String carModel;
    private String infractionDescription;
    private String infractionAddress;

    public InfractionModel(String carLicensePLate, String carBrand, String carModel, String infractionDescription, String infractionAddress) {
        this.carLicensePLate = carLicensePLate;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.infractionDescription = infractionDescription;
        this.infractionAddress = infractionAddress;
    }

    public InfractionModel(int id, String carLicensePLate, String carBrand, String carModel, String infractionDescription, String infractionAddress) {
        this.id = id;
        this.carLicensePLate = carLicensePLate;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.infractionDescription = infractionDescription;
        this.infractionAddress = infractionAddress;
    }

    public InfractionModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarLicensePLate() {
        return carLicensePLate;
    }

    public void setCarLicensePLate(String carLicensePLate) {
        this.carLicensePLate = carLicensePLate;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getInfractionDescription() {
        return infractionDescription;
    }

    public void setInfractionDescription(String infractionDescription) {
        this.infractionDescription = infractionDescription;
    }

    public String getInfractionAddress() {
        return infractionAddress;
    }

    public void setInfractionAddress(String infractionAddress) {
        this.infractionAddress = infractionAddress;
    }
}

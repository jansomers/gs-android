package br.com.managersystems.guardasaude.exams.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Exam implements Parcelable {

    private int id;
    private String identification;
    private String serviceName;
    private String clinicName;
    private String patient;
    private String executionDate;
    private String status;
    private String reportingPhysicianName;
    private String reportingPhysicianProID;
    private String referringPhysicianName;
    private String referringPhysicianProID;
    private String reportLink;
    private List<Object> images = new ArrayList<Object>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Exam() {
    }

    public Exam(int id, String identification, String serviceName,String clinicName, String patient, String executionDate, String status, String reportingPhysicianName, String reportingPhysicianProID, String referringPhysicianName, String referringPhysicianProID, String reportLink, List<Object> images, Map<String, Object> additionalProperties) {
        this.id = id;
        this.identification = identification;
        this.serviceName = serviceName;
        this.clinicName = clinicName;
        this.patient = patient;
        this.executionDate = executionDate;
        this.status = status;
        this.reportingPhysicianName = reportingPhysicianName;
        this.reportingPhysicianProID = reportingPhysicianProID;
        this.referringPhysicianName = referringPhysicianName;
        this.referringPhysicianProID = referringPhysicianProID;
        this.reportLink = reportLink;
        this.images = images;
        this.additionalProperties = additionalProperties;
    }

    public Exam(Parcel in){
        this.id = in.readInt();
        this.identification = in.readString();
        this.serviceName = in.readString();
        this.clinicName = in.readString();
        this.patient = in.readString();
        this.executionDate = in.readString();
        this.status = in.readString();
        this.reportingPhysicianName = in.readString();
        this.reportingPhysicianProID = in.readString();
        this.referringPhysicianName = in.readString();
        this.referringPhysicianProID = in.readString();
        this.reportLink = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The identification
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * @param identification The identification
     */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * @return The serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName The serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    /**
     * @return The patient
     */
    public String getPatient() {
        return patient;
    }

    /**
     * @param patient The patient
     */
    public void setPatient(String patient) {
        this.patient = patient;
    }

    /**
     * @return The executionDate
     */
    public String getExecutionDate() {
        return executionDate;
    }

    /**
     * @param executionDate The executionDate
     */
    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The reportingPhysicianName
     */
    public String getReportingPhysicianName() {
        return reportingPhysicianName;
    }

    /**
     * @param reportingPhysicianName The reportingPhysicianName
     */
    public void setReportingPhysicianName(String reportingPhysicianName) {
        this.reportingPhysicianName = reportingPhysicianName;
    }

    /**
     * @return The reportingPhysicianProID
     */
    public String getReportingPhysicianProID() {
        return reportingPhysicianProID;
    }

    /**
     * @param reportingPhysicianProID The reportingPhysicianProID
     */
    public void setReportingPhysicianProID(String reportingPhysicianProID) {
        this.reportingPhysicianProID = reportingPhysicianProID;
    }

    /**
     * @return The referringPhysicianName
     */
    public String getReferringPhysicianName() {
        return referringPhysicianName;
    }

    /**
     * @param referringPhysicianName The referringPhysicianName
     */
    public void setReferringPhysicianName(String referringPhysicianName) {
        this.referringPhysicianName = referringPhysicianName;
    }

    /**
     * @return The referringPhysicianProID
     */
    public String getReferringPhysicianProID() {
        return referringPhysicianProID;
    }

    /**
     * @param referringPhysicianProID The referringPhysicianProID
     */
    public void setReferringPhysicianProID(String referringPhysicianProID) {
        this.referringPhysicianProID = referringPhysicianProID;
    }

    /**
     * @return The reportLink
     */
    public String getReportLink() {
        return reportLink;
    }

    /**
     * @param reportLink The reportLink
     */
    public void setReportLink(String reportLink) {
        this.reportLink = reportLink;
    }

    /**
     * @return The images
     */
    public List<Object> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<Object> images) {
        this.images = images;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.identification);
        dest.writeString(this.serviceName);
        dest.writeString(this.clinicName);
        dest.writeString(this.patient);
        dest.writeString(this.executionDate);
        dest.writeString(this.status);
        dest.writeString(this.reportingPhysicianName);
        dest.writeString(this.reportingPhysicianProID);
        dest.writeString(this.referringPhysicianName);
        dest.writeString(this.referringPhysicianProID);
        dest.writeString(this.reportLink);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };
}

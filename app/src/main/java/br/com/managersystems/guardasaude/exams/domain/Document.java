package br.com.managersystems.guardasaude.exams.domain;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Document implements Parcelable {
    private String examIdentification;
    private String documentIdentification;
    private String link;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Document() {
    }

    public Document(String examIdentification, String docIdentification,String link, Map<String, Object> additionalProperties) {
        this.examIdentification = examIdentification;
        this.documentIdentification = docIdentification;
        this.link = link;
        this.additionalProperties = additionalProperties;
    }

    public Document(Parcel in) {
        this.examIdentification = in.readString();
        this.documentIdentification = in.readString();
        this.link = in.readString();
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public String getExamIdentification() {
        return examIdentification;
    }

    public void setExamIdentification(String examIdentification) {
        this.examIdentification = examIdentification;
    }

    public String getDocIdentification() {
        return documentIdentification;
    }

    public void setDocIdentification(String docIdentification) {
        this.documentIdentification = docIdentification;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        dest.writeString(this.examIdentification);
        dest.writeString(this.documentIdentification);
        dest.writeString(this.link);
    }
}
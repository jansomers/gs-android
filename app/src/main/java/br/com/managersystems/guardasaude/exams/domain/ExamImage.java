package br.com.managersystems.guardasaude.exams.domain;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO that holds information about an Image.
 */
public class ExamImage implements Parcelable {
    private String examIdentification;
    private String imageIdentification;
    private String mimeType;
    private String link;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ExamImage() {
    }

    public ExamImage(String examIdentification, String imageIdentification, String mimeType, String link, Map<String, Object> additionalProperties) {
        this.examIdentification = examIdentification;
        this.imageIdentification = imageIdentification;
        this.mimeType = mimeType;
        this.link = link;
        this.additionalProperties = additionalProperties;
    }

    public ExamImage(Parcel in) {
        this.examIdentification = in.readString();
        this.imageIdentification = in.readString();
        this.mimeType = in.readString();
        this.link = in.readString();
    }

    public static final Creator<ExamImage> CREATOR = new Creator<ExamImage>() {
        @Override
        public ExamImage createFromParcel(Parcel in) {
            return new ExamImage(in);
        }

        @Override
        public ExamImage[] newArray(int size) {
            return new ExamImage[size];
        }
    };

    public String getExamIdentification() {
        return examIdentification;
    }

    public void setExamIdentification(String examIdentification) {
        this.examIdentification = examIdentification;
    }

    public String getImageIdentification() {
        return imageIdentification;
    }

    public void setImageIdentification(String imageIdentification) {
        this.imageIdentification = imageIdentification;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
        dest.writeString(this.imageIdentification);
        dest.writeString(this.mimeType);
        dest.writeString(this.link);
    }
}

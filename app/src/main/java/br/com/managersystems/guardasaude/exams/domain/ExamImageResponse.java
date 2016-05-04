package br.com.managersystems.guardasaude.exams.domain;

public class ExamImageResponse {

    private String result;
    private String code;
    private String username;
    private String examDocumentIdentification;
    private String examIdentification;
    private String documentEncoding;
    private String documentValue;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExamDocumentIdentification() {
        return examDocumentIdentification;
    }

    public void setExamDocumentIdentification(String examDocumentIdentification) {
        this.examDocumentIdentification = examDocumentIdentification;
    }

    public String getExamIdentification() {
        return examIdentification;
    }

    public void setExamIdentification(String examIdentification) {
        this.examIdentification = examIdentification;
    }

    public String getDocumentEncoding() {
        return documentEncoding;
    }

    public void setDocumentEncoding(String documentEncoding) {
        this.documentEncoding = documentEncoding;
    }

    public String getDocumentValue() {
        return documentValue;
    }

    public void setDocumentValue(String documentValue) {
        this.documentValue = documentValue;
    }
}

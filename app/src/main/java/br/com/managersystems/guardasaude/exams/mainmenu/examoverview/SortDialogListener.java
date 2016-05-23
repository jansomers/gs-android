package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface SortDialogListener {
    /**
     * Sets sortby and ordby for current examList
     * Requests sorted exams to presenter
     * @param orderBy
     * @param sortBy
     * @param isEmergency
     */
    void sortExamListBy(String orderBy, String sortBy, String isEmergency);
}

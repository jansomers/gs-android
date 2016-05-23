package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface SortDialogListener {
    /**
     * Sets sort by and order by for current examList
     * Requests sorted exams to presenter
     * @param orderBy String object representing how to order
     * @param sortBy String object representing how to sort.
     */
    void sortExamListBy(String orderBy,String sortBy);
}

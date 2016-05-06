package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IExamAdapter {
    /**
     * Gets strings from resources
     */
    void initialiseStrings();

    /**
     * Removes the hours from a date-string
     * @param date: The date with hours
     * @return The string of the date without hours
     */
    String removeHoursFromDate(String date);

    /**
     * Adds all exams in a list to the adapters examList
     * calls notifyDataSetChanged
     * @param exams: exams you want to add to list
     */
    void addAllExams(List<Exam> exams);

    /**
     * Splits string on space
     * Converts a string to camelcase
     * @param string: The string you want to convert to camelcase
     * @return converted string
     */
    String toCamelCase(String string);
}

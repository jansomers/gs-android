package br.com.managersystems.guardasaude.util;


import br.com.managersystems.guardasaude.R;

/**
 * Utility class to handle Status definitions
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 */
public class StatusUtils {


    public static int showCorrespondingStatus(String string) {

        switch (string) {
            case ("F"):
                return R.string.finished;
            case ("W"):
                return R.string.writing;
            case ("WR"):
                return R.string.waiting_rev;
            case ("RR"):
                return R.string.rev_rejected;
            case ("D") :
                return R.string.delivered;
            default:
                return R.string.unknown;

        }
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Locale;

/**
 * The exception that is thrown when the dates compared are not close enough.
 */
public class DatesNotApproximatelyEqualException extends RuntimeException {

    public DatesNotApproximatelyEqualException(DateTime expected, DateTime actual, Period delta) {
        super(String.format(Locale.getDefault(), Resources.getString("DatesNotApproximatelyEqualException_ctor_DefaultMessage"), expected.toString(), actual.toString(), delta.toString()));
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class DatesNotApproximatelyEqualException extends RuntimeException {

    public DatesNotApproximatelyEqualException(DateTime expected, DateTime actual, Period delta) {
        super(String.format(Locale.getDefault(), Resources.getString("DatesNotApproximatelyEqualException_ctor_DefaultMessage"), expected.toString(), actual.toString(), delta.toString()));
    }
}

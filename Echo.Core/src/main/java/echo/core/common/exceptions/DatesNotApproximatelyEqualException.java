package echo.core.common.exceptions;

import echo.core.common.Resources;
import org.joda.time.Period;

import java.util.Date;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class DatesNotApproximatelyEqualException extends RuntimeException {
    public DatesNotApproximatelyEqualException(Date expected, Date actual, Period delta) {
        super(String.format(Resources.getString("DatesNotApproximatelyEqualException_ctor_DefaultMessage"), expected.toString(), actual.toString(), delta.toString()));
    }
}

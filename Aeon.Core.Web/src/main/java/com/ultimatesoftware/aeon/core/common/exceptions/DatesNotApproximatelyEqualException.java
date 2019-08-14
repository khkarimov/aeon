package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

/**
 * The exception that is thrown when the dates compared are not close enough.
 */
public class DatesNotApproximatelyEqualException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link DatesNotApproximatelyEqualException} class.
     *
     * @param expected the expected date.
     * @param actual   the actual date.
     * @param delta    the allowed delta period.
     */
    public DatesNotApproximatelyEqualException(LocalDate expected, LocalDate actual, Period delta) {
        super(String.format(Locale.getDefault(), Resources.getString("DatesNotApproximatelyEqualException_ctor_DefaultMessage"), expected.toString(), actual.toString(), delta.toString()));
    }
}

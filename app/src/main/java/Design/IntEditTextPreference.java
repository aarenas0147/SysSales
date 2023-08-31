package Design;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;

public class IntEditTextPreference extends EditTextPreference{

    private static final int default_value = 80;

    public IntEditTextPreference(Context context) {
        super(context);
    }

    public IntEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IntEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        int defaultNumber = default_value;

        if (defaultReturnValue != null && !defaultReturnValue.isEmpty())
        {
            defaultNumber = Integer.parseInt(defaultReturnValue);
        }
        return String.valueOf(getPersistedInt(defaultNumber));

        //return super.getPersistedString(defaultReturnValue);
    }

    @Override
    protected boolean persistString(String value) {
        int number = (value != null && !value.isEmpty() ? Integer.parseInt(value) : default_value);
        return persistInt(number);

        //return super.persistString(value);
    }
}

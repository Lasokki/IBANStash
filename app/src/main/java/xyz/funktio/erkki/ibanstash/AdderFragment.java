package xyz.funktio.erkki.ibanstash;

/**
 * Created by Erkki on 7.2.2016.
 */

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class AdderFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_adder, container, false);

        final EditText nameField = (EditText) rootView.findViewById(R.id.field_name);
        final TextView EntryAddedField = (TextView) rootView.findViewById(R.id.field_added_entry);

        // When user types something, check if name is not empty
        // and clear info about earlier addition.
        nameField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                EntryAddedField.setText(R.string.no_entry_added);

                TextView NameValidity = (TextView) rootView.findViewById(R.id.name_validity);
                String name = nameField.getText().toString();

                if (!name.equals("")){
                    NameValidity.setText(R.string.name_ok);
                }
                else {
                    NameValidity.setText(R.string.name_not_ok);
                    NameValidity.setTextColor(Color.RED);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        });

        final EditText IBANField = (EditText) rootView.findViewById(R.id.field_iban);
        final IBANCheckDigit validator  = new IBANCheckDigit();

        // When user types something, check if IBAN is valid
        // and clear info about earlier addition.
        IBANField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                EntryAddedField.setText(R.string.no_entry_added);

                // Remove whitespace from the IBAN before validating
                String newIBAN = IBANField.getText().toString().toUpperCase().replaceAll("\\s","");
                TextView IBANValidity = (TextView) rootView.findViewById(R.id.iban_validity);

                if (newIBAN.equals("")) {
                    IBANValidity.setText(R.string.iban_empty);
                    IBANValidity.setTextColor(Color.RED);
                }
                else if (validator.isValid(newIBAN)){
                    IBANValidity.setText(R.string.iban_ok);
                    IBANValidity.setTextColor(Color.GREEN);
                }
                else {
                    IBANValidity.setText(R.string.iban_not_ok);
                    IBANValidity.setTextColor(Color.RED);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        });

        return rootView;
    }
}

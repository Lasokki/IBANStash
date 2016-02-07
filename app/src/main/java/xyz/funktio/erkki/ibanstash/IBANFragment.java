package xyz.funktio.erkki.ibanstash;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Erkki on 7.2.2016.
 */
public class IBANFragment extends ListFragment{

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_iban, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // This is a hack. Updates the list of items whenever this fragment is visible.
    // Better practice would be to use a listener.
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ArrayList<String> iban_array = getEntries();
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, iban_array);
            setListAdapter(adapter);
        }
    }

    public ArrayList<String> getEntries() {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        ArrayList<String> output = new ArrayList<String>();

        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String newEntry = String.format(getString(R.string.list_entry), entry.getKey(), entry.getValue().toString());
            output.add(newEntry);

        }

        return output;
    }
}

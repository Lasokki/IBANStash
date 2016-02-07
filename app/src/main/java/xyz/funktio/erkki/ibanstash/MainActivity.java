package xyz.funktio.erkki.ibanstash;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;;import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void addEntry(View view) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        IBANCheckDigit validator  = new IBANCheckDigit();

        EditText nameField = (EditText) findViewById(R.id.field_name);
        EditText IBANField = (EditText) findViewById(R.id.field_iban);
        TextView EntryAddedField = (TextView) findViewById(R.id.field_added_entry);

        String newName = nameField.getText().toString();
        // Remove whitespace from the IBAN before validating
        String newIBAN = IBANField.getText().toString().toUpperCase().replaceAll("\\s","");

        if (validator.isValid(newIBAN) && !newName.equals("")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(newName, newIBAN);
            editor.commit();
            nameField.setText("");
            IBANField.setText("");

            EntryAddedField.setText(String.format(getString(R.string.added_entry), newName, newIBAN));
        }
    }

    public void removeEntry(View view) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        EditText nameField = (EditText) findViewById(R.id.targetname);

        String targetName = nameField.getText().toString();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(targetName);
        editor.commit();
    }

    public void clearEntries(View view) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment item = null;

            if (position == 0)
                item = new AdderFragment();
            else if (position == 1)
                item = new IBANFragment();
            else if (position == 2)
                item = new RemoverFragment();

            return item;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_add);
                case 1:
                    return getString(R.string.tab_list);
                case 2:
                    return getString(R.string.tab_remove);
            }
            return null;
        }
    }

}

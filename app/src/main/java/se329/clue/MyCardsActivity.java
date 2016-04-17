package se329.clue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import se329.clue.util.CardUtil;
import se329.clue.util.GameState;

import android.util.Log;

public class MyCardsActivity extends AppCompatActivity {

    MyApp appState;
    GameState gameState;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        appState = (MyApp) getApplication();
        gameState = appState.getGameState();
        Log.d("order", gameState.getOrder().toString());

        //setup continue button
        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyCardsActivity.this, AssistantActivity.class);
                MyCardsActivity.this.startActivity(intent);
            }
        });
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, (String) getPageTitle(position));
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
                    return "Suspects";
                case 1:
                    return "Weapons";
                case 2:
                    return "Rooms";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_NAME = "section_name";
        private static final String ARG_NUM_OBJECTS = "num_objects";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String sectionName) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_NAME, sectionName);
            args.putInt(ARG_NUM_OBJECTS, getNumObjects(sectionNumber));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_SECTION_NAME));

            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.list);
            int numButtons = (getArguments().getInt(ARG_NUM_OBJECTS));
            for(int i = 0; i < numButtons; i++){
                CheckBox cb = new CheckBox(getActivity());
                //Load Json data
                CardUtil util = new CardUtil(getContext());
                addCheckboxName(util,getArguments().getString(ARG_SECTION_NAME),cb,i);
                ll.addView(cb);
            }
            return rootView;
        }

        private static int getNumObjects(int sectionNumber){
            switch (sectionNumber){
                case 1 :
                    return 6;
                case 2 :
                    return 6;
                case 3 :
                    return 9;
            }
            return -1;
        }
        private static void addCheckboxName(CardUtil util,String sectionName,CheckBox cb,int index){
            if(sectionName.equals("Suspects")){
                cb.setText(util.getSuspects().get(index));
            }else if(sectionName.equals("Weapons")){
                cb.setText(util.getWeapons().get(index));
            }else if(sectionName.equals(("Rooms"))){
                cb.setText(util.getRooms().get(index));
            }
        }
    }
}

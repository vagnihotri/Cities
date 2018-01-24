package com.assignment.view.city;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.assignment.R;

/**
 * Created by agni on 25/01/18.
 */
public class CityActivityTest extends
        ActivityInstrumentationTestCase2<CityActivity> {

    private CityActivity cityActivity;
    private RecyclerView recyclerView;

    public CityActivityTest() {
        super(CityActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cityActivity = getActivity();
        recyclerView = cityActivity.findViewById(R.id.recycler_cities);
    }

    public void testPreconditions() {
        assertNotNull("CityActivity is null", cityActivity);
        assertNotNull("RecyclerView is null", recyclerView);
    }
}
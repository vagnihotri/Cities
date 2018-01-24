package com.assignment.view.City;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.assignment.MainApplication;
import com.assignment.R;
import com.assignment.repository.AppRepository;
import com.assignment.repository.model.City;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by agni on 24/01/18.
 */

public class CityActivity extends AppCompatActivity implements CityContract.View, SwipeRefreshLayout.OnRefreshListener {

    private CityContract.Presenter presenter;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private static int RESULT_LIMIT = 10;
    private boolean userScrolled = false;

    @Inject
    AppRepository repository;

    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_city);
        //Inject dependency
        MainApplication.getAppComponent().inject(this);

        listView = (ListView) findViewById(R.id.list_cities);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<>();

        new CityPresenter(repository, this);
    }

    @Override
    public void showCities(final List<City> cities) {
        list.clear();
        for (int i = 0; i < cities.size(); i++) {
            list.add(cities.get(i).getName());
        }
        //Create the array adapter and set it to list view
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = "Selection is - " + cities.get(i).getName();
                Toast.makeText(CityActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (userScrolled
                        && firstVisibleItem + visibleItemCount == totalItemCount) {
                    userScrolled = false;
                    presenter.loadCitiesFromNetwork(RESULT_LIMIT, adapter.getCount());
                }
            }
        });
    }

    @Override
    public void showError(String message) {
        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }

    @Override
    public void showComplete() {
        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public void setPresenter(CityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.loadCitiesFromNetwork(0,0);
    }
}


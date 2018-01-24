package com.assignment.view.City;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private ArrayList<String> list;
    private LinearLayoutManager layoutManager;
    private static int RESULT_LIMIT = 10;
    private DividerItemDecoration divider;
    private ProgressDialog progressDialog;

    @Inject
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_city);
        //Inject dependency
        MainApplication.getAppComponent().inject(this);

        recyclerView = findViewById(R.id.recycler_cities);
        list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(CityActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        divider = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        new CityPresenter(repository, this);
    }

    @Override
    public void showCities(final List<City> cities) {
        stopLoading();
        list.clear();
        for (int i = 0; i < cities.size(); i++) {
            list.add(cities.get(i).getName());
        }
        cityAdapter = new CityAdapter(this, list);
        recyclerView.removeItemDecoration(divider);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (dy > 0 && (visibleItemCount + firstVisibleItem) >= totalItemCount)
                {
                    showLoading();
                    presenter.loadCitiesFromNetwork(RESULT_LIMIT, cityAdapter.getItemCount());
                }
            }
        });
    }

    private void showLoading() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading");
        }
        progressDialog.show();
    }

    private void stopLoading() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        stopLoading();
    }

    @Override
    public void showComplete() {
        stopLoading();
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
        showLoading();
        presenter.loadCitiesFromNetwork(0,0);
    }
}


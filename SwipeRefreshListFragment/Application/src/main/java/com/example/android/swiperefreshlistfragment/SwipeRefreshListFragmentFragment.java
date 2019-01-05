/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.swiperefreshlistfragment;

import com.example.android.common.dummydata.Cheeses;
import com.example.android.common.logger.Log;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

public class SwipeRefreshListFragmentFragment extends SwipeRefreshListFragment {

    private static final String TAG = "ESENS::"+SwipeRefreshListFragmentFragment.class.getSimpleName();

    private static final int LIST_ITEM_COUNT = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true); // option 메뉴를 생성해준다
    }

    // BEGIN_INCLUDE (setup_views)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        //ListAdapter 초기화, 리스트를 구성할 view와 item 갯수를 넘겨준다
        ListAdapter adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Cheeses.randomList(LIST_ITEM_COUNT));
        setListAdapter(adapter);

        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
            }
        });
    }

    //프래그먼트 내부에서 메뉴 설정(MainActivity의 메뉴와 다름)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG,"onCreateOptionMenu");

        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Log.i(TAG, "Refresh menu item selected");

                // We make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if (!isRefreshing()) {
                    setRefreshing(true);
                }

                // Start our refresh background task
                initiateRefresh();
                return true;

            case R.id.menu_color_scheme_1:
                Log.i(TAG, "setColorScheme #1");
                item.setChecked(true);

                //swipe시 로딩 애니메이션 이미지의 색상 변경
                setColorScheme(R.color.color_scheme_1_1, R.color.color_scheme_1_2,
                        R.color.color_scheme_1_3, R.color.color_scheme_1_4);
                return true;

            case R.id.menu_color_scheme_2:
                Log.i(TAG, "setColorScheme #2");
                item.setChecked(true);

                // Change the colors displayed by the SwipeRefreshLayout by providing it with 4
                // color resource ids
                setColorScheme(R.color.color_scheme_2_1, R.color.color_scheme_2_2,
                        R.color.color_scheme_2_3, R.color.color_scheme_2_4);
                return true;

            case R.id.menu_color_scheme_3:
                Log.i(TAG, "setColorScheme #3");
                item.setChecked(true);

                // Change the colors displayed by the SwipeRefreshLayout by providing it with 4
                // color resource ids
                setColorScheme(R.color.color_scheme_3_1, R.color.color_scheme_3_2,
                        R.color.color_scheme_3_3, R.color.color_scheme_3_4);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initiateRefresh() {
        Log.i(TAG, "initiateRefresh");

        /**
         * Execute the background task, which uses {@link android.os.AsyncTask} to load the data.
         */
        new DummyBackgroundTask().execute();
    }

    private void onRefreshComplete(List<String> result) {
        Log.i(TAG, "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter.clear();
        for (String cheese : result) {
            adapter.add(cheese);
        }

        setRefreshing(false);
    }

    /**
     * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
     */
    private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

        static final int TASK_DURATION = 1000; // 1 seconds

        @Override
        protected List<String> doInBackground(Void... params) {
            // Sleep for a small amount of time to simulate a background-task
            Log.d(TAG,"doInBackground");
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Return a new random list of cheeses
            return Cheeses.randomList(LIST_ITEM_COUNT);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            Log.d(TAG,"onPostExecute");
            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }
    }
}

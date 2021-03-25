package com.example.fetchrewardsassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MainActivity is the main/launcher activity.
 * This activity uses Retrofit to save the JSON Array into an ArrayList.
 *      Next, the ArrayList is filtered and sorted.
 *      Lastly, a RecyclerView displays the ArrayList.
 *
 * @author Justin Mabutas
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<DataModel> dataModels = new ArrayList<>();
    private DataAdapter dataAdapter;
    private RecyclerView dataRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataRecyclerView = findViewById(R.id.data_recycler_view);
        dataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for interface
        JSONAPICall JSONAPICall = retrofit.create(JSONAPICall.class);

        Call<List<DataModel>> call = JSONAPICall.getData();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                dataModels = new ArrayList<>(response.body());
                prepareDataModels(dataModels);

                dataAdapter = new DataAdapter(MainActivity.this, dataModels);
                dataRecyclerView.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Prepares ArrayList<DataModel> by: 1) filtering JSON objects with null or empty names.
     *                                   2) Sorting the remaining elements by listId.
     *                                   3) Sorting elements grouped by listId by their name.
     * @param array
     */
    public void prepareDataModels(ArrayList<DataModel> array) {
        // Save the number of occurrences in a HashMap to determine the number of elements in each group.
        HashMap<Integer, Integer> listIdOccurrenceMap = new HashMap<Integer, Integer>();

        // Start at end and remove any element who's name is null or empty.
        for(int i = array.size()-1; i >= 0; i--) {
            DataModel currentModel = array.get(i);
            if(currentModel.getName()==null || currentModel.getName().isEmpty())
                array.remove(i);
            else {
                if(!listIdOccurrenceMap.containsKey(currentModel.getListId())) {
                    listIdOccurrenceMap.put(currentModel.getListId(), 1);
                } else {
                    listIdOccurrenceMap.put(currentModel.getListId(),
                            listIdOccurrenceMap.get(currentModel.getListId()) + 1);
                }
            }
        }

        // Sort array based on listId
        Collections.sort(array, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel o1, DataModel o2) {
                return Integer.valueOf(o1.getListId()).compareTo(o2.getListId());
            }
        });

        // Sort array by names. The markers indicate start/end of the group that is currently being sorted.
        for(int leftMarker = 0; leftMarker < array.size(); leftMarker = leftMarker) {
            int currentListIdOccurrences = listIdOccurrenceMap.get(array.get(leftMarker).getListId());

            int rightMarker = leftMarker + currentListIdOccurrences;
            Collections.sort(array.subList(leftMarker,rightMarker), new Comparator<DataModel>() {
                @Override
                public int compare(DataModel o1, DataModel o2) {
                    return Integer.valueOf(o1.getName().substring(5)).compareTo(Integer.valueOf(o2.getName().substring(5)));
                }
            });
            leftMarker = rightMarker;
        }
    }
}
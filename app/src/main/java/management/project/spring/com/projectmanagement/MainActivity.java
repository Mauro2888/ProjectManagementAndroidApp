package management.project.spring.com.projectmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import management.project.spring.com.projectmanagement.Adapters.AdapterRecycler;
import management.project.spring.com.projectmanagement.Bean.BeanResources;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnClickItems {

  private RecyclerView mRecyclerResources;
  private RecyclerView.LayoutManager mLayoutManager;
  private ArrayList<BeanResources>mArrayList;
  private AdapterRecycler mAdapterRecycler;
  private FloatingActionMenu mMenuFloat;
  private com.github.clans.fab.FloatingActionButton mFloatAddResources;
  private com.github.clans.fab.FloatingActionButton mFloatAssignment;
  private com.github.clans.fab.FloatingActionButton mFloatprojectManager;
  int id;


  private static final String CONTENT_URL_EMU = "http://10.0.2.2:8080/rest/resources/show";


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Resources available");

    mFloatAddResources = findViewById(R.id.floatingActionButton);
    mRecyclerResources = findViewById(R.id.recyclerResources);
    mFloatAssignment = findViewById(R.id.floatingActionButtonAssignment);
    mFloatprojectManager = findViewById(R.id.floatingActionButtonProjectManger);
    mMenuFloat = findViewById(R.id.mFloatMenu);
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerResources.setLayoutManager(mLayoutManager);
    mRecyclerResources.setHasFixedSize(true);
    mArrayList = new ArrayList<>();
    mAdapterRecycler = new AdapterRecycler(this,mArrayList);
    mRecyclerResources.setAdapter(mAdapterRecycler);
    mAdapterRecycler.setOnClickItems(this);



    mRecyclerResources.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0.8){
          mMenuFloat.hideMenu(true);
        }else {
          mMenuFloat.showMenu(true);
        }
      }
    });

    mFloatprojectManager.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent openManger = new Intent(MainActivity.this,AddProjectActivity.class);
        startActivity(openManger);
      }
    });

    mFloatAssignment.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent openAssignment = new Intent(MainActivity.this,AssignmentActivity.class);
        startActivity(openAssignment);
      }
    });

    mFloatAddResources.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent addResources = new Intent(MainActivity.this,AddResourcesActivity.class);
        startActivity(addResources);
      }
    });
  }

  @Override protected void onStart() {
    super.onStart();
    if (mArrayList.size() > 0){
      mArrayList.clear();
    }
    new AsyncTaskDatabase().execute(CONTENT_URL_EMU);
  }

  @Override public void onClick(View view, int pos) {

    Intent openEditResources = new Intent(MainActivity.this,EditResourcesActivity.class);
    openEditResources.putExtra("pos_resources",mArrayList.get(pos));
    startActivity(openEditResources);


  }

  public class AsyncTaskDatabase extends AsyncTask<String,Void,String> {

    @Override protected void onPreExecute() {
      super.onPreExecute();
    }

    StringBuilder buildString = new StringBuilder();

    @Override protected String doInBackground(String... strings) {
      HttpURLConnection urlConnection = null;
      try {
        URL url = new URL(strings[0]);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(15000);
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String data = null;
        while ((data = bufferedReader.readLine())!= null) {
          buildString.append(data);
        }

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return buildString.toString();
    }

    @Override protected void onPostExecute(String result) {
      getJsonData(result);
      super.onPostExecute(result);
    }

    public void getJsonData(String dataResult){
      try {
        JSONArray jsonArray = new JSONArray(dataResult);
        for (int i = 0; i < jsonArray.length();i++){
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          id = jsonObject.getInt("id");
          String assigned = jsonObject.getString("assigned");
          String name = jsonObject.getString("name");
          String surname = jsonObject.getString("surname");
          String type = jsonObject.getString("type");
          String hire = jsonObject.getString("hire");
          mArrayList.add(new BeanResources(id,name,surname,type,hire,assigned));
          mAdapterRecycler.notifyDataSetChanged();

        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

  }


}

package management.project.spring.com.projectmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import management.project.spring.com.projectmanagement.Adapters.AdapterAvailableProjects;
import management.project.spring.com.projectmanagement.Bean.BeanAssignement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssignmentActivity extends AppCompatActivity implements OnClickItems {

  //for projects
  private ArrayList<String> mListProjectSpinner;
  private ArrayAdapter<String> mAdapterSpinner;
  private Spinner mSpinnerProject;
  private static final String URL_PROJECT = "http://10.0.2.2:8080/rest/resources/show/projects";
  private static final String URL_PROJECT_BY_ID = "http://10.0.2.2:8080/rest/resources/show/projects/";

  private int idFromDataBase;
  private RecyclerView mRecyclerAvailable;
  private RecyclerView.LayoutManager mLayoutManger;
  private ArrayList<BeanAssignement> mListAssignement;
  private AdapterAvailableProjects mAdapterRecycler;

  int id_toPut;



  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_assignement);

    mRecyclerAvailable = findViewById(R.id.recylcerAvailable);
    mLayoutManger =  new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    mRecyclerAvailable.setLayoutManager(mLayoutManger);
    mRecyclerAvailable.setHasFixedSize(true);
    mListAssignement = new ArrayList<>();
    mAdapterRecycler = new AdapterAvailableProjects(this,mListAssignement);
    mRecyclerAvailable.setAdapter(mAdapterRecycler);
    mAdapterRecycler.setmClickItem(this);

  }

  @Override protected void onStart() {
    super.onStart();
    if (mListAssignement.size() > 0){
      mListAssignement.clear();
    }
    new AsyncTaskGetProjectsInfo().execute(URL_PROJECT);
  }


  @Override public void onClick(View view, int pos) {
    Intent modify = new Intent(this,ProjectModifyActivity.class);
    modify.putExtra("pos_project",mListAssignement.get(pos));
    startActivity(modify);
  }


  public class AsyncTaskGetProjectsInfo extends AsyncTask<String,Void,String> {

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
      mAdapterRecycler.notifyDataSetChanged();
      super.onPostExecute(result);
    }

    public void getJsonData(String dataResult){
      try {
        JSONArray jsonOarray = new JSONArray(dataResult);
        for (int i = 0; i < jsonOarray.length(); i++){
          JSONObject object = jsonOarray.getJSONObject(i);
          int id = object.getInt("id");
          String name_project = object.getString("name_project");
          String start_project = object.getString("start_project");
          String status = object.getString("status");
          String nsenior = object.getString("nsenior");
          String njunior = object.getString("njunior");
          String deadline = object.getString("deadline");

          mListAssignement.add(new BeanAssignement(id,name_project,start_project,status,nsenior,njunior,deadline));
        }


      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

  }


}

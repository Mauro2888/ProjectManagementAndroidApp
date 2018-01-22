package management.project.spring.com.projectmanagement;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddProjectActivity extends AppCompatActivity {


  private ArrayAdapter<String> mArrayAdapterStatus;
  private ArrayList<String> mArrayListStatus;
  private Spinner mSpinnerStaus;
  private DatePickerDialog.OnDateSetListener mDatePickerDeadLine;
  private DatePickerDialog.OnDateSetListener mDatePickerStart;
  private Calendar calendar;

  private EditText mNameProject;
  private EditText mDeadLineDate;
  private EditText mStartProject;

  private ArrayList<String> mPostDatiArray;

  //for level
  private ArrayList<String> mListLevelSpinner;
  private ArrayAdapter<String> mAdapterSpinnerLevel;
  private Spinner mSpinnerLevel;

  private static final String URL_POST_PROJECTS = "http://10.0.2.2:8080/rest/resources/load/project";
  private static final String URL_LEVEL_DEVELOPMENT = "http://10.0.2.2:8080/rest/resources/show/type/";

  //for programmers
  private ArrayList<String> mArrayListProgrammers;
  private ArrayAdapter<String> mAdapterListViewProgrammers;
  private ListView mListViewProgrammers;

  private JSONObject mPostProject;


  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_project);
    setTitle("Insert new project");

    mNameProject = findViewById(R.id.addNameProject);
    mDeadLineDate = findViewById(R.id.deadlineProject);
    mSpinnerStaus = findViewById(R.id.statusSpinner);
    mStartProject = findViewById(R.id.startProject);
    mArrayListStatus = new ArrayList<>();
    String [] status = {"Evaluation","Design","Development","Delivery","Closing"};
    mArrayListStatus.addAll(Arrays.asList(status));
    mArrayAdapterStatus = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mArrayListStatus);
    mSpinnerStaus.setAdapter(mArrayAdapterStatus);

    calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    mDatePickerDeadLine = new DatePickerDialog.OnDateSetListener() {
      @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        mDeadLineDate.setText(dateFormat.format(calendar.getTime()));
      }
    };

    mDatePickerStart = new DatePickerDialog.OnDateSetListener() {
      @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        mStartProject.setText(dateFormat.format(calendar.getTime()));
      }
    };

    mDeadLineDate.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new DatePickerDialog(AddProjectActivity.this, mDatePickerDeadLine,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    mStartProject.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new DatePickerDialog(AddProjectActivity.this, mDatePickerStart,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });




    mSpinnerLevel = findViewById(R.id.spinnerLevel);
    mListLevelSpinner = new ArrayList<>();
    mListLevelSpinner.add("Senior");
    mListLevelSpinner.add("Junior");
    mAdapterSpinnerLevel = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mListLevelSpinner);
    mSpinnerLevel.setAdapter(mAdapterSpinnerLevel);




    //programmers
    mListViewProgrammers = findViewById(R.id.listProgrammers);
    mArrayListProgrammers = new ArrayList<>();
    mListViewProgrammers.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
    mAdapterListViewProgrammers = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,mArrayListProgrammers);
    mListViewProgrammers.setAdapter(mAdapterListViewProgrammers);


    mListViewProgrammers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SparseBooleanArray dati = mListViewProgrammers.getCheckedItemPositions();
        mPostDatiArray = new ArrayList<>();
        for (int i = 0; i < dati.size(); i++){
          int posadapter = dati.keyAt(i);
          if (dati.valueAt(i)){
            mPostDatiArray.add(mAdapterListViewProgrammers.getItem(posadapter));

          }
        }

        Log.d("ARRAYS"," " + Arrays.toString(mPostDatiArray.toArray()));

      }
    });




    mSpinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mArrayListProgrammers.clear();
        mAdapterListViewProgrammers.notifyDataSetChanged();
        String pos = (String) adapterView.getItemAtPosition(position);
        new AsyncTaskResourcesAvailable().execute(URL_LEVEL_DEVELOPMENT + pos);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });


  }
  public class AsyncTaskResourcesAvailable extends AsyncTask<String,Void,String> {

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
          String name = jsonObject.getString("name");
          String surname = jsonObject.getString("surname");
          mArrayListProgrammers.add(name + " " + surname);
          Log.d("TAGGGG","" + dataResult);
          mListViewProgrammers.setAdapter(mAdapterListViewProgrammers);

        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

  }
  public class AsyncTaskPostToProjects extends AsyncTask<String,Void,String> {

    @Override protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override protected String doInBackground(String... strings) {
      HttpURLConnection urlConnection = null;
      String finishdata = null;
      StringBuilder stringBuilder = new StringBuilder();

      try {
        URL url = new URL(strings[0]);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
        outputStream.writeBytes(mPostProject.toString());
        outputStream.flush();
        outputStream.close();
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((finishdata = bufferedReader.readLine()) != null) {
          stringBuilder.append(finishdata);
        }
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return stringBuilder.toString();
    }

    @Override protected void onPostExecute(String result) {
      super.onPostExecute(result);
    }
  }


  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_project,menu);
    return super.onCreateOptionsMenu(menu);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.saveResource:
        mPostProject = new JSONObject();
        try {
          mPostProject.put("name_project", mNameProject.getText().toString());
          mPostProject.put("start_project", calendar.getTimeInMillis());
          mPostProject.put("status", mSpinnerStaus.getSelectedItem().toString());
          if (Objects.equals(mSpinnerLevel.getSelectedItem().toString().toLowerCase(), "junior")){
            mPostProject.put("njunior",mPostDatiArray.size()).toString();
          }
          if (Objects.equals(mSpinnerLevel.getSelectedItem().toString().toLowerCase(), "senior")){
            mPostProject.put("nsenior",mPostDatiArray.size()).toString();
          }
          mPostProject.put("deadline", calendar.getTimeInMillis());

        } catch (JSONException e) {
          e.printStackTrace();
        }
        new AsyncTaskPostToProjects().execute(URL_POST_PROJECTS);
    }
    return super.onOptionsItemSelected(item);
  }
}

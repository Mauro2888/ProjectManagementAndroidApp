package management.project.spring.com.projectmanagement;

import android.app.DatePickerDialog;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

public class AddResourcesActivity extends AppCompatActivity implements View.OnClickListener {
  private EditText mName;
  private EditText mSurname;
  private EditText mType;
  private EditText mHire;
  private DatePickerDialog.OnDateSetListener mListenerDate;
  private Calendar calendar;
  private Button mAddResource;
  private ProgressBar mProgress;

  private static final String POST_URL = "http://10.0.2.2:8080/rest/resources/load";
  private JSONObject  mPostRisorse;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_resources);
    mName = findViewById(R.id.addName);
    mSurname = findViewById(R.id.addSurname);
    mHire = findViewById(R.id.addHire);
    mType = findViewById(R.id.addType);
    mProgress = findViewById(R.id.progress);
    mAddResource = findViewById(R.id.saveResource);
    mHire.setOnClickListener(this);
    mAddResource.setOnClickListener(this);

    calendar = Calendar.getInstance();
    final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    mListenerDate = new DatePickerDialog.OnDateSetListener() {
      @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        mHire.setText(format.format(calendar.getTime()));
      }
    };
  }

  @Override public void onClick(View v) {

    if (v == mHire) {

      new DatePickerDialog(this, mListenerDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    if (v == mAddResource) {
      mProgress.setVisibility(View.VISIBLE);
      mPostRisorse = new JSONObject();
      try {
        mPostRisorse.put("name", mName.getText().toString());
        mPostRisorse.put("surname", mSurname.getText().toString());
        mPostRisorse.put("type", mType.getText().toString());
        mPostRisorse.put("hire", calendar.getTimeInMillis());
      } catch (JSONException e) {
        e.printStackTrace();
      }
      new AsyncTaskPost().execute(POST_URL);
    }
  }

  public class AsyncTaskPost extends AsyncTask<String,Void,String> {

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
        outputStream.writeBytes(mPostRisorse.toString());
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
      mProgress.setVisibility(View.GONE);
      Toast.makeText(AddResourcesActivity.this, "Resource is added", Toast.LENGTH_SHORT).show();
      super.onPostExecute(result);
    }
  }
}

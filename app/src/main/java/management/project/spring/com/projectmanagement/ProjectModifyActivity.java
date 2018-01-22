package management.project.spring.com.projectmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import management.project.spring.com.projectmanagement.Bean.BeanAssignement;
import org.json.JSONException;
import org.json.JSONObject;

public class ProjectModifyActivity extends AppCompatActivity {

  BeanAssignement beanAssignement ;
  private TextView mName,mStart,mStatus,mJunior,mSenior,mDeadLine;
  private Button mBtnUpdate;
  private static final String URL_UPDATE = "http://10.0.2.2:8080/rest/resources/update/project/";
  private JSONObject mPutUpdate;
  int id;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_modify);

    Intent intent = getIntent();
    beanAssignement = (BeanAssignement)intent.getSerializableExtra("pos_project");

    id = beanAssignement.getId();
    Toast.makeText(this, "ID" + id, Toast.LENGTH_SHORT).show();

    mName = findViewById(R.id.titleProjectModify);
    mStart = findViewById(R.id.startProjectModify);
    mStatus = findViewById(R.id.statusModify);
    mJunior = findViewById(R.id.juniorModify);
    mSenior = findViewById(R.id.seniorModify);
    mDeadLine = findViewById(R.id.deadlineModify);
    mBtnUpdate = findViewById(R.id.update);


    mName.setText(beanAssignement.getTitleP());
    mStart.setText(beanAssignement.getStartP());
    mStatus.setText(beanAssignement.getStatusP());
    mJunior.setText(beanAssignement.getJuniorP());
    mSenior.setText(beanAssignement.getSeniorP());
    mDeadLine.setText(beanAssignement.getDeadLineP());

    final Calendar calendar = Calendar.getInstance();


    mBtnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPutUpdate = new JSONObject();
        try {
          mPutUpdate.put("name_project", mName.getText().toString());
          mPutUpdate.put("start_project", mStart.getText().toString());
          mPutUpdate.put("status", mStatus.getText().toString());
          mPutUpdate.put("nsenior", mSenior.getText().toString());
          mPutUpdate.put("njunior",mJunior.getText().toString());
          mPutUpdate.put("deadline",calendar.getTimeInMillis());
        } catch (JSONException e) {
          e.printStackTrace();
        }
        new AsyncTaskModifyProjects().execute(URL_UPDATE + id);
      }
    });
  }
  public class AsyncTaskModifyProjects extends AsyncTask<String,Void,String> {

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
        urlConnection.setRequestMethod("PUT");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
        outputStream.writeBytes(mPutUpdate.toString());
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
      Toast.makeText(ProjectModifyActivity.this, "Project Update", Toast.LENGTH_SHORT).show();
      super.onPostExecute(result);
    }
  }
}

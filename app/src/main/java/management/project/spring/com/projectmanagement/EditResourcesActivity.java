package management.project.spring.com.projectmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import management.project.spring.com.projectmanagement.Bean.BeanResources;
import org.json.JSONException;
import org.json.JSONObject;

public class EditResourcesActivity extends AppCompatActivity {

  private BeanResources mBeanResources;
  private EditText mName,mSurname,mType,mAssigned,mHired;
  private Button mBtnUpdate;
  private JSONObject mPutUpdate;

  int id;

  private static final String URL_UPDATE_RESOURCES = "http://10.0.2.2:8080/rest/resources/update/resources/";
  private static final String URL_DELETE_RESOURCE  = "http://10.0.2.2:8080/rest/resources/delete/resources/";
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_resources);

    Intent getDataResources = getIntent();
    mBeanResources =(BeanResources)getDataResources.getSerializableExtra("pos_resources");


    mName = findViewById(R.id.nameEdit);
    mSurname = findViewById(R.id.surnameEdit);
    mType = findViewById(R.id.typeEdit);
    mAssigned = findViewById(R.id.assignedEdit);
    mHired = findViewById(R.id.hireEdit);
    mBtnUpdate = findViewById(R.id.updateResoruceBtn);

    id = mBeanResources.getId();
    mName.setText(mBeanResources.getmName());
    mSurname.setText(mBeanResources.getmSurname());
    mType.setText(mBeanResources.getmType());
    mAssigned.setText(mBeanResources.getmIdProject());
    mHired.setText(mBeanResources.getmHire());

    final Calendar calendar = Calendar.getInstance();

    mBtnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPutUpdate = new JSONObject();
        try {
          mPutUpdate.put("name", mName.getText().toString());
          mPutUpdate.put("surname", mSurname.getText().toString());
          mPutUpdate.put("type", mType.getText().toString());
          mPutUpdate.put("assigned", mAssigned.getText().toString());
          mPutUpdate.put("hire",calendar.getTimeInMillis());
        } catch (JSONException e) {
          e.printStackTrace();
        }
        new AsyncTaskModifyResources().execute(URL_UPDATE_RESOURCES + id);
        Toast.makeText(EditResourcesActivity.this, " " + URL_UPDATE_RESOURCES + id, Toast.LENGTH_SHORT).show();
      }
    });


  }
  public class AsyncTaskModifyResources extends AsyncTask<String,Void,String> {

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

      Toast.makeText(EditResourcesActivity.this, "Resource update", Toast.LENGTH_SHORT).show();
      super.onPostExecute(result);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.delete_menu,menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.delete_menu_btn:
        new AsyncTaskDeleteResource().execute(URL_DELETE_RESOURCE + id);
    }
    return super.onOptionsItemSelected(item);
  }

  public class AsyncTaskDeleteResource extends AsyncTask<String,Void,String> {

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
        urlConnection.setRequestMethod("DELETE");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
        outputStream.writeBytes("");
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

      Toast.makeText(EditResourcesActivity.this, "Resource deleted", Toast.LENGTH_SHORT).show();
      super.onPostExecute(result);
      finish();
    }
  }


}


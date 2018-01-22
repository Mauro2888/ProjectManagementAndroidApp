package management.project.spring.com.projectmanagement;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity implements Runnable {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    run();

  }

  @Override public void run() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      Intent main = new Intent(IntroActivity.this,MainActivity.class);
      startActivity(main);
      finish();
    }
  }
}

package management.project.spring.com.projectmanagement.Bean;

import java.io.Serializable;

/**
 * Created by Mauro on 16/01/2018.
 */

public class BeanResources implements Serializable {

  private int id;
  private String mName;
  private String mSurname;
  private String mType;
  private String mHire;
  private String mIdProject;

  public BeanResources(int id, String mName, String mSurname, String mType, String mHire,
      String mIdProject) {
    this.id = id;
    this.mName = mName;
    this.mSurname = mSurname;
    this.mType = mType;
    this.mHire = mHire;
    this.mIdProject = mIdProject;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getmName() {
    return mName;
  }

  public void setmName(String mName) {
    this.mName = mName;
  }

  public String getmSurname() {
    return mSurname;
  }

  public void setmSurname(String mSurname) {
    this.mSurname = mSurname;
  }

  public String getmType() {
    return mType;
  }

  public void setmType(String mType) {
    this.mType = mType;
  }

  public String getmHire() {
    return mHire;
  }

  public void setmHire(String mHire) {
    this.mHire = mHire;
  }

  public String getmIdProject() {
    return mIdProject;
  }

  public void setmIdProject(String mIdProject) {
    this.mIdProject = mIdProject;
  }
}

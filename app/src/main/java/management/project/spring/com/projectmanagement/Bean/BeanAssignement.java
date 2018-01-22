package management.project.spring.com.projectmanagement.Bean;

import java.io.Serializable;

/**
 * Created by Mauro on 19/01/2018.
 */

public class BeanAssignement implements Serializable {

  int id;
  String titleP;
  String startP;
  String statusP;
  String seniorP;
  String juniorP;
  String deadLineP;

  public BeanAssignement(int id, String titleP, String startP, String statusP, String seniorP,
      String juniorP, String deadLineP) {
    this.id = id;
    this.titleP = titleP;
    this.startP = startP;
    this.statusP = statusP;
    this.seniorP = seniorP;
    this.juniorP = juniorP;
    this.deadLineP = deadLineP;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitleP() {
    return titleP;
  }

  public void setTitleP(String titleP) {
    this.titleP = titleP;
  }

  public String getStartP() {
    return startP;
  }

  public void setStartP(String startP) {
    this.startP = startP;
  }

  public String getStatusP() {
    return statusP;
  }

  public void setStatusP(String statusP) {
    this.statusP = statusP;
  }

  public String getSeniorP() {
    return seniorP;
  }

  public void setSeniorP(String seniorP) {
    this.seniorP = seniorP;
  }

  public String getJuniorP() {
    return juniorP;
  }

  public void setJuniorP(String juniorP) {
    this.juniorP = juniorP;
  }

  public String getDeadLineP() {
    return deadLineP;
  }

  public void setDeadLineP(String deadLineP) {
    this.deadLineP = deadLineP;
  }
}
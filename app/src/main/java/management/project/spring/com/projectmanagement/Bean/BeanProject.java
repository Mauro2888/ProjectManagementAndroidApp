package management.project.spring.com.projectmanagement.Bean;

/**
 * Created by Mauro on 16/01/2018.
 */

public class BeanProject {
  String nameProject;
  String startProjectDate;
  String statusProject;
  String nSenior;
  String nJunior;
  String deadLineProject;

  public BeanProject(String nameProject, String startProjectDate, String statusProject,
      String nSenior, String nJunior, String deadLineProject) {
    this.nameProject = nameProject;
    this.startProjectDate = startProjectDate;
    this.statusProject = statusProject;
    this.nSenior = nSenior;
    this.nJunior = nJunior;
    this.deadLineProject = deadLineProject;
  }

  public String getNameProject() {
    return nameProject;
  }

  public void setNameProject(String nameProject) {
    this.nameProject = nameProject;
  }

  public String getStartProjectDate() {
    return startProjectDate;
  }

  public void setStartProjectDate(String startProjectDate) {
    this.startProjectDate = startProjectDate;
  }

  public String getStatusProject() {
    return statusProject;
  }

  public void setStatusProject(String statusProject) {
    this.statusProject = statusProject;
  }

  public String getnSenior() {
    return nSenior;
  }

  public void setnSenior(String nSenior) {
    this.nSenior = nSenior;
  }

  public String getnJunior() {
    return nJunior;
  }

  public void setnJunior(String nJunior) {
    this.nJunior = nJunior;
  }

  public String getDeadLineProject() {
    return deadLineProject;
  }

  public void setDeadLineProject(String deadLineProject) {
    this.deadLineProject = deadLineProject;
  }
}

package management.project.spring.com.projectmanagement.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import management.project.spring.com.projectmanagement.Bean.BeanAssignement;
import management.project.spring.com.projectmanagement.OnClickItems;
import management.project.spring.com.projectmanagement.R;

/**
 * Created by Mauro on 16/01/2018.
 */




public class AdapterAvailableProjects extends RecyclerView.Adapter<AdapterAvailableProjects.ViewHolderData> {

  private Context mContext;
  private List<BeanAssignement> mListAssignement;
  OnClickItems mClickItem;

  public void setmClickItem(OnClickItems mClickItem) {
    this.mClickItem = mClickItem;
  }

  public AdapterAvailableProjects(Context mContext, List<BeanAssignement> mListAssignement) {
    this.mContext = mContext;
    this.mListAssignement = mListAssignement;
  }

  @Override public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_available_project,parent,false);

    return new ViewHolderData(view);
  }

  @Override public void onBindViewHolder(ViewHolderData holder, int position) {
   BeanAssignement beanAssignement = mListAssignement.get(position);
    holder.titleProjectAvailable.setText(beanAssignement.getTitleP());
   holder.startProjectAvailable.setText(beanAssignement.getStartP());
   holder.statusAvailable.setText(beanAssignement.getStatusP());
   holder.seniorAvailable.setText(beanAssignement.getSeniorP());
    holder.juniorAvailable.setText(beanAssignement.getJuniorP());
    holder.deadlineAvailable.setText(beanAssignement.getDeadLineP());
  }

  @Override public int getItemCount() {
    return mListAssignement.size();
  }

  public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView titleProjectAvailable;
    TextView startProjectAvailable;
    TextView statusAvailable;
    TextView seniorAvailable;
    TextView juniorAvailable;
    TextView deadlineAvailable;

    public ViewHolderData(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);

      titleProjectAvailable = itemView.findViewById(R.id.titleProjectAvailable);
      startProjectAvailable = itemView.findViewById(R.id.startProjectAvailable);
      statusAvailable = itemView.findViewById(R.id.statusAvailable);
      seniorAvailable = itemView.findViewById(R.id.seniorAvailable);
      juniorAvailable = itemView.findViewById(R.id.juniorAvailable);
      deadlineAvailable = itemView.findViewById(R.id.deadlineAvailable);
    }

    @Override public void onClick(View v) {
      if (v != null){
        mClickItem.onClick(v,getAdapterPosition());
      }
    }
  }
}

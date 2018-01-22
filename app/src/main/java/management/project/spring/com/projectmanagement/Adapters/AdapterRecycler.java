package management.project.spring.com.projectmanagement.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import management.project.spring.com.projectmanagement.Bean.BeanResources;
import management.project.spring.com.projectmanagement.OnClickItems;
import management.project.spring.com.projectmanagement.R;

/**
 * Created by Mauro on 16/01/2018.
 */




public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderData> {

  private Context mContext;
  private List<BeanResources> mListResources;
  private OnClickItems onClickItems;

  public void setOnClickItems(OnClickItems onClickItems) {
    this.onClickItems = onClickItems;
  }

  public AdapterRecycler(Context mContext, List<BeanResources> mListResources) {
    this.mContext = mContext;
    this.mListResources = mListResources;
  }

  @Override public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_resources,parent,false);

    return new ViewHolderData(view);
  }

  @Override public void onBindViewHolder(ViewHolderData holder, int position) {
    BeanResources beanResources = mListResources.get(position);

    holder.name.setText(beanResources.getmName());
    holder.surname.setText(beanResources.getmSurname());
    holder.type.setText(beanResources.getmType());
    holder.hire.setText(beanResources.getmHire());
    holder.idP.setText(beanResources.getmIdProject());

  }

  @Override public int getItemCount() {
    return mListResources.size();
  }

  public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView name;
    TextView surname;
    TextView hire;
    TextView type;
    TextView idP;

    public ViewHolderData(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      name = itemView.findViewById(R.id.nameResources);
      surname = itemView.findViewById(R.id.surnameResources);
      hire = itemView.findViewById(R.id.hireResources);
      type = itemView.findViewById(R.id.typeResources);
      idP = itemView.findViewById(R.id.project_number);
    }

    @Override public void onClick(View v) {
      if (v != null){
        onClickItems.onClick(v,getAdapterPosition());
      }
    }
  }
}

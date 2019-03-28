package coding.assignment.locus.view.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coding.assignment.locus.R;
import coding.assignment.locus.data.models.DataModel;

import static coding.assignment.locus.view.ViewTypes.COMMENT;
import static coding.assignment.locus.view.ViewTypes.PHOTO;
import static coding.assignment.locus.view.ViewTypes.SINGLE_CHOICE;


/**
 * Created by varun.am on 27/03/19
 */
public class LocusAdapter extends RecyclerView.Adapter<LocusAdapter.ViewHolder> {
    
    private static final String TAG = LocusAdapter.class.getSimpleName();
    private List<DataModel> dataList;
    private ImageClickedCallbacks imageClickedCallbacks;
    
    public void setDataList(ArrayList<DataModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    
    public LocusAdapter(@NonNull ImageClickedCallbacks imageClickedCallbacks) {
        this.imageClickedCallbacks = imageClickedCallbacks;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case PHOTO:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_image, viewGroup, false);
                break;
            case SINGLE_CHOICE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_single_choice, viewGroup, false);
                break;
            case COMMENT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_comment, viewGroup, false);
                break;
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_image, viewGroup, false);
                break;
        }
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final DataModel dataModel = dataList.get(position);
        
        switch (dataModel.getViewType()) {
            case PHOTO:
                viewHolder.photoTitle.setText(dataModel.getTitle());
                
                if (dataModel.getImageFilePath() != null) {
                    viewHolder.photoView.setImageURI(Uri.parse(dataModel.getImageFilePath()));
                    viewHolder.closeImageIcon.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Setting imageBitmap: " + dataModel.getImageFilePath());
                } else {
                    viewHolder.closeImageIcon.setVisibility(View.GONE);
                    viewHolder.photoView.setImageDrawable(viewHolder.photoView.getContext().getResources().getDrawable(R.drawable.ic_camera));
                }
                
                viewHolder.closeImageIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataModel.setImageFilePath(null);
                        notifyItemChanged(position);
                    }
                });
                viewHolder.photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageClickedCallbacks.onImageClicked(position, dataModel.getImageFilePath());
                    }
                });
                break;
            case SINGLE_CHOICE:
                viewHolder.optionsTitle.setText(dataModel.getTitle());
                viewHolder.optionOne.setText(dataModel.getDataMap().getOptions().getOption1());
                viewHolder.optionTwo.setText(dataModel.getDataMap().getOptions().getOption2());
                viewHolder.optionThree.setText(dataModel.getDataMap().getOptions().getOption3());
                break;
            case COMMENT:
                viewHolder.commentSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (viewHolder.commentSwitch.isChecked()) {
                            dataModel.setTitle("Enter your comment here...");
                        } else {
                            dataModel.setTitle("");
                        }
                        notifyItemChanged(position);
                    }
                });
                if (TextUtils.isEmpty(dataModel.getTitle())) {
                    viewHolder.commentSwitch.setChecked(false);
                    viewHolder.commentBox.setVisibility(View.GONE);
                } else {
                    viewHolder.commentSwitch.setChecked(true);
                    viewHolder.commentBox.setVisibility(View.VISIBLE);
                    viewHolder.commentBox.setText(dataModel.getTitle());
                }
        }
        
    }
    
    @Override
    public int getItemViewType(int position) {
        switch (dataList.get(position).getViewType()) {
            case 0:
                return PHOTO;
            case 1:
                return SINGLE_CHOICE;
            case 2:
                return COMMENT;
            default:
                return -1;
        }
    }
    
    @Override
    public int getItemCount() {
        if (dataList != null && dataList.size() > 0)
            return dataList.size();
        else
            return 0;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private ImageView photoView;
        private TextView photoTitle;
        private ImageView closeImageIcon;
        
        private RadioButton optionOne, optionTwo, optionThree;
        private TextView optionsTitle;
        
        private EditText commentBox;
        private Switch commentSwitch;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            photoView = itemView.findViewById(R.id.photo_id);
            photoTitle = itemView.findViewById(R.id.photo_title_id);
            closeImageIcon = itemView.findViewById(R.id.close_photo_id);
            
            optionOne = itemView.findViewById(R.id.option_one_id);
            optionTwo = itemView.findViewById(R.id.option_two_id);
            optionThree = itemView.findViewById(R.id.option_three_id);
            optionsTitle = itemView.findViewById(R.id.options_title_id);
            
            commentBox = itemView.findViewById(R.id.comment_box_et_id);
            commentSwitch = itemView.findViewById(R.id.comment_switch_id);
        }
    }
    
    public void setImageInItem(int position, String imageFilePath) {
        DataModel dataModel = dataList.get(position);
        dataModel.setImageFilePath(imageFilePath);
        notifyDataSetChanged();
    }
}

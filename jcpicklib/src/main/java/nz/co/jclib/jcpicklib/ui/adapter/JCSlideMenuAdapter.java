package nz.co.jclib.jcpicklib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCSlideMenuItem;

/**
 * Created by Johnnie on 19/04/17.
 */
public final class JCSlideMenuAdapter extends RecyclerView.Adapter {

    private ArrayList<String> itemTitles;
    private ArrayList<Integer> itemIcons;
    private int selectedMenuIndex;

    private SlideMenuItemClickedListener mListener;

    public JCSlideMenuAdapter(Context context, int selectedMenuIndex) {
        this.selectedMenuIndex = selectedMenuIndex;
        itemTitles = new ArrayList<>();
        itemIcons = new ArrayList<>();

        if(JCPickerClient.getDefaultInstance(context).isAllowSelectImage()){
            this.itemTitles.add(context.getResources().getString(R.string.jc_slide_menu_image_title));
            this.itemIcons.add(R.drawable.jcpick_no_image_icon);
        }

        if(JCPickerClient.getDefaultInstance(context).isAllowSelectDir()
                || JCPickerClient.getDefaultInstance(context).isAllowSelectFile())
        {
            boolean isSelectDirectoryOnly = JCPickerClient.getDefaultInstance(context).isAllowSelectDir()
                    && !JCPickerClient.getDefaultInstance(context).isAllowSelectFile();
            this.itemTitles.add(context.getResources().getString(isSelectDirectoryOnly ? R.string.jc_directory : R.string.jc_slide_menu_file_title));
            this.itemIcons.add(R.drawable.jcpick_no_file_icon);
        }

        if(this.itemTitles.size() == 1){
            this.selectedMenuIndex = 0;
        }
    }

    public void setListener(SlideMenuItemClickedListener Listener) {
        this.mListener = Listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_menu, parent, false);
        return new JCSlideMenuItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        JCSlideMenuItem viewHolder = (JCSlideMenuItem) holder;
        Context context = viewHolder.itemView.getContext();

        Picasso.with(context)
                .load(itemIcons.get(position))
                .fit()
                .centerInside()
                .into(viewHolder.ivIcon);

        viewHolder.txtTitle.setText(itemTitles.get(position));
        viewHolder.bottomDivider.setVisibility(position == itemTitles.size() - 1 ? View.GONE : View.VISIBLE);

        viewHolder.itemView.setBackgroundDrawable(context.getResources().getDrawable(selectedMenuIndex == position ?  R.drawable.selected_clickable_item_bg_color : R.drawable.clickable_item_bg_color));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedMenuIndex = position;
                if(mListener != null){
                    switch (position){
                        case 0:
                            mListener.onImagesClicked(position);
                            break;

                        case 1:
                            mListener.onFilesClicked(position);
                            break;
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTitles.size();
    }



    public interface SlideMenuItemClickedListener{
        void onImagesClicked(int selectedMenuIndex);
        void onFilesClicked(int selectedMenuIndex);
    }
}

package nz.co.jclib.jcpicklib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCSlideMenuItem;

/**
 * Created by Johnnie on 19/04/17.
 */
public final class JCSlideMenuAdapter extends RecyclerView.Adapter {

    private String[] itemTitles;
    private int[] itemIcons;
    private int selectedMenuIndex;

    private SlideMenuItemClickedListener mListener;

    public JCSlideMenuAdapter(Context context, int selectedMenuIndex) {
        this.selectedMenuIndex = selectedMenuIndex;
        this.itemTitles = new String[]{
                context.getResources().getString(R.string.jc_slide_menu_image_title),
                context.getResources().getString(R.string.jc_slide_menu_file_title)
        };

        this.itemIcons = new int[]{
            R.drawable.jcpick_no_image_icon,
            R.drawable.jcpick_no_file_icon
        };
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
                .load(itemIcons[position])
                .fit()
                .centerInside()
                .into(viewHolder.ivIcon);

        viewHolder.txtTitle.setText(itemTitles[position]);
        viewHolder.bottomDivider.setVisibility(position == itemTitles.length - 1 ? View.GONE : View.VISIBLE);

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTitles.length;
    }



    public interface SlideMenuItemClickedListener{
        void onImagesClicked(int selectedMenuIndex);
        void onFilesClicked(int selectedMenuIndex);
    }
}

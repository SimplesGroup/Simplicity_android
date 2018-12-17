package simplicity_an.simplicity_an.Tamil.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.Columnsdetailpage;
import simplicity_an.simplicity_an.EventsDescription;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.NewsDescription;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tamil.Activity.TamilEventsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TamilNewsDescription;
import simplicity_an.simplicity_an.Tamil.Tab_new_newstamil;

public class VerticalAdapterstamil extends RecyclerView.Adapter<VerticalAdapterstamil.Userview> {
    List<Tab_new_newstamil.ItemModel> modelList=new ArrayList<>();
    Context conxt;
    public VerticalAdapterstamil(List<Tab_new_newstamil.ItemModel> students, RecyclerView recyclerView, Context context) {
        modelList = students;
        conxt=context;

    }
    @NonNull
    @Override
    public Userview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        Userview userview=new Userview(inflater.inflate(R.layout.horizontal_small_design,parent,false));
        return userview;
    }

    @Override
    public void onBindViewHolder(@NonNull Userview holder, int position) {
        final Tab_new_newstamil.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);

        ImageLoader imageLoader= MySingleton.getInstance(conxt).getImageLoader();
        holder.title.setText(Html.fromHtml(model.getTitle()));
        holder.title.setTypeface(seguiregular);
        holder.imageView.setImageUrl(model.getImage(),imageLoader);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getSubqueuetitle().equals("Special Column")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                   conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                  conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, TamilNewsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.startActivity(intent);
                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getSubqueuetitle().equals("Special Column")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                    conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, TamilNewsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.startActivity(intent);
                }
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getSubqueuetitle().equals("Special Column")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                    conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, TamilNewsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Userview extends RecyclerView.ViewHolder{
    NetworkImageView imageView;
    TextView title;
    RelativeLayout layout;
        public Userview(View itemView) {
            super(itemView);
            imageView=(NetworkImageView)itemView.findViewById(R.id.image_small_design);
            title=(TextView)itemView.findViewById(R.id.item_title_small_design);
            layout=(RelativeLayout)itemView.findViewById(R.id.listlayout_taball);
        }
    }
}

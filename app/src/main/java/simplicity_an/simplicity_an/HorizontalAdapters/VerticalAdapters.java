package simplicity_an.simplicity_an.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class VerticalAdapters extends RecyclerView.Adapter<VerticalAdapters.Userview> {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    public static final String backgroundcolor = "color";
    String colorcodes;
    List<Tab_new_news.ItemModel> modelList=new ArrayList<>();
    Context conxt;
    public VerticalAdapters(List<Tab_new_news.ItemModel> students, RecyclerView recyclerView, Context context) {
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

        sharedpreferences = conxt. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        String simplycity_title_sans = "fonts/Oxygen-Regular.ttf";
        final Typeface sansfrancisco = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_sans);

        final Tab_new_news.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);

        ImageLoader imageLoader= MySingleton.getInstance(conxt).getImageLoader();

        if(colorcodes.equals("#FFFFFFFF")){
            holder.title.setTextColor(Color.BLACK);
        }else {
            holder.title.setTextColor(Color.WHITE);
        }


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
                }else if(model.getSubqueuetitle().equals("Today's Events in Coimbatore")){
                    Intent intent = new Intent(conxt, EventsDescription.class);
                    intent.putExtra("ID", model.getId());
                  conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, NewsDescription.class);
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
                }else if(model.getSubqueuetitle().equals("Today's Events in Coimbatore")){
                    Intent intent = new Intent(conxt, EventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, NewsDescription.class);
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
                }else if(model.getSubqueuetitle().equals("Today's Events in Coimbatore")){
                    Intent intent = new Intent(conxt, EventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")){
                    Intent intent = new Intent(conxt, NewsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.startActivity(intent);
                }
            }
        });
        if(fontname.equals("sanfrancisco")){
            holder.title.setTypeface(sansfrancisco);
            holder.title.setTextSize(15);
        }
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

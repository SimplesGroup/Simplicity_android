package simplicity_an.simplicity_an.Tamil.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.PhotoStoriesDetail;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tamil.Tab_new_newstamil;

public class HorizontalPhotostoryadaptertamil extends RecyclerView.Adapter<HorizontalPhotostoryadaptertamil.Userview> {
    List<Tab_new_newstamil.ItemModel> modelList=new ArrayList<>();
    Context conxt;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    public static final String backgroundcolor = "color";
    String colorcodes;
    public HorizontalPhotostoryadaptertamil(List<Tab_new_newstamil.ItemModel> students, RecyclerView recyclerView, Context context) {
        modelList = students;
        conxt=context;

    }
    @NonNull
    @Override
    public Userview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        Userview userview=new Userview(layoutInflater.inflate(R.layout.horizontal_photo_item,parent,false));
        return userview;
    }

    @Override
    public void onBindViewHolder(@NonNull Userview holder, int position) {

        sharedpreferences = conxt. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        final Tab_new_newstamil.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);
        if(colorcodes.equals("#FFFFFFFF")){
            Log.e("horizontal",colorcodes);
            holder.textView.setTextColor(Color.BLACK);
        }else {
            holder.textView.setTextColor(Color.WHITE);
        }
        holder.textView.setText(Html.fromHtml(model.getTitle()));
        holder.textView.setTypeface(seguiregular);
        int j;
        String image;
        for (j = 0; j < model.getAlbum().size(); j++) {
            image = model.getAlbum().get(0);
            Picasso.with(conxt)
                    .load(image)
                    .into(holder.imageView);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photostory=new Intent(conxt,PhotoStoriesDetail.class);
                photostory.putExtra("Image", model.getId());
                photostory.putExtra("TITLE",model.getTitle());
                photostory.putExtra("DATE",model.getPdate());
               conxt. startActivity(photostory);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photostory=new Intent(conxt,PhotoStoriesDetail.class);
                photostory.putExtra("Image", model.getId());
                photostory.putExtra("TITLE",model.getTitle());
                photostory.putExtra("DATE",model.getPdate());
                conxt. startActivity(photostory);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Userview extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public Userview(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            textView=(TextView)itemView.findViewById(R.id.title);
        }
    }
}

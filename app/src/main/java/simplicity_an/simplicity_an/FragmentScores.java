package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuppusamy on 2/25/2016.
 */
public class FragmentScores extends Fragment {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=sportsscore&key=simples";
    private ProgressDialog pdialog;
    View view;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    private final String TAG_REQUEST = "MY_TAG";
    public FragmentScores() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_scores, container, false);
        lLayout = new LinearLayoutManager(getActivity());
        RequestQueue queue =  MySingleton.getInstance(getActivity()).
                getRequestQueue();

        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view_sportsscores);
        rView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rView.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(getActivity());

        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rcAdapter = new RecyclerViewAdapter(getActivity(), modelList);
        rView.setAdapter(rcAdapter);
        rView.addOnItemTouchListener(new Sports.RecyclerTouchListener(getActivity(), rView, new Sports.ClickListener() {
            public void onClick(View view, int position) {


             String bitmap = ((ItemModel) modelList.get(position)).getImages();
                String tourvalues = ((ItemModel) modelList.get(position)).getEnames();
                String catid = ((ItemModel) modelList.get(position)).getCats();
                String trophynames= ((ItemModel) modelList.get(position)).getTitles();
                String Inningsteam1=((ItemModel) modelList.get(position)).getCkf1s();
                String Inningsteam2=((ItemModel) modelList.get(position)).getCkf2s();
                String Inningsteam3=((ItemModel) modelList.get(position)).getCks1s();
                String Inningsteam4=((ItemModel) modelList.get(position)).getCks2s();
                String teamnameone=((ItemModel) modelList.get(position)).getTeam1names();
                String teamnametwo=((ItemModel) modelList.get(position)).getTeam2names();
                String venue=((ItemModel) modelList.get(position)).getVenues();
                String time=((ItemModel) modelList.get(position)).getTimes();
                String bestplayer=((ItemModel) modelList.get(position)).getBtplayers();
                String refree=((ItemModel) modelList.get(position)).getReferees();
                String edate=((ItemModel) modelList.get(position)).getEdates();
                String tennistotalone=((ItemModel) modelList.get(position)).getType2_tot1s();
                String tennistotaltwo=((ItemModel) modelList.get(position)).getType2_tot2s();
                String tennisteamone_setone=((ItemModel) modelList.get(position)).getS11s();
                String tennisteamone_settwo=((ItemModel) modelList.get(position)).getS21s();
                String tennisteamone_setthree=((ItemModel) modelList.get(position)).getS31s();
                String tennisteamone_setfour=((ItemModel) modelList.get(position)).getS41s();
                String tennisteamone_setfive=((ItemModel) modelList.get(position)).getS51s();

                String tennisteamtwo_setone=((ItemModel) modelList.get(position)).getS12s();
                String tennisteamtwo_settwo=((ItemModel) modelList.get(position)).getS22s();
                String tennisteamtwo_setthree=((ItemModel) modelList.get(position)).getS32s();
                String tennisteamtwo_setfour=((ItemModel) modelList.get(position)).getS42s();
                String tennisteamtwo_setfive=((ItemModel) modelList.get(position)).getS52s();

                String footteamone_total=((ItemModel) modelList.get(position)).getType1_tot1s();
                String footteamtwo_total=((ItemModel) modelList.get(position)).getType1_tot2s();
                String footteamone_fhalf=((ItemModel) modelList.get(position)).getFhalf1s();
                String footteamone_shalf=((ItemModel) modelList.get(position)).getFhalf2s();
                String footteamtwo_fhalf=((ItemModel) modelList.get(position)).getShalf1s();
                String footteamtwo_shalf=((ItemModel) modelList.get(position)).getShalf2s();

                //  Toast.makeText(getActivity(),description,Toast.LENGTH_SHORT).show();
                Intent fooddescription = new Intent(getActivity(), SportsScoreDetailPage.class);
               // fooddescription.putExtra("IMAGE", bitmap);
                fooddescription.putExtra("TITLESPORTS", catid);
                fooddescription.putExtra("TOUR", tourvalues);
                fooddescription.putExtra("TROPHY", trophynames);
                fooddescription.putExtra("TEAMSCOREONE", Inningsteam1);
                fooddescription.putExtra("TEAMSCOREONE_TEAMTWO", Inningsteam2);
                fooddescription.putExtra("TEAMSCORE_OTHER", Inningsteam3);
                fooddescription.putExtra("TEAMSCORE_OTHER_TWO", Inningsteam4);
                fooddescription.putExtra("TEAMNAMEONE", teamnameone);
                fooddescription.putExtra("TEAMNAMETWO", teamnametwo);
                fooddescription.putExtra("VENUE", venue);
                fooddescription.putExtra("TIME", time);
                fooddescription.putExtra("RREFREE", refree);
                fooddescription.putExtra("BESTPLAYER", bestplayer);
                fooddescription.putExtra("DATE", edate);
                fooddescription.putExtra("TENNISTEAMONETOTAL",tennistotalone );
                fooddescription.putExtra("TENNISTEAMTWOTOTAL",tennistotaltwo );
                fooddescription.putExtra("TENNISTEAMONE_SETONE",tennisteamone_setone );
                fooddescription.putExtra("TENNISTEAMONE_SETTWO",tennisteamone_settwo );
                fooddescription.putExtra("TENNISTEAMONE_SETTHREE",tennisteamone_setthree );
                fooddescription.putExtra("TENNISTEAMONE_SETFOUR",tennisteamone_setfour);
                fooddescription.putExtra("TENNISTEAMONE_SETFIVE",tennisteamone_setfive);

                fooddescription.putExtra("TENNISTEAMTWO_SETONE",tennisteamtwo_setone );
                fooddescription.putExtra("TENNISTEAMTWO_SETTWO",tennisteamtwo_settwo );
                fooddescription.putExtra("TENNISTEAMTWO_SETTHREE",tennisteamtwo_setthree );
                fooddescription.putExtra("TENNISTEAMTWO_SETFOUR",tennisteamtwo_setfour);
                fooddescription.putExtra("TENNISTEAMTWO_SETFIVE",tennisteamtwo_setfive);

                fooddescription.putExtra("FOOTTEAMONETOTAL",footteamone_total);
                fooddescription.putExtra("FOOTTEAMTWOTOTAL",footteamtwo_total);
                fooddescription.putExtra("FOOTTEAMONEFHALF",footteamone_fhalf);
                fooddescription.putExtra("FOOTTEAMONESHALF",footteamone_shalf);
                fooddescription.putExtra("FOOTTEAMTWOFHALF",footteamtwo_fhalf);
                fooddescription.putExtra("FOOTTEAMTWOSHALF",footteamtwo_shalf);


                startActivity(fooddescription);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    dissmissDialog();
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonreq.setTag(TAG_REQUEST);

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonreq);
        return view;
    }

    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);


                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
               String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
              model.setImages(image);
                String edate = obj.isNull("edate") ? null : obj
                        .getString("edate");
                model.setEdates(edate);
              //  model.setEdates(obj.getString("edate"));
                //int types = obj.isNull("type") ? null : obj
                      //  .getInt("type");
               // model.setTypeid(types);
              //model.setTypeid(obj.getInt("type"));
                String pdate = obj.isNull("pdate") ? null : obj
                        .getString("pdate");
                model.setPdate(pdate);
               // model.setPdate(obj.getString("pdate"));
                String title = obj.isNull("title") ? null : obj
                        .getString("title");
                model.setTitles(title);
                //model.setTitles(obj.getString("title"));
                String ename = obj.isNull("ename") ? null : obj
                        .getString("ename");
                model.setEnames(ename);
               // model.setEnames(obj.getString("ename"));
                String tour = obj.isNull("tour") ? null : obj
                        .getString("tour");
                model.setTours(tour);
               // model.setTours(obj.getString("tour"));
                String scat = obj.isNull("scat") ? null : obj
                        .getString("scat");
                model.setScats(scat);
                //model.setScats(obj.getString("scat"));
                String tnameone = obj.isNull("team1name") ? null : obj
                        .getString("team1name");
                model.setTeam1names(tnameone);
               // model.setTeam1names(obj.getString("team1name"));
                String tnametwo = obj.isNull("team2name") ? null : obj
                        .getString("team2name");
                model.setTeam2names(tnametwo);
                //model.setTeam2names(obj.getString("team2name"));
                String venue = obj.isNull("venue") ? null : obj
                        .getString("venue");
                model.setVenues(venue);
               // model.setVenues(obj.getString("venue"));
                String time = obj.isNull("time") ? null : obj
                        .getString("time");
                model.setTimes(time);
               // model.setTimes(obj.getString("time"));
                String refreee = obj.isNull("referee") ? null : obj
                        .getString("referee");
                model.setReferees(refreee);
               // model.setReferees(obj.getString("referee"));
                String bestplayer = obj.isNull("btplayer") ? null : obj
                        .getString("btplayer");
                model.setBtplayers(bestplayer);
              // model.setBtplayers(obj.getString("btplayer"));
                String teamonefirsthalf = obj.isNull("fhalf1") ? null : obj
                        .getString("fhalf1");
                model.setFhalf1s(teamonefirsthalf);
              // model.setFhalf1s(obj.getString("fhalf1"));
                String teamonesecondhalf = obj.isNull("fhalf2") ? null : obj
                        .getString("fhalf2");
                model.setFhalf2s(teamonesecondhalf);
              // model.setFhalf2s(obj.getString("fhalf2"));
                String teamtwofirsthalf = obj.isNull("shalf1") ? null : obj
                        .getString("shalf1");
                model.setShalf1s(teamtwofirsthalf);
              // model.setShalf1s(obj.getString("shalf1"));
                String teamtwosecondhalf = obj.isNull("shalf2") ? null : obj
                        .getString("shalf2");
                model.setShalf2s(teamtwosecondhalf);
             // model.setShalf2s(obj.getString("shalf2"));
                String totone = obj.isNull("type1_tot1") ? null : obj
                        .getString("type1_tot1");
                model.setType1_tot1s(totone);
               // model.setType1_tot1s(obj.getString("type1_tot1"));
                String tottwo = obj.isNull("type1_tot2") ? null : obj
                        .getString("type1_tot2");
                model.setType1_tot2s(tottwo);
               // model.setType1_tot2s(obj.getString("type1_tot2"));
                String set1 = obj.isNull("s11") ? null : obj
                        .getString("s11");
                model.setS11s(set1);
               // model.setS11s(obj.getString("s11"));
                String set12 = obj.isNull("s12") ? null : obj
                        .getString("s12");
                model.setS12s(set12);
               // model.setS12s(obj.getString("s12"));
                String set21 = obj.isNull("s21") ? null : obj
                        .getString("s21");
                model.setS21s(set21);
                //model.setS21s(obj.getString("s21"));
                String set22 = obj.isNull("s22") ? null : obj
                        .getString("s22");
                model.setS22s(set22);
               // model.setS22s(obj.getString("s22"));
                String set31 = obj.isNull("s31") ? null : obj
                        .getString("s31");
                model.setS31s(set31);
                //model.setS31s(obj.getString("s31"));
                String set32 = obj.isNull("s32") ? null : obj
                        .getString("s32");
                model.setS32s(set32);
               // model.setS32s(obj.getString("s32"));
                String set41 = obj.isNull("s41") ? null : obj
                        .getString("s41");
                model.setS41s(set41);
               // model.setS41s(obj.getString("s41"));
                String set42 = obj.isNull("s42") ? null : obj
                        .getString("s42");
                model.setS42s(set42);
               // model.setS42s(obj.getString("s42"));
                String set51 = obj.isNull("s51") ? null : obj
                        .getString("s51");
                model.setS51s(set51);
                //model.setS51s(obj.getString("s51"));
                String set52 = obj.isNull("s52") ? null : obj
                        .getString("s52");
                model.setS52s(set52);
               // model.setS52s(obj.getString("s52"));
                String tenninstotone= obj.isNull("type2_tot1") ? null : obj
                        .getString("type2_tot1");
                model.setType2_tot1s(tenninstotone);
               // model.setType2_tot1s(obj.getString("type2_tot1"));
                String tenninstottwo= obj.isNull("type2_tot2") ? null : obj
                        .getString("type2_tot2");
                model.setType2_tot1s(tenninstottwo);
               // model.setType2_tot2s(obj.getString("type2_tot2"));
                String cricin1 = obj.isNull("ckf1") ? null : obj
                        .getString("ckf1");
                model.setCkf1s(cricin1);
                //model.setCkf1s(obj.getString("ckf1"));
                String cricin2 = obj.isNull("ckf2") ? null : obj
                        .getString("ckf2");
                model.setCkf2s(cricin2);
               // model.setCkf2s(obj.getString("ckf2"));
                String cricinfoi = obj.isNull("cks1") ? null : obj
                        .getString("cks1");
                model.setCks1s(cricinfoi);
                //model.setCkf1s(obj.getString("ckf1"));
                String cricinfo2 = obj.isNull("cks2") ? null : obj
                        .getString("cks2");
                model.setCks2s(cricinfo2);

               // model.setCko1s(obj.getString("cko1"));
               // model.setCko2s(obj.getString("cko2"));
              int qtypes = obj.isNull("qtype") ? null : obj
                        .getInt("qtype");
                model.setQtypes(qtypes);
                //model.setQtypes(obj.getInt("qtype"));
                String cat= obj.isNull("cat") ? null : obj
                        .getString("cat");
                model.setCats(cat);
               // model.setCats(obj.getString("cat"));
                modelList.add(model);
            }

            // notify data changes to list adapater
            rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class ItemModel{
        private int typeid;
        private int qtypes;
        private String enames;
        private String images;
        private String tours;
        private String pdates;
        private String edates;
        private String titles;
        private String scats;
        private String team1names;
        private String team2names;
        private String venues;
        private String times;
        private String referees;
        private String btplayers;
        private String fhalf1s;
        private String fhalf2s;
        private String shalf1s;
        private String shalf2s;
        private String type1_tot1s;
        private String type1_tot2s;
        private String s11s;
        private String s12s;
        private String s21s;
        private String s22s;
        private String s31s;
        private String s32s;
        private String s41s;
        private String s42s;
        private String s51s;
        private String s52s;
        private String type2_tot1s;
        private String type2_tot2s;

        private String ckf1s;
        private String ckf2s;
        private String cks1s;
        private String cks2s;
        private String cko1s;
        private String cko2s;
        private String cats;

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }
        public String getEnames() {
            return enames;
        }
        public void setEnames(String enames) {
            this.enames = enames;
        }
        public String getTours() {
            return tours;
        }
        public void setTours(String tours) {
            this.tours = tours;
        }
        public String getImages() {
            return images;
        }
        public void setImages(String images) {
            this.images = images;
        }
        public String getEdates(){return edates;}
        public  void setEdates(String edates){
            this.edates=edates;
        }
        public String getPdates(){return  pdates;}

        public void setPdate(String pdates) {
            this.pdates = pdates;
        }
        public String getTitles(){return  titles;}

        public void setTitles(String titles) {
            this.titles = titles;
        }
        public String getScats(){return  scats;}

        public void setScats(String scats) {
            this.scats = scats;
        }
        public String getTeam1names(){return team1names;}
        public void setTeam1names(String team1names){
            this.team1names=team1names;
        }
        /******** start the Food category names****/
        public String getTeam2names(){return  team2names;}

        public void setTeam2names(String team2names) {
            this.team2names = team2names;
        }

        public String getVenues() {
            return venues;
        }

        public void setVenues(String venues) {
            this.venues = venues;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getBtplayers() {
            return btplayers;
        }

        public void setBtplayers(String btplayers) {
            this.btplayers = btplayers;
        }

        public String getReferees() {
            return referees;
        }

        public void setReferees(String referees) {
            this.referees = referees;
        }

        public String getFhalf1s() {
            return fhalf1s;
        }

        public void setFhalf1s(String fhalf1s) {
            this.fhalf1s = fhalf1s;
        }

        public String getFhalf2s() {
            return fhalf2s;
        }

        public void setFhalf2s(String fhalf2s) {
            this.fhalf2s = fhalf2s;
        }

        public String getShalf1s() {
            return shalf1s;
        }

        public void setShalf1s(String shalf1s) {
            this.shalf1s = shalf1s;
        }

        public void setShalf2s(String shalf2s) {
            this.shalf2s = shalf2s;
        }

        public String getShalf2s() {
            return shalf2s;
        }

        public String getType1_tot1s() {
            return type1_tot1s;
        }

        public void setType1_tot1s(String type1_tot1s) {
            this.type1_tot1s = type1_tot1s;
        }

        public String getType1_tot2s() {
            return type1_tot2s;
        }

        public void setType1_tot2s(String type1_tot2s) {
            this.type1_tot2s = type1_tot2s;
        }

        public String getS11s() {
            return s11s;
        }

        public void setS11s(String s11s) {
            this.s11s = s11s;
        }

        public String getS12s() {
            return s12s;
        }

        public void setS12s(String s12s) {
            this.s12s = s12s;
        }

        public String getS21s() {
            return s21s;
        }

        public void setS21s(String s21s) {
            this.s21s = s21s;
        }

        public String getS22s() {
            return s22s;
        }

        public void setS22s(String s22s) {
            this.s22s = s22s;
        }

        public String getS31s() {
            return s31s;
        }

        public void setS31s(String s31s) {
            this.s31s = s31s;
        }

        public String getS32s() {
            return s32s;
        }

        public void setS32s(String s32s) {
            this.s32s = s32s;
        }

        public String getS41s() {
            return s41s;
        }

        public void setS41s(String s41s) {
            this.s41s = s41s;
        }

        public String getS42s() {
            return s42s;
        }

        public void setS42s(String s42s) {
            this.s42s = s42s;
        }

        public String getS51s() {
            return s51s;
        }

        public void setS51s(String s51s) {
            this.s51s = s51s;
        }

        public String getS52s() {
            return s52s;
        }

        public void setS52s(String s52s) {
            this.s52s = s52s;
        }

        public String getType2_tot1s() {
            return type2_tot1s;
        }

        public void setType2_tot1s(String type2_tot1s) {
            this.type2_tot1s = type2_tot1s;
        }

        public String getType2_tot2s() {
            return type2_tot2s;
        }

        public void setType2_tot2s(String type2_tot2s) {
            this.type2_tot2s = type2_tot2s;
        }

        public String getCkf1s() {
            return ckf1s;
        }

        public void setCkf1s(String ckf1s) {
            this.ckf1s = ckf1s;
        }

        public String getCkf2s() {
            return ckf2s;
        }

        public void setCkf2s(String ckf2s) {
            this.ckf2s = ckf2s;
        }

        public String getCks1s() {
            return cks1s;
        }

        public void setCks1s(String cks1s) {
            this.cks1s = cks1s;
        }

        public String getCks2s() {
            return cks2s;
        }

        public void setCks2s(String cks2s) {
            this.cks2s = cks2s;
        }

        public String getCko1s() {
            return cko1s;
        }

        public void setCko1s(String cko1s) {
            this.cko1s = cko1s;
        }

        public String getCko2s() {
            return cko2s;
        }

        public void setCko2s(String cko2s) {
            this.cko2s = cko2s;
        }

        public int getQtypes() {
            return qtypes;
        }

        public void setQtypes(int qtypes) {
            this.qtypes = qtypes;
        }

        public String getCats() {
            return cats;
        }

        public void setCats(String cats) {
            this.cats = cats;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
       // ImageLoader imageLoader = AppControllers.getInstance().getImageLoader();
        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_scores, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {


            ItemModel itemmodel=modelList.get(position);
            if (itemmodel.getQtypes()==0){
                holder.sportsname.setText(itemmodel.getCats());
                holder.trophynames.setText(itemmodel.getTitles());
                holder.teamname_first.setText(itemmodel.getTeam1names());
                holder.teamname_second.setText(itemmodel.getTeam2names());
                holder.arrowicon.setVisibility(View.VISIBLE);

                if(itemmodel.getCkf1s()!=null||itemmodel.getCkf2s()!=null){
                    String s1=itemmodel.getCkf1s();
                    String s2=itemmodel.getCkf2s();
                    String s3=itemmodel.getCks1s();
                    String s4=itemmodel.getCks2s();
                   // Toast.makeText(getActivity(),s1+""+s2,Toast.LENGTH_SHORT).show();
                    holder.teamname_score.setText(s1 + "&" + s2);
                    holder.teamname_second_score.setText(s3 + "&" + s4);

                }else if(itemmodel.getS11s()!=null||itemmodel.getS21s()!=null||itemmodel.getS31s()!=null||itemmodel.getS41s()!=null||itemmodel.getS51s()
                        !=null||itemmodel.getS12s()!=null||itemmodel.getS22s()!=null||itemmodel.getS32s()!=null||itemmodel.getS42s()!=null||itemmodel.getS52s()!=null){
                    String set1=itemmodel.getS11s();
                    String set2=itemmodel.getS21s();
                    String set3=itemmodel.getS31s();
                    String set4=itemmodel.getS41s();
                    String set5=itemmodel.getS51s();
                    String set12=itemmodel.getS12s();
                    String set22=itemmodel.getS22s();
                    String set32=itemmodel.getS32s();
                    String set42=itemmodel.getS42s();
                    String set52=itemmodel.getS52s();
                    final float scale = getResources().getDisplayMetrics().density;
                    int padding_5dp = (int) (5 * scale + 0.5f);
                    int padding_20dp = (int) (20 * scale + 0.5f);
                    int padding_50dp = (int) (50 * scale + 0.5f);

                    holder.teamname_score.setText(Html.fromHtml(set1+""+set2+""+set3+""+set4+""+set5));
                    holder.teamname_second_score.setText(Html.fromHtml(set12+""+set22+""+set32+""+set42+""+set52));

                }else if(itemmodel.getType1_tot1s()!=null||itemmodel.getType1_tot2s()!=null){
                    String teamonetotal=itemmodel.getType1_tot1s();
                    String teamtwototal=itemmodel.getType1_tot2s();
                    holder.teamname_score.setText(teamonetotal);
                    holder.teamname_second_score.setText(teamtwototal);

                }



          }else {
                holder.sportsname.setVisibility(View.GONE);
                if(itemmodel.getTitles()!=null) {
                    holder.trophynames.setText(itemmodel.getTitles());
                }else {
                    holder.trophynames.setText("Others");
                }
                holder.teamname_first.setText(itemmodel.getTeam1names());
                holder.teamname_second.setText(itemmodel.getTeam2names());

                holder.arrowicon.setVisibility(View.GONE);
                if(itemmodel.getCats().equals("Cricket")){
                    String s1=itemmodel.getCkf1s();
                    String s2=itemmodel.getCkf2s();
                    String s3=itemmodel.getCks1s();
                    String s4=itemmodel.getCks2s();
                    holder.teamname_score.setText(s1+"&"+s2);
                    holder.teamname_second_score.setText(s3+"&"+s4);

                }else if (itemmodel.getCats().equals("Tennis")){
                    String set1=itemmodel.getS11s();
                    String set2=itemmodel.getS21s();
                    String set3=itemmodel.getS31s();
                    String set4=itemmodel.getS41s();
                    String set5=itemmodel.getS51s();
                    String set12=itemmodel.getS12s();
                    String set22=itemmodel.getS22s();
                    String set32=itemmodel.getS32s();
                    String set42=itemmodel.getS42s();
                    String set52=itemmodel.getS52s();


                    holder.teamname_score.setText(Html.fromHtml(set1+""+set2+""+set3+""+set4+""+set5));
                   holder.teamname_second_score.setText(Html.fromHtml(set12+""+set22+""+set32+""+set42+""+set52));
                }else if (itemmodel.getCats().equals("Football")){
                    String teamonetotal=itemmodel.getType1_tot1s();
                    String teamtwototal=itemmodel.getType1_tot2s();
                   holder.teamname_score.setText(teamonetotal);
                   holder.teamname_second_score.setText(teamtwototal);
                }
            }

        }

        @Override
        public int getItemCount() {
            return this.modelList.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{

            public TextView sportsname;
            public TextView trophynames;
            public TextView teamname_first;
            public TextView teamname_second;
            public TextView teamname_score;
            public TextView teamname_second_score;


            public ImageView arrowicon;

            public RecyclerViewHolders(View itemView) {
                super(itemView);
                arrowicon = (ImageView) itemView.findViewById(R.id.imageView_arrrow);
                sportsname = (TextView) itemView.findViewById(R.id.sports_category_nameslist);
                trophynames = (TextView)itemView
                        .findViewById(R.id.title_trophy_names);
                teamname_first=(TextView)itemView.findViewById(R.id.teamnameone);
                // date.setPadding(7, 7, 7, 7);
                teamname_second=(TextView)itemView.findViewById(R.id.teamnametwo);
                teamname_score=(TextView)itemView.findViewById(R.id.teamnameone_score);
                teamname_second_score=(TextView)itemView.findViewById(R.id.teamnametwo_score);

            }
        }
    }


}

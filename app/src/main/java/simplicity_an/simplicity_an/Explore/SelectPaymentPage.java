package simplicity_an.simplicity_an.Explore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.PaymentGateway.CardTypeDTO;
import simplicity_an.simplicity_an.PaymentGateway.PayOptAdapter;
import simplicity_an.simplicity_an.PaymentGateway.PaymentOptionDTO;
import simplicity_an.simplicity_an.PaymentGateway.WebViewActivity;
import simplicity_an.simplicity_an.PaymentGateway.utility.AvenuesParams;
import simplicity_an.simplicity_an.PaymentGateway.utility.Constants;
import simplicity_an.simplicity_an.PaymentGateway.utility.ServiceHandler;
import simplicity_an.simplicity_an.PaymentGateway.utility.ServiceUtility;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/16/2017.
 */
public class SelectPaymentPage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USEREMAIL= "myprofileemail";
    public static final String USERMAILID= "myprofileemail";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts,my_profilename,my_profileimage,my_profileemail;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;
    RequestQueue requestQueue;
    TextView title_textview,back_tocart_textview,deliverytype_textview,totalitems_textview,deliverycharges_textview,place_order_textview;
    TextView total_textview,totalitems_count_textview,delivery_ount_textview,total_pricevalue_textview,payment_title,payment_gateway_textview;
    String URL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=checkout&user_id=";
    String URLFULL;
TextView select_payment_option_title;
/**** ccavenue credentials**/
    ArrayList<PaymentOptionDTO> payOptionList = new ArrayList<PaymentOptionDTO>();
    String selectedPaymentOption;
    private Map<String, String> paymentOptions = new LinkedHashMap<String, String>();
    Map<String, ArrayList<CardTypeDTO>> cardsList = new LinkedHashMap<String, ArrayList<CardTypeDTO>>();

    int counter;
    private JSONObject jsonRespObj;

    ProgressDialog pDialog;
    String vAccessCode, vMerchantId, vCurrency, vAmount,email;

   /**** ccavenue credentials**/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_select_payment);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        String simplycity_title_fontPath_1 = "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath_1);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        final Intent get=getIntent();
        String price=get.getStringExtra("TOTALPRICE");
        String id=get.getStringExtra("ID");
        final String address=get.getStringExtra("ADDRESS");
        final String name=get.getStringExtra("NAME");
        final String pincode=get.getStringExtra("PIN");
        final String phonenumber=get.getStringExtra("PHONE");
         email=get.getStringExtra("EMAIL");

        String city=get.getStringExtra("CITY");
        String state=get.getStringExtra("STATE");
if(id!=null){
    Log.e("IDS",id+email+price);
}
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }
        Log.e("Msg", "hihihi");


            my_profileemail=sharedpreferences.getString(USERMAILID,"");

vAmount=price;


            URLFULL=URL+myprofileid;
        new GetData().execute();

        getData();
        requestQueue= Volley.newRequestQueue(this);
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        if(colorcodes.length()==0){

        }else {
            if (colorcodes.equalsIgnoreCase("004")) {
                Log.e("Msg", "hihihi");
            } else {

                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
            }
        }
        title_textview=(TextView)findViewById(R.id.city_title);
        title_textview.setTypeface(tf);
        title_textview.setText("Shipping & Delivery");
        deliverytype_textview=(TextView)findViewById(R.id.explore_user_delivery_type);
        deliverytype_textview.setText("Standard Delivery");
        deliverytype_textview.setTypeface(tf1);
        back_tocart_textview=(TextView)findViewById(R.id.your_delivery_backtocart);
        totalitems_textview=(TextView)findViewById(R.id.explore_user_delivery_totalitems);
       deliverycharges_textview=(TextView)findViewById(R.id.explore_user_delivery_address_Delivarycharges);
        totalitems_count_textview=(TextView)findViewById(R.id.explore_user_delivery_totalitems_value);
        delivery_ount_textview=(TextView)findViewById(R.id.explore_user_delivery_address_Delivarycharges_value);
        total_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal);
        total_pricevalue_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal_value);
        payment_title=(TextView) findViewById(R.id.explore_user_delivery_payment_title);
        payment_gateway_textview=(TextView)findViewById(R.id.explore_user_delivery_payment_gateway) ;
        place_order_textview=(TextView)findViewById(R.id.your_delivery_placeorder) ;
select_payment_option_title=(TextView)findViewById(R.id.explore_user_delivery_payment_option) ;

        back_tocart_textview.setText("BACK TO CART");
        back_tocart_textview.setTypeface(tf);
        totalitems_count_textview.setTypeface(tf);
        totalitems_textview.setTypeface(tf);

        deliverycharges_textview.setTypeface(tf);
        deliverycharges_textview.setText("Delivery charges");
        delivery_ount_textview.setTypeface(tf);
        total_textview.setText("Total");
        total_textview.setTypeface(tf);
        total_pricevalue_textview.setTypeface(tf);
        place_order_textview.setTypeface(tf);
        place_order_textview.setText("");
        payment_title.setText("Payment");
        payment_title.setTypeface(tf);
        payment_gateway_textview.setTypeface(tf);
        payment_gateway_textview.setText("Pay with CCAVENUE");
        place_order_textview.setText("Place Order");
select_payment_option_title.setText("Select Payment Option");
        select_payment_option_title.setTypeface(tf);
        back_tocart_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final Integer randomNum = ServiceUtility.randInt(0, 9999999);

        vAccessCode="AVRU68DK07CK80URKC";
        vMerchantId="116527";
        vCurrency="INR";
place_order_textview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!paymentOptions.equals("")) {
            if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra(AvenuesParams.ACCESS_CODE, vAccessCode);
                intent.putExtra(AvenuesParams.MERCHANT_ID, vMerchantId);
                intent.putExtra(AvenuesParams.ORDER_ID, randomNum.toString().trim());
                intent.putExtra(AvenuesParams.CURRENCY, vCurrency);
                intent.putExtra(AvenuesParams.AMOUNT, vAmount);

                intent.putExtra(AvenuesParams.REDIRECT_URL, Constants.redirectUrl);
                intent.putExtra(AvenuesParams.CANCEL_URL, Constants.cancelUrl);
                intent.putExtra(AvenuesParams.RSA_KEY_URL, Constants.rsaKeyUrl);

                intent.putExtra(AvenuesParams.BILLING_NAME, name);
                intent.putExtra(AvenuesParams.BILLING_ADDRESS, address);
                intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
                intent.putExtra(AvenuesParams.BILLING_STATE, "TamilNadu");
                intent.putExtra(AvenuesParams.BILLING_CITY, "CoimBatore");
                intent.putExtra(AvenuesParams.BILLING_ZIP, pincode);
                intent.putExtra(AvenuesParams.BILLING_TEL, phonenumber);
                intent.putExtra(AvenuesParams.BILLING_EMAIL, email);
                intent.putExtra(AvenuesParams.DELIVERY_NAME, name);
                intent.putExtra(AvenuesParams.DELIVERY_ADDRESS, address);
                intent.putExtra(AvenuesParams.DELIVERY_COUNTRY, "India");
                intent.putExtra(AvenuesParams.DELIVERY_STATE, "TamilNadu");
                intent.putExtra(AvenuesParams.DELIVERY_CITY, "CoimBatore");
                intent.putExtra(AvenuesParams.DELIVERY_ZIP, pincode);
                intent.putExtra(AvenuesParams.DELIVERY_TEL, phonenumber);
                intent.putExtra(AvenuesParams.PAYMENT_OPTION, selectedPaymentOption);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "All parameters are mandatory.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Select Payment Option.", Toast.LENGTH_SHORT).show();
        }
    }
});

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData(){
        JsonObjectRequest edit_request=new JsonObjectRequest(Request.Method.GET, URLFULL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray reslutarray=response.getJSONArray("result");
                    for (int i=0;i<reslutarray.length();i++){
                        UPDATEITEM updateitem=new UPDATEITEM();
                        JSONObject object=(JSONObject)reslutarray.get(i);
                        updateitem.setTotalitems(object.getInt("items"));
                        updateitem.setTotal(object.getInt("total"));
                        updateitem.setSubtotal(object.getInt("subtotal"));
                       updateitem.setShippingcharge(object.getInt("shipping"));

                        totalitems_textview.setText(String.valueOf(object.getInt("items"))+" Items");
                        totalitems_count_textview.setText(String.valueOf(object.getInt("subtotal")));
                        delivery_ount_textview.setText(String.valueOf(object.getInt("shipping")));
                        total_pricevalue_textview.setText(String.valueOf(object.getInt("total")));
//vAmount=String.valueOf(object.getInt("total"));
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        edit_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(edit_request);
    }
   class UPDATEITEM{
       int shippingcharge,total,totalitems,subtotal;

       public int getShippingcharge() {
           return shippingcharge;
       }

       public void setShippingcharge(int shippingcharge) {
           this.shippingcharge = shippingcharge;
       }

       public int getSubtotal() {
           return subtotal;
       }

       public void setSubtotal(int subtotal) {
           this.subtotal = subtotal;
       }

       public int getTotal() {
           return total;
       }

       public void setTotal(int total) {
           this.total = total;
       }

       public int getTotalitems() {
           return totalitems;
       }

       public void setTotalitems(int totalitems) {
           this.totalitems = totalitems;
       }

   }
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
			/*pDialog = new ProgressDialog(getApplicationContext());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            List<NameValuePair> vParams = new ArrayList<NameValuePair>();
            vParams.add(new BasicNameValuePair(AvenuesParams.COMMAND, "getJsonDataVault"));
            vParams.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE, "AVRU68DK07CK80URKC"));
            vParams.add(new BasicNameValuePair(AvenuesParams.CURRENCY, "INR"));
            vParams.add(new BasicNameValuePair(AvenuesParams.AMOUNT, "1"));
            vParams.add(new BasicNameValuePair(AvenuesParams.CUSTOMER_IDENTIFIER, "simplicity"));

            String vJsonStr = sh.makeServiceCall(Constants.JSON_URL, ServiceHandler.POST, vParams);
            Log.e("Response: ", "> " + vJsonStr);
            Log.d("Response: ", "> " + vJsonStr);

            if (vJsonStr != null && !vJsonStr.equals("")) {
                try {
                    jsonRespObj = new JSONObject(vJsonStr);
                    if (jsonRespObj != null) {
                        if (jsonRespObj.getString("payOptions") != null) {
                            JSONArray vPayOptsArr = new JSONArray(jsonRespObj.getString("payOptions"));
                            for (int i = 0; i < vPayOptsArr.length(); i++) {
                                JSONObject vPaymentOption = vPayOptsArr.getJSONObject(i);
                                if (vPaymentOption.getString("payOpt").equals("OPTIVRS")) continue;
                                payOptionList.add(new PaymentOptionDTO(vPaymentOption.getString("payOpt"), vPaymentOption.getString("payOptDesc").toString()));//Add payment option only if it includes any card
                                paymentOptions.put(vPaymentOption.getString("payOpt"), vPaymentOption.getString("payOptDesc"));
                                try {
                                    JSONArray vCardArr = new JSONArray(vPaymentOption.getString("cardsList"));
                                    if (vCardArr.length() > 0) {
                                        cardsList.put(vPaymentOption.getString("payOpt"), new ArrayList<CardTypeDTO>()); //Add a new Arraylist
                                        for (int j = 0; j < vCardArr.length(); j++) {
                                            JSONObject card = vCardArr.getJSONObject(j);
                                            try {
                                                CardTypeDTO cardTypeDTO = new CardTypeDTO();
                                                cardTypeDTO.setCardName(card.getString("cardName"));
                                                cardTypeDTO.setCardType(card.getString("cardType"));
                                                cardTypeDTO.setPayOptType(card.getString("payOptType"));
                                                cardTypeDTO.setDataAcceptedAt(card.getString("dataAcceptedAt"));
                                                cardTypeDTO.setStatus(card.getString("status"));

                                                cardsList.get(vPaymentOption.getString("payOpt")).add(cardTypeDTO);
                                            } catch (Exception e) {
                                                Log.e("ServiceHandler", "Error parsing cardType", e);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("ServiceHandler", "Error parsing payment option", e);
                                }
                            }
                        }
                       /* if ((jsonRespObj.getString("EmiBanks") != null && jsonRespObj.getString("EmiBanks").length() > 0) &&
                                (jsonRespObj.getString("EmiPlans") != null && jsonRespObj.getString("EmiPlans").length() > 0)) {
                            paymentOptions.put("OPTEMI", "Credit Card EMI");
                            payOptionList.add(new PaymentOptionDTO("OPTEMI", "Credit Card EMI"));
                        }*/
                    }
                } catch (JSONException e) {
                    Log.e("ServiceHandler", "Error fetching data from server", e);
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
			/*if (pDialog.isShowing())
				pDialog.dismiss();*/

            // bind adapter to spinner
            final Spinner payOpt = (Spinner) findViewById(R.id.explore_payment_option_spinnner);
            PayOptAdapter payOptAdapter = new PayOptAdapter(SelectPaymentPage.this, android.R.layout.simple_spinner_item, payOptionList);
            payOpt.setAdapter(payOptAdapter);
            payOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //((LinearLayout) findViewById(R.id.vCardCVVCont)).setVisibility(View.GONE);

                    selectedPaymentOption = payOptionList.get(position).getPayOptId();
                    String vCustPayments = null;
                    try {
                        vCustPayments = jsonRespObj.getString("CustPayments");
                    } catch (Exception e) {
                    }
                    if (counter != 0 || vCustPayments == null) {
                        //LinearLayout ll = (LinearLayout) findViewById(R.id.cardDetails);
                        if (selectedPaymentOption.equals("OPTDBCRD") ||
                                selectedPaymentOption.equals("OPTCRDC")) {
                            //ll.setVisibility(View.VISIBLE);
                        } else {
                            //	ll.setVisibility(View.GONE);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //set a listener for selected items in the spinner

        }
    }
}

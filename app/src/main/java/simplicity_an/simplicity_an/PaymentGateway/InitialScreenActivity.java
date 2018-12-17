package simplicity_an.simplicity_an.PaymentGateway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.PaymentGateway.utility.AvenuesParams;
import simplicity_an.simplicity_an.PaymentGateway.utility.Constants;
import simplicity_an.simplicity_an.PaymentGateway.utility.ServiceHandler;
import simplicity_an.simplicity_an.PaymentGateway.utility.ServiceUtility;
import simplicity_an.simplicity_an.R;

public class InitialScreenActivity extends Activity {
	ArrayList<PaymentOptionDTO> payOptionList = new ArrayList<PaymentOptionDTO>();
	String selectedPaymentOption;
	private Map<String, String> paymentOptions = new LinkedHashMap<String, String>();
	Map<String, ArrayList<CardTypeDTO>> cardsList = new LinkedHashMap<String, ArrayList<CardTypeDTO>>();

	int counter;
	private JSONObject jsonRespObj;
	private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;
	ProgressDialog pDialog;
	String vAccessCode, vMerchantId, vCurrency, vAmount;

	private void init() {

		accessCode = (EditText) findViewById(R.id.accessCode);
		merchantId = (EditText) findViewById(R.id.merchantId);
		orderId = (EditText) findViewById(R.id.orderId);
		currency = (EditText) findViewById(R.id.currency);
		amount = (EditText) findViewById(R.id.amount);
		rsaKeyUrl = (EditText) findViewById(R.id.rsaUrl);
		redirectUrl = (EditText) findViewById(R.id.redirectUrl);
		cancelUrl = (EditText) findViewById(R.id.cancelUrl);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial_screen);
		init();

		//generating order number
		Integer randomNum = ServiceUtility.randInt(0, 9999999);
		orderId.setText(randomNum.toString());
		vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
		vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
		vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
		vAmount = ServiceUtility.chkNull(amount.getText()).toString().trim();
		new GetData().execute();
	}

	public void onClick(View view) {
		//Mandatory parameters. Other parameters can be added if required.

		if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
			intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
			intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount.getText()).toString().trim());

			intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl.getText()).toString().trim());

			intent.putExtra(AvenuesParams.BILLING_NAME, "kuppusamy");
			intent.putExtra(AvenuesParams.BILLING_ADDRESS, "simple solutions" + "2nd Floor, Sasha Building," + "East Venkataswamy Road, RS Puram, ");
			intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
			intent.putExtra(AvenuesParams.BILLING_STATE, "TamilNadu");
			intent.putExtra(AvenuesParams.BILLING_CITY, "CoimBatore");
			intent.putExtra(AvenuesParams.BILLING_ZIP, "641002");
			intent.putExtra(AvenuesParams.BILLING_TEL, "8526206028");
			intent.putExtra(AvenuesParams.BILLING_EMAIL, "kuppusamykaliyappan@gmail.com");
			intent.putExtra(AvenuesParams.DELIVERY_NAME, "kuppusamy");
			intent.putExtra(AvenuesParams.DELIVERY_ADDRESS, "simple solutions" + "2nd Floor, Sasha Building," + "East Venkataswamy Road, RS Puram, ");
			intent.putExtra(AvenuesParams.DELIVERY_COUNTRY, "India");
			intent.putExtra(AvenuesParams.DELIVERY_STATE, "TamilNadu");
			intent.putExtra(AvenuesParams.DELIVERY_CITY, "CoimBatore");
			intent.putExtra(AvenuesParams.DELIVERY_ZIP, "641002");
			intent.putExtra(AvenuesParams.DELIVERY_TEL, "8526206028");
			intent.putExtra(AvenuesParams.PAYMENT_OPTION, selectedPaymentOption);
			startActivity(intent);
		} else {
			showToast("All parameters are mandatory.");
		}
	}

	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
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
			vParams.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE, vAccessCode));
			vParams.add(new BasicNameValuePair(AvenuesParams.CURRENCY, vCurrency));
			vParams.add(new BasicNameValuePair(AvenuesParams.AMOUNT, vAmount));
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
						if ((jsonRespObj.getString("EmiBanks") != null && jsonRespObj.getString("EmiBanks").length() > 0) &&
								(jsonRespObj.getString("EmiPlans") != null && jsonRespObj.getString("EmiPlans").length() > 0)) {
							paymentOptions.put("OPTEMI", "Credit Card EMI");
							payOptionList.add(new PaymentOptionDTO("OPTEMI", "Credit Card EMI"));
						}
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
			final Spinner payOpt = (Spinner) findViewById(R.id.payopt);
			PayOptAdapter payOptAdapter = new PayOptAdapter(InitialScreenActivity.this, android.R.layout.simple_spinner_item, payOptionList);
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
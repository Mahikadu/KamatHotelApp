package com.example.admin.kamathotelapp.libs;

import android.content.Context;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAPWebservice {

	Context context;


	String url = "http://114.79.142.72:82/khil_wcf/service1.svc";


	public SOAPWebservice(Context con) {
		context = con;
	}

	// soap service for login
	public SoapObject loginWebservice(String username, String os,String deviceID,
										 String source,String password,int versionCode,String version) {
		SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Get_login");// soap object
			request.addProperty("userid", username);
			request.addProperty("os", os);
			request.addProperty("device_id", deviceID);
			request.addProperty("source", source);
			request.addProperty("password", password);
			request.addProperty("build_version_code", versionCode);
			request.addProperty("version", version);


			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/Get_login",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result Login", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public SoapObject MasterSyncservice(String id, String date,
										 String userid) {
        SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Get_Masters");// soap object
			request.addProperty("Id", id);
			request.addProperty("date", date);
			request.addProperty("userid", userid);



			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/Get_Masters",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result master", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}



}

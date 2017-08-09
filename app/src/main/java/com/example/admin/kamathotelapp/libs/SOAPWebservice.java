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
	public SoapPrimitive loginWebservice(String id, String username,
										 String password) {
		SoapPrimitive result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Get_login");// soap object
			request.addProperty("userid", username);
			request.addProperty("os", password);
			request.addProperty("device_id", password);
			request.addProperty("source", password);
			request.addProperty("password", password);
			request.addProperty("build_version_code", password);
			request.addProperty("version", password);


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
			result = (SoapPrimitive) envelope.getResponse();
			Log.e("result Login", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public SoapPrimitive MasterSyncservice(String id, String username,
										 String password) {
		SoapPrimitive result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Get_Masters");// soap object
			request.addProperty("Id", username);
			request.addProperty("date", password);
			request.addProperty("userid", password);



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
			result = (SoapPrimitive) envelope.getResponse();
			Log.e("result master", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}



}

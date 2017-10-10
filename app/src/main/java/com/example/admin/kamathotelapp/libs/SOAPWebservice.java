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


	//String url = "http://114.79.142.72:82/khil_wcf/service1.svc";

	String url = "http://192.168.0.154/khilwcf/service1.svc";


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
										 String userid, String roleId) {
        SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Get_Masters");// soap object
			request.addProperty("Id", id);
			request.addProperty("date", date);
			request.addProperty("userid", userid);
			request.addProperty("roleid", roleId);



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

	// TODO: insert upload web service
	public SoapPrimitive Insert_upload(String Role_Id, String Legal_Entity_Id,
								   String Property_Id, String Location_Id, String Document_No,
								   String Created_By, String Updated_By, String Created_Date, String Updated_Date,
								   String Individuals_Id, String NewProposal, String Trans_Id, String Level2_Id, String Level3_Id,
								   String Level4_Id, String Level5_Id, String Level6_Id, String Level7_Id, String Year, String Quarter,
								   String Month, String File_Name, String File_Path, String File_Path_File_Name, String File_Exten,String base64String) {
		SoapPrimitive result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Insert_Upload");// soap object
			request.addProperty("Role_Id", Role_Id);
			request.addProperty("Legal_Entity_Id", Legal_Entity_Id);
			request.addProperty("Property_Id", Property_Id);
			request.addProperty("Location_Id", Location_Id);
			request.addProperty("Document_No", Document_No);
			request.addProperty("Created_By", Created_By);
			request.addProperty("Updated_By", Updated_By);
			request.addProperty("Created_Date", Created_Date);
			request.addProperty("Updated_Date", Updated_Date);
			request.addProperty("Individuals_Id", Individuals_Id);
			request.addProperty("NewProposal", NewProposal);
			request.addProperty("Trans_Id", Trans_Id);
			request.addProperty("Level2_Id", Level2_Id);
			request.addProperty("Level3_Id", Level3_Id);
			request.addProperty("Level4_Id", Level4_Id);
			request.addProperty("Level5_Id", Level5_Id);
			request.addProperty("Level6_Id", Level6_Id);
			request.addProperty("Level7_Id", Level7_Id);
			request.addProperty("Year", Year);
			request.addProperty("Quarter", Quarter);
			request.addProperty("Month", Month);
			request.addProperty("File_Name", File_Name);
			request.addProperty("File_Path", File_Path);
			request.addProperty("File_Path_File_Name", File_Path_File_Name);
			request.addProperty("File_Exten", File_Exten);
			request.addProperty("base64String", base64String);


			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/Insert_Upload",
					envelope);

			// response soap object
			result = (SoapPrimitive) envelope.getResponse();
			Log.e("result Insert_Upload", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	// DashboardData
	public SoapObject Dashboarddata(String username,String userid) {
		SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"Dashboarddata");// soap object
			request.addProperty("Param", username);
			request.addProperty("Id", userid);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/Dashboarddata",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public SoapObject chartdata(String EmpCode, String item) {
		SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"chartdata");// soap object
			request.addProperty("EmpCode", EmpCode);
			request.addProperty("item", item);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/chartdata",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	// DashboardData
	public SoapObject QC1_Grid_APK(String userid) {
		SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"QC1_Grid_APK");// soap object
			request.addProperty("created_by", userid);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/QC1_Grid_APK",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

    public SoapPrimitive Update_Trans(String Role_Id, String Legal_Entity_Id,
                                       String Property_Id, String Location_Id, String Document_No,
                                       String Created_By, String Updated_By, String Created_Date, String Updated_Date,
                                       String Individuals_Id, String NewProposal, String Trans_Id,String Year, String Quarter,
                                       String Month, String Status, String NewDocument_No) {
        SoapPrimitive result = null;

        try {
            SoapObject request = new SoapObject("http://tempuri.org/",
                    "Update_Trans");// soap object
            request.addProperty("Role_Id", Role_Id);
            request.addProperty("Legal_Entity_Id", Legal_Entity_Id);
            request.addProperty("Property_Id", Property_Id);
            request.addProperty("Location_Id", Location_Id);
            request.addProperty("Trans_Id", Trans_Id);
            request.addProperty("Created_By", Created_By);
            request.addProperty("Updated_By", Updated_By);
            request.addProperty("Created_Date", Created_Date);
            request.addProperty("Updated_Date", Updated_Date);
            request.addProperty("Year", Year);
            request.addProperty("Quarter", Quarter);
            request.addProperty("Month", Month);
            request.addProperty("Status", Status);
            request.addProperty("Document_No", Document_No);
            request.addProperty("NewDocument_No", NewDocument_No);
            request.addProperty("Individuals_Id", Individuals_Id);
            request.addProperty("NewProposal", NewProposal);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// soap envelop with version
            envelope.setOutputSoapObject(request); // set request object
            envelope.dotNet = true;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
            // transport
            // call
            androidHttpTransport.call("http://tempuri.org/IService1/Update_Trans",
                    envelope);

            // response soap object
            result = (SoapPrimitive) envelope.getResponse();
            Log.e("result Update Trans", result.toString());
            return result;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public SoapPrimitive Save_Trans_Dtls(String Role_Id, String Legal_Entity_Id,
                                       String Property_Id, String Location_Id, String Document_No,
                                       String Created_By, String Updated_By, String Created_Date, String Updated_Date,
                                       String Individuals_Id, String NewProposal, String Trans_Id, String Level2_Id, String Level3_Id,
                                       String Level4_Id, String Level5_Id, String Level6_Id, String Level7_Id, String Year, String Quarter,
                                       String Month, String File_Name, String File_Path, String File_Path_File_Name, String File_Exten,String Status,
                                         String NewDocument_No) {
        SoapPrimitive result = null;

        try {
            SoapObject request = new SoapObject("http://tempuri.org/",
                    "Save_Trans_Dtls");// soap object

            request.addProperty("Trans_Id", Trans_Id);
            request.addProperty("Level2_Id", Level2_Id);
            request.addProperty("Level3_Id", Level3_Id);
            request.addProperty("Level4_Id", Level4_Id);
            request.addProperty("Level5_Id", Level5_Id);
            request.addProperty("Level6_Id", Level6_Id);
            request.addProperty("Level7_Id", Level7_Id);
            request.addProperty("Year", Year);
            request.addProperty("Quarter", Quarter);
            request.addProperty("Month", Month);
            request.addProperty("File_Name", File_Name);
            request.addProperty("File_Path", File_Path);
            request.addProperty("File_Path_File_Name", File_Path_File_Name);
            request.addProperty("File_Exten", File_Exten);
            request.addProperty("Role_Id", Role_Id);
            request.addProperty("Legal_Entity_Id", Legal_Entity_Id);
            request.addProperty("Property_Id", Property_Id);
            request.addProperty("Location_Id", Location_Id);
            request.addProperty("Created_By", Created_By);
            request.addProperty("Updated_By", Updated_By);
            request.addProperty("Created_Date", Created_Date);
            request.addProperty("Updated_Date", Updated_Date);
            request.addProperty("Status", Status);
            request.addProperty("Document_No", Document_No);
            request.addProperty("NewDocument_No", NewDocument_No);
            request.addProperty("Individuals_Id", Individuals_Id);
            request.addProperty("NewProposal", NewProposal);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// soap envelop with version
            envelope.setOutputSoapObject(request); // set request object
            envelope.dotNet = true;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
            // transport
            // call
            androidHttpTransport.call("http://tempuri.org/IService1/Save_Trans_Dtls",
                    envelope);

            // response soap object
            result = (SoapPrimitive) envelope.getResponse();
            Log.e("result Save_Trans_Dtls", result.toString());
            return result;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public SoapPrimitive Edit_Details_QC1(String Role_Id, String Legal_Entity_Id,
                                         String Property_Id, String Location_Id, String Document_No,
                                         String Created_By, String Updated_By, String Created_Date, String Updated_Date,
                                         String Individuals_Id, String NewProposal, String Trans_Id, String Level2_Id, String Level3_Id,
                                         String Level4_Id, String Level5_Id, String Level6_Id, String Level7_Id, String Year, String Quarter,
                                         String Month, String File_Name, String File_Path, String File_Path_File_Name, String File_Exten,String Status,
                                         String NewDocument_No) {
        SoapPrimitive result = null;

        try {
            SoapObject request = new SoapObject("http://tempuri.org/",
                    "Edit_Details");// soap object

            request.addProperty("Trans_Id", Trans_Id);
            request.addProperty("Level2_Id", Level2_Id);
            request.addProperty("Level3_Id", Level3_Id);
            request.addProperty("Level4_Id", Level4_Id);
            request.addProperty("Level5_Id", Level5_Id);
            request.addProperty("Level6_Id", Level6_Id);
            request.addProperty("Level7_Id", Level7_Id);
            request.addProperty("Year", Year);
            request.addProperty("Quarter", Quarter);
            request.addProperty("Month", Month);
            request.addProperty("File_Name", File_Name);
			request.addProperty("Role_Id", Role_Id);
			request.addProperty("Legal_Entity_Id", Legal_Entity_Id);
			request.addProperty("Property_Id", Property_Id);
			request.addProperty("Location_Id", Location_Id);
			request.addProperty("Created_By", Created_By);
			request.addProperty("Updated_By", Updated_By);
			request.addProperty("Created_Date", Created_Date);
			request.addProperty("Updated_Date", Updated_Date);
			request.addProperty("Status", Status);
			request.addProperty("Document_No", Document_No);
			request.addProperty("NewDocument_No", NewDocument_No);
			request.addProperty("Individuals_Id", Individuals_Id);
			request.addProperty("NewProposal", NewProposal);
            request.addProperty("File_Path", File_Path);
            request.addProperty("File_Path_File_Name", File_Path_File_Name);
            request.addProperty("File_Exten", File_Exten);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// soap envelop with version
            envelope.setOutputSoapObject(request); // set request object
            envelope.dotNet = true;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
            // transport
            // call
            androidHttpTransport.call("http://tempuri.org/IService1/Edit_Details",
                    envelope);

            // response soap object
            result = (SoapPrimitive) envelope.getResponse();
            Log.e("result Edit_Details", result.toString());
            return result;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

	// GetEmailID
	public SoapObject getEmailID(String username) {
		SoapObject result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"getEmailID");// soap object
			request.addProperty("Username", username);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/getEmailID",
					envelope);

			// response soap object
			result = (SoapObject) envelope.getResponse();
			Log.e("result", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	// GetEmailID
	public SoapPrimitive changepassword(String rollId, String newpass,String username) {
		SoapPrimitive result = null;

		try {
			SoapObject request = new SoapObject("http://tempuri.org/",
					"changepassword");// soap object
			request.addProperty("id", rollId);
			request.addProperty("NewPassword", newpass);
			request.addProperty("Username", username);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);// soap envelop with version
			envelope.setOutputSoapObject(request); // set request object
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);// http
			// transport
			// call
			androidHttpTransport.call("http://tempuri.org/IService1/changepassword",
					envelope);

			// response soap object
			result = (SoapPrimitive) envelope.getResponse();
			Log.e("result", result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}

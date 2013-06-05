package com.cellshop.login;



import com.cellshop.main.R;
import data.PasswordAndUserGroup;
import device.ByteArrayStaticDriver;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class LockScreenActivity extends Activity implements FreshPassword{
	SIMCardInfo sCardInfo;
	boolean register;
	private TextView textView;
	private PasswordAndUserGroup passwordAndUserGroup;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_lock_screen);
	        RelativeLayout layout = (RelativeLayout)findViewById(R.id.lockScreenLayout);
	        layout.addView(new NinePointView(this,this));
	        sCardInfo=new SIMCardInfo(this);
	        textView=(TextView)findViewById(R.id.textForLogin);
	        
	        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() 
	        .detectDiskReads().detectDiskWrites().detectNetwork() // 这里或者用 .detectAll() 方法
	        .penaltyLog().build()); 
	        
	        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder() 
	        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects() 
	        .penaltyLog().penaltyDeath().build());
	    }
	 
	 @Override
	    protected void onStart() {
	    	// TODO Auto-generated method stub
	    	super.onStart();
	    	String string=sCardInfo.getNativePhoneNumber();
	    	passwordAndUserGroup=new PasswordAndUserGroup(sCardInfo.getNativePhoneNumber(),"","","");
	        passwordAndUserGroup=new PasswordAndUserGroup(
	        			ByteArrayStaticDriver.getByteArrayResult(
	        					"Register", "RegisterMove", passwordAndUserGroup,"42.96.172.134"));
	        if (passwordAndUserGroup.getPassword().equals("")) {
	        	 register=true;
		  	}
	        else {
	        	register=false;
	        }
	        if (string.equals("")) {
				string="取不出来"+sCardInfo.getProvidersName();
			}
	        else {
				string=string+sCardInfo.getProvidersName()+"请输入密码"+passwordAndUserGroup.getPassword();
			}
	        
	        textView.setText(string);
	 }
	 public void freshPassword(String password) {
			// TODO Auto-generated method stub
			String string;
			if (register) {
				passwordAndUserGroup.setPassword(password);
					passwordAndUserGroup=new PasswordAndUserGroup(
							ByteArrayStaticDriver.getByteArrayResult(
									"Register", "RegisterMove", passwordAndUserGroup,"42.96.172.134"));
					string="手机号：  "+passwordAndUserGroup.getUsername()+
			  				"  密码："+passwordAndUserGroup.getPassword()+"    权限：   "+passwordAndUserGroup.getGroupName();
			        	
			}
		    else if (password.equals(passwordAndUserGroup.getPassword())) {
					string="密码正确";
				}
				else {
					string="密码错误";
				}
			textView.setText(string);
		}
	
	public class SIMCardInfo {  
	    /** 
	     * TelephonyManager提供设备上获取通讯服务信息的入口。 应用程序可以使用这个类方法确定的电信服务商和国家 以及某些类型的用户访问信息。 
	     * 应用程序也可以注册一个监听器到电话收状态的变化。不需要直接实例化这个类 
	     * 使用Context.getSystemService(Context.TELEPHONY_SERVICE)来获取这个类的实例。 
	     */  
	    private TelephonyManager telephonyManager;  
	    /** 
	     * 国际移动用户识别码 
	     */  
	    private String IMSI;  
	  
	    public SIMCardInfo(Context context) {  
	        telephonyManager = (TelephonyManager) context  
	                .getSystemService(Context.TELEPHONY_SERVICE);  
	    }  
	  
	    /** 
	     * Role:获取当前设置的电话号码 
	     * <BR>Date:2012-3-12 
	     * 
	     */  
	    public String getNativePhoneNumber() {  
	        String NativePhoneNumber=null;  
	        NativePhoneNumber=telephonyManager.getLine1Number();  
	        return NativePhoneNumber;  
	    }  
	  
	    /** 
	     * Role:Telecom service providers获取手机服务商信息 <BR> 
	     * 需要加入权限<uses-permission 
	     * android:name="android.permission.READ_PHONE_STATE"/> <BR> 
	     * Date:2012-3-12 <BR> 
	     *  
	     * 
	     */  
	    public String getProvidersName() {  
	        String ProvidersName = null;  
	        // 返回唯一的用户ID;就是这张卡的编号神马的  
	        IMSI = telephonyManager.getSubscriberId();  
	        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。  
	        System.out.println(IMSI);  
	        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
	            ProvidersName = "中国移动";  
	        } else if (IMSI.startsWith("46001")) {  
	            ProvidersName = "中国联通";  
	        } else if (IMSI.startsWith("46003")) {  
	            ProvidersName = "中国电信";  
	        }  
	        return ProvidersName;  
	    }  
	    public String getSimSerialNumber() {
	    	String  imei = telephonyManager.getSimSerialNumber(); 
	    	return imei;
			
		}
	}  
}

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
	        .detectDiskReads().detectDiskWrites().detectNetwork() // ��������� .detectAll() ����
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
				string="ȡ������"+sCardInfo.getProvidersName();
			}
	        else {
				string=string+sCardInfo.getProvidersName()+"����������"+passwordAndUserGroup.getPassword();
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
					string="�ֻ��ţ�  "+passwordAndUserGroup.getUsername()+
			  				"  ���룺"+passwordAndUserGroup.getPassword()+"    Ȩ�ޣ�   "+passwordAndUserGroup.getGroupName();
			        	
			}
		    else if (password.equals(passwordAndUserGroup.getPassword())) {
					string="������ȷ";
				}
				else {
					string="�������";
				}
			textView.setText(string);
		}
	
	public class SIMCardInfo {  
	    /** 
	     * TelephonyManager�ṩ�豸�ϻ�ȡͨѶ������Ϣ����ڡ� Ӧ�ó������ʹ������෽��ȷ���ĵ��ŷ����̺͹��� �Լ�ĳЩ���͵��û�������Ϣ�� 
	     * Ӧ�ó���Ҳ����ע��һ�����������绰��״̬�ı仯������Ҫֱ��ʵ��������� 
	     * ʹ��Context.getSystemService(Context.TELEPHONY_SERVICE)����ȡ������ʵ���� 
	     */  
	    private TelephonyManager telephonyManager;  
	    /** 
	     * �����ƶ��û�ʶ���� 
	     */  
	    private String IMSI;  
	  
	    public SIMCardInfo(Context context) {  
	        telephonyManager = (TelephonyManager) context  
	                .getSystemService(Context.TELEPHONY_SERVICE);  
	    }  
	  
	    /** 
	     * Role:��ȡ��ǰ���õĵ绰���� 
	     * <BR>Date:2012-3-12 
	     * 
	     */  
	    public String getNativePhoneNumber() {  
	        String NativePhoneNumber=null;  
	        NativePhoneNumber=telephonyManager.getLine1Number();  
	        return NativePhoneNumber;  
	    }  
	  
	    /** 
	     * Role:Telecom service providers��ȡ�ֻ���������Ϣ <BR> 
	     * ��Ҫ����Ȩ��<uses-permission 
	     * android:name="android.permission.READ_PHONE_STATE"/> <BR> 
	     * Date:2012-3-12 <BR> 
	     *  
	     * 
	     */  
	    public String getProvidersName() {  
	        String ProvidersName = null;  
	        // ����Ψһ���û�ID;�������ſ��ı�������  
	        IMSI = telephonyManager.getSubscriberId();  
	        // IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�  
	        System.out.println(IMSI);  
	        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
	            ProvidersName = "�й��ƶ�";  
	        } else if (IMSI.startsWith("46001")) {  
	            ProvidersName = "�й���ͨ";  
	        } else if (IMSI.startsWith("46003")) {  
	            ProvidersName = "�й�����";  
	        }  
	        return ProvidersName;  
	    }  
	    public String getSimSerialNumber() {
	    	String  imei = telephonyManager.getSimSerialNumber(); 
	    	return imei;
			
		}
	}  
}

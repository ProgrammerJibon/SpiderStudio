package app.jibon.spider;

import android.app.Activity;

public class Data{
    protected Activity activity;
    protected String JSONLINK;
    protected String storedCookie;

    public Data(Activity activity) {
        this.activity = activity;
        this.storedCookie = new Settings(activity).storedCookie(null);
        this.JSONLINK = "http://1.1.7.8:8080/data.php?cookie_app="+userCookie()+"&";
    }

    public String linkForJson(String extra) {
        return this.JSONLINK + extra;
    }

    public String userId(){
        return new Settings(activity).userId(null);
    }

    public String userCookie(){
        return storedCookie;
    }
}


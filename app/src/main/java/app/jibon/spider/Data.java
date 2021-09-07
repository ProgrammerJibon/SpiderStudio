package app.jibon.spider;

import java.util.Arrays;

public class Data {
    protected String JSONLINK;

    public Data() {
        this.JSONLINK = "http://1.1.7.38:8080/data.php";
    }

    public String getJSONLINK(String... extra) {
        return this.JSONLINK + extra[0];
    }
}


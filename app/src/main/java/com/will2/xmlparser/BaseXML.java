package com.will2.xmlparser;

import android.content.ContentResolver;
import android.content.Context;

class BaseXML {
    private ContentResolver contentResolver;

    public  BaseXML (Context context){
        contentResolver = this.contentResolver;

    }

    public final static int VERSION = 9 ;
    public final static String DB_NAME ="projet" ;
    public final static String TABLE = "projet"
}

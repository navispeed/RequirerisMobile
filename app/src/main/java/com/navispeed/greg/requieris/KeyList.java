package com.navispeed.greg.requieris;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by greg on 12/11/2016.
 */

public class KeyList {

    private static final String PREFS_NAME = "MyPrefsFile";

    private final Activity activity;

    private Map<String, String> keyList = new HashMap<>();

    public KeyList(AppCompatActivity appCompatActivity) {
        SharedPreferences settings = appCompatActivity.getSharedPreferences(PREFS_NAME, 0);

        Map<String, ?> prefsMap = settings.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet())
        {
            if (entry.getValue() instanceof String) {
                keyList.put(entry.getKey(), (String) entry.getValue());
            }
            Log.v("SharedPreferences", entry.getKey() + ":" + entry.getValue().toString());
        }

        this.activity = appCompatActivity;
    }

    public boolean addKey(String name, String key) {
        if (this.keyList.containsKey(name)) {
            return false;
        }
        this.keyList.put(name, key);
        return true;
    }

    public void save() {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        for (Map.Entry<String, String> pair : keyList.entrySet()) {
            editor.putString(pair.getKey(), pair.getValue());
        }

        editor.apply();
    }

    public List<String> getList() {
        ArrayList<String> res = new ArrayList<>();

        Stack<String> toRemove = new Stack<>();

        for (Map.Entry<String, String> pair : keyList.entrySet()) {
            try {
                res.add(pair.getKey() + " : " + new GoogleAuthenticator(pair.getValue()).getToken());
            } catch (Exception e) {
                e.printStackTrace();
                toRemove.add(pair.getKey());
            }
        }
        for (String s : toRemove) {
            keyList.remove(s);
        }
        return res;
    }
}

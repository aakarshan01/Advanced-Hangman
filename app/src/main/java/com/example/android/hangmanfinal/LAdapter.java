package com.example.android.hangmanfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LAdapter extends BaseAdapter {

    private String[] letters;
    private LayoutInflater letterIn;

    public LAdapter(Context con) {

        int i=0;
        letters=new String[28];
        while(i!=26){
            letters[i]=""+(char)(i+'A');
            i++;
        }
        letters[i++]=""+(char)('.');
        letters[i++]=""+(char)('-');

        letterIn = LayoutInflater.from(con);
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public View getView(int pos, View convertV, ViewGroup par) {

        Button lBtn;
        if (convertV == null)
        {
            lBtn = (Button)letterIn.inflate(R.layout.letter, par, false);
        }
        else
        {
            lBtn = (Button) convertV;
        }
        //set the text to this letter
        lBtn.setText(letters[pos]);
        return lBtn;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

}

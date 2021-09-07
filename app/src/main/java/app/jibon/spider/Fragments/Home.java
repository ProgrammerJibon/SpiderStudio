package app.jibon.spider.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.jibon.spider.R;

public class Home extends Fragment {
    public View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

        /*EditText pasteFileLink = findViewById(R.id.pasteLinkOfFile);
        pasteFileLink.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if (!(pasteFileLink.getText()).equals("")){
                    String pastedLink = String.valueOf(pasteFileLink.getText());
                    (new SaveImageFromLink(, pastedLink, null)).execute();
                }
                pasteFileLink.setText("");
            }
            return false;
        });*/
}
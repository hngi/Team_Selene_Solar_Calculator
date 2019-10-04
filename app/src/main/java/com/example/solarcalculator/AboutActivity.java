package com.example.solarcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.solarcalculator.Adapter.AboutAdapter;
import com.example.solarcalculator.Model.Developer;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Developer> arrayList;
    private AboutAdapter aboutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.team_recycler);
        arrayList = new ArrayList<>();
        arrayList.add(developer(R.drawable.robin, "Igboanyika Nnaemeka", "\"Android Developer\"", "https://facebook.com/emeka.igboanyika", "https://twitter.com/codejunk1e","https://github.com/codejunk1e","https://www.linkedin.com/in/innaemeka/"));
        arrayList.add(developer(R.drawable.ehma, "Ehma Ugbogo", "\"Android Developer\"", "https://facebook.com/emmanuel.ugbogo", "http://twitter.com/ehmaugbogo","https://github.com/EhmaUgbogo","https://linkedin.com/in/ehmaugbogo"));
        arrayList.add(developer(R.drawable.paul, "Paul Nnadi", "\"Android Developer\"", "http://facebook.com/officialpaulnaija", "https://twitter.com/paulnaija_","https://github.com/paulextrawonder","https://linkedin/in/paulnaija"));
        arrayList.add(developer(R.drawable.etim, "Utibe Etim", "\"Android Developer\"","https://facebook.com/etim.utibe", "https://twitter.com/Utcodes","https://github.com/Uticodes","https://www.linkedin.com/in/utibe-etim-0a901a107/"));
        aboutAdapter = new AboutAdapter(arrayList, this);
        recyclerView.setAdapter(aboutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setTitle("Team Members");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Developer developer(int image, String fullName, String mTitle, String mFaceBookUrl, String mTwitterUrl, String mGitHubUrl, String mLinkedInUrl){
        Developer developer = new Developer();
        developer.setmImage(image);
        developer.setmName(fullName);
        developer.setmTitle(mTitle);
        developer.setmFaceBookUrl(mFaceBookUrl);
        developer.setmTwitterUrl(mTwitterUrl);
        developer.setmGitHubUrl(mGitHubUrl);
        developer.setmLinkedInUrl(mLinkedInUrl);
        return developer;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

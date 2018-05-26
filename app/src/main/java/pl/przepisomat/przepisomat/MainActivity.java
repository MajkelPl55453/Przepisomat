package pl.przepisomat.przepisomat;

import android.os.Bundle;

import pl.przepisomat.przepisomat.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaults();
    }
}

package nl.noxlogic.gpsfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startService(new Intent(GpsFixService.class.getName()));
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	Toast.makeText(this, "Stopped updating site", Toast.LENGTH_LONG).show();
    	
    	stopService(new Intent(GpsFixService.class.getName()));
    }
}
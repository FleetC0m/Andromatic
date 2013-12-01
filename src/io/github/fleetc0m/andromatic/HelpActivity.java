package io.github.fleetc0m.andromatic;

import io.github.fleetc0m.andromatic.UI.WelcomeFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelpActivity extends Activity{
	private Button backButton;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final View view = View.inflate(this, R.layout.help_activity, null);  
        setContentView(view);  
        backButton = (Button)view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackToMainListener());
	}
	public class BackToMainListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			redirectTo(); 
		}
		
	}
    private void redirectTo() {  
        Intent intent = new Intent(this, RootActivity.class);  
        startActivity(intent);  
        finish();  
    } 
}

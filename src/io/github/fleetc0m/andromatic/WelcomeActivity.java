package io.github.fleetc0m.andromatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 
import android.view.View;  
import android.view.animation.AlphaAnimation;  
import android.view.animation.Animation;  
import android.view.animation.Animation.AnimationListener;  
  
public class WelcomeActivity extends Activity {  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        final View view = View.inflate(this, R.layout.welcome_activity, null);  
        setContentView(view);  
      
        AlphaAnimation aa = new AlphaAnimation(1.0f, 1.0f);  
        aa.setDuration(2000);  
        view.startAnimation(aa);  
          
        aa.setAnimationListener(new AnimationListener() {  
            @Override  
            public void onAnimationEnd(Animation arg0) {  
                redirectTo();  
            }  
  
            @Override  
            public void onAnimationRepeat(Animation animation) {  
            }  
  
            @Override  
            public void onAnimationStart(Animation animation) {  
            }  
  
        });  
    }  
  
    private void redirectTo() {  
        Intent intent = new Intent(this, RootActivity.class);  
        startActivity(intent);  
        finish();  
    }  
}  
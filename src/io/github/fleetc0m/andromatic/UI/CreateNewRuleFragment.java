package io.github.fleetc0m.andromatic.UI;
import io.github.fleetc0m.andromatic.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateNewRuleFragment extends Fragment {
	public final String TITLE = getResources().getString(R.string.create_new_rule_fragment_title);
	public CreateNewRuleFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.new_rule_fragment, container, false);
		
		return rootView;
	}
}

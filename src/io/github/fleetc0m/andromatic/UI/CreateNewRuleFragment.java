package io.github.fleetc0m.andromatic.UI;
import java.util.ArrayList;
import java.util.HashMap;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class CreateNewRuleFragment extends Fragment {
	//public final String TITLE = getResources().getString(R.string.create_new_rule_fragment_title);
	
	private Context context;
	private ListView configListView;
	private ArrayList<String> titleForListView;
	private ArrayList<String> availTriggers;
	private HashMap<String, String> triggerClassMap;
	private ArrayList<String> availActions;
	private HashMap<String, String> actionClassMap;
	
	public CreateNewRuleFragment(){
		this.context = this.getActivity();
		titleForListView = new ArrayList<String>();
		titleForListView.add("trigger");
		titleForListView.add("");
		titleForListView.add("action");
		titleForListView.add("");
		availTriggers = new ArrayList<String>();
		triggerClassMap = new HashMap<String, String>();
		availActions = new ArrayList<String>();
		actionClassMap = new HashMap<String, String>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.new_rule_fragment, container, false);
		Button cancelBtn = (Button)rootView.findViewById(R.id.cancel_button);
		cancelBtn.setOnClickListener(new CancelActionListener());
		Button saveBtn = (Button) rootView.findViewById(R.id.save_button);
		saveBtn.setOnClickListener(new SaveActionListener());
		configListView = (ListView)rootView.findViewById(R.id.list);
		NewRuleConfigAdapter adapter = new NewRuleConfigAdapter(this.getActivity(), android.R.id.list,
				titleForListView);
		configListView.setAdapter(adapter);
		return rootView;
	}
	
	public static class NewRuleConfigAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> objects;
		public NewRuleConfigAdapter(Context context, int resource, ArrayList<String> objects){
			super(context, resource, objects);
			this.context = context;
			this.objects = objects;
		}
		
		@Override
		public View getView(int pos, View cacheView, ViewGroup parent){
			//TODO:
			if((cacheView != null) && 
					(cacheView.getTag() != null) &&( 
					cacheView.getTag().equals(objects.get(pos)))){
				return cacheView;
			}
			switch(pos){
			case 0:{
				LayoutInflater inflater = (LayoutInflater) 
						context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.choose_trigger_entry, null);
				Spinner triggerSpinner = (Spinner)view.findViewById(R.id.choose_trigger_spinner);
				//TODO: add trigger spinner
				return view;
			}
			case 1:{
				LayoutInflater inflater = (LayoutInflater) 
						context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View view = new View(context);
				//TODO:
				return view;
				}
			case 2:{
				LayoutInflater inflater = (LayoutInflater) 
						context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.choose_action_entry, null);
				Spinner actionSpinner = (Spinner) view.findViewById(R.id.choose_action_spinner);
				//TODO: add spinner
				return view;
				}
			case 3:{
				LayoutInflater inflater = (LayoutInflater) 
						context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View view = new View(context);
				//TODO:
				return view;
			}
			default:
				throw new IllegalArgumentException("invalid pos");
			}
		}
	}
	
	
	public class CancelActionListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {	
			//TODO:
		}
	}
	
	public class SaveActionListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			//TODO:
		}
	}
}

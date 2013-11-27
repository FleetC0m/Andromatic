package io.github.fleetc0m.andromatic.UI;
import java.util.ArrayList;
import java.util.HashMap;

import io.github.fleetc0m.andromatic.R;
import io.github.fleetc0m.andromatic.action.Action;
import io.github.fleetc0m.andromatic.trigger.Trigger;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class CreateNewRuleFragment extends Fragment {
	//public final String TITLE = getResources().getString(R.string.create_new_rule_fragment_title);
	
	private Context context;
	private ListView configListView;
	private ArrayList<String> titleForListView;
	private ArrayList<String> availTriggers;
	private HashMap<String, String> triggerClassMap;
	private ArrayList<String> availActions;
	private HashMap<String, String> actionClassMap;
	private NewRuleConfigAdapter adapter;
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
		init();
	}
	
	private void init(){
		availTriggers.add("");
		availActions.add("");
		
		triggerClassMap.put("SMS Incoming", "io.github.fleetc0m.andromatic.trigger.SMSReceivedTrigger");
		availTriggers.add("SMS Incoming");
		actionClassMap.put("Change ringtone volume", 
				"io.github.fleetc0m.andromatic.action.ChangeVolumeAction");
		availActions.add("Change ringtone volume");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.new_rule_fragment, container, false);
		Button cancelBtn = (Button)rootView.findViewById(R.id.cancel_button);
		cancelBtn.setOnClickListener(new CancelActionListener());
		Button saveBtn = (Button) rootView.findViewById(R.id.save_button);
		saveBtn.setOnClickListener(new SaveActionListener());
		configListView = (ListView)rootView.findViewById(R.id.list);
		adapter = new NewRuleConfigAdapter(this.getActivity(), android.R.id.list,
				titleForListView);
		adapter.configure(availTriggers, triggerClassMap, availActions, actionClassMap);
		configListView.setAdapter(adapter);
		return rootView;
	}
	
	public static class NewRuleConfigAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> objects;
		private View triggerConfigView;
		private View actionConfigView;
		private Trigger trigger;
		private Action action;
		private TriggerSpinnnerOnClickListener trigListener;
		private ActionSpinnerOnClickListener actionListener;
		private HashMap<String, String> triggerMap, actionMap;
		private ArrayList<String> availTriggers, availActions;
		private ArrayAdapter<String> triggerSpinnerAdapter;
		private ArrayAdapter<String> actionSpinnerAdapter;
		
		private View chooseTriggerView;
		private View chooseActionView;
		private Spinner triggerSpinner;
		private Spinner actionSpinner;
		

		
		private static final String TAG = "NRCA";
		public NewRuleConfigAdapter(Context context, int resource, ArrayList<String> objects){
			super(context, resource, objects);
			this.context = context;
			this.objects = objects;
			trigListener = new TriggerSpinnnerOnClickListener();
			actionListener = new ActionSpinnerOnClickListener();

		}
		public Spinner getTriggerSpinner(){
			return triggerSpinner;
		}
		public Spinner getActionSpinner(){
			return actionSpinner;
		}
		
		public Trigger getTrigger(){
			return trigger;
		}
		public Action getAction(){
			return action;
		}
		
		public void configure(ArrayList<String> availTriggers,
				HashMap<String, String> triggerMap,
				ArrayList<String> availActions,
				HashMap<String, String> actionMap){
			this.availTriggers = availTriggers;
			this.triggerMap = triggerMap;
			this.availActions = availActions;
			this.actionMap = actionMap;
			
			triggerSpinnerAdapter = new ArrayAdapter<String>
							(context, android.R.layout.simple_dropdown_item_1line, availTriggers);
			actionSpinnerAdapter = new ArrayAdapter<String>
							(context, android.R.layout.simple_dropdown_item_1line, availActions);
			//triggerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			LayoutInflater i = (LayoutInflater) 
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			chooseTriggerView = i.inflate(R.layout.choose_trigger_entry, null);
			triggerSpinner = (Spinner)chooseTriggerView.findViewById(R.id.choose_trigger_spinner);
			triggerSpinner.setAdapter(triggerSpinnerAdapter);
			triggerSpinner.setOnItemSelectedListener(trigListener);
			
			chooseActionView = i.inflate(R.layout.choose_action_entry, null);
			actionSpinner = (Spinner) chooseActionView.findViewById(R.id.choose_action_spinner);
			actionSpinner.setAdapter(actionSpinnerAdapter);
			actionSpinner.setOnItemSelectedListener(actionListener);
		}
		@Override
		public View getView(int pos, View cacheView, ViewGroup parent){
			//Log.d(TAG, "getView called. pos " + pos);
			switch(pos){
			case 0:{
				return chooseTriggerView;
			}
			case 1:{
				return triggerConfigView != null ? triggerConfigView : new View(context);
				}
			case 2:{
				return chooseActionView;
				}
			case 3:{
				return actionConfigView != null ? actionConfigView : new View(context);
			}
			default:
				throw new IllegalArgumentException("invalid pos");
			}
		}
		
		private class TriggerSpinnnerOnClickListener implements OnItemSelectedListener{
			private static final String TAG = "TSOCL";
			@SuppressWarnings("unchecked")
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long id) {
				triggerSpinner.setEnabled(false);
				Log.d(TAG, "clicked, pos " + pos);
				if(pos == 0){
					trigger = null;
					triggerConfigView = null; 
				}else{
					try {
						Class <? extends Trigger> triggerClass = (Class<? extends Trigger>)
								Class.forName(triggerMap.get(availTriggers.get(pos)));
						trigger = triggerClass.newInstance();
						trigger.setContext(context);
						triggerConfigView = trigger.getConfigView();
						triggerConfigView.setTag(availTriggers.get(pos));
						objects.set(pos, availTriggers.get(pos));
					} catch (ClassNotFoundException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					} catch (java.lang.InstantiationException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
					//NewRuleConfigAdapter.this.notifyDataSetChanged();
				}
				NewRuleConfigAdapter.this.notifyDataSetChanged();
				
				triggerSpinner.setEnabled(true); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		}
		
		private class ActionSpinnerOnClickListener implements OnItemSelectedListener{

			@SuppressWarnings("unchecked")
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long id) {
				actionSpinner.setEnabled(false);
				if(pos == 0){
					action = null;
					actionConfigView = null;
				}else{
					try {
						Class <? extends Action> actionClass = (Class<? extends Action>)
								Class.forName(actionMap.get(availActions.get(pos)));
						action = actionClass.newInstance();
						action.setContext(context);
						actionConfigView = action.getConfigView();
						actionConfigView.setTag(availActions.get(pos));
						objects.set(pos, availActions.get(pos));
					} catch (ClassNotFoundException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					} catch (java.lang.InstantiationException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
				}
				NewRuleConfigAdapter.this.notifyDataSetChanged();
				actionSpinner.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		}
	}
	
	
	public class CancelActionListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {	
			adapter.getTriggerSpinner().setSelection(0, true);
			adapter.getActionSpinner().setSelection(0, true);
		}
	}
	
	public class SaveActionListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			String triggerRule = adapter.getTrigger().getConfigString();
			String actionRule = adapter.getAction().getConfigString();
			String triggerIntent = adapter.getTrigger().getIntentAction();
			//TODO: Write into database.
		}
	}
}

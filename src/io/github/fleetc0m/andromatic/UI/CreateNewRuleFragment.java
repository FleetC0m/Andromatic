package io.github.fleetc0m.andromatic.UI;
import io.github.fleetc0m.andromatic.R;
import io.github.fleetc0m.andromatic.SQLHandler;
import io.github.fleetc0m.andromatic.action.Action;
import io.github.fleetc0m.andromatic.trigger.Trigger;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateNewRuleFragment extends Fragment {
	//public final String TITLE = getResources().getString(R.string.create_new_rule_fragment_title);
	public static final String TAG = "CNRF";
	private Context context;
	private ListView configListView;
	private EditText ruleNameField;
	private ArrayList<String> titleForListView;
	private ArrayList<String> availTriggers;
	private HashMap<String, String> triggerClassMap;
	private ArrayList<String> availActions;
	private HashMap<String, String> actionClassMap;
	private NewRuleConfigAdapter adapter;
	private SQLHandler sqlHandler;
	
	private ViewPager viewPager;
	private ShowAllRulesFragment showAllRulesFragment;
	
	private Button clearBtn;
	private Button saveBtn;
	private Button deleteBtn;
	
	public CreateNewRuleFragment(){

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
	
	public void setArgs(ViewPager viewPager, ShowAllRulesFragment showAllRulesFragment){
			this.viewPager = viewPager;
			this.showAllRulesFragment = showAllRulesFragment;
	}
	
	private void init(){
		availTriggers.add("");
		availActions.add("");
		
		triggerClassMap.put("SMS Incoming", "io.github.fleetc0m.andromatic.trigger.SMSReceivedTrigger");
		availTriggers.add("SMS Incoming");
		
		triggerClassMap.put("Timed Event", "io.github.fleetc0m.andromatic.trigger.TimedEventTrigger");
		availTriggers.add("Timed Event");
		
		triggerClassMap.put("Driving Mode", "io.github.fleetc0m.andromatic.trigger.DrivingModeTrigger");
		availTriggers.add("Driving Mode");
		
		triggerClassMap.put("Phone Incoming", "io.github.fleetc0m.andromatic.trigger.IncomingCallTrigger");
		availTriggers.add("Phone Incoming");
		
		//triggerClassMap.put("Low Battery", "io.github.fleetc0m.andromatic.trigger.LowBatteryTrigger");
		//availTriggers.add("Low Battery");
		
		actionClassMap.put("Change ringtone volume", 
				"io.github.fleetc0m.andromatic.action.ChangeVolumeAction");
		availActions.add("Change ringtone volume");
		
		actionClassMap.put("Send SMS", 
				"io.github.fleetc0m.andromatic.action.SendSMSAction");
		availActions.add("Send SMS");
		
		actionClassMap.put("Set vibration", 
				"io.github.fleetc0m.andromatic.action.SetVibrateAction");
		availActions.add("Set vibration");
		
		actionClassMap.put("Change Brightness", 
				"io.github.fleetc0m.andromatic.action.ChangeBrightnessAction");
		availActions.add("Change Brightness");

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		this.context = this.getActivity();
		//this.viewPager = (ViewPager) this.getActivity().findViewById(R.id.pager);
		//this.showAllRulesFragment = (ShowAllRulesFragment) this.viewPager.getAdapter().instantiateItem(this.viewPager, 2);
		sqlHandler = new SQLHandler(context);
		View rootView = inflater.inflate(R.layout.new_rule_fragment, container, false);
		ruleNameField = (EditText) rootView.findViewById(R.id.rule_name_field);

		clearBtn = (Button)rootView.findViewById(R.id.cancel_button);
		clearBtn.setOnClickListener(new ClearActionListener());
		saveBtn = (Button) rootView.findViewById(R.id.save_button);
		saveBtn.setOnClickListener(new SaveActionListener());
		deleteBtn = (Button) rootView.findViewById(R.id.delete_button);
		deleteBtn.setOnClickListener(new DeleteActionListener());
		configListView = (ListView)rootView.findViewById(R.id.list);
		adapter = new NewRuleConfigAdapter(this.getActivity(), android.R.id.list,
				titleForListView);
		adapter.configure(availTriggers, triggerClassMap, availActions, actionClassMap, saveBtn);
		adapter.setArgs(ruleNameField);
		configListView.setAdapter(adapter);
		return rootView;
	}
	
	
	public NewRuleConfigAdapter getAdapter(){
		return this.adapter;
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
		
		private boolean readyStat;
		private Button saveBtn;
		private long rowId;
		private EditText ruleNameField;
		
		public boolean triggerSetByNerd;
		public boolean actionSetByNerd;
		
		private static final String TAG = "NRCA";
		public NewRuleConfigAdapter(Context context, int resource, ArrayList<String> objects){
			super(context, resource, objects);
			this.context = context;
			this.objects = objects;
			trigListener = new TriggerSpinnnerOnClickListener();
			actionListener = new ActionSpinnerOnClickListener();
			readyStat = false;
			rowId = -1;
			triggerSetByNerd = false;
			actionSetByNerd = false;
		}
		public void setArgs(EditText ruleNameField){
			this.ruleNameField = ruleNameField;
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
		
		public boolean readyToSave(){
			return readyStat;
		}
		
		public void setSavedState(Bundle b){
			Trigger t = null;
			Action a = null;
			try{
				t = (Trigger) Class.forName(b.getString(SQLHandler.TRIGGER_CLASS_NAME)).newInstance();
				a = (Action) Class.forName(b.getString(SQLHandler.ACTION_CLASS_NAME)).newInstance();
			}catch(IllegalAccessException ex){
				ex.printStackTrace();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String triggerCommonName = null;
			for(final String key : triggerMap.keySet()){
				if(triggerMap.get(key).equals(b.getString(SQLHandler.TRIGGER_CLASS_NAME))){
					triggerCommonName = key;
					break;
				}
			}
			String actionCommonName = null;;
			for(final String key : actionMap.keySet()){
				if(actionMap.get(key).equals(b.getString(SQLHandler.ACTION_CLASS_NAME))){
					actionCommonName = key;
					break;
				}
			}
			triggerSetByNerd = true;
			actionSetByNerd = true;
			triggerSpinner.setSelection(availTriggers.indexOf(triggerCommonName), false);
			actionSpinner.setSelection(availActions.indexOf(actionCommonName), false);
			
			//rowId must be set after updated spinner selections because
			//spinner selection change will overwrite rowId
			rowId = b.getLong(SQLHandler.RULE_ID);
			t.setContext(context);
			triggerConfigView = t.getConfigView(b.getString(SQLHandler.TRIGGER_RULE));
			a.setContext(context);
			actionConfigView = a.getConfigView(b.getString(SQLHandler.ACTION_RULE));
			ruleNameField.setText(b.getString(SQLHandler.RULE_NAME));
			
			trigger = t;
			action = a;
			readyStat = true;
			saveBtn.setEnabled(true);
			this.notifyDataSetChanged();
		}
		
		public long getCurrentRowId(){
			return rowId;
		}
		
		public void setCurrentRowId(long id){
			this.rowId = id;
		}
		
		public void configure(ArrayList<String> availTriggers,
				HashMap<String, String> triggerMap,
				ArrayList<String> availActions,
				HashMap<String, String> actionMap,
				Button saveBtn){
			this.saveBtn = saveBtn;
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
				if(triggerSetByNerd){
					triggerSetByNerd = false;
					return;
				}
				triggerSpinner.setEnabled(false);
				rowId = -1;
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
						objects.set(1, availTriggers.get(pos));
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
				if(trigger != null && action != null){
					readyStat = true;
					saveBtn.setEnabled(true);
				}else{
					readyStat = false;
					saveBtn.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		}
		
		private class ActionSpinnerOnClickListener implements OnItemSelectedListener{

			@SuppressWarnings("unchecked")
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long id) {
				if(actionSetByNerd){
					actionSetByNerd = false;
					return;
				}
				actionSpinner.setEnabled(false);
				rowId = -1;
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
						objects.set(3, availActions.get(pos));
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
				if(trigger != null && action != null){
					readyStat = true;
					saveBtn.setEnabled(true);
				}else{
					readyStat = false;
					saveBtn.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		}
	}
	
	
	public class ClearActionListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			if(adapter.getCurrentRowId() != -1){
				CreateNewRuleFragment.this.viewPager = (ViewPager)
						CreateNewRuleFragment.this.getActivity().findViewById(R.id.pager);
				CreateNewRuleFragment.this.viewPager.setCurrentItem(2);
			}
			adapter.setCurrentRowId(-1);
			adapter.triggerSetByNerd = false;
			adapter.actionSetByNerd = false;
			ruleNameField.setText("");
			adapter.getTriggerSpinner().setSelection(0, true);
			adapter.getActionSpinner().setSelection(0, true);
		}
	}
	
	public class DeleteActionListener implements OnClickListener{
		private long rowId;
		@Override
		public void onClick(View v){
			if(adapter.getCurrentRowId() == -1){
				return;
			}
			rowId = adapter.getCurrentRowId();
			new DeleteTask().execute(new Integer[]{});
		}
		
		private class DeleteTask extends AsyncTask<Integer, Integer, Integer>{
			@Override
			protected Integer doInBackground(Integer... params) {
				sqlHandler.deleteRuleById(rowId);
				return null;
			}
			
			@Override
			protected void onPostExecute(Integer params){
				Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
				CreateNewRuleFragment.this.viewPager = (ViewPager)
						CreateNewRuleFragment.this.getActivity().findViewById(R.id.pager);
				CreateNewRuleFragment.this.viewPager.setCurrentItem(2);
				CreateNewRuleFragment.this.showAllRulesFragment = (ShowAllRulesFragment)
						CreateNewRuleFragment.this.viewPager.getAdapter().instantiateItem(
								CreateNewRuleFragment.this.viewPager, 2);
				CreateNewRuleFragment.this.showAllRulesFragment.notifyDataSetChanged();
				showAllRulesFragment.notifyDataSetChanged();
			}
		}
	}
	
	public class SaveActionListener implements OnClickListener{
		private String triggerClassName;
		private String triggerRule;
		private String triggerDescription;
		private String actionClassName;
		private String actionRule;
		private String actionDescription;
		private String triggerIntent;
		private boolean needPolling;
		private String ruleName;
		private long rowId;
		@Override
		public void onClick(View v) {
			if(!adapter.readyToSave()){
				return;
			}
			ruleName = ruleNameField.getText().toString();
			
			triggerClassName = triggerClassMap.get( 
					availTriggers.get(adapter.getTriggerSpinner().getSelectedItemPosition()));
			triggerRule = adapter.getTrigger().getConfigString();
			triggerDescription = adapter.getTrigger().getHumanReadableString();
			
			actionClassName = actionClassMap.get(
					availActions.get(adapter.getActionSpinner().getSelectedItemPosition()));
			actionRule = adapter.getAction().getConfigString();
			actionDescription = adapter.getAction().getHumanReadableString();
			triggerIntent = adapter.getTrigger().getIntentAction();
			needPolling = adapter.getTrigger().needPolling();
			rowId = adapter.getCurrentRowId();
			new SaveToDBTask().execute(new Bundle[]{});
		}
		private class SaveToDBTask extends AsyncTask<Bundle, Integer, Integer>{

			private static final String TAG = "STDBT";
			@Override
			protected Integer doInBackground(Bundle... params) {
				Bundle b = new Bundle();
				b.putString(SQLHandler.TRIGGER_CLASS_NAME, triggerClassName);
				b.putString(SQLHandler.TRIGGER_RULE, triggerRule);
				b.putString(SQLHandler.ACTION_CLASS_NAME, actionClassName);
				b.putString(SQLHandler.ACTION_RULE, actionRule);
				b.putString(SQLHandler.INTENT_TYPE, triggerIntent);
				b.putString(SQLHandler.RULE_NAME, ruleName);
				b.putBoolean(SQLHandler.POLLING_TYPE, needPolling);
				
				if(rowId != -1){
					b.putLong(SQLHandler.RULE_ID, rowId);
					sqlHandler.updateRuleById(b);
				}else{
					sqlHandler.addRule(b);
				}
				return null;
			}
			@Override
			protected void onPostExecute(Integer param){
				String description = triggerDescription + ", " + actionDescription + ".";
				Toast.makeText(context, description, Toast.LENGTH_LONG).show();
				Log.d(TAG, description);
				CreateNewRuleFragment.this.viewPager = (ViewPager)
						CreateNewRuleFragment.this.getActivity().findViewById(R.id.pager);
				CreateNewRuleFragment.this.viewPager.setCurrentItem(2);
				CreateNewRuleFragment.this.showAllRulesFragment = (ShowAllRulesFragment)
						CreateNewRuleFragment.this.viewPager.getAdapter().instantiateItem(
								CreateNewRuleFragment.this.viewPager, 2);
				CreateNewRuleFragment.this.showAllRulesFragment.notifyDataSetChanged();
				clearBtn.performClick();
			}
		}
	}
}

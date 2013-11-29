package io.github.fleetc0m.andromatic.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.fleetc0m.andromatic.R;
import io.github.fleetc0m.andromatic.SQLHandler;
import io.github.fleetc0m.andromatic.action.Action;
import io.github.fleetc0m.andromatic.trigger.Trigger;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAllRulesFragment extends Fragment {
	
	private static final String TAG = "SARF";
	private SQLHandler mSQLHandler;
	private Context context;
	private List<Bundle> allEntries;
	private HashMap<String, Trigger> triggerMap;
	private HashMap<String, Action> actionMap;
	private static final String TRIGGER_DESCRIPTION = "triggerDescription";
	private static final String ACTION_DESCRIPTION = "actionDescription";
	private AllRuleListAdapter adapter;
	private ListView listView;
	private ViewPager viewPager;
	private CreateNewRuleFragment createNewRuleFragment;
	
	public ShowAllRulesFragment(){
		//allEntries = new List<Bundle>();
		triggerMap = new HashMap<String, Trigger>();
		actionMap = new HashMap<String, Action>();
		allEntries = new ArrayList<Bundle>();
	}
	
	public void setArgs(ViewPager viewPager, CreateNewRuleFragment fragment){
		this.viewPager = viewPager;
		this.createNewRuleFragment = fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		context = this.getActivity();
		mSQLHandler = new SQLHandler(context);
		adapter = new AllRuleListAdapter(this.getActivity(), android.R.id.list, allEntries);
		adapter.setArgs(viewPager, createNewRuleFragment);
		View rootView = inflater.inflate(R.layout.show_all_rules_fragment, null);
		listView = (ListView) rootView.findViewById(R.id.all_rule_list);
		listView.setAdapter(adapter);
		this.notifyDataSetChanged();
		return rootView;
	}
	
	public void notifyDataSetChanged(){
		List<Bundle> allEntries = mSQLHandler.getAllRules();
		for(Bundle b : allEntries){
			String triggerClass = b.getString(SQLHandler.TRIGGER_CLASS_NAME);
			String actionClass = b.getString(SQLHandler.ACTION_CLASS_NAME);
			if(!triggerMap.containsKey(triggerClass)){
				try {
					triggerMap.put(triggerClass, (Trigger)Class.forName(triggerClass).newInstance());
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			if(!actionMap.containsKey(actionClass)){
				try {
					actionMap.put(actionClass, (Action)Class.forName(actionClass).newInstance());
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			b.putString(TRIGGER_DESCRIPTION, triggerMap.get(triggerClass).
					getHumanReadableString(b.getString(SQLHandler.TRIGGER_RULE)));
			b.putString(ACTION_DESCRIPTION, actionMap.get(actionClass).
					getHumanReadableString(b.getString(SQLHandler.ACTION_RULE)));
		}
		adapter.clear();
		adapter.addAll(allEntries);
		adapter.notifyDataSetChanged();
		
	}
	private static class AllRuleListAdapter extends ArrayAdapter<Bundle>{
		private List<Bundle> object;
		private Context context;
		private LayoutInflater inflater;
		private ViewPager viewPager;
		private CreateNewRuleFragment createNewRuleFragment;
		public AllRuleListAdapter(Context context, int resource, List<Bundle> object){
			super(context, resource, object);
			this.context = context;
			this.inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			this.object = object;
		}
		
		public void setArgs(ViewPager v, CreateNewRuleFragment f){
			this.viewPager = v;
			this.createNewRuleFragment = f;
		}
		
		@Override
		public View getView(int pos, View cachedView, ViewGroup parent){
			if(cachedView != null){
				
			}else{
				cachedView = inflater.inflate(R.layout.single_rule_entry, null);
			}
			TextView nameView = (TextView)cachedView.findViewById(R.id.single_rule_name);
			nameView.setText(object.get(pos).getString(SQLHandler.RULE_NAME));
			TextView descriptionView = (TextView)cachedView.findViewById(R.id.single_rule_description);
			String description = object.get(pos).getString(TRIGGER_DESCRIPTION) + ", "
					+ object.get(pos).getString(ACTION_DESCRIPTION);
			descriptionView.setText(description);			
			cachedView.setOnClickListener(new ItemOnClickListener(object.get(pos)));
			return cachedView;
		}
		
		private class ItemOnClickListener implements OnClickListener{
			public Bundle b;
			
			public ItemOnClickListener(Bundle b){
				this.b = b;
			}
			
			@Override
			public void onClick(View v) {
				if(createNewRuleFragment == null){
					Log.d(TAG, "createNewRule is null");
				}
				if(createNewRuleFragment.getAdapter() == null){
					Log.d(TAG, "adapter is null");
				}
				createNewRuleFragment.getAdapter().setSavedState(b);
				viewPager.setCurrentItem(1);
			}
		}
	}
}
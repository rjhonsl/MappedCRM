package com.santeh.rjhonsl.samplemap.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;

import java.util.List;

public class AdapterUserMonitoring_ViewByUser extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> ItemList;
	ListView listViewItem;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;

	public AdapterUserMonitoring_ViewByUser(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.ItemList = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(tag, "Adapter Context");
	}

	private class ViewHolder {
		TextView fullName;
		TextView areaAssigned;
		TextView position;
		TextView initials;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		Log.d(tag, "Adapter Getview");
		if (view == null) {

			Log.d(tag, "if null");
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_viewusers, null);

			holder.areaAssigned = (TextView) view.findViewById(R.id.item_user_areaAssigned);
			holder.position = (TextView) view.findViewById(R.id.item_user_position);
			holder.fullName = (TextView) view.findViewById(R.id.item_user_fullname);
			holder.initials = (TextView) view.findViewById(R.id.item_user_initials);
			view.setTag(holder);
		}
		else
		{
//			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

		String userPosition="";
		if (ItemList.get(position).getUserlevel() == 2 ) {
			userPosition = "Area Manager";
			holder.initials.setBackground(context.getResources().getDrawable(R.drawable.bg_violet_oval));
		}else if (ItemList.get(position).getUserlevel() == 3 ) {
			userPosition = "Area Supervisor";
			holder.initials.setBackground(context.getResources().getDrawable(R.drawable.bg_orange_oval));
		}else if (ItemList.get(position).getUserlevel() == 4 ) {
			userPosition = "TSR/Technician";
			holder.initials.setBackground(context.getResources().getDrawable(R.drawable.bg_green1_oval));
		}else if(ItemList.get(position).getUserlevel() == 0 ){
			userPosition = "Admin";
			holder.initials.setBackground(context.getResources().getDrawable(R.drawable.bg_amber_oval));
		}else if(ItemList.get(position).getUserlevel() == 1 ){
			userPosition = "Top Management";
			holder.initials.setBackground(context.getResources().getDrawable(R.drawable.bg_skyblue_oval));
		}

		//admin dark blue
		//top = light blue
		//area manager violet
		//supervisor oragne
		//tsr green

//		 Capture position and set to the TextViews
		holder.position.setText(userPosition);//reversed this//
		holder.areaAssigned.setText("Assigned Area: N/A");//reversed this//
		holder.fullName.setText(ItemList.get(position).getFirstname() + " " + ItemList.get(position).getLastname());
		holder.initials.setText(ItemList.get(position).getFirstname().substring(0,1) + ItemList.get(position).getLastname().substring(0,1));


		return view;
	}

	@Override
	public void remove(CustInfoObject object) {
		ItemList.remove(object);
		notifyDataSetChanged();
	}

	public List<CustInfoObject> getAnswerList() {
		return ItemList;
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}

}

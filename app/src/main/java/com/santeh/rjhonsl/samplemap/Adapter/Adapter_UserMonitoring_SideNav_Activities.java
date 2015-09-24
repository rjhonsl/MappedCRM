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
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.List;

public class Adapter_UserMonitoring_SideNav_Activities extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> objArrayList;
	ListView listViewItem;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;

//	android:id="@+id/itemlv_feedconsSummary_farmname"

	public Adapter_UserMonitoring_SideNav_Activities(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.objArrayList = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(tag, "Adapter Context");
	}

	private class ViewHolder {
		TextView txtActivityName, txtDateAndTime, txtLatlong, txtnum;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {

			Log.d(tag, "if null");
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_useractivity, null);

			holder.txtActivityName = (TextView) view.findViewById(R.id.item_useractivity_txtactivity);
			holder.txtDateAndTime = (TextView) view.findViewById(R.id.item_useractivity_datetime);
			holder.txtLatlong = (TextView) view.findViewById(R.id.item_useractivity_txtLocation);
			holder.txtnum = (TextView) view.findViewById(R.id.item_useractivity_activity_num);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

//		 Capture position and set to the TextViews
		holder.txtDateAndTime.setText(Helper.convertLongtoDate_GregorianWithTime(Helper.convertDateTimeStringToMilis_DB_Format(objArrayList.get(position).getDateTime())));

		holder.txtLatlong.setText("Loc: " +
						Helper.deciformat(Double.parseDouble(objArrayList.get(position).getLatitude()), 7)
						+ ", " +
						Helper.deciformat(Double.parseDouble(objArrayList.get(position).getLongtitude()), 7)
		);
		holder.txtActivityName.setText(objArrayList.get(position).getActionDone());
		holder.txtnum.setText(objArrayList.get(position).getPondIndex()+"");

		return view;
	}

	@Override
	public void remove(CustInfoObject object) {
		objArrayList.remove(object);
		notifyDataSetChanged();
	}

	public List<CustInfoObject> getAnswerList() {
		return objArrayList;
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

package com.santeh.rjhonsl.samplemap.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;

import java.util.List;

public class AdapterPonds extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> ItemList;
	ListView listViewItem;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;

	public AdapterPonds(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.ItemList = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private class ViewHolder {
		TextView species, quantity, datestocked, pondid;
		LinearLayout initialHolder;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_manageponds, null);
			holder.species = (TextView) view.findViewById(R.id.itemlv_managepond_Species);
			holder.quantity = (TextView) view.findViewById(R.id.itemlv_managepond_quantity);
			holder.datestocked = (TextView) view.findViewById(R.id.itemlv_managepond_datestocked);
			holder.pondid = (TextView) view.findViewById(R.id.itemlv_managepond_initials);
			holder.initialHolder = (LinearLayout) view.findViewById(R.id.weeknoHOlder);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

		if (ItemList.get(position).getSpecie().equalsIgnoreCase("bangus")) {
			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_skyblue_oval));
		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("tilapia")){
			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_amber_oval));
		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("vannamei")){
			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_green_oval));
		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("prawns")){
			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_red_oval));
		}


		holder.species.setText(ItemList.get(position).getSpecie()+"");
		holder.quantity.setText("Quantity: "+ItemList.get(position).getQuantity()+"");
		holder.datestocked.setText("Date Stocked: "+ItemList.get(position).getDateStocked()+"");
		holder.pondid.setText(ItemList.get(position).getPondID()+"");
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

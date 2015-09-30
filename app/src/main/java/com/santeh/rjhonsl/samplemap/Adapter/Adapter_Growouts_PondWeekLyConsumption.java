package com.santeh.rjhonsl.samplemap.Adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

public class Adapter_Growouts_PondWeekLyConsumption extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> objArrayList;
	ListView listViewItem;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;
	ArrayList<Boolean> positionArray;
	boolean[] checked;
	int[] visiblePosArray;
	private volatile int positionCheck;


	public Adapter_Growouts_PondWeekLyConsumption(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.objArrayList = items;
		visiblePosArray = new int[objArrayList.size()];
		checked = new boolean[objArrayList.size()];
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		positionArray = new ArrayList<Boolean>(objArrayList.size());
	}

	private class ViewHolder {
		TextView txtweekno, txtfeedtype, txtactual, txtrecommended, txtremarks;
		LinearLayout weeknoHOlder, wrapper;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {

			Log.d(tag, "if null");
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_weeklypondsummary, null);

			holder.txtweekno = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_weeknum);
			holder.txtfeedtype = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_feedtype);
			holder.txtactual = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_abw);
			holder.txtrecommended = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_recommended);
			holder.txtremarks = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_remarks);

			holder.wrapper = (LinearLayout) view.findViewById(R.id.ll_item_weekLyReportWrapper);

//			holder.chkisVisited = (CheckBox) view.findViewById(R.id.chk_weeklyreport_pondsummary_isVisited);

			holder.weeknoHOlder = (LinearLayout) view.findViewById(R.id.weeknoHOlder);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");

			holder = (ViewHolder) view.getTag();
//			holder.chkisVisited.setOnCheckedChangeListener(null);

		}

		visiblePosArray[position] = objArrayList.get(position).getIsVisited();

//		if(objArrayList.get(position).getIsVisited() == 0){
//			holder.chkisVisited.setChecked(false);
//		}else{
//			holder.chkisVisited.setChecked(true);
//		}


//		holder.chkisVisited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				//					holder.chkisVisited.setChecked(false);
////					holder.chkisVisited.setChecked(true);
//				checked[position] = isChecked;
//			}
//		});

//		holder.chkisVisited.setChecked(checked[position]);


		holder.txtweekno.setText("" + objArrayList.get(position).getCurrentweekofStock());
		holder.txtfeedtype.setText("Feed Type: "+objArrayList.get(position).getCurrentfeedType());
		holder.txtremarks.setText("Remarks: "+objArrayList.get(position).getRemarks());






		if(position + 1 == objArrayList.get(position).getStartweekofStock() ) {
			holder.wrapper.setBackgroundColor(context.getResources().getColor(R.color.red_200));
			holder.txtrecommended.setTextColor(Color.parseColor("#000000"));
		}else if((position+1) == objArrayList.get(position).getWeek()) {
			holder.wrapper.setBackgroundColor(context.getResources().getColor(R.color.skyblue_100));
			holder.txtrecommended.setTextColor(Color.parseColor("#000000"));
		}
		else if ( position + 1 < objArrayList.get(position).getWeek() ) {
			holder.wrapper.setBackgroundColor(context.getResources().getColor(R.color.gray_200));
			holder.txtrecommended.setTextColor(Color.parseColor("#000000"));
		}
		else{
			holder.wrapper.setBackgroundColor(Color.parseColor("#00000000"));
			holder.txtrecommended.setTextColor(Color.parseColor("#42A5F5"));
		}



		if ( (position + 1) == objArrayList.get(position).getStartweekofStock()) {
			holder.weeknoHOlder.setBackground(context.getResources().getDrawable(R.drawable.bg_lightred_box_curvebottom));
		}else if( (position+1) == objArrayList.get(position).getWeek() ){
			holder.weeknoHOlder.setBackground(context.getResources().getDrawable(R.drawable.bg_blue_box_curvebottom));
		}else if( (position+1) == objArrayList.get(position).getWeek() && (position + 1) == objArrayList.get(position).getStartweekofStock()) {
			holder.weeknoHOlder.setBackground(context.getResources().getDrawable(R.drawable.bg_blue_box_curvebottom));
		}else  if(position + 1 < objArrayList.get(position).getWeek()){
			holder.weeknoHOlder.setBackground(context.getResources().getDrawable(R.drawable.bg_darkgray_box_curvebottom));
		}else{
			holder.weeknoHOlder.setBackground(context.getResources().getDrawable(R.drawable.bg_darkteal_box_curvebottom));
		}





		if (Double.parseDouble(objArrayList.get(position).getRecommendedConsumption()) > 0){
			holder.txtrecommended.setText("Recommended: "+ (objArrayList.get(position).getRecommendedConsumption())+"kg");
		}else{
			holder.txtrecommended.setText("Recommended: Blind Feeding");
		}

		if (objArrayList.get(position).getActualConsumption().equalsIgnoreCase("n/a")){
			holder.txtactual.setText("Actual: "+ (objArrayList.get(position).getActualConsumption())+"");
		}else{
			holder.txtactual.setText("Actual: " +objArrayList.get(position).getActualConsumption ()+ "kg");
		}


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

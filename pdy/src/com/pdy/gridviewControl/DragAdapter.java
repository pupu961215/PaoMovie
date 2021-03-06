package com.pdy.gridviewControl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.pdy.mobile.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class DragAdapter extends BaseAdapter implements DragGridListener {
	private List<HashMap<String, Object>> list;
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	public static Boolean isShowAdd = true;

	public DragAdapter(Context context, List<HashMap<String, Object>> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (isShowAdd) {
			return list.size() + 1;
		} else {
			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 由于复用convertView导致某些item消失了，�?以这里不复用item�?
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item, null);
		ImageView mImageView = (ImageView) convertView.findViewById(R.id.ic_image);
		TextView mTextView = (TextView) convertView.findViewById(R.id.ic_text);
		if (position < list.size()) {
			mImageView.setImageBitmap((Bitmap) list.get(position).get(
					"item_image"));
			mTextView.setText((CharSequence) list.get(position)
					.get("item_text"));
			mTextView.setVisibility(View.VISIBLE);
		} else {
			mImageView.setImageResource(R.drawable.import_video_add);
		}
		if (position == mHidePosition) {
			convertView.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		HashMap<String, Object> temp = list.get(oldPosition);
		if (oldPosition < newPosition) {
			for (int i = oldPosition; i < newPosition; i++) {
				Collections.swap(list, i, i + 1);
			}
		} else if (oldPosition > newPosition) {
			for (int i = oldPosition; i > newPosition; i--) {
				Collections.swap(list, i, i - 1);
			}
		}

		list.set(newPosition, temp);
	}

	@Override
	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition;
		notifyDataSetChanged();
	}

	@Override
	public void removeItem(int deletePosition) {
		list.remove(deletePosition);
		notifyDataSetChanged();
	}

}

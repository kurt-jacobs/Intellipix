package com.intellipix;

import java.io.FilenameFilter;

import android.view.View;
import android.widget.ListView;

import com.intellipix.filters.PNGFileFilter;

public class FileSelectForDelete extends FileSelect {

	// TODO - Right now, the user is confined to removing only PNG files.
	// I made this method abstract in the base class because I believe
	// we'll probably open it up and let user delete any file.
	public FilenameFilter getFilenameFilter() {
		return new PNGFileFilter();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		boolean result = fileList.get(position).delete();
		if (result) {
			fileList.remove(position);
		}
		// Refresh the view with update smaller list.
		setListAdapterForActivity();
	}

}

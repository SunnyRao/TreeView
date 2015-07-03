package com.sunnyrao.treeview;

import java.util.ArrayList;
import java.util.List;

import com.sunnyrao.treeview.adapter.SimpleTreeListViewAdapter;
import com.sunnyrao.treeview.bean.FileBean;
import com.sunnyrao.treeview.utils.Node;
import com.sunnyrao.treeview.utils.adapter.TreeListViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView mTree;
	private SimpleTreeListViewAdapter<FileBean> mAdapter;
	private List<FileBean> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTree = (ListView) findViewById(R.id.id_listView);

		initDatas();
		try {
			mAdapter = new SimpleTreeListViewAdapter<FileBean>(mTree, this,
					mData, 0);
			mTree.setAdapter(mAdapter);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		initEvent();

	}

	private void initEvent() {
		mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
			@Override
			public void onClick(Node node, int position) {
				if (node.isLeaf()) {
					Toast.makeText(MainActivity.this, node.getName(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		mTree.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// DialogFragment
				final EditText et = new EditText(MainActivity.this);
				new AlertDialog.Builder(MainActivity.this).setTitle("Add Node")
						.setView(et)
						.setPositiveButton("Sure", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (TextUtils.isEmpty(et.getText().toString()))
									return;
								mAdapter.addExtraNode(position, et.getText()
										.toString());
							}
						}).setNegativeButton("Cancel", null).show();

				return true;
			}
		});
	}

	private void initDatas() {
		mData = new ArrayList<FileBean>();
		FileBean bean = new FileBean(1, 0, "根目录1");
		mData.add(bean);
		bean = new FileBean(2, 0, "根目录2");
		mData.add(bean);
		bean = new FileBean(3, 0, "根目录3");
		mData.add(bean);
		bean = new FileBean(4, 1, "根目录1-1");
		mData.add(bean);
		bean = new FileBean(5, 1, "根目录1-2");
		mData.add(bean);
		bean = new FileBean(6, 5, "根目录1-2-1");
		mData.add(bean);
		bean = new FileBean(7, 3, "根目录3-1");
		mData.add(bean);
		bean = new FileBean(8, 3, "根目录3-2");
		mData.add(bean);
	}
}

package dev.jugo.processmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{

	private JNIInterface	jniinterface	= null;

	private ListView		listView		= null;
	private Button			btnUpdate		= null;
	private TextView		tvCount			= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) this.findViewById(R.id.listViewProcess);
		btnUpdate = (Button) this.findViewById(R.id.buttonUpdate);
		btnUpdate.setOnClickListener(updateClickListener);
		tvCount = (TextView) this.findViewById(R.id.textViewProcessCount);
		getProcessInfo();
	}

	public void getProcessInfo()
	{
		jniinterface = JNIInterface.getInstance();
		int nCount = jniinterface.doDataLoad(1);
		Logs.showTrace(String.format("process count:%d", nCount));
		String strCount = String.format("Total Process:%d", nCount);
		tvCount.setText(strCount);

		String[] values = new String[nCount];
		String[] strProcList = null;
		String strValue = null;

		for (int i = 0; i < nCount; ++i)
		{
			strProcList = jniinterface.GetProcessInfo(i);
			strValue = String.format("%s ---> %s", strProcList[0], strProcList[1]);
			values[i] = strValue;
			Logs.showTrace("Process Name: " + strProcList[0] + " ---> " + strProcList[1]);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,
				android.R.id.text1, values);

		listView.setAdapter(adapter);
	}

	private OnClickListener	updateClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														MainActivity.this.getProcessInfo();
													}
												};

}

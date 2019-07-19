package com.example.ubuntu.mymaxbox;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;
//import com.example.ubuntu.mymaxbox.adapter.SpinnerAdapter;
import com.example.ubuntu.mymaxbox.adapter.UnitAdapter;
import com.example.ubuntu.mymaxbox.adapter.UnitListAdapter;
import com.example.ubuntu.mymaxbox.entity.Unit;
import com.example.ubuntu.mymaxbox.entity.UnitListItem;
import com.example.ubuntu.mymaxbox.entity.UnitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UnitActivity extends AppCompatActivity {
    private static final String TAG = "UnitActivity";


    private ListView mUnitList;
    private EditText mEditText;
    private Spinner mSpinner;
    private GridView mGridView;

    private int selectedUnit = -1;
    private Unit[] units;
    private String editTextValue = "1";

    private List<String> spinnerList = new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    private List<String> listData;
    private int spinnerSelectedPostion;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            selectedUnit = msg.arg1;
            units = unitData.get(selectedUnit);
            spinnerList.clear();
            for( Unit unit : units ) {
                spinnerList.add(unit.getName());
            }
            spinnerAdapter = new ArrayAdapter<String>(UnitActivity.this, android.R.layout.simple_spinner_item,spinnerList);
            ((ArrayAdapter) spinnerAdapter).setDropDownViewResource(R.layout.spinner_drop_down);
//            ((ArrayAdapter) spinnerAdapter).setDropDownViewTheme();
//            spinnerAdapter = new SpinnerAdapter(spinnerList, UnitActivity.this);
            mSpinner.setAdapter(spinnerAdapter);
        }
    };

    private List<Unit[]> unitData = new ArrayList<>();

    private UnitType[] unitTypes = new UnitType[] {
            new UnitType(R.drawable.unitconverter_length,"长度"), new UnitType(R.drawable.unitconverter_area,"面积"),
            new UnitType(R.drawable.unitconverter_weight,"重量"), new UnitType(R.drawable.unitconverter_volume, "体积"),
            new UnitType(R.drawable.unitconverter_temperature,"温度"),new UnitType(R.drawable.unitconverter_time,"时间"),
            new UnitType(R.drawable.unitconverter_pressure,"压力"),new UnitType(R.drawable.unitconverter_speed,"速度"),
            new UnitType(R.drawable.unitconverter_data,"数据"),new UnitType(R.drawable.unitconverter_radioactivity,"放射性")
    };

    private List<Map<String, List<String>>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        initData();


        mUnitList = findViewById(R.id.unit_value_list);
        mEditText = findViewById(R.id.unit_edit_text);
        mEditText.setText(editTextValue);
        mSpinner = findViewById(R.id.unit_spinner);
        mGridView = findViewById(R.id.unit_grid_view);

        mGridView.setAdapter(new UnitAdapter(unitTypes, this, handler));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelectedPostion = position;
                initListData(spinnerSelectedPostion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initListData(spinnerSelectedPostion);
            }
        });

        units = unitData.get(0);
        initListData(0);
        Message message = new Message();
        message.arg1 = 0;
        handler.sendMessage(message);
    }

    private void initListData(int position) {
        Unit curUnit = units[position];
        Log.d(TAG, "curUnit: " + curUnit.getName() + "  " + curUnit.getRate());
        editTextValue = mEditText.getText().toString();
        int v = 0;
        try {
            v = Integer.valueOf(editTextValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        List<UnitListItem> listAdapterData = new ArrayList<>();
        for( int i=0; i<units.length; ++i ) {
            Log.d(TAG, ""+units[i].getName() + " : " + units[i].getRate() + " value: " + v*(units[i].getRate()/curUnit.getRate()));
            double value = v*(units[i].getRate()/curUnit.getRate());
            listAdapterData.add(new UnitListItem(value, units[i].getName()));
        }
        UnitListAdapter listAdapter = new UnitListAdapter(UnitActivity.this, R.layout.unit_list_item, listAdapterData);
        mUnitList.setAdapter(listAdapter);
    }

    private void initData() {
        Unit[] lengthData = new Unit[] {
                new Unit("mm", 1.0), new Unit("cm", 0.1),new Unit("m", 0.001),
                new Unit("km", 0.000001),new Unit("yd",0.0010936133),new Unit("ft",0.0032808399),
                new Unit("inch",0.0393700787),new Unit("mile",0.00000053996),
                new Unit("尺",0.0033),new Unit("寸",0.033),new Unit("丈",0.00033),
                new Unit("里",0.00000025463),new Unit("海里",0.00000053993)
        };
        Unit[] areaData = new Unit[] {
                new Unit("cm²", 1.0), new Unit("m²", 0.1),new Unit("km²", 0.001),
                new Unit("ha", 0.000001),new Unit("ac",0.0010936133),new Unit("ft²",0.0032808399),
                new Unit("in²",0.0393700787),new Unit("yd²",0.00000053996)
        };
        Unit[] weightData = new Unit[] {
                new Unit("mg", 1.0), new Unit("g", 0.1),new Unit("kg", 0.001),
                new Unit("t", 0.000001),new Unit("gr",0.0010936133),new Unit("oz",0.0032808399),
                new Unit("lb",0.0393700787),new Unit("斤",0.00000053996),new Unit("两",0.00000053996)
        };
        unitData.add(lengthData);
        unitData.add(areaData);
        unitData.add(weightData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
        unitData.add(lengthData);
    }

}

package com.example.ubuntu.mymaxbox;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.example.ubuntu.mymaxbox.adapter.ClothesListAdapter;
import com.example.ubuntu.mymaxbox.adapter.ClothesPantsListAdapter;
import com.example.ubuntu.mymaxbox.adapter.UnderClothesListAdapter;
import com.example.ubuntu.mymaxbox.adapter.UnitAdapter;
import com.example.ubuntu.mymaxbox.entity.Unit;
import com.example.ubuntu.mymaxbox.entity.UnitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class SizeTableActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SizeTableActivity";


    private GridView mGridView;
    private LayoutInflater mLayoutInflater;
    private FrameLayout mViewGroup;

    private Stack<View> mStackView = new Stack<>();


    private List<List<String>> femaleClothesData = new ArrayList<>();
    private List<List<String>> femalePantsData = new ArrayList<>();
    private List<List<String>> maleClothesData = new ArrayList<>();
    private List<List<String>> malePantsData = new ArrayList<>();
    private List<List<String>> selectedClothesData;
    private List<List<String>> selectedPantsData;
    private List<List<String>> underClothesData = new ArrayList<>();




    private Button femaleBtn;
    private Button maleBtn;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.arg1;
            Log.d(TAG, "pos: " + pos);
            View view = null;
            if( !mStackView.empty() ) {
                View view1 = mStackView.pop();
                mViewGroup.removeView(view1);
            }
            switch (pos) {
                case 0:
                    view = displayClothesView();
                    break;
                case 1:
//                    view = mLayoutInflater.inflate(R.layout.size_table_hat, null);
                    view = displayClothesView();
                    break;
                case 2:
                    view = displayUnderClothesView();
                    break;
                case 3:
//                    view = mLayoutInflater.inflate(R.layout.size_table_ring, null);
                    view = displayClothesView();
                    break;
                case 4:
//                    view = mLayoutInflater.inflate(R.layout.size_table_shoes, null);
                    view = displayUnderClothesView();
                    break;
                default:
                    break;
            }
            mViewGroup.addView(view);
            mStackView.push(view);
        }
    };

    private List<Unit[]> unitData = new ArrayList<>();

    private UnitType[] unitTypes = new UnitType[] {
            new UnitType(R.drawable.unitconverter_length,"衣服"), new UnitType(R.drawable.unitconverter_area,"帽子"),
            new UnitType(R.drawable.unitconverter_weight,"内衣"), new UnitType(R.drawable.unitconverter_volume, "指环"),
            new UnitType(R.drawable.unitconverter_temperature,"鞋子")
    };

    private View displayClothesView() {
        View view = mLayoutInflater.inflate(R.layout.size_table_clothes,null);
        femaleBtn = view.findViewById(R.id.size_table_female);
        maleBtn = view.findViewById(R.id.size_table_male);
        femaleBtn.setOnClickListener(SizeTableActivity.this);
        maleBtn.setOnClickListener(SizeTableActivity.this);
        ListView clothesListView = view.findViewById(R.id.size_table_clothes);
        clothesListView.setAdapter(new ClothesListAdapter(SizeTableActivity.this, R.layout.size_table_clothes_item, selectedClothesData));
        ListView clothesPantsListView = view.findViewById(R.id.size_table_clothes_pants);
        clothesPantsListView.setAdapter(new ClothesPantsListAdapter(SizeTableActivity.this, R.layout.size_table_clothes_pants_item, selectedPantsData));
        return view;
    }

    private View displayUnderClothesView() {
        View view = mLayoutInflater.inflate(R.layout.size_table_underclothes, null);
        ListView underClothesListView = view.findViewById(R.id.size_table_under_clothes_list_view);
        underClothesListView.setAdapter(new UnderClothesListAdapter(SizeTableActivity.this, R.layout.size_table_under_clothes_item, underClothesData));
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_table);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //在Toolbar中加入滑动菜单提示按钮
        if( actionBar!=null ) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("尺码表");
        }

        initData();
        mLayoutInflater = LayoutInflater.from(this);

        mViewGroup = findViewById(R.id.size_table_view_group);
        mGridView = findViewById(R.id.size_table_grid_view);

        mGridView.setAdapter(new UnitAdapter(unitTypes, this, handler));

        selectedClothesData = femaleClothesData;
        selectedPantsData = femalePantsData;
        Message message = new Message();
        message.arg1 = 0;
        handler.sendMessage(message);
    }


    private void initData() {
        femaleClothesData.add(Arrays.asList(new String[]{"～0(～XXS)", "～2", "～32", "～34", " ", "～XS"}));
        femaleClothesData.add(Arrays.asList(new String[]{"2(～XS,S)", "4", "34", "36", "44(85)", "S"}));
        femaleClothesData.add(Arrays.asList(new String[]{"4(～XS,S)", "6", "36", "68", "55(90)", "S"}));
        femaleClothesData.add(Arrays.asList(new String[]{"6(～S,M)", "8", "38", "40", "66(95)", "M"}));
        femaleClothesData.add(Arrays.asList(new String[]{"8(～S,M)", "10", "40", "42", "77(100)", "M"}));
        femaleClothesData.add(Arrays.asList(new String[]{"10(～M,L)", "12", "42", "44", "88(105)", "L"}));
        femaleClothesData.add(Arrays.asList(new String[]{"12(～M,L)", "14", "44", "46", "110", "L～"}));
        femaleClothesData.add(Arrays.asList(new String[]{"14(L)", "16", "46", "48", " ", "XL"}));
        femaleClothesData.add(Arrays.asList(new String[]{"16(L)", "18～", "48～", "50～", " ", "XL～"}));
        femalePantsData.add(Arrays.asList(new String[]{"26", "56"}));
        femalePantsData.add(Arrays.asList(new String[]{"27", "58"}));
        femalePantsData.add(Arrays.asList(new String[]{"28", "61"}));
        femalePantsData.add(Arrays.asList(new String[]{"29", "63"}));
        femalePantsData.add(Arrays.asList(new String[]{"30", "66"}));
        femalePantsData.add(Arrays.asList(new String[]{"31", "68"}));
        femalePantsData.add(Arrays.asList(new String[]{"32", "71"}));
        femalePantsData.add(Arrays.asList(new String[]{"33", "73"}));
        femalePantsData.add(Arrays.asList(new String[]{"34", "76"}));
        femalePantsData.add(Arrays.asList(new String[]{"35", "79"}));
        femalePantsData.add(Arrays.asList(new String[]{"36", "81"}));

        maleClothesData.add(Arrays.asList(new String[]{"～34(～XS,S)", "～34", "～38", "～44", "85", "～S"}));
        maleClothesData.add(Arrays.asList(new String[]{"36(～XS,S)", "36", "40", "46", "90", "S"}));
        maleClothesData.add(Arrays.asList(new String[]{"38(～S,M)", "38", "42", "48", "95", "M"}));
        maleClothesData.add(Arrays.asList(new String[]{"40(～S,M)", "40", "44", "50", "95", "M"}));
        maleClothesData.add(Arrays.asList(new String[]{"42(～M,L)", "42", "46", "52", "100", "L"}));
        maleClothesData.add(Arrays.asList(new String[]{"44(～M,L)", "44", "48", "54", "105", "L"}));
        maleClothesData.add(Arrays.asList(new String[]{"46～(M,L～)", "46～", "50～", "56～", "110", "XL～"}));
        malePantsData.add(Arrays.asList(new String[]{"26", "66"}));
        malePantsData.add(Arrays.asList(new String[]{"27", "68"}));
        malePantsData.add(Arrays.asList(new String[]{"28", "71"}));
        malePantsData.add(Arrays.asList(new String[]{"29", "73"}));
        malePantsData.add(Arrays.asList(new String[]{"30", "76"}));
        malePantsData.add(Arrays.asList(new String[]{"31", "78"}));
        malePantsData.add(Arrays.asList(new String[]{"32", "81"}));
        malePantsData.add(Arrays.asList(new String[]{"33", "83"}));
        malePantsData.add(Arrays.asList(new String[]{"34", "86"}));
        malePantsData.add(Arrays.asList(new String[]{"35", "89"}));
        malePantsData.add(Arrays.asList(new String[]{"36", "91"}));
        malePantsData.add(Arrays.asList(new String[]{"38", "96"}));
        malePantsData.add(Arrays.asList(new String[]{"40", "101"}));
        malePantsData.add(Arrays.asList(new String[]{"42", "106"}));
        malePantsData.add(Arrays.asList(new String[]{"44", "111"}));
        malePantsData.add(Arrays.asList(new String[]{"46", "116"}));
        malePantsData.add(Arrays.asList(new String[]{"48", "121"}));
        malePantsData.add(Arrays.asList(new String[]{"50", "126"}));

        underClothesData.add(Arrays.asList(new String[]{"30A", "80A", "65A", "A65", "75/65"}));
        underClothesData.add(Arrays.asList(new String[]{"32A", "85A", "70A", "A70", "80/70"}));
        underClothesData.add(Arrays.asList(new String[]{"34A", "90A", "75A", "A75", "85/75"}));
        underClothesData.add(Arrays.asList(new String[]{"36A", "95A", "80A", "A80", "90/80"}));
        underClothesData.add(Arrays.asList(new String[]{"38A", "100A", "95A", "A85", "95/85"}));
        underClothesData.add(Arrays.asList(new String[]{"40A", "105A", "100A", "A90", "100/90"}));
        underClothesData.add(Arrays.asList(new String[]{"30B", "80B", "65B", "B65", "78/65"}));
        underClothesData.add(Arrays.asList(new String[]{"32B", "85B", "70B", "B75", "88/75"}));
        underClothesData.add(Arrays.asList(new String[]{"36B", "95B", "80B", "B80", "93/80"}));
        underClothesData.add(Arrays.asList(new String[]{"38B", "100B", "85B", "B85", "98/85"}));
        underClothesData.add(Arrays.asList(new String[]{"40B", "105B", "90B", "B90", "103/90"}));
        underClothesData.add(Arrays.asList(new String[]{"30C", "80C", "60C", "C65", "80/65"}));
        underClothesData.add(Arrays.asList(new String[]{"34C", "90C", "75C", "C75", "85/70"}));
        underClothesData.add(Arrays.asList(new String[]{"36C", "95C", "80C", "C80", "95/80"}));
        underClothesData.add(Arrays.asList(new String[]{"38C", "100C", "85C", "C85", "100/85"}));
        underClothesData.add(Arrays.asList(new String[]{"40C", "105C", "90C", "C90", "105/90"}));
        underClothesData.add(Arrays.asList(new String[]{"30D", "80D", "65D", "D65", "83/65"}));
        underClothesData.add(Arrays.asList(new String[]{"34D", "90D", "75D", "D75", "93/75"}));
        underClothesData.add(Arrays.asList(new String[]{"36D", "95D", "80D", "D80", "98/80"}));
        underClothesData.add(Arrays.asList(new String[]{"38D", "100D", "85D", "D85", "103/85"}));
        underClothesData.add(Arrays.asList(new String[]{"40D", "105D", "90D", "D90", "108/90"}));
        underClothesData.add(Arrays.asList(new String[]{"30E", "80E", "65E", "E65", "85/65"}));
        underClothesData.add(Arrays.asList(new String[]{"32E", "85E", "70E", "E70", "90/70"}));
        underClothesData.add(Arrays.asList(new String[]{"34E", "90E", "75E", "E75", "95/75"}));
        underClothesData.add(Arrays.asList(new String[]{"36E", "95E", "80E", "E80", "100/80"}));
        underClothesData.add(Arrays.asList(new String[]{"38E", "100E", "85E", "E85", "105/90"}));
        underClothesData.add(Arrays.asList(new String[]{"30F", "80F", "65F", "F65", "88/65"}));
        underClothesData.add(Arrays.asList(new String[]{"32F", "85F", "70F", "F70", "93/70"}));
        underClothesData.add(Arrays.asList(new String[]{"34F", "90F", "75F", "F75", "98/75"}));
        underClothesData.add(Arrays.asList(new String[]{"36F", "95F", "80F", "F80", "103/80"}));
        underClothesData.add(Arrays.asList(new String[]{"30F", "105F", "90F", "F90", "113/90"}));
    }

    @Override
    public void onClick(View v) {
        Message message = new Message();
        switch (v.getId()) {
            case R.id.size_table_female:
                selectedClothesData = femaleClothesData;
                selectedPantsData = femalePantsData;
                message.arg1 = 0;
                handler.sendMessage(message);
                break;
            case R.id.size_table_male:
                selectedClothesData = maleClothesData;
                selectedPantsData = malePantsData;
                message.arg1 = 0;
                handler.sendMessage(message);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {//HomeAsUp的点击事件，返回箭头按钮
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

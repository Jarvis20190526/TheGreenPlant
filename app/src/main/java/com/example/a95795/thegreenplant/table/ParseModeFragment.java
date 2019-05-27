package com.example.a95795.thegreenplant.table;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.tools.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import comc.example.administrator.form.component.IComponent;
import comc.example.administrator.form.core.SmartTable;
import comc.example.administrator.form.core.TableConfig;
import comc.example.administrator.form.data.CellInfo;
import comc.example.administrator.form.data.column.Column;
import comc.example.administrator.form.data.column.ColumnInfo;
import comc.example.administrator.form.data.format.IFormat;
import comc.example.administrator.form.data.format.bg.BaseBackgroundFormat;
import comc.example.administrator.form.data.format.bg.BaseCellBackgroundFormat;
import comc.example.administrator.form.data.format.bg.ICellBackgroundFormat;
import comc.example.administrator.form.data.format.draw.TextImageDrawFormat;
import comc.example.administrator.form.data.format.tip.MultiLineBubbleTip;
import comc.example.administrator.form.data.format.title.TitleImageDrawFormat;
import comc.example.administrator.form.data.style.FontStyle;
import comc.example.administrator.form.data.table.TableData;
import comc.example.administrator.form.listener.OnColumnClickListener;
import comc.example.administrator.form.utils.DensityUtils;


public class ParseModeFragment extends Fragment implements View.OnClickListener{
    private Button btn;
    private SmartTable<EnvironmentInfo> table;
    private BaseCheckDialog<TableStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    private Map<String,Bitmap> map = new HashMap<>();
    private Column<String> nameColumn;
    private Column<String> timeColumn;
    private View view;

//    private List<Integer> list = new ArrayList<>(); //数据集合
//    private String city = "beijing";
//    private String TAG = "volley";
//    private String json;
//    private JSONArray HeWeather6;
//    private JSONObject now;
//    private int tmp,pm,hum;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table, container, false);

        initView();
        initTable();
//        getEnvironmentInfoList();
        return view;
    }



    public void initView(){
        quickChartDialog = new QuickChartDialog();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this.getContext(),15)); //设置全局字体大小
        table = (SmartTable<EnvironmentInfo>) view.findViewById(R.id.table);
        btn = view.findViewById(R.id.btn_tb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStyle();
            }
        });
    }

    public void getEnvironmentInfoList() {
        String url = "http://192.168.43.26:8080/shop/queryEnvironmentInfo";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject r) {
                        Log.d("volley", r.toString());
                        try {
                            List<EnvironmentInfo> environmentInfoList = new Gson().fromJson(r.getString("EnvironmentInfo").toString(), new TypeToken<List<EnvironmentInfo>>() {
                            }.getType());
                            Log.e("6666", "onResponse: "+environmentInfoList.get(0).toString() );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", error.toString());
                    }
                }
        );
        MyApplication.addRequest(jsonObjectRequest, "getEnvironmentInfoList");
    }

    public void initTable(){
        final List<EnvironmentInfo> testData = new ArrayList<>();
        Random random = new Random();
        //测试 从其他地方获取url
// public UserInfo(String name, int vel, int tmp, int hum, int pm, boolean isTmp, boolean isHum, boolean isPM, long time, Boolean isEvent) {
        EnvironmentInfo data1 = new EnvironmentInfo("车间1",getStringDateShort(),15,36,70,true,true,false
                ,false);
        EnvironmentInfo data2 = new EnvironmentInfo("车间2",getStringDateShort(),35,56,88,false,true,true
                ,true);
        EnvironmentInfo data3 = new EnvironmentInfo("车间3",getStringDateShort(),20,36,32,true,false,false
                ,false);
        EnvironmentInfo data4 = new EnvironmentInfo("车间4",getStringDateShort(),22,36,68,false,true,true
                ,true);

        testData.add(data1);
        testData.add(data2);
        testData.add(data3);
        testData.add(data4);


        nameColumn = new Column<>("车间", "emName");
        nameColumn.setAutoCount(true);
        nameColumn.setFixed(false);
        int size = DensityUtils.dp2px(this.getContext(),15);

        Column<Integer> tmpColumn = new Column<>("温度(℃)", "tmp");
        tmpColumn.setAutoCount(true);
        Column<Integer> humColumn = new Column<>("湿度(RH)", "hum");
        humColumn.setAutoCount(true);
        Column<Integer> pmColumn = new Column<>("PM2.5(μg/m3)", "pm");
        pmColumn.setAutoCount(true);


        timeColumn = new Column<>("日期", "time");
        timeColumn.setAutoCount(true);

        Column<Boolean> column_wd = new Column<>("温度", "isTmp" , new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ParseModeFragment.this.getContext();
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });

        Column<Boolean> column_sd = new Column<>("湿度", "isHum", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ParseModeFragment.this.getContext();
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });

        Column<Boolean> column_pm = new Column<>("PM2.5", "isPM", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ParseModeFragment.this.getContext();
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });


        final IFormat<Long> format =  new IFormat<Long>() {
            @Override
            public String format(Long aLong) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(aLong);
                return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            }
        };


//        timeColumn = new Column<>("时间", "time",format);
//        timeColumn.setCountFormat(new ICountFormat<Long, Long>() {
//            private long maxTime;
//            @Override
//            public void count(Long aLong) {
//                if(aLong > maxTime){
//                    maxTime = aLong;
//                }
//            }
//
//            @Override
//            public Long getCount() {
//                return maxTime;
//            }
//
//            @Override
//            public String getCountString() {
//                return format.format(maxTime);
//            }
//
//            @Override
//            public void clearCount() {
//                maxTime =0;
//            }
//        });
        timeColumn.setFixed(true);

        Column totalColumn1 = new Column("日平均监测值",tmpColumn,humColumn,pmColumn);
        Column totalColumn2 = new Column("达标情况",column_wd,column_sd,column_pm);
        Column totalColumn = new Column("监测因子",totalColumn1,totalColumn2);

        Column<Boolean> countColumn = new Column<>("综合评价", "isEvent", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ParseModeFragment.this.getContext();
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });

        final TableData<EnvironmentInfo> tableData = new TableData<>("环境监测数据表格",testData,nameColumn,
                timeColumn,totalColumn,countColumn);
        tableData.setShowCount(true);
        tableData.setShowCount(false);//关闭统计行
        table.getConfig().setShowTableTitle(true);

        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
        table.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {

            }
        });
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size,size, TitleImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ParseModeFragment.this.getContext();
            }

            @Override
            protected int getResourceID(Column column) {
                if(!column.isParent()){
                    if(tableData.getSortColumn() == column){
                        setDirection(TextImageDrawFormat.RIGHT);
                        if(column.isReverseSort()){
                            return R.mipmap.sort_up;
                        }
                        return R.mipmap.sort_down;

                    }else{
                        setDirection(TextImageDrawFormat.LEFT);
                        if(column == nameColumn){
                            return R.mipmap.name;
                        }else if(column == timeColumn){
                            return R.mipmap.update;
                        }
                    }
                    return 0;
                }
                setDirection(TextImageDrawFormat.LEFT);
                int level = tableData.getTableInfo().getMaxLevel()-column.getLevel();
                if(level ==0){
                    return R.mipmap.level1;
                }else if(level ==1){
                    return R.mipmap.level2;
                }
                return 0;
            }
        });

        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(this.getContext(),R.mipmap.round_rect,R.mipmap.triangle,fontStyle) {
            @Override
            public boolean isShowTip(Column column, int position) {
                if(column == nameColumn){
                    return true;
                }
                return false;
            }


            @Override
            public String[] format(Column column, int position) {
                EnvironmentInfo data = testData.get(position);
                String[] strings = {"批注","姓名："+data.getEmName()};
                return strings;
            }
        };
        tip.setColorFilter(Color.parseColor("#FA8072"));
        tip.setAlpha(0.8f);
        table.getProvider().setTip(tip);

        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if(!columnInfo.column.isParent()) {

                    table.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());
                }
                Toast.makeText(ParseModeFragment.this.getContext(),"点击了"+columnInfo.column.getColumnName(),Toast.LENGTH_SHORT).show();
            }
        });
        table.getConfig().setTableTitleStyle(new FontStyle(this.getContext(),15,getResources().getColor(R.color.arc1)).setAlign(Paint.Align.CENTER));
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.row %2 == 0) {
                    return ContextCompat.getColor(ParseModeFragment.this.getContext(), R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }


        };
        ICellBackgroundFormat<Integer> backgroundFormat2 = new BaseCellBackgroundFormat<Integer>() {
            @Override
            public int getBackGroundColor(Integer position) {
                if(position%2 == 0){
                    return ContextCompat.getColor(ParseModeFragment.this.getContext(),R.color.arc1);
                }
                return TableConfig.INVALID_COLOR;

            }


            @Override
            public int getTextColor(Integer position) {
                if(position%2 == 0) {
                    return ContextCompat.getColor(ParseModeFragment.this.getContext(), R.color.white);
                }
                return TableConfig.INVALID_COLOR;
            }
        };
        table.getConfig().setContentCellBackgroundFormat(backgroundFormat)
                .setYSequenceCellBgFormat(backgroundFormat2);
        table.setTableData(tableData);
        table.getConfig().setSequenceHorizontalPadding(50);
    }

    @Override
    public void onClick(View view) {
        changedStyle();
    }


    private void changedStyle() {

        if (chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("表格设置", new BaseCheckDialog.OnCheckChangeListener<TableStyle>() {
                @Override
                public String getItemText(TableStyle chartStyle) {
                    return chartStyle.value;
                }

                @Override
                public void onItemClick(TableStyle item, int position) {
                    switch (item) {
                        case FIXED_TITLE:
                            fixedTitle(item);
                            break;
                        case FIXED_X1_AXIS:
                            fixedXAxis(item);
                            break;
                        case FIXED_X2_AXIS:
                            fixedX2Axis(item);
                            break;
                        case FIXED_Y_AXIS:
                           fixedYAxis(item);
                            break;
                        case FIXED_COUNT_ROW:
                            fixedCountRow(item);
                            break;
                        case ZOOM:
                            zoom(item);
                            break;
                        case SHOW_SEQ:
                            showSeq(item);
                            break;

                    }
                }
            });
        }
        ArrayList<TableStyle> items = new ArrayList<>();

        items.add(TableStyle.FIXED_X1_AXIS);
        items.add(TableStyle.FIXED_X2_AXIS);
        items.add(TableStyle.FIXED_Y_AXIS);
        items.add(TableStyle.FIXED_TITLE);
        items.add(TableStyle.ZOOM);
        items.add(TableStyle.SHOW_SEQ);
        chartDialog.show(this.getContext(), true, items);
    }

    private void zoom(TableStyle item) {
        quickChartDialog.showDialog(this.getActivity(), item, new String[]{"缩放", "不缩放"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.setZoom(true,3,1);
                } else if (position == 1) {
                    table.setZoom(false,3,1);
                }
                table.invalidate();
            }
        });
    }

    private void showSeq(TableStyle item) {
        quickChartDialog.showDialog(this.getActivity(), item, new String[]{"显示", "不显示"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                   table.getConfig().setShowXSequence(true).setShowYSequence(true);
                } else if (position == 1) {
                    table.getConfig().setShowXSequence(false).setShowYSequence(false);
                }
                table.notifyDataChanged();
            }
        });
    }

    //固定顶行
    private void fixedXAxis(TableStyle c) {
        quickChartDialog.showDialog(this.getActivity(), c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
//                    table.getConfig().setFixedXSequence(true);
                    nameColumn.setFixed(true);
                } else if (position == 1) {
//                    table.getConfig().setFixedXSequence(false);
                    nameColumn.setFixed(false);
                }
               table.invalidate();
            }
        });
    }
    private void fixedX2Axis(TableStyle c) {
        quickChartDialog.showDialog(this.getActivity(), c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    timeColumn.setFixed(true);
                } else if (position == 1) {
                    timeColumn.setFixed(false);
                }
                table.invalidate();
            }
        });
    }
    private void fixedYAxis(TableStyle c) {

        quickChartDialog.showDialog(this.getActivity(), c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedYSequence(true);
                } else if (position == 1) {
                    table.getConfig().setFixedYSequence(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedTitle(TableStyle c) {

        quickChartDialog.showDialog(this.getActivity(), c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {
            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedTitle(true);
                } else if (position == 1) {
                    table.getConfig().setFixedTitle(false);
                }
                table.invalidate();
            }
        });
    }


    private void fixedCountRow(TableStyle c) {

        quickChartDialog.showDialog(this.getActivity(), c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedCountRow(true);
                } else if (position == 1) {
                    table.getConfig().setFixedCountRow(false);
                }
                table.invalidate();
            }
        });
    }

    /**
     * 测试是否可以兼容之前smartChart
     * @param tableName
     * @param chartYDataList
     * @param list
     */
    private void showChartDialog(String tableName, List<String> chartYDataList, List<Integer> list ){
        View chartView = View.inflate(this.getContext(),R.layout.dialog_chart,null);
        LineChart lineChart = (LineChart) chartView.findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        com.daivd.chart.data.style.FontStyle.setDefaultTextSpSize(this.getContext(),12);
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        ArrayList<String> ydataList = new ArrayList<>();
        for(int i = 0;i <30;i++){
            String value = chartYDataList.get(i);
            ydataList.add(value);
        }
        for(int i = 0;i <30;i++){
            int value = list.get(i);
            tempList1.add(Double.valueOf(value));
        }
        LineData columnData1 = new LineData(tableName,"", IAxis.AxisDirection.LEFT,getResources().getColor(R.color.arc1),tempList1);
        ColumnDatas.add(columnData1);
        ChartData<LineData> chartData2 = new ChartData<>("Area Chart",ydataList,ColumnDatas);
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChart.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(this.getContext(),1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this.getContext(),1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this.getContext(),1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        lineChart.getProvider().setCross(new VerticalCross());
        lineChart.getProvider().setShowText(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(this.getContext()));

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题样式
        com.daivd.chart.data.style.FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this.getContext(),15);
        LevelLine levelLine = new LevelLine(30);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this.getContext(),1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.SQUARE);
        lineChart.getProvider().setArea(true);
        lineChart.getHorizontalAxis().setRotateAngle(90);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(400);
        BaseDialog dialog = new  BaseDialog.Builder(this.getContext()).setFillWidth(true).setContentView(chartView).create();
        dialog.show();
    }

//   获取现在的时间
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chartDialog = null;
        quickChartDialog = null;
    }
}

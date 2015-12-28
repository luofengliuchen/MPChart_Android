package luofeng.mpchart_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LineChart lineView;
    private int[] colors = {0XFFABCBED,0XFF00D700};
    private float yAxisMax = Float.MIN_VALUE;
    private float yAxisMin = Float.MAX_VALUE;
    private float yAxisGap = 0.1f;
    private MyMarkerView mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineView = (LineChart) findViewById(R.id.lc_chart);
        addLineChartView();
    }

    private void addLineChartView(){

        Resources resource = getResources();

        lineView.setDescription("图表样例");
        lineView.setScaleEnabled(false);
        lineView.getAxisRight().setEnabled(true);
        lineView.setDrawGridBackground(true);
        lineView.setTouchEnabled(true);
        lineView.getLegend().setEnabled(false);
        lineView.setHardwareAccelerationEnabled(true);
        lineView.setGridBackgroundColor(0X00EDF4FC);
        lineView.setSpecialMarkerView(true);
        lineView.setSpecialMarkerCircle(BitmapFactory
                .decodeResource(getResources(), R.drawable.icon_point));
        lineView.setHighlightAutoDismiss(true);
        lineView.setForceTouchEventEnable(true);
        lineView.setSpecialMarkerLineColor(Color.RED);
        mv = new MyMarkerView(this, R.layout.custom_marker_assets_view);

        // set the marker to the chart
        lineView.setMarkerView(mv);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        lineView.setHighLightIndicatorEnabled(true);

        ArrayList<Entry> yRawData = new ArrayList<Entry>();
        ArrayList<String> xRawDatas = new ArrayList<String>();
        int index = 0;
        for (int i = 0; i<30; i++)
        {
            Random random = new Random();
            float num = (float) random.nextDouble()*1000;

            yRawData.add(new Entry(num, index));
            xRawDatas.add("8-8");
            index++;
            initMaxMin(num);
        }
        /**
         * x轴样式设置
         */
        XAxis xAxis = lineView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴在底部显示
        xAxis.setDrawGridLines(true);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.enableAxisDashedLine(10f, 10f, 0f);
        xAxis.setLabelsToSkip(5);
        /**
         * y轴样式设置
         */
        YAxis leftAxis = lineView.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setDrawLabels(false);
//        leftAxis.setTextColor(resource.getColor(R.color.grey_low_txt));
//        leftAxis.setAxisLineColor(resource.getColor(R.color.color_dddddd));
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.enableAxisDashedLine(10f, 10f, 0f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        leftAxis.setDrawAxisLine(false);
//        leftAxis.setEnabled(false);
//        leftAxis.setGridColor(resource.getColor(R.color.color_dddddd));
        leftAxis.setAxisMaxValue(yAxisMax + yAxisGap+500); // 设置Y轴最大值
        leftAxis.setAxisMinValue(yAxisMin - yAxisGap-500);// 设置Y轴最小值
//        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineView.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setAxisLineColor(0xffdddddd);
        /**
         * 曲线样式设置
         */
        LineDataSet set = new LineDataSet(yRawData, "");
        //允许平滑
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setLineWidth(dip2px(this, 1.0f));
        set.setColor(0XFFFF3B3B);
//        set.setCircleSize(dip2px(this, 2.0f));
        set.setFillAlpha(128);
        set.setDrawFilled(true);
        set.setDrawValues(false);
        set.setDrawCircleHole(false);
        set.setDrawCircles(true);
//        set.setDrawDoubleColor(true);
//        set.setCircleColors(colors);
        set.setFillColor(0XFFABCBED);
        set.setDrawFillMiddleValueEnable(true);
        set.setDrawFillMiddleValue(Float.MIN_VALUE);
        set.setFillColorGradualChange(true);
        int[] colors2 = {0Xff172743, 0xff662e41, 0xffbd353d, 0XFFFF3B3B};
        set.setFillGradualColors(colors2);
        set.setDrawVerticalHighlightIndicator(true);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setLastPointIndicator(true, BitmapFactory.decodeResource(getResources(),R.drawable.popup));
        set.setLastPointBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_red_point)
                ,BitmapFactory.decodeResource(getResources(),R.drawable.icon_red_point));
        set.setLastPointIndecatorUnit("¥");

        //设置平滑度
        set.setCubicIntensity(0.15f);
        LineData data = new LineData(xRawDatas, set);
        lineView.setData(data);
        lineView.invalidate();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取最大最小年化率
     * @param currentNum
     */
    private void initMaxMin(float currentNum)
    {
        if (currentNum >= yAxisMax)
        {
            yAxisMax = currentNum;
        }

        if (currentNum < yAxisMin)
        {
            yAxisMin = currentNum;
        }
    }


}

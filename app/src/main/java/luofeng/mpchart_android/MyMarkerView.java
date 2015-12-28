package luofeng.mpchart_android;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private TextView tvDate;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvDate = (TextView) findViewById(R.id.tv_date);
    }
    
    
    @Override
    public int getXOffset() {
        return -(getWidth() / 2);
    }
    
    @Override
    public int getYOffset() {
        return -getHeight();
    }
    
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        System.out.println("e.getData()"+e.getData()+"index:"+e.getXIndex());
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
        }
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight, String xLable) {
        System.out.println("xLable"+xLable);
        DecimalFormat df = new DecimalFormat("###,##0.00");
        tvDate.setText(xLable);
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            
            String lastValueStr = df.format(new BigDecimal(ce.getHigh()));
            tvContent.setText("¥" + lastValueStr);
        } else {
            String lastValueStr = df.format(new BigDecimal(e.getVal()));
            tvContent.setText("¥" + lastValueStr);
        }
    }
    

	/**
	 * TODO (描述该方法的实现功能)
	 * @see com.github.mikephil.charting.components.MarkerView#refreshContent(int, int, List)
	 */
	@Override
	public void refreshContent(int highlightXIndex, int highlightDataSetIndex, List<? extends DataSet<? extends Entry>> e) {
		
		// TODO Auto-generated method stub
		
	}
}
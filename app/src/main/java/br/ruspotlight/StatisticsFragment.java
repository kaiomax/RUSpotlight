package br.ruspotlight;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StatisticsFragment extends Fragment {
    private PieChart mChart;

    private static final String[] xData = {"★", "★★", "★★★", "★★★★", "★★★★★"};

    Toast toast;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        mChart = (PieChart)v.findViewById(R.id.chart);

        setupChart();


        // Os dados a pegar do firebase (Quantos votos para cada numero de estrelas)
        // indice 0 -> Quantos votaram 1 estrela
        // indice 1 -> Quantos votaram 2 estrelas
        // ...
        // indice 4 -> Quantos votaram 5 estrelas
        List<Integer> yData;

        // Um exemplo para testar
        yData = Arrays.asList(5, 10, 15, 30, 40);

        addData(yData);
        return v;
    }

    private void setupChart() {
        mChart.setUsePercentValues(true);

        //mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.TRANSPARENT);
        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(64);
        mChart.setDrawCenterText(true);
        mChart.setDescription("");
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e==null) {
                    return;
                }

                showToast(xData[dataSetIndex] + " == " + e.getVal() + "%");
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addData(List<Integer> yData) {
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < yData.size(); ++i) {
            yVals1.add(new Entry(yData.get(i), i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < xData.length; ++i) {
            xVals.add(xData[i]);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(0xFFFF0000);
        colors.add(0xFFFFA500);
        colors.add(0xFFFFFF00);
        colors.add(0xFF9ACD32);
        colors.add(0xFF00FF00);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }



    void showToast(String text)
    {
        if(toast != null)
        {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }
}

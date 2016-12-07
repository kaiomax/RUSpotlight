package br.ruspotlight;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.content.res.AppCompatResources.getDrawable;


public class StatisticsFragment extends Fragment {

    private PieChart lunchChart, dinnerChart;
    private TextView dateView;
    private Button dayBtn;
    private Button weekBtn;
    private Button monthBtn;

    private static final String[] xData = {"★", "★★", "★★★", "★★★★", "★★★★★"};
    private Calendar calendar = Calendar.getInstance();
    private int buttonIdChecked = R.id.dayBtn; // id do botao atualmente ativo (Dia, Semana ou Mes)


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

        View statisticsView = inflater.inflate(R.layout.fragment_statistics, container, false);

        lunchChart = (PieChart) statisticsView.findViewById(R.id.chart_lunch);
        setChartOptions(lunchChart);

        dinnerChart = (PieChart) statisticsView.findViewById(R.id.chart_dinner);
        setChartOptions(dinnerChart);

        dateView = (TextView) statisticsView.findViewById(R.id.dateView);
        dayBtn = (Button) statisticsView.findViewById(R.id.dayBtn);
        weekBtn = (Button) statisticsView.findViewById(R.id.weekBtn);
        monthBtn = (Button) statisticsView.findViewById(R.id.monthBtn);

        View.OnClickListener onPreviousOrNextDayClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousOrNextDayClicked(v);
            }
        };

        View.OnClickListener onDayWeekOrMonthButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayWeekOrMonthButtonClicked(v);
            }
        };

        statisticsView.findViewById(R.id.previousDay).setOnClickListener(onPreviousOrNextDayClickedListener);

        statisticsView.findViewById(R.id.nextDay).setOnClickListener(onPreviousOrNextDayClickedListener);

        statisticsView.findViewById(R.id.dayBtn).setOnClickListener(onDayWeekOrMonthButtonClickedListener);

        statisticsView.findViewById(R.id.weekBtn).setOnClickListener(onDayWeekOrMonthButtonClickedListener);

        statisticsView.findViewById(R.id.monthBtn).setOnClickListener(onDayWeekOrMonthButtonClickedListener);

        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateView();
            }

        };

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), onDateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return statisticsView;
    }

    @Override
    public void onActivityCreated(Bundle t) {
        super.onActivityCreated(t);
        updateView();
    }

    private void setChartOptions(PieChart chart) {
        Paint p = chart.getPaint(PieChart.PAINT_INFO);
        p.setTextSize(32);
        p.setColor(0xFF444444);
        chart.setNoDataText("Dados indisponíveis.");
        chart.setUsePercentValues(true);
        chart.setHoleColor(Color.TRANSPARENT);
        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(40f);
        chart.setDrawCenterText(true);
        chart.setDescription("");
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.getLegend().setEnabled(false);
    }

    private List<Integer> calculateRatings(Map<String, Integer> ratings) {
        Map<Integer, Integer> ratingsMap = new HashMap<Integer, Integer>();
        ratingsMap.put(1, 0);
        ratingsMap.put(2, 0);
        ratingsMap.put(3, 0);
        ratingsMap.put(4, 0);
        ratingsMap.put(5, 0);

        for(Integer rating : ratings.values()) {
            ratingsMap.put(rating, ratingsMap.get(rating) + 1);
        }

        return Arrays.asList(
                ratingsMap.get(1),
                ratingsMap.get(2),
                ratingsMap.get(3),
                ratingsMap.get(4),
                ratingsMap.get(5));
    }

    private void updateChartReference(final String mealKey, final PieChart chart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("meals").child(mealKey);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Integer>> t = new GenericTypeIndicator<Map<String, Integer>>() {};
                Map<String, Integer> ratings = dataSnapshot.child("ratings").getValue(t);
                if( ratings == null ) {
                    clearChartData(chart);
                }
                else {
                    setChartData(chart, calculateRatings(ratings));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void clearChartData(PieChart chart) {
        chart.clear();
        chart.setData(null);
        chart.highlightValues(null);
        chart.invalidate();
    }

    private void setChartData(PieChart chart, List<Integer> yData) {
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < yData.size(); ++i) {
            yVals.add(new Entry(yData.get(i), i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < xData.length; ++i) {
            xVals.add(xData[i]);
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(0xFFDD2C00);
        colors.add(0xFFFF6D00);
        colors.add(0xFFFFAB00);
        colors.add(0xFFAEEA00);
        colors.add(0xFF64DD17);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }

    // Ao clicar nos botoes '<' ou '>'
    public void onPreviousOrNextDayClicked(View view) {
        int oneOrMinusOne = 0;

        switch (view.getId()) {
            case R.id.previousDay:
                oneOrMinusOne = -1;
                break;

            case R.id.nextDay:
                oneOrMinusOne = 1;
                break;
        }

        switch (buttonIdChecked) {
            case R.id.dayBtn:
                calendar.add(Calendar.DAY_OF_MONTH, oneOrMinusOne);
                break;

            case R.id.weekBtn:
                calendar.add(Calendar.WEEK_OF_YEAR, oneOrMinusOne);
                break;

            case R.id.monthBtn:
                calendar.add(Calendar.MONTH, oneOrMinusOne);
                break;
        }

        updateView();
    }

    // Ao clicar nos botoes 'Dia', 'Semana' ou 'Mes'
    public void onDayWeekOrMonthButtonClicked(View view) {
        buttonIdChecked = view.getId();
        updateView();
    }

    private void updateView() {
        SimpleDateFormat sdf;
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        dayBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        weekBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        monthBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        getView().findViewById(buttonIdChecked).setBackground(getDrawable(getActivity(), R.drawable.button_active));

        switch (buttonIdChecked) {
            case R.id.dayBtn:
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateView.setText(sdf.format(calendar.getTime()));
                sdf = new SimpleDateFormat("dd-MM-yy");
                String date = sdf.format(calendar.getTime());
                updateChartReference(date + "-A", lunchChart);
                updateChartReference(date + "-J", dinnerChart);
                break;

            case R.id.weekBtn:
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                cal.setTime(calendar.getTime());
                cal.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                String str = sdf.format(cal.getTime());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                str += " - " + sdf.format(cal.getTime());
                dateView.setText(str);
                break;

            case R.id.monthBtn:
                sdf = new SimpleDateFormat("MM/yyyy");
                cal.setTime(calendar.getTime());
                cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                dateView.setText(sdf.format(cal.getTime()));
                break;
        }
    }

    // Os dados a pegar do firebase (Quantos votaram para cada numero de estrelas)
    // indice 0 -> Quantos votaram 1 estrela
    // indice 1 -> Quantos votaram 2 estrelas
    // ...
    // indice 4 -> Quantos votaram 5 estrelas
    // Use os campos radioIdChecked e  buttonIdChecked para determinar os filtros
    /*private List<Float> getYData() {
        List result = new ArrayList();
        Date from = calendar.getTime();
        Date to = calendar.getTime();

        switch (buttonIdChecked) {
            case R.id.dayBtn: // pega apenas os votos do dia
                break;

            case R.id.weekBtn: { // pega apenas os votos na semana
                Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(Calendar.MONDAY);
                cal.setTime(calendar.getTime());
                cal.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                from = cal.getTime();
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                to = cal.getTime();
                break;
            }
            case R.id.monthBtn: { // pega todos os votos do mes
                Calendar cal = Calendar.getInstance();
                cal.setTime(calendar.getTime());
                cal.set(Calendar.DAY_OF_MONTH, 1);
                from = cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                to = cal.getTime();
                break;
            }
        }

        result = Arrays.asList((float) 10, (float) 9, (float) 28, (float) 21, (float) 32); // Exemplo para testes!

        return result;
    }*/
}

package br.ruspotlight;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;


public class StatisticsFragment extends Fragment {
<<<<<<< HEAD
=======
    private static final int SELECTED_BACKGROUND_COLOR = 0xFFFF0000;
    private static final int UNSELECTED_BACKGROUND_COLOR = 0xFFFFAAAA;
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59

    private PieChart chart;
    private TextView dateView;
    private Button dayBtn;
    private Button weekBtn;
    private Button monthBtn;

    private static final String[] xData = {"★", "★★", "★★★", "★★★★", "★★★★★"};
    private Calendar calendar = Calendar.getInstance();
    private int radioIdChecked = R.id.radioTodos; // id do radio atualmente ativo
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
<<<<<<< HEAD
        chart = (PieChart) statisticsView.findViewById(R.id.chart);
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

        View.OnClickListener onRadioButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        };

=======
        chart = (PieChart)statisticsView.findViewById(R.id.chart);
        dateView = (TextView)statisticsView.findViewById(R.id.dateView);
        dayBtn = (Button)statisticsView.findViewById(R.id.dayBtn);
        weekBtn = (Button)statisticsView.findViewById(R.id.weekBtn);
        monthBtn = (Button)statisticsView.findViewById(R.id.monthBtn);

        View.OnClickListener onPreviousOrNextDayClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousOrNextDayClicked(v);
            }
        };

        View.OnClickListener onRadioButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        };

>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
        View.OnClickListener onDayWeekOrMonthButtonClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayWeekOrMonthButtonClicked(v);
            }
        };

        statisticsView.findViewById(R.id.previousDay).setOnClickListener(onPreviousOrNextDayClickedListener);

        statisticsView.findViewById(R.id.nextDay).setOnClickListener(onPreviousOrNextDayClickedListener);

        statisticsView.findViewById(R.id.radioAlmoco).setOnClickListener(onRadioButtonClickedListener);

        statisticsView.findViewById(R.id.radioJantar).setOnClickListener(onRadioButtonClickedListener);

        statisticsView.findViewById(R.id.radioTodos).setOnClickListener(onRadioButtonClickedListener);

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
<<<<<<< HEAD

        dateView.setOnClickListener(new View.OnClickListener() {

=======

        dateView.setOnClickListener(new View.OnClickListener() {

>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
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
        setupChart();
        updateView();
    }

<<<<<<< HEAD
=======

>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
    // Os dados a pegar do firebase (Quantos votaram para cada numero de estrelas)
    // indice 0 -> Quantos votaram 1 estrela
    // indice 1 -> Quantos votaram 2 estrelas
    // ...
    // indice 4 -> Quantos votaram 5 estrelas
    // Use os campos radioIdChecked e  buttonIdChecked para determinar os filtros
<<<<<<< HEAD
    private List<Float> getYData() {
=======
    private List<Integer> getYData() {
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
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


        // TODO: Aki deve ficar o codigo  que filtra por data. Use a variavel 'from' para a data minima
        // e a variavel 'to' para a data maxima


        switch (radioIdChecked) {
            case R.id.radioAlmoco: {
                // TODO: Ponha aki o codigo para filtrar apenas para almoco
                break;
            }

            case R.id.radioJantar: {
                // TODO: Ponha aki o codigo para filtrar para apenas jantar
                break;
            }
        }

<<<<<<< HEAD
        result = Arrays.asList((float) 10, (float) 20, (float) 30.6, (float) 20.1, (float) 19.3); // Exemplo para testes!
=======
        result =  Arrays.asList(5, 10, 15, 30, 40); // Exemplo para testes!
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59

        return result;
    }

    // Define as configuracoes iniciasi do grafico
    private void setupChart() {
<<<<<<< HEAD
        chart = (PieChart) getView().findViewById(R.id.chart);
=======
        chart = (PieChart)getView().findViewById(R.id.chart);
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
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

    // Atualiza os dados do gráfico
<<<<<<< HEAD
    private void updateGraphData(List<Float> yData) {
=======
    private void updateGraphData(List<Integer> yData) {
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
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
<<<<<<< HEAD
                oneOrMinusOne = -1;
=======
                oneOrMinusOne= -1;
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59
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


    // Ao clicar nos radioButton
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioAlmoco:
                if (checked)
                    radioIdChecked = R.id.radioAlmoco;
                break;
            case R.id.radioJantar:
                if (checked)
                    radioIdChecked = R.id.radioJantar;
                break;
            case R.id.radioTodos:
                if (checked)
                    radioIdChecked = R.id.radioTodos;
                break;
        }

        updateView();
    }

    // Atualiza toda a view, nao apenas o grafico
    private void updateView() {
        SimpleDateFormat sdf;
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

<<<<<<< HEAD
        dayBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        weekBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        monthBtn.setBackground(getDrawable(getActivity(), R.drawable.button));
        getView().findViewById(buttonIdChecked).setBackground(getDrawable(getActivity(), R.drawable.button_active));
=======
        dayBtn.setBackgroundColor(UNSELECTED_BACKGROUND_COLOR);
        weekBtn.setBackgroundColor(UNSELECTED_BACKGROUND_COLOR);
        monthBtn.setBackgroundColor(UNSELECTED_BACKGROUND_COLOR);
        getView().findViewById(buttonIdChecked).setBackgroundColor(SELECTED_BACKGROUND_COLOR);
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59

        switch (buttonIdChecked) {
            case R.id.dayBtn:
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateView.setText(sdf.format(calendar.getTime()));
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

        updateGraphData(getYData()); // Atualiza o grafico
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 68e1b2094faab5ea8c74071cb83a003fcf854a59

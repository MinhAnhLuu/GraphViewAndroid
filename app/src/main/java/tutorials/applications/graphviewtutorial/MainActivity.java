package tutorials.applications.graphviewtutorial;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import  java.util.Random;
import java.util.SimpleTimeZone;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    int[] temperature = new int[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //      Get date
        SimpleDateFormat date = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        int currentDate = Integer.parseInt(date.format(new Date())) - 1;
        String currentMonth = month.format(new Date());
        final int currentHour = Integer.parseInt(hour.format(new Date()));

        int y,x ;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i <= currentHour; i++){
            x = i;
            y = (int) getTemperature(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }

        Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        String url = "https://api.thingspeak.com/channels/765963/feeds.json?results=2";
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        Response response = null;

                        try {
                            response = client.newCall(request).execute();
                            return temperature[currentHour] + " celcius";
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        textView.setText(o.toString());
                    }
                }.execute();
            }

        });



//      Set tile
        graph.setTitle("Temperature on " + currentDate + "/" + currentMonth);
        graph.setTitleTextSize(70);
        graph.addSeries(series);

//      Set X/Y Axis name
        graph.getGridLabelRenderer().setVerticalAxisTitle("Temp. in celsius");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");

//      Custom View
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setScalable(true);
        viewport.setMaxX(24);
        viewport.setMinY(20);
        viewport.setMaxY(36);



    }

    protected float getTemperature(int timeInHour) {
        int max = 1;
        int min = 0;

        int[] temp = {24, 24, 25, 26, 27, 27, 28, 29, 30, 31, 31, 32, 32, 33, 32, 31, 29, 29, 28, 27, 26, 25, 24, 24};
        int random = new Random().nextInt(max + 1 - min) + min;

        temperature[timeInHour] = temp[timeInHour] + random;

        return temp[timeInHour] + random;
    }

    protected float getTemperatureApi(int timeInHour) {

        return 0;
    }
}

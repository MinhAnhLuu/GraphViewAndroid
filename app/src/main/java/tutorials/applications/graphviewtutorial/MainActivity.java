Thì là đục tường đó chịpackage tutorials.applications.graphviewtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import  java.util.Random;
import java.util.SimpleTimeZone;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    int[] temp = {24, 24, 25, 26, 27, 27, 28, 29, 30, 31, 31, 32, 32, 33, 32, 31, 29, 29, 28, 27, 26, 25, 24, 24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int y,x ;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < 24; i++){
            x = i;
            y = (int) getTemperature(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }
//      Get date
        SimpleDateFormat date = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        int currentDate = Integer.parseInt(date.format(new Date())) - 1;
        String currentMonth = month.format(new Date());

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

        int random = new Random().nextInt(max + 1 - min) + min;
        return temp[timeInHour] + random;
    }
}

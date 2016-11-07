package ndk.com.example.apple.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * http://blog.csdn.net/raptor/article/details/18898937
 */
public class HttpsActivity extends AppCompatActivity {

    public String httpUrl = "http://www.baidu.com/";
    public String httpsUrl = "https://www.baidu.com/";//证书由CA颁发
    public String httpsUrl2 = "https://kyfw.12306.cn/otn/leftTicket/init";//证书非CA颁发,需要内置证书

    TextView browserTextView;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        browserTextView = (TextView) findViewById(R.id.browser);
        mHandler = new Handler();
    }

    public void http(View view) {
        doRequest(httpUrl);
    }
    public void https(View view) {
        doRequest(httpsUrl);
    }

    private void doRequest(final String url) {
        new Thread() {
            @Override
            public void run() {
                final String response = HttpUtil.getRequest(url, null);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        browserTextView.setText(response);

                    }
                });
            }
        }.start();
    }

    public void https2(View view) {
        doRequest(httpsUrl2);
    }
}

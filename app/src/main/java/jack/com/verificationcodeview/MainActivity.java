package jack.com.verificationcodeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import jack.com.verificationcodeview.view.IdentifyingCodeView;

public class MainActivity extends AppCompatActivity {
    private IdentifyingCodeView icv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icv = (IdentifyingCodeView) findViewById(R.id.icv);

        icv.setInputCompleteListener(new IdentifyingCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Log.i("icv_input", icv.getTextContent());
            }

            @Override
            public void deleteContent() {
                Log.i("icv_delete", icv.getTextContent());
            }
        });


    }

    public void onClick(View view) {
        icv.clearAllText();
    }
}

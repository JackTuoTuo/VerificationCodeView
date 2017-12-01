package jack.com.verificationcodeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tuo.customview.VerificationCodeView;


public class MainActivity extends AppCompatActivity {
    private VerificationCodeView icv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icv = (VerificationCodeView) findViewById(R.id.icv);

        icv.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {
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

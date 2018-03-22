package converter_lab.sergey.com.converterlab.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import converter_lab.sergey.com.converterlab.R;

/**
 * Created by Moby on 14.03.2018.
 */

public class MyProgressDialog extends Dialog {

    private final String mTitle;

    public MyProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        mTitle = context.getString(R.string.msg_loading);
    }

    public MyProgressDialog(Context context, String title) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ((TextView) findViewById(R.id.tvProgressTitle_LPD)).setText(mTitle);
    }
}

package converter_lab.sergey.com.converterlab.presentation.screens.Detail;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import converter_lab.sergey.com.converterlab.R;

/**
 * Created by Moby on 19.03.2018.
 */

public class DetailShareFragment extends DialogFragment {

    private Bitmap mBitmap;
    private DetailActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (DetailActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_detail_share, null);
        mBitmap = mActivity.createBitMap();
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_bitmap_DDS);
        imageView.setImageBitmap(mBitmap);
        view.findViewById(R.id.tv_share_DDS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.sendBitMap(mBitmap);
            }
        });
        return view;
    }


}

package android.com.base.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

public interface IImage {
    IImage load(Context context,String url);

    IImage load(Activity activity, String url);

    IImage load(Fragment context, String url);

    IImage placeHolder(@IdRes int placeHolderId);

    IImage errorHolder(@IdRes int errorHolderId);

    IImage scaleType(ImageView.ScaleType scaleType);

    IImage resize(int width, int height);

    IImage asRound(int radii, int radiiWidth, @ColorInt int radiiColor);

    IImage asCircle(int radiiWidth, @ColorInt int radiiColor);

    void into(ImageView imageView);

}

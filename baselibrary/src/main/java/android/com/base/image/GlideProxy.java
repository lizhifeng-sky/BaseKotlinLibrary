package android.com.base.image;

import android.app.Activity;
import android.com.base.image.glide.GlideCircleTransform;
import android.com.base.image.glide.GlideRoundTransform;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

public class GlideProxy implements IImage {
    private RequestBuilder<Drawable> load;
    private RequestOptions options;
    private ViewGroup.LayoutParams layoutParam;

    @Override
    public IImage load(Context context, String url) {
        load = Glide.with(context).load(url);
        return this;
    }

    @Override
    public IImage load(Activity activity, String url) {
        load = Glide.with(activity).load(url);
        return this;
    }

    @Override
    public IImage load(Fragment fragment, String url) {
        load = Glide.with(fragment).load(url);
        return this;
    }

    @Override
    public IImage placeHolder(@DrawableRes int placeHolderId) {
        if (options == null) {
            options = new RequestOptions();
        }
        options = options.placeholder(placeHolderId);
        return this;
    }

    @Override
    public IImage errorHolder(@DrawableRes int errorHolderId) {
        if (options == null) {
            options = new RequestOptions();
        }
        options = options.error(errorHolderId);
        return this;
    }

    @Override
    public IImage scaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            if (options == null) {
                options = new RequestOptions();
            }
            options = options.fitCenter();
        } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            if (options == null) {
                options = new RequestOptions();
            }
            options = options.centerInside();
        } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            if (options == null) {
                options = new RequestOptions();
            }
            options = options.centerCrop();
        }
        return this;
    }

    @Override
    public IImage resize(int width, int height) {
        if (options == null) {
            options = new RequestOptions();
        }
        layoutParam = new ViewGroup.LayoutParams(width, height);
        return this;
    }

    //需要在scaleType之后
    @Override
    public IImage asRound(int radii, int radiiWidth, int radiiColor) {
        if (options == null) {
            options = new RequestOptions();
        }
        options = options.transform(new GlideRoundTransform(radii, radiiWidth, radiiColor));
        return this;
    }

    //需要在scaleType之后
    @Override
    public IImage asCircle(int radiiWidth, @ColorInt int radiiColor) {
        if (options == null) {
            options = new RequestOptions();
        }
        options = options.transform(new GlideCircleTransform(radiiWidth, radiiColor));
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        if (layoutParam != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = layoutParam.height;
            layoutParams.width = layoutParam.width;
            imageView.setLayoutParams(layoutParams);
        }
        load.apply(options).into(imageView);
    }
}

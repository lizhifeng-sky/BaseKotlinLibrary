package android.com.base.image;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;

public class FrescoProxy implements IImage{
    private File file;
    private GenericDraweeHierarchyBuilder hierarchyBuilder;
    private PipelineDraweeControllerBuilder controllerBuilder;
    private Context context;
    private ImageView.ScaleType scaleType;
    private int width;
    private int height;
    @Override
    public IImage load(Context context, String url) {
        this.context=context;
        Uri uri=Uri.parse(url);
        file=getFileFromDiskCache(context,uri);
        if (file!=null){
            uri=Uri.fromFile(file);
        }
        controllerBuilder=Fresco.newDraweeControllerBuilder();
        controllerBuilder.setUri(uri);
        return this;
    }

    @Override
    public IImage load(Activity activity, String url) {
        this.context=activity.getApplicationContext();
        Uri uri=Uri.parse(url);
        file=getFileFromDiskCache(activity.getApplicationContext(),uri);
        if (file!=null){
            uri=Uri.fromFile(file);
        }
        controllerBuilder=Fresco.newDraweeControllerBuilder();
        controllerBuilder.setUri(uri);
        return this;
    }

    @Override
    public IImage load(Fragment context, String url) {
        this.context=context.getContext();
        Uri uri=Uri.parse(url);
        file=getFileFromDiskCache(context.getContext(),uri);
        if (file!=null){
            uri=Uri.fromFile(file);
        }
        controllerBuilder=Fresco.newDraweeControllerBuilder();
        controllerBuilder.setUri(uri);
        return this;
    }

    @Override
    public IImage placeHolder(int placeHolderId) {
        if (hierarchyBuilder==null){
            hierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
        }
        hierarchyBuilder.setPlaceholderImage(placeHolderId);
        return this;
    }

    @Override
    public IImage errorHolder(int errorHolderId) {
        if (hierarchyBuilder==null){
            hierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
        }
        hierarchyBuilder.setFailureImage(errorHolderId);
        return this;
    }

    @Override
    public IImage scaleType(ImageView.ScaleType scaleType) {
        this.scaleType=scaleType;
        return this;
    }

    @Override
    public IImage resize(int width, int height) {
        this.height=height;
        this.width=width;
        return this;
    }

    @Override
    public IImage asRound(int radii, int radiiWidth, int radiiColor) {
        if (hierarchyBuilder==null){
            hierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
        }
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setBorderWidth(radiiWidth);
        roundingParams.setBorderColor(radiiColor);
        roundingParams.setCornersRadii(radii,radii,radii,radii);
        hierarchyBuilder.setRoundingParams(roundingParams);
        return this;
    }

    @Override
    public IImage asCircle(int radiiWidth, int radiiColor) {
        if (hierarchyBuilder==null){
            hierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
        }
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setRoundAsCircle(true);
        hierarchyBuilder.setRoundingParams(roundingParams);
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        final SimpleDraweeView simpleDraweeView= (SimpleDraweeView) imageView;
        if (controllerBuilder!=null) {
            controllerBuilder.setOldController(simpleDraweeView.getController());
            simpleDraweeView.setController(controllerBuilder.build());
        }
        if (hierarchyBuilder!=null) {
            ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
            layoutParams.height=height;
            layoutParams.width=width;
            simpleDraweeView.setLayoutParams(layoutParams);
            if (scaleType!=null){
                hierarchyBuilder.setActualImageScaleType(scaleTypeAdapter(scaleType));
            }
            simpleDraweeView.setHierarchy(hierarchyBuilder.build());
        }
    }

    private ScalingUtils.ScaleType scaleTypeAdapter(ImageView.ScaleType scaleType) {
        ScalingUtils.ScaleType scaleType1= ScalingUtils.ScaleType.CENTER_CROP;
        switch (scaleType){
            case CENTER_CROP:
                scaleType1= ScalingUtils.ScaleType.CENTER_CROP;
                break;
            case CENTER_INSIDE:
                scaleType1= ScalingUtils.ScaleType.CENTER_INSIDE;
                break;
            case FIT_CENTER:
                scaleType1= ScalingUtils.ScaleType.FIT_CENTER;
                break;
        }
        return scaleType1;
    }

    //own

    /**
     * 本地缓存文件
     */
    public static File getFileFromDiskCache(Context context, Uri uri) {
        try {
            if (!isCached(context, uri))
                return null;
            ImageRequest imageRequest = ImageRequest.fromUri(uri);
            CacheKey cacheKey = null;
            if (imageRequest != null) {
                cacheKey = DefaultCacheKeyFactory.getInstance()
                        .getEncodedCacheKey(imageRequest, context);
            }
            BinaryResource resource = ImagePipelineFactory.getInstance()
                    .getMainFileCache().getResource(cacheKey);
            return ((FileBinaryResource) resource).getFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片是否已经存在了
     */
    public static boolean isCached(Context context, Uri uri) {
        try {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<Boolean> dataSource = imagePipeline.isInDiskCache(uri);
            if (dataSource == null) {
                return false;
            }
            ImageRequest imageRequest = ImageRequest.fromUri(uri);
            CacheKey cacheKey = null;
            if (imageRequest != null) {
                cacheKey = DefaultCacheKeyFactory.getInstance()
                        .getEncodedCacheKey(imageRequest, context);
            }
            BinaryResource resource = ImagePipelineFactory.getInstance()
                    .getMainFileCache().getResource(cacheKey);
            return resource != null && dataSource.getResult() != null && dataSource.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package carService.app.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.FileProvider
import carService.app.BuildConfig
import carService.app.R
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.google.android.material.imageview.ShapeableImageView
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import java.io.File

@KoinApiExtension
class AppImageView : KoinComponent {

    fun getTmpFileUri(ctx: Context): Uri {
        val cacheDir: File = ctx.filesDir
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(ctx, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    fun useCoilToLoadPhoto(container: ShapeableImageView, imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(container.context)
            .data(imageLink)
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.default_photo)
                }
            )
            .build()

        ImageLoader(container.context).execute(request)
    }

    fun useLoadPhotoToProfile(
        container: ShapeableImageView,
        imageView: ImageView,
        imageLink: String
    ) {
        val request = LoadRequest.Builder(container.context)
            .data(imageLink)
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.default_photo)
                }
            )
            .transformations(
                CircleCropTransformation()
            )
            .size(140, 140)
            .build()

        ImageLoader(container.context).execute(request)
    }
}
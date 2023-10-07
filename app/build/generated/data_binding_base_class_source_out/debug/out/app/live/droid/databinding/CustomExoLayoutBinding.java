// Generated by view binder compiler. Do not edit!
package app.live.droid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import app.live.droid.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomExoLayoutBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button exoPlay;

  @NonNull
  public final Button exoPlayPause;

  private CustomExoLayoutBinding(@NonNull FrameLayout rootView, @NonNull Button exoPlay,
      @NonNull Button exoPlayPause) {
    this.rootView = rootView;
    this.exoPlay = exoPlay;
    this.exoPlayPause = exoPlayPause;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomExoLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomExoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_exo_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomExoLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.exo_play;
      Button exoPlay = ViewBindings.findChildViewById(rootView, id);
      if (exoPlay == null) {
        break missingId;
      }

      id = R.id.exo_play_pause;
      Button exoPlayPause = ViewBindings.findChildViewById(rootView, id);
      if (exoPlayPause == null) {
        break missingId;
      }

      return new CustomExoLayoutBinding((FrameLayout) rootView, exoPlay, exoPlayPause);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

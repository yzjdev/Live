// Generated by view binder compiler. Do not edit!
package app.live.droid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import app.live.droid.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class FragmentFocusBinding implements ViewBinding {
  @NonNull
  private final RecyclerView rootView;

  @NonNull
  public final RecyclerView rv;

  private FragmentFocusBinding(@NonNull RecyclerView rootView, @NonNull RecyclerView rv) {
    this.rootView = rootView;
    this.rv = rv;
  }

  @Override
  @NonNull
  public RecyclerView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFocusBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFocusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_focus, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFocusBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    RecyclerView rv = (RecyclerView) rootView;

    return new FragmentFocusBinding((RecyclerView) rootView, rv);
  }
}
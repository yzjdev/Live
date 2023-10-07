package app.live.droid.databinding;
import app.live.droid.R;
import app.live.droid.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemLiveBindingImpl extends ItemLiveBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.coverContainer, 7);
        sViewsWithIds.put(R.id.peopleIcon, 8);
    }
    // views
    @NonNull
    private final androidx.cardview.widget.CardView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemLiveBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private ItemLiveBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[5]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[4]
            );
        this.avatar.setTag(null);
        this.game.setTag(null);
        this.mboundView0 = (androidx.cardview.widget.CardView) bindings[0];
        this.mboundView0.setTag(null);
        this.num.setTag(null);
        this.preview.setTag(null);
        this.room.setTag(null);
        this.title.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.m == variableId) {
            setM((app.live.droid.logic.model.LiveBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setM(@Nullable app.live.droid.logic.model.LiveBean M) {
        this.mM = M;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.m);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String mCoverUrl = null;
        java.lang.String mNum = null;
        java.lang.String mAvatar = null;
        java.lang.String mGameName = null;
        java.lang.String mName = null;
        java.lang.String mTitle = null;
        app.live.droid.logic.model.LiveBean m = mM;

        if ((dirtyFlags & 0x3L) != 0) {



                if (m != null) {
                    // read m.coverUrl
                    mCoverUrl = m.getCoverUrl();
                    // read m.num
                    mNum = m.getNum();
                    // read m.avatar
                    mAvatar = m.getAvatar();
                    // read m.gameName
                    mGameName = m.getGameName();
                    // read m.name
                    mName = m.getName();
                    // read m.title
                    mTitle = m.getTitle();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            app.live.droid.extensions.GlideKt.setImgUrl(this.avatar, mAvatar);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.game, mGameName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.num, mNum);
            app.live.droid.extensions.GlideKt.setImgUrl(this.preview, mCoverUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.room, mName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.title, mTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): m
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}
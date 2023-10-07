package app.live.droid.ui.player;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00140\u0013\u00f8\u0001\u0000\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lapp/live/droid/ui/player/PlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "parser", "Lapp/live/droid/parser/LiveParser;", "(Lapp/live/droid/parser/LiveParser;)V", "getParser", "()Lapp/live/droid/parser/LiveParser;", "roomLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "getRoomLiveData", "()Landroidx/lifecycle/MutableLiveData;", "stream", "Lapp/live/droid/logic/model/StreamBean;", "getStream", "()Lapp/live/droid/logic/model/StreamBean;", "setStream", "(Lapp/live/droid/logic/model/StreamBean;)V", "streamLiveData", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "getStreamLiveData", "()Landroidx/lifecycle/LiveData;", "", "room", "app_debug"})
public final class PlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final app.live.droid.parser.LiveParser parser = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> roomLiveData = null;
    @org.jetbrains.annotations.Nullable
    private app.live.droid.logic.model.StreamBean stream;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<kotlin.Result<app.live.droid.logic.model.StreamBean>> streamLiveData = null;
    
    public PlayerViewModel(@org.jetbrains.annotations.NotNull
    app.live.droid.parser.LiveParser parser) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final app.live.droid.parser.LiveParser getParser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getRoomLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final app.live.droid.logic.model.StreamBean getStream() {
        return null;
    }
    
    public final void setStream(@org.jetbrains.annotations.Nullable
    app.live.droid.logic.model.StreamBean p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<kotlin.Result<app.live.droid.logic.model.StreamBean>> getStreamLiveData() {
        return null;
    }
    
    public final void getStream(@org.jetbrains.annotations.NotNull
    java.lang.String room) {
    }
}
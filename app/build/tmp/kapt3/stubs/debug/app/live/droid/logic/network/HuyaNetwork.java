package app.live.droid.logic.network;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u00042\u0006\u0010\b\u001a\u00020\t\u00f8\u0001\u0000J\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00050\u00042\u0006\u0010\f\u001a\u00020\r\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lapp/live/droid/logic/network/HuyaNetwork;", "", "()V", "getLives", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "", "Lapp/live/droid/logic/model/LiveBean;", "page", "", "getStream", "Lapp/live/droid/logic/model/StreamBean;", "room", "", "app_debug"})
public final class HuyaNetwork {
    @org.jetbrains.annotations.NotNull
    public static final app.live.droid.logic.network.HuyaNetwork INSTANCE = null;
    
    private HuyaNetwork() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<kotlin.Result<app.live.droid.logic.model.StreamBean>> getStream(@org.jetbrains.annotations.NotNull
    java.lang.String room) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<kotlin.Result<java.util.List<app.live.droid.logic.model.LiveBean>>> getLives(int page) {
        return null;
    }
}
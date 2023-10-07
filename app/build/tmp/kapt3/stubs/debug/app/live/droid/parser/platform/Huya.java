package app.live.droid.parser.platform;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u00042\u0006\u0010\b\u001a\u00020\tH\u0016\u00f8\u0001\u0000J\u001f\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00050\u00042\u0006\u0010\f\u001a\u00020\rH\u0016\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lapp/live/droid/parser/platform/Huya;", "Lapp/live/droid/parser/LiveParser;", "()V", "getLives", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "", "Lapp/live/droid/logic/model/LiveBean;", "page", "", "getStream", "Lapp/live/droid/logic/model/StreamBean;", "room", "", "app_debug"})
public final class Huya extends app.live.droid.parser.LiveParser {
    
    public Huya() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public androidx.lifecycle.LiveData<kotlin.Result<java.util.List<app.live.droid.logic.model.LiveBean>>> getLives(int page) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public androidx.lifecycle.LiveData<kotlin.Result<app.live.droid.logic.model.StreamBean>> getStream(@org.jetbrains.annotations.NotNull
    java.lang.String room) {
        return null;
    }
}
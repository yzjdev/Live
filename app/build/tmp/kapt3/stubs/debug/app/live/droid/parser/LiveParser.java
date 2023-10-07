package app.live.droid.parser;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J%\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e0\r2\u0006\u0010\u0011\u001a\u00020\u0012H&\u00f8\u0001\u0000J\u001f\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000e0\r2\u0006\u0010\u0015\u001a\u00020\u0004H&\u00f8\u0001\u0000R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lapp/live/droid/parser/LiveParser;", "Ljava/io/Serializable;", "()V", "className", "", "getClassName", "()Ljava/lang/String;", "setClassName", "(Ljava/lang/String;)V", "name", "getName", "setName", "getLives", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "", "Lapp/live/droid/logic/model/LiveBean;", "page", "", "getStream", "Lapp/live/droid/logic/model/StreamBean;", "room", "app_debug"})
public abstract class LiveParser implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    private java.lang.String name;
    @org.jetbrains.annotations.Nullable
    private java.lang.String className;
    
    public LiveParser() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getClassName() {
        return null;
    }
    
    public final void setClassName(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<kotlin.Result<java.util.List<app.live.droid.logic.model.LiveBean>>> getLives(int page);
    
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<kotlin.Result<app.live.droid.logic.model.StreamBean>> getStream(@org.jetbrains.annotations.NotNull
    java.lang.String room);
}
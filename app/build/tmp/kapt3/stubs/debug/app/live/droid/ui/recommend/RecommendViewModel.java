package app.live.droid.ui.recommend;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0014R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR&\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000f0\u000e0\r\u00f8\u0001\u0000\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lapp/live/droid/ui/recommend/RecommendViewModel;", "Landroidx/lifecycle/ViewModel;", "parser", "Lapp/live/droid/parser/LiveParser;", "(Lapp/live/droid/parser/LiveParser;)V", "liveList", "Ljava/util/ArrayList;", "Lapp/live/droid/logic/model/LiveBean;", "getLiveList", "()Ljava/util/ArrayList;", "setLiveList", "(Ljava/util/ArrayList;)V", "liveLiveData", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "", "getLiveLiveData", "()Landroidx/lifecycle/LiveData;", "pageLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "getLives", "", "pageNo", "app_debug"})
public final class RecommendViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> pageLiveData = null;
    @org.jetbrains.annotations.NotNull
    private java.util.ArrayList<app.live.droid.logic.model.LiveBean> liveList;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<kotlin.Result<java.util.List<app.live.droid.logic.model.LiveBean>>> liveLiveData = null;
    
    public RecommendViewModel(@org.jetbrains.annotations.NotNull
    app.live.droid.parser.LiveParser parser) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<app.live.droid.logic.model.LiveBean> getLiveList() {
        return null;
    }
    
    public final void setLiveList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<app.live.droid.logic.model.LiveBean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<kotlin.Result<java.util.List<app.live.droid.logic.model.LiveBean>>> getLiveLiveData() {
        return null;
    }
    
    public final void getLives(int pageNo) {
    }
}
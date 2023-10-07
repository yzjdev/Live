package app.live.droid.logic.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u0010H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2 = {"Lapp/live/droid/logic/model/CategoryChild;", "Lcom/drake/brv/item/ItemExpand;", "itemExpand", "", "itemGroupPosition", "", "(ZI)V", "getItemExpand", "()Z", "setItemExpand", "(Z)V", "getItemGroupPosition", "()I", "setItemGroupPosition", "(I)V", "getItemSublist", "", "", "app_debug"})
public class CategoryChild implements com.drake.brv.item.ItemExpand {
    private boolean itemExpand;
    private int itemGroupPosition;
    
    public CategoryChild(boolean itemExpand, int itemGroupPosition) {
        super();
    }
    
    @java.lang.Override
    public boolean getItemExpand() {
        return false;
    }
    
    @java.lang.Override
    public void setItemExpand(boolean p0) {
    }
    
    @java.lang.Override
    public int getItemGroupPosition() {
        return 0;
    }
    
    @java.lang.Override
    public void setItemGroupPosition(int p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.util.List<java.lang.Object> getItemSublist() {
        return null;
    }
    
    public CategoryChild() {
        super();
    }
}
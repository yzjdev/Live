package app.live.droid.utils;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0019\u0010\t\u001a\n \n*\u0004\u0018\u00010\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lapp/live/droid/utils/CrashUtils;", "Ljava/lang/Thread$UncaughtExceptionHandler;", "()V", "carshInfo", "", "getCarshInfo", "()Ljava/lang/String;", "setCarshInfo", "(Ljava/lang/String;)V", "defaultHandler", "kotlin.jvm.PlatformType", "getDefaultHandler", "()Ljava/lang/Thread$UncaughtExceptionHandler;", "getCrashInfo", "e", "", "handleException", "", "init", "", "uncaughtException", "t", "Ljava/lang/Thread;", "CrashActivity", "app_debug"})
public final class CrashUtils implements java.lang.Thread.UncaughtExceptionHandler {
    private static final java.lang.Thread.UncaughtExceptionHandler defaultHandler = null;
    @org.jetbrains.annotations.NotNull
    private static java.lang.String carshInfo = "";
    @org.jetbrains.annotations.NotNull
    public static final app.live.droid.utils.CrashUtils INSTANCE = null;
    
    private CrashUtils() {
        super();
    }
    
    public final java.lang.Thread.UncaughtExceptionHandler getDefaultHandler() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCarshInfo() {
        return null;
    }
    
    public final void setCarshInfo(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final void init() {
    }
    
    @java.lang.Override
    public void uncaughtException(@org.jetbrains.annotations.NotNull
    java.lang.Thread t, @org.jetbrains.annotations.NotNull
    java.lang.Throwable e) {
    }
    
    public final boolean handleException(@org.jetbrains.annotations.Nullable
    java.lang.Throwable e) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCrashInfo(@org.jetbrains.annotations.NotNull
    java.lang.Throwable e) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lapp/live/droid/utils/CrashUtils$CrashActivity;", "Lapp/live/droid/base/BaseActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
    public static final class CrashActivity extends app.live.droid.base.BaseActivity {
        @org.jetbrains.annotations.NotNull
        public static final app.live.droid.utils.CrashUtils.CrashActivity.Companion Companion = null;
        
        public CrashActivity() {
            super();
        }
        
        @java.lang.Override
        protected void onCreate(@org.jetbrains.annotations.Nullable
        android.os.Bundle savedInstanceState) {
        }
        
        @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lapp/live/droid/utils/CrashUtils$CrashActivity$Companion;", "", "()V", "actionStart", "", "context", "Landroid/content/Context;", "info", "", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            public final void actionStart(@org.jetbrains.annotations.NotNull
            android.content.Context context, @org.jetbrains.annotations.NotNull
            java.lang.String info) {
            }
        }
    }
}
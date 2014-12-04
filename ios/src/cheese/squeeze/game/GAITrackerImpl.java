package cheese.squeeze.game;

import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.bindings.googleanalytics.GAITracker;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.Property;
import org.robovm.rt.bro.annotation.Library;
 
@Library(Library.INTERNAL)
@NativeClass()
public class GAITrackerImpl extends NSObject implements GAITracker {
    static {
        ObjCRuntime.bind();
    }
 
    @Property
    public native String getName ();
 
    /** Set a tracking parameter.
     *
     * @param parameterName The parameter name.
     *
     * @param value The value to set for the parameter. If this is {@code null}, the value for the parameter will be cleared. */
    @Method(selector = "set:value:")
    public native void set (String parameterName, String value);
 
    /** Get a tracking parameter.
     *
     * @param parameterName The parameter name.
     *
     * @returns The parameter value, or {@code null} if no value for the given parameter is set. */
    @Method(selector = "get:")
    public native String get (String parameterName);
 
    /** Queue tracking information with the given parameter values.
     *
     * @param parameters A map from parameter names to parameter values which will be set just for this piece of tracking
     *           information, or {@code null} for none. */
    @Method(selector = "send:")
    public native void send (NSDictionary<NSString, NSString> parameters);
}
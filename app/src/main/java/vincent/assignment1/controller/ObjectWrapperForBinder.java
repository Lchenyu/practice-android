package vincent.assignment1.controller;

import android.os.Binder;

public class ObjectWrapperForBinder extends Binder {

    private Object mData;

    public ObjectWrapperForBinder(Object data){
        this.mData = data;
    }

    public Object getData(){
        return this.mData;
    }

}

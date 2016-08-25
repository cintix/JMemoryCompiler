package dk.cintix.memorycompiler.demo;

/*
 */

/**
 *
 * @author migo
 */
public abstract class CustomScriptedPageMaybeJSP {
    
    protected final Object request;
    protected final Object response;
    protected final Object session;

    public CustomScriptedPageMaybeJSP(Object request, Object response, Object session) {
        this.request = request;
        this.response = response;
        this.session = session;
    }
    
    
    public abstract void render();
    
    
}

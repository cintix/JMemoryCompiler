package dk.cintix.memorycompiler.demo;


import dk.cintix.memorycompiler.compilers.MemoryCompiler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 */
/**
 *
 * @author migo
 */
public class DemoMemoryCompiler {

    public DemoMemoryCompiler() {
    }

    public void test() {

        /**
         * LEST BUILT A JAVA FILE FROM A STRING * When are going to pretend that
         * we parsed and build it from something.
         *
         * Maybe parsed a JSP file and made String from html and read the code
         * I'm going to make the file extend CustomScriptedPageMaybeJSP so i
         * know how to call it.
         *
         */
        String className = "MyCustomParsePage";

        StringBuilder javaCode = new StringBuilder();
        javaCode.append("/**\n");
        javaCode.append(" * @auther DemoMemoryCompiler\n");
        javaCode.append("*/\n");
        javaCode.append("\n");
        javaCode.append("\n");
        javaCode.append("import dk.cintix.memorycompiler.demo.CustomScriptedPageMaybeJSP;\n");
        javaCode.append("\n");
        javaCode.append("public class ").append(className).append(" extends CustomScriptedPageMaybeJSP {\n");
        javaCode.append("\n");
        javaCode.append("    public ").append(className).append("(Object request, Object response, Object session) {\n");
        javaCode.append("       super(request, response, session);\n");
        javaCode.append("    }\n");
        javaCode.append("\n");
        javaCode.append("    @Override\n");
        javaCode.append("    public void render() {\n");

        /**
         * The custom code implementation *
         */
        // Yeah I know not very creative
        javaCode.append("       System.out.println(\"Hello my name is ").append(className).append(" and my session is \" + session);\n");

        /**
         * End *
         */
        javaCode.append("    } \n");
        javaCode.append("\n");
        javaCode.append("}");

        
        
        // Okay lets compile and run!
        
        if (MemoryCompiler.compileClass("./", className, javaCode.toString())) {
            System.out.println("YEAH compiled....");
            
            // Lets get ready to run it
            Class[] constructorArguments = {Object.class, Object.class, Object.class};
            Object[] constructorValues = {"RequestInstance", "ResponseInstance", "SessionInstance"};
            try {
                CustomScriptedPageMaybeJSP pageMaybeJSP = MemoryCompiler.getInstance("./", className, constructorArguments, constructorValues);
                pageMaybeJSP.render();
            } catch (Exception ex) {
                Logger.getLogger(DemoMemoryCompiler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.err.println("Guess I made a mistake... sorry");
        }

    }

    public static void main(String[] args) {
        new DemoMemoryCompiler().test();
    }
}

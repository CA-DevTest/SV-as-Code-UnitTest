class Main
{
    static class Main2
    {
        public static void main ( String [ ] args )
        {
            System . out . println ( "Main2:  YES!!!!!!!!!!!" ) ;
        }
    }

    public static void main ( String [ ] args ) throws Exception
    {
        System . out . println ( Main2 . class . getCanonicalName ( ) ) ;
        System . out . println ( Main2 . class . getName ( ) ) ;
        startSecondJVM(Main2.class, true);
        startSecondJVM(Main3.class, true);
        System.out.println("Main");
    }
    
    public static void startSecondJVM(Class<? extends Object> clazz, boolean redirectStream) throws Exception {
        System.out.println(clazz.getCanonicalName());
        String separator = System.getProperty("file.separator");
        String classpath = System.getProperty("java.class.path");
        String path = System.getProperty("java.home")
                + separator + "bin" + separator + "java";
        ProcessBuilder processBuilder = 
                new ProcessBuilder(path, "-cp", 
                classpath, 
                clazz.getCanonicalName());
        processBuilder.redirectErrorStream(redirectStream);
        Process process = processBuilder.start();
        process.waitFor();
        System.out.println("Fin");
      }
}

class Main3
{
    public static void main ( String [ ] args )
    {
        System . out . println ( "Main3 : YES!!!!!!!!!!!" ) ;
    }
    
 
     
    
}
package np.engine.core;
public final class OsUtils
{
   private static String OS = null;
   
   public static String getOsName()
   {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }
   
   public static boolean IsWindows()
   {
      return getOsName().startsWith("Windows");
   }

   public static boolean IsLinux()  {
	   return getOsName().startsWith("Linux");
   }
   
   public static boolean IsMacOS()  {
	   return getOsName().startsWith("Mac");
   }
}
package ac.at.tuwien.aic13.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 * This Utility Class provides static methods for common operations like checks for empty strings.
 */
public abstract class Utils {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	/** The Constant HOUR_MS. */
	public static final Long HOUR_MS = 1000L*60L*60L;
	
	/** The Constant MINUTE_MS. */
	public static final Long MINUTE_MS = 1000L*60L;
	/**
	 * Returns null if the given value is null, empty, or only contains whitespaces.<br />
	 * Otherwise this method returns the given value.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String emptyNull(String value){
		if(value == null) return null;
		if("".equals(value.trim())) return null;
		return value;
	}
	
	/**
	 * Gets the time as a DateObject with todays Date and the specified hours and minutes.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 * @return the date today time
	 */
	public static Date getDateTodayTime(int hour, int minute) {
		return new Date(Utils.getDateStartOfDay(new Date()).getTime() + Utils.HOUR_MS*hour + Utils.MINUTE_MS*minute);
	}
	
	/**
	 * THis method adds a specific amount of minutes to the given date.
	 * @param time - Date to add the minutes. If null 'now' will be taken.
	 * @param minutesToAdd - how many minutes to add to the given time (can also be negative.).
	 * @return a date object.
	 */
	public static Date addMinutesToDate(Date time, Long minutesToAdd){
		if(time == null) time = new Date();
		if(minutesToAdd == null) return time;
		return new Date(time.getTime()+Utils.MINUTE_MS*minutesToAdd);
	}
	

	/**
	 * This mthod returns the difference in minutes between the given date.<br />
	 * e.g. from=19:30 and to=19:32 then this method returns  2<br />
	 * e.g. from=19:32 and to=19:30 then this method returns -2<br />
	 * @param from
	 * @param to
	 * @return
	 */
	public static long getDifferenceMinutes(Date from, Date to){
		if(from == null)from = new Date();
		if(to == null)to=new Date();
		long diff = to.getTime() - from.getTime();
		return diff/MINUTE_MS;
	}
	
	/**
	 * Fill string with desired values.
	 *
	 * @param satz the satz
	 * @param laenge the laenge
	 * @param zeichen the zeichen
	 * @param linksbuendig the linksbuendig
	 * @return the string
	 */
	public static String fill(String satz, int laenge, String zeichen, boolean linksbuendig){
		String[] lines = satz.split("\n");
		while(((satz.length() - lines.length + 1) % laenge) != laenge){
			if (linksbuendig){
				satz = satz + zeichen;
			} else {
				satz = zeichen + satz;
			}
		}
		return satz;
	}

	/**
	 * Check Versicherungsnummer.
	 *
	 * @param num the num
	 * @return true, if successful
	 */
	public static boolean checkVersicherungsNummer(String num){
		try {
			int checksum = (
					Integer.valueOf(num.substring(0,1)) * 3 + 
					Integer.valueOf(num.substring(1,2)) * 7 + 
					Integer.valueOf(num.substring(2,3)) * 9 + 
					Integer.valueOf(num.substring(4,5)) * 5 + 
					Integer.valueOf(num.substring(5,6)) * 8 + 
					Integer.valueOf(num.substring(6,7)) * 4 + 
					Integer.valueOf(num.substring(7,8)) * 2 + 
					Integer.valueOf(num.substring(8,9)) * 1 + 
					Integer.valueOf(num.substring(9)) * 6 ) % 11;
			return checksum == Integer.valueOf(num.substring(3,4)).intValue();

		} catch(Exception e) {
			return false;
			// Es ist ein nicht numerisches Zeichen in der Versicherungsnummer
			// Es handelt sich wahrscheinlich um einen Patienten ohne österreichischer Versicherungsnummer
		}

	}

	/**
	 * Check vertrags partner nummer.
	 *
	 * @param num the num
	 * @return true, if successful
	 */
	public static boolean checkVertragsPartnerNummer(String num){
		try {
			int checksum = (
					Integer.valueOf(num.substring(0,1)) * 3 + 
					Integer.valueOf(num.substring(1,2)) * 7 + 
					Integer.valueOf(num.substring(2,3)) * 5 + 
					Integer.valueOf(num.substring(3,4)) * 1 + 
					Integer.valueOf(num.substring(4,5)) * 6) % 11;
			return checksum == Integer.valueOf(num.substring(5,6)).intValue();
		} catch(NumberFormatException e) {
			// Es ist ein nicht numerisches Zeichen in der VertragspartnerNummer
			// Die Vertragspartnernummer ist ungültig
			return false;
		} catch (StringIndexOutOfBoundsException e) {
			// Die Vertragspartnernummer ist nicht 6-Stellig
			// Die Vertragspartnernummer ist ungültig
			return false;
		}
	}
	
	/**
	 * Gets the search phrase.
	 *
	 * @param phrase the phrase
	 * @return the search phrase
	 */
	public static String getSearchPhrase(String phrase){
		if(emptyNull(phrase) == null) phrase = "";
		
		phrase = phrase.replaceAll("\\(", "");
		phrase = phrase.replaceAll("\\)", "");
		phrase +="%";
		return phrase;
	}
	
//	/**
//	 * Copys a File from srFile to dtFile
//	 * srFile and dtFile are directorys including filenames
//	 */
//	private static void copyfile(String srFile, String dtFile){
//	File f1 = new File(srFile);
//	File f2 = new File(dtFile);
//	try{
//		InputStream in = new FileInputStream(f1);
//		OutputStream out = new FileOutputStream(f2);
//		byte[] buf = new byte[1024];
//		int len;
//		while ((len = in.read(buf)) > 0){
//			out.write(buf, 0, len);
//		}
//		in.close();
//		out.close();
//		System.out.println("File copied.");
//	}
//	catch(FileNotFoundException ex){
//		System.out.println(ex.getMessage() + " in the specified directory.");
//		System.exit(0);
//	}
//	catch(IOException e){
//		System.out.println(e.getMessage());  
//	}
//	f1.delete();
//	f1 = new File("import.log");
//}
	
	/**
 * Lazy load.
 *
 * @param <X> the generic type
 * @param dto the dto
 * @param level the level
 * @return the x
 */
	@SuppressWarnings("unchecked")
	public static <X extends DTO> X lazyLoad(X dto, int level){
		if(level == 0 || dto == null) return dto;
		Method[] methods = dto.getClass().getMethods();
		final Object[] emptyArgs = new Object[]{};
		for(Method m : methods){
			if(DTO.class.isAssignableFrom(m.getReturnType())){
				if(m.getParameterTypes().length == 0){
					try {
						DTO childDto = (DTO)m.invoke(dto, emptyArgs);
						lazyLoad(childDto, level-1);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(Collection.class.isAssignableFrom(m.getReturnType())){

				try {
					@SuppressWarnings("rawtypes")
					Collection collection = (Collection) m.invoke(dto, emptyArgs);
					if(collection != null){
						collection.size();
						if(!collection.isEmpty() && level > 1){
							//If there are elements in the list and the level is greater than 0 iterate the Collection and lazy-Load
							Iterator<Object> it = collection.iterator();
							if(it.hasNext()){
								Object elem = it.next();
								if(DTO.class.isAssignableFrom(elem.getClass())){
									// LazyLoad the list.
									lazyLoad(collection, level-1);
								}
							}
							
						}
					}
					
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		return dto;
	}
	
	/**
	 * Lazy load.
	 *
	 * @param <X> the generic type
	 * @param dtoList the dto list
	 * @param level the level
	 */
	public static <X extends DTO> void lazyLoad(Iterable<X> dtoList, int level){
		if(dtoList == null || level == 0 ) return;
		for(X dto : dtoList){
			if(dto == null) continue;
			lazyLoad(dto, level);
		}
	}
	
	
	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static String getDate(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	
	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date getDate(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
//		SimpleDateFormat df = new SimpleDateFormat("E MMM d yyyy HH:mm:ss", Locale.ENGLISH); 	
		try {
			return new Date(df.parse(date).getTime());
		} catch (ParseException e) {
			df = new SimpleDateFormat("E MMM d yyyy HH:mm:ss", Locale.ENGLISH);
			try {
				return new Date(df.parse(date).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
				logger.error("Fehler beim Parsen des Datum-Strings: "+date, e);
				return null;
			}
		}
	}
	
	/**
	 * This method returns the date with the first possible time for the given Date-Objects day.
	 *
	 * @param date the date
	 * @return the date start of day
	 */
	public static Date getDateStartOfDay(Date date){
		 if (date == null) {
	            return null;
	        }
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        return c.getTime();
	}
	
	/**
	 * This method returns the date with the last possible time for the given Date-Objects day.
	 *
	 * @param date the date
	 * @return the date end of day
	 */
	public static Date getDateEndOfDay(Date date){
		if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
	}
	
	/**
	 * This method makes the first character of the given String Upper Case.
	 * @param word
	 * @return
	 */
	public static String getUpperCaseWord(String word){
		if(word == null || word.length() == 0) return word;
		String x = String.valueOf(word.charAt(0));
		return x.toUpperCase()+word.substring(1);
	}
	
	public static String newLineToBreak(String msg){
		return msg.replaceAll("\\n", "<br />");
	}
	
	public static int sizeOf(Collection<Object> c){
		return c.size();
	}
	
}

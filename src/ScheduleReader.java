import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleReader implements IFileReader{
	
	File file;
	ScheduleData sd;
	String filename;
	
	public ScheduleReader(ScheduleData sd, String filename) {
		this.sd = sd;
		this.filename = filename;
	}

	@Override
	public File getFile() {
		return new File(filename);
	}
	
	

	@Override
	public void ParseFile() throws IOException {
			String line = "";
			file = getFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
				int linenumber =0;
				while((line = br.readLine())!=null){
					linenumber++; 
					 //Get all tokens available in line
	                String[] tokens = line.split(",");
	                if(linenumber ==1) {
	                	sd.setDates(StringToDatenotime(tokens[1]),StringToDatenotime(tokens[7]));
	                	//schedule.setEnd(tokens[7]);
	                }else {
	                	Employee e = new Employee();
	                	e.setName(tokens[0]+tokens[1]);
	                	for(int i=2; i<tokens.length;i++) {
	                		//String strshifts = "";
	                		String[] shifttokens = tokens[i].split(" ");
	                		if(shifttokens.length!=1) {
	                			String[] starttime = shifttokens[4].split("-");
	                			Date start = StringToDate(shifttokens[2]+" "+shifttokens[3]+" "+starttime[0]);
	                			Date end = StringToDate(starttime[1]+" "+shifttokens[5]+" "+shifttokens[6]);
	                			e.addShift(start, end);
	                		}
	                	}
	                	sd.addEmployee(e);
	                }
				}
	}
	
	public Date StringToDate(String str) {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		Date date;
		try {
			date = format.parse(str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Date StringToDatenotime(String str) {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		try {
			date = format.parse(str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileReader {

	
	
	File getFile();
	void ParseFile() throws FileNotFoundException, IOException;
	
}

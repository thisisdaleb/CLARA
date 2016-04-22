import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class TerrainCheck {

	public static void main(String[] args) {
		try {
			checkTerrain();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkTerrain() throws IOException{
		 DataOutputStream os = new DataOutputStream(new FileOutputStream("C:\\binout.raw"));
		 int z=(2048*2048/16000);
		 for(int i = 0; i<(2048*2048);i++){
		 	//os.writeInt((short)(i/z));
		 	System.out.println(i/z);
		 }
		 os.close();
	}
	
}
